package bookapp.database;

import java.util.*;
import java.io.*;
import java.sql.*;

import bookapp.*;
import bookapp.bean.Book;
import bookapp.Config;

import bookapp.util.Command;

public class DB {
  protected Connection connection;
  protected Integer userId;
  protected static String ASC = "ASC";
  protected static String DESC = "DESC";

  public DB() {
    setConnection();
  }

  private void setConnection() {
    try {
      connection = DriverManager.getConnection(
        Config.DB_URL,
        Config.DB_USERNAME,
        Config.DB_PASSWORD
      );
    } catch(SQLException e) {
      System.out.println("Error connecting to DB");
      e.printStackTrace();
    }
  }
  
  protected void closeConnection() {
    try {
      connection.close();
    } catch(SQLException e) {
      System.out.println("Error closing DB connection");
      e.printStackTrace();
    }
  }

  public void setUserId(Integer id) {
    userId = id;
  }
}