package bookapp.database;

import java.util.*;
import java.io.*;
import java.sql.*;

import bookapp.*;
import bookapp.bean.Book;
import bookapp.Config;

import bookapp.util.Command;

public class BookDB extends DB {

  public static List<String> BOOK_FIELDS = Arrays.asList(
    "title",
    "summary",
    "author",
    "publisher",
    "published_date",
    "genre"
  );
  public static String ASC = "ASC";
  public static String DESC = "DESC";

  public Integer addBook(Book book) {
    
    String query = DBQueries.ADD_BOOK(
      book.title,
      book.summary,
      book.author,
      book.publisher,
      String.valueOf(book.publishedDate),
      book.genre
    );

    try {
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery(query);

      if(result.next()) {
        return result.getInt(1);
      }
    } catch(SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();
    }

    return -1;
  }

  public boolean removeBook(Integer id) {
    String query = DBQueries.REMOVE_BOOK(id);

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

  public List<Book> getAllBooks() {
    String query = DBQueries.GET_ALL_BOOKS(userId);
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
        boolean isFavorite = result.getBoolean("is_favorite");

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

  public List<Book> getAllBooksSorted(String field, String order) {
    List<Book> bookList = new ArrayList<Book>();

    String query = DBQueries.GET_ALL_BOOKS_SORTED(userId, field, order);

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
        boolean isFavorite = result.getBoolean("is_favorite");

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

  public List<Book> searchBooks(
    String title,
    String author,
    String publisher,
    int publishedDate,
    String genre
  ) {
    String query = DBQueries.SEARCH_BOOKS(
      userId,
      title,
      author,
      publisher,
      publishedDate,
      genre
    );

    List<Book> bookList = new ArrayList<Book>();

    try {
      Statement statement = connection.createStatement();
      
      ResultSet result = statement.executeQuery(query);
      
      while(result.next()) {
        int bId = result.getInt("id");
        String bTitle = result.getString("title");
        String bSummary = result.getString("summary");
        String bAuthor = result.getString("author");
        String bPublisher = result.getString("publisher");
        int bPublishedDate = result.getInt("published_date");
        String bGenre = result.getString("genre");
        boolean isFavorite = result.getBoolean("is_favorite");

        Book book = new Book(
          bId,
          bTitle,
          bSummary,
          bAuthor,
          bPublisher,
          bPublishedDate,
          bGenre,
          isFavorite
        );

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