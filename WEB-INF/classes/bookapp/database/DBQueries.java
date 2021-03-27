package bookapp.database;

import java.util.*;
import bookapp.Config;
import java.util.stream.Collectors;

public class DBQueries {
  
  private static String COMBINE_BOOKS_AND_FAVORITES(Integer userId) {
    return
      "SELECT b.*, COALESCE(f.is_favorite, false) AS is_favorite, " +
      "g.name AS genre FROM " + 
      Config.DB_BOOK_TABLE + " AS b " +
      "LEFT JOIN " +
      Config.DB_GENRES_TABLE + " AS g " +
      "ON b.genre_id = g.id " +
      "LEFT JOIN (" +
        "SELECT book_id, true AS is_favorite " +
        "FROM " +
        Config.DB_FAVORITE_TABLE + " " +
        "WHERE " +
        "user_id=" + userId +
      ") AS f ON f.book_id = b.id ";
  }

  public static String ADD_BOOK(String ...values) {
    String bookFields = String.join(", ", BookDB.BOOK_FIELDS);
    String bookValues = String.join("', '", values);

    String query = 
      "INSERT INTO " +
      Config.DB_BOOK_TABLE + " " +
      "(" + bookFields + ")" +
      "VALUES " +
      "('" + bookValues + "') " +
      "RETURNING id";      

    return query;
  }

  public static String REMOVE_BOOK(Integer id) {
    String query = 
      "DELETE FROM " +
      Config.DB_BOOK_TABLE + " " +
      "WHERE " +
      "id = " + String.valueOf(id);      

    return query;
  }

  public static String GET_ALL_BOOKS(Integer userId, Integer offset, Integer limit) {
    String query = COMBINE_BOOKS_AND_FAVORITES(userId);
    query +=
      "ORDER BY b.id DESC " +
      "OFFSET " + offset + " " +
      "LIMIT " + limit;

    return query;
  }

  public static String GET_ALL_BOOKS_SORTED(Integer userId, String field, String order, Integer offset, Integer limit) {
    String query = COMBINE_BOOKS_AND_FAVORITES(userId);
    query +=
      "ORDER BY " +
      field + " " + order + " " +
      "OFFSET " + offset + " " +
      "LIMIT " + limit;

    return query;
  }

  public static String SEARCH_BOOKS(
    int userId,
    String title,
    String author,
    String publisher,
    int publishedDate,
    String genre
  ) {

    String query = COMBINE_BOOKS_AND_FAVORITES(userId);

    List<String> conditions = new ArrayList<String>();

    if(!title.isEmpty()) {
      conditions.add("title='" + title + "'");
    }
    if(!author.isEmpty()) {
      conditions.add("author='" + author + "'");
    }
    if(!publisher.isEmpty()) {
      conditions.add("publisher='" + publisher + "'");
    }
    if(publishedDate != 0) {
      conditions.add("published_date=" + publishedDate);
    }
    if(!genre.isEmpty()) {
      conditions.add("g.name ='" + genre + "'");
    }

    if(conditions.size() > 0) {
      String conditionsString =
        conditions.stream().collect(Collectors.joining(" AND ", " WHERE ", ""));

      query += conditionsString;
    }

    return query;
  }
  
  public static String ADD_USER(String ...values) {
    String userFields = String.join(", ", UserDB.USER_FIELDS);
    String userValues = String.join("', '", values);

    String query = 
      "INSERT INTO " +
      Config.DB_USER_TABLE + " " +
      "(" + userFields + ")" +
      "VALUES " +
      "('" + userValues + "')";      

    return query;
  }

  public static String REMOVE_USER(Integer id) {
    String query = 
      "DELETE FROM " +
      Config.DB_USER_TABLE + " " +
      "WHERE " +
      "id = " + String.valueOf(id);      

    return query;
  }

  public static String GET_USER_DETAILS(Integer id) {
    String query = 
      "SELECT * FROM " +
      Config.DB_USER_TABLE + " " +
      "WHERE " +
      "id = " + id;

    return query;
  }

  public static String VERIFY_USER(String email, String password) {
    String query = 
      "SELECT * FROM " +
      Config.DB_USER_TABLE + " " +
      "WHERE " +
      "email='" + email + "' AND " +
      "password='" + password + "';";

    return query;
  }

  public static String ADD_FAVORITE(String ...values) {
    String favoriteFields = String.join(", ", FavoriteDB.FAVORITE_FIELDS);
    String favoriteValues = String.join("', '", values);

    String query = 
      "INSERT INTO " +
      Config.DB_FAVORITE_TABLE + " " +
      "(" + favoriteFields + ")" +
      "VALUES " +
      "('" + favoriteValues + "')";      

    return query;
  }

  public static String REMOVE_FAVORITE(Integer userId, Integer bookId) {
    String query = 
      "DELETE FROM " +
      Config.DB_FAVORITE_TABLE + " " +
      "WHERE " +
      "user_id=" + String.valueOf(userId) + " AND " +
      "book_id=" + String.valueOf(bookId);

    return query;
  }

  public static String GET_USER_FAVORITES(Integer userId) {
    String query = 
      "SELECT b.*, g.name AS genre FROM " +
      Config.DB_BOOK_TABLE + " AS b " +
      "LEFT JOIN " +
      Config.DB_GENRES_TABLE + " AS g " +
      "ON b.genre_id = g.id " +
      "INNER JOIN " +
      Config.DB_FAVORITE_TABLE + " AS f " +
      "ON f.book_id=b.id " +
      "WHERE " +
      "f.user_id=" + userId;

    return query;
  }
  
  public static String ADD_GENRE(String ...values) {
    String genreFields = String.join(", ", GenreDB.GENRE_FIELDS);
    String genreValues = String.join("', '", values);

    String query = 
      "INSERT INTO " +
      Config.DB_GENRES_TABLE + " " +
      "(" + genreFields + ")" +
      "VALUES " +
      "('" + genreValues + "') " +
      "RETURNING id";      

    return query;
  }
  
  public static String GET_ALL_GENRES() {
    String query =
      "SELECT * FROM " +
      Config.DB_GENRES_TABLE + " ";
      
    return query;
  }
  
  public static String ADD_COLLECTION(String ...valuesList) {
    String fields = String.join(", ", CollectionDB.COLLECTION_FIELDS);
    String values = String.join("', '", valuesList);

    String query = 
      "INSERT INTO " +
      Config.DB_COLLECTIONS_TABLE + " " +
      "(" + fields + ")" +
      "VALUES " +
      "('" + values + "') " +
      "RETURNING id";

    return query;
  }
  
  public static String ADD_BOOKS_TO_COLLECTION() {
    String query = 
    "INSERT INTO " +
    Config.DB_COLLECTIONS_BOOKS_TABLE + " " +
    "(collection_id, book_id) VALUES (?, ?)";
    
    return query;
  }
  
  public static String DELETE_BOOK_FROM_COLLECTION(Integer collectionId, Integer bookId) {
    String query = 
      "DELETE FROM " +
      Config.DB_COLLECTIONS_BOOKS_TABLE + " " +
      "WHERE collection_id = " + collectionId + " " +
      "AND book_id = " + bookId;

    return query;
  }
  
  public static String GET_ALL_COLLECTIONS() {
    String query =
      "SELECT * FROM " +
      Config.DB_COLLECTIONS_TABLE + " ";
      
    return query;
  }
  
  public static String GET_ALL_COLLECTIONS_WITH_BOOKS(Integer userId) {
    String query =
      "SELECT c.name, cb.*, b.*, g.name AS genre FROM " +
      Config.DB_COLLECTIONS_BOOKS_TABLE + " AS cb " +
      "LEFT JOIN " + 
      Config.DB_COLLECTIONS_TABLE + " AS c " +
      "ON cb.collection_id = c.id " +
      "LEFT JOIN " +
      Config.DB_BOOK_TABLE + " AS b " +
      "ON cb.book_id = b.id " +
      "LEFT JOIN " +
      Config.DB_GENRES_TABLE + " AS g " +
      "ON b.genre_id = g.id ";

    return query;
  }
  
  public static String GET_ALLOWED_COLLECTIONS_WITH_BOOKS(Integer userId) {
    String query =
      "SELECT c.name, cb.*, uc.*, b.*, g.name AS genre FROM " +
      Config.DB_COLLECTIONS_BOOKS_TABLE + " AS cb " +
      "LEFT JOIN " + 
      Config.DB_COLLECTIONS_TABLE + " AS c " +
      "ON cb.collection_id = c.id " +
      "LEFT JOIN " +
      Config.DB_USERS_COLLECTIONS_TABLE + " AS uc " +
      "ON cb.collection_id = uc.collection_id AND uc.user_id = " + userId + " " +
      "LEFT JOIN " +
      Config.DB_BOOK_TABLE + " AS b " +
      "ON cb.book_id = b.id " +
      "LEFT JOIN " +
      Config.DB_GENRES_TABLE + " AS g " +
      "ON b.genre_id = g.id ";
      
    return query;
  }
  
  public static String GET_COLLECTION_ACCESS_REQUESTS() {
    String query =
      "SELECT uc.user_id, uc.collection_id, u.name AS user_name, " +
      "c.name AS collection_name, uc.access FROM " +
      Config.DB_USERS_COLLECTIONS_TABLE + " AS uc " +
      "LEFT JOIN " +
      Config.DB_USER_TABLE + " AS u " +
      "ON uc.user_id = u.id " +
      "LEFT JOIN " +
      Config.DB_COLLECTIONS_TABLE + " AS c " +
      "ON uc.collection_id = c.id " +
      "WHERE uc.access = '" + CollectionDB.ACCESS_REQUEST + "'";

    return query;
  }
  
  public static String REQUEST_COLLECTION_ACCESS(Integer userId, Integer collectionId) {
    String query =
      "INSERT INTO " +
      Config.DB_USERS_COLLECTIONS_TABLE + " " +
      "(user_id, collection_id, access) " +
      "VALUES (" +
      userId + "," +
      collectionId + ",'" +
      CollectionDB.ACCESS_REQUEST + "') " +
      "ON CONFLICT (user_id, collection_id) DO " +
      "UPDATE SET access = '" + CollectionDB.ACCESS_REQUEST + "'";
      ;

    return query;
  }

  public static String ALLOW_COLLECTION_ACCESS(Integer userId, Integer collectionId) {
    String query =
      "UPDATE " +
      Config.DB_USERS_COLLECTIONS_TABLE + " " +
      "set access = '" + CollectionDB.ACCESS_ALLOW + "' " +
      "WHERE " +
      "user_id = " + userId + " AND " +
      "collection_id = " + collectionId;
    
    return query;
  }

  public static String REJECT_COLLECTION_ACCESS(Integer userId, Integer collectionId) {
    String query =
      "UPDATE " +
      Config.DB_USERS_COLLECTIONS_TABLE + " " +
      "set access = '" + CollectionDB.ACCESS_REJECT + "' " +
      "WHERE " +
      "user_id = " + userId + " AND " +
      "collection_id = " + collectionId;
    
    return query;
  }
  
  public static String GET_BOOKS_COUNT() {
    String query = "SELECT COUNT(*) FROM " + Config.DB_BOOK_TABLE;

    return query;
  }
}