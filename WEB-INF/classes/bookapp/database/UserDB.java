package bookapp.database;

import java.util.*;
import java.io.*;
import java.sql.*;

import bookapp.*;
import bookapp.bean.User;
import bookapp.Config;

import bookapp.util.Command;

public class UserDB extends DB {

  public static List<String> USER_FIELDS = Arrays.asList(
    "name",
    "email",
    "password",
    "role"
  );

  public boolean addUser(User user) {
    
    String query = DBQueries.ADD_USER(
      user.name,
      user.email,
      user.password,
      user.role
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

  public boolean removeUser(Integer id) {
    String query = DBQueries.REMOVE_USER(id);

    try {
      Statement statement = connection.createStatement();
      int rows = statement.executeUpdate(query);

      if(rows <= 0) {
        return false;
      }

    } catch(SQLException e) {
      e.printStackTrace();
    }

    closeConnection();
    return true;
  }

  public Optional<User> verifyUser(String userEmail, String userPassword) {
    String query = DBQueries.VERIFY_USER(userEmail, userPassword);

    try {
      Statement statement = connection.createStatement();

      ResultSet result = statement.executeQuery(query);

      if(result.next()) {
        int id = result.getInt("id");
        String name = result.getString("name");
        String email = result.getString("email");
        String password = result.getString("password");
        String role = result.getString("role");

        User user = new User(id, name, email, password, role);
        return Optional.of(user);
      }

    } catch(SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();
    }
    
    return Optional.empty();
  }

}