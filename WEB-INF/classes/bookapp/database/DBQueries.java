package bookapp.database;

import java.util.*;
import bookapp.*;
import bookapp.Config;
import java.util.stream.Collectors;

public class DBQueries {
  
  private static String COMBINE_BOOKS_AND_FAVORITES(Integer userId) {
    return
      "SELECT b.*, COALESCE(f.is_favorite, false) AS is_favorite FROM " +
      Config.DB_BOOK_TABLE + " " +
      "AS b " +
      "LEFT JOIN (" +
        "SELECT book_id, true AS is_favorite " +
        "FROM " +
        Config.DB_FAVORITE_TABLE + " " +
        "WHERE " +
        "user_id=" + userId +
      ") AS f ON f.book_id = b.id";
  }
  
  public static String ADD_BOOK(String ...values) {
    String bookFields = String.join(", ", BookDB.BOOK_FIELDS);
    String bookValues = String.join("', '", values);

    String query = 
      "INSERT INTO " +
      Config.DB_BOOK_TABLE + " " +
      "(" + bookFields + ")" +
      "VALUES " +
      "('" + bookValues + "')";      

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

  public static String GET_ALL_BOOKS(Integer userId) {
    String query = COMBINE_BOOKS_AND_FAVORITES(userId);

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

    String query = COMBINE_BOOKS_AND_FAVORITES(userId) + " ";

    List<String> conditions = new ArrayList<String>();

    if(!title.isEmpty()) {
      conditions.add("title = '" + title + "'");
    }
    if(!author.isEmpty()) {
      conditions.add("author = '" + author + "'");
    }
    if(!publisher.isEmpty()) {
      conditions.add("publisher = '" + publisher + "'");
    }
    if(publishedDate != 0) {
      conditions.add("published_date = " + publishedDate);
    }
    if(!genre.isEmpty()) {
      conditions.add("genre = '" + genre + "'");
    }

    String conditionsString =
      conditions.stream().collect(Collectors.joining(" AND ", " AND ", ""));

    query += conditionsString;
    return query;
  }

  public static String GET_ALL_BOOKS_SORTED(Integer userId, String field, String order) {
    String query = 
      COMBINE_BOOKS_AND_FAVORITES(userId) + " " +
      "ORDER BY " +
      field + " " +
      order;

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
      "SELECT * FROM " +
      Config.DB_BOOK_TABLE + " AS b " +
      "INNER JOIN " +
      Config.DB_FAVORITE_TABLE + " AS f " +
      "ON f.book_id=b.id " +
      "WHERE " +
      "f.user_id=" + userId;

    return query;
  }
}