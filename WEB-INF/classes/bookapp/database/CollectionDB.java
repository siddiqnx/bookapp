package bookapp.database;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
import java.sql.*;

import bookapp.*;
import bookapp.bean.*;
import bookapp.Config;

public class CollectionDB extends DB {

  public static List<String> COLLECTION_FIELDS = Arrays.asList(
    "name"
  );

  public static String ACCESS_REQUEST = "request";  
  public static String ACCESS_ALLOW = "allow";
  public static String ACCESS_REJECT = "reject";
  
  public Integer addCollection(String name) {
    String query = DBQueries.ADD_COLLECTION(name);

    try {
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery(query);

      if(result.next()) {
        return result.getInt(1);
      }
    } catch(SQLException e) {
      e.printStackTrace();
    }

    return -1;
  }
  
  public boolean addBooksToCollection(Integer collectionId, List<Integer> bookIds) {
    String query = DBQueries.ADD_BOOKS_TO_COLLECTION();

    try {
      PreparedStatement ps = connection.prepareStatement(query);            
      for (Integer bookId : bookIds) {
          ps.setInt(1, collectionId);
          ps.setInt(2, bookId);
          ps.addBatch();
      }

      ps.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();
    }

    return true;
  }

  public boolean deleteBookFromCollection(Integer collectionId, Integer bookId) {
    String query = DBQueries.DELETE_BOOK_FROM_COLLECTION(collectionId, bookId);

    try {
      Statement statement = connection.createStatement();
      int rows = statement.executeUpdate(query);

      if(rows <= 0) {
        return false;
      }
    } catch(SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();
    }

    return true;
  }

  public List<Collection> getAllCollections() {
    String query = DBQueries.GET_ALL_COLLECTIONS();
    List<Collection> collectionList = new ArrayList<Collection>();

    try {
      Statement statement = connection.createStatement();

      ResultSet result = statement.executeQuery(query);

      while(result.next()) {
        int id = result.getInt("id");
        String name = result.getString("name");

        Collection collection = new Collection(id, name);
        collectionList.add(collection);
      }

    } catch(SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();      
    }

    return collectionList;
  }
  
  public List<Collection> getAllCollectionsWithBooks() {
    String query = DBQueries.GET_ALL_COLLECTIONS_WITH_BOOKS(userId);
    
    List<Collection> collectionList = new ArrayList<Collection>();

    try {
      Statement statement = connection.createStatement();

      ResultSet result = statement.executeQuery(query);

      while(result.next()) {
        int id = result.getInt("collection_id");
        String name = result.getString("name");
        String access = ACCESS_ALLOW;
        
        int bookId = result.getInt("id");
        String bookTitle = result.getString("title");
        String bookSummary = result.getString("summary");
        String bookAuthor = result.getString("author");
        String bookPublisher = result.getString("publisher");
        int bookPublishedDate = result.getInt("published_date");
        int bookGenreId = result.getInt("genre_id");
        String bookGenre = result.getString("genre");
        boolean bookIsFavorite = false;

        Book book = new Book(
          bookId,
          bookTitle,
          bookSummary,
          bookAuthor,
          bookPublisher,
          bookPublishedDate,
          bookGenreId,
          bookGenre,
          bookIsFavorite
        );

        boolean isCollectionPresentInList =
          collectionList.stream()
                        .anyMatch(collection -> collection.id == id);
        
        if(!isCollectionPresentInList) {
          List<Book> books = new ArrayList<Book>();
          books.add(book);
          Collection collection = new Collection(id, name, books, access);
          collectionList.add(collection);
        } else {
          collectionList.stream()
                      .filter(collection -> collection.id == id)
                      .forEach(collection -> collection.books.add(book));
        }
      }

    } catch(SQLException e) {
      e.printStackTrace();
    }

    return collectionList;
  }

  public List<Collection> getAllowedCollectionsWithBooks() {
    String query = DBQueries.GET_ALLOWED_COLLECTIONS_WITH_BOOKS(userId);
    
    List<Collection> collectionList = new ArrayList<Collection>();

    try {
      Statement statement = connection.createStatement();

      ResultSet result = statement.executeQuery(query);

      while(result.next()) {
        int id = result.getInt("collection_id");
        String name = result.getString("name");
        String access = result.getString("access");
        
        int bookId = result.getInt("id");
        String bookTitle = result.getString("title");
        String bookSummary = result.getString("summary");
        String bookAuthor = result.getString("author");
        String bookPublisher = result.getString("publisher");
        int bookPublishedDate = result.getInt("published_date");
        int bookGenreId = result.getInt("genre_id");
        String bookGenre = result.getString("genre");
        boolean bookIsFavorite = false;
        
        Integer userId = result.getInt("user_id");

        Book book = new Book(
          bookId,
          bookTitle,
          bookSummary,
          bookAuthor,
          bookPublisher,
          bookPublishedDate,
          bookGenreId,
          bookGenre,
          bookIsFavorite
        );

        boolean isCollectionPresentInList =
          collectionList.stream()
                        .anyMatch(collection -> collection.id == id);
        
        if(!isCollectionPresentInList) {
          List<Book> books = new ArrayList<Book>();
          if(userId != 0) {
            books.add(book);
          }
          Collection collection = new Collection(id, name, books, access);
          collectionList.add(collection);
        } else if(userId != 0) {
          collectionList.stream()
                      .filter(collection -> collection.id == id)
                      .forEach(collection -> collection.books.add(book));
        }
      }

    } catch(SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();      
    }

    return collectionList;
  }
  
  public List<CollectionRequest> getCollectionAccessRequests() {
    String query = DBQueries.GET_COLLECTION_ACCESS_REQUESTS();
    
    List<CollectionRequest> collectionRequests = new ArrayList<CollectionRequest>();

    try {
      Statement statement = connection.createStatement();

      ResultSet result = statement.executeQuery(query);

      while(result.next()) {
        int userId = result.getInt("user_id");
        String userName = result.getString("user_name");
        int collectionId = result.getInt("collection_id");
        String collectionName = result.getString("collection_name");

        CollectionRequest request =
          new CollectionRequest(
            userId,
            collectionId,
            userName,
            collectionName
          );

        collectionRequests.add(request);
      }

    } catch(SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();
    }

    return collectionRequests;
  }
  
  public boolean requestCollectionAccess(Integer collectionId) {
    String query = DBQueries.REQUEST_COLLECTION_ACCESS(userId, collectionId);

    try {
      Statement statement = connection.createStatement();
      int rows = statement.executeUpdate(query);

      if(rows <= 0) {
        return false;
      }
    } catch(SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();
    }

    return true;
  }
  
  public boolean allowCollectionAccess(Integer requestingUserId, Integer collectionId) {
    String query = DBQueries.ALLOW_COLLECTION_ACCESS(requestingUserId, collectionId);

    try {
      Statement statement = connection.createStatement();
      int rows = statement.executeUpdate(query);

      if(rows <= 0) {
        return false;
      }
    } catch(SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();
    }

    return true;
  }

  public boolean rejectCollectionAccess(Integer requestingUserId, Integer collectionId) {
    String query = DBQueries.REJECT_COLLECTION_ACCESS(requestingUserId, collectionId);

    try {
      Statement statement = connection.createStatement();
      int rows = statement.executeUpdate(query);

      if(rows <= 0) {
        return false;
      }
    } catch(SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();
    }

    return true;
  }
}