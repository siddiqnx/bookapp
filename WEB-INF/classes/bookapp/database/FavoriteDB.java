package bookapp.database;

import java.util.*;
import java.io.*;
import java.sql.*;

import bookapp.*;
import bookapp.bean.*;
import bookapp.Config;

import bookapp.util.Command;

public class FavoriteDB extends DB {

  public static List<String> FAVORITE_FIELDS = Arrays.asList(
    "user_id",
    "book_id"
  );

  public boolean addFavorite(Favorite favorite) {
    String query = DBQueries.ADD_FAVORITE(
      String.valueOf(favorite.userId),
      String.valueOf(favorite.bookId)
    );

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

  public boolean removeFavorite(Integer bookId) {
    String query = DBQueries.REMOVE_FAVORITE(userId, bookId);

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

  public List<Book> getUserFavorites() {
    String query = DBQueries.GET_USER_FAVORITES(userId);
    List<Book> bookList = new ArrayList<Book>();
    
    try {
      Statement statement = connection.createStatement();

      ResultSet result = statement.executeQuery(query);

      while(result.next()) {
        int id = result.getInt("id");
        String title = result.getString("title");
        String summary = result.getString("summary");
        String author = result.getString("author");
        String publisher = result.getString("publisher");
        int publishedDate = result.getInt("published_date");
        String genre = result.getString("genre");
        boolean isFavorite = true;

        Book book = new Book(id, title, summary, author, publisher, publishedDate, genre, isFavorite);
        bookList.add(book);
      }
     
    } catch(SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();
    }

    return bookList;
  }


}