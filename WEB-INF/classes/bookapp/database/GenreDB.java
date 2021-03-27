package bookapp.database;

import java.util.*;
import java.io.*;
import java.sql.*;

import bookapp.*;
import bookapp.bean.Book;
import bookapp.bean.Genre;
import bookapp.Config;

public class GenreDB extends DB {
  public static List<String> GENRE_FIELDS = Arrays.asList(
    "name"
  );

  public Integer addGenre(Genre genre) {

    String query = DBQueries.ADD_GENRE(genre.name);

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
  
  public List<Genre> getAllGenres() {
    String query = DBQueries.GET_ALL_GENRES();
    List<Genre> genreList = new ArrayList<Genre>();

    try {
      Statement statement = connection.createStatement();

      ResultSet result = statement.executeQuery(query);

      while(result.next()) {
        int id = result.getInt("id");
        String name = result.getString("name");

        Genre genre = new Genre(id, name);
        genreList.add(genre);
      }

    } catch(SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();      
    }

    return genreList;
  }
}