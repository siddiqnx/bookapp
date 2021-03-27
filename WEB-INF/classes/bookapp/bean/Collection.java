package bookapp.bean;

import java.util.List;

public class Collection {
  public int id;
  public String name;
  public List<Book> books;
  public String access;

  public Collection(String name) {
    this.name = name;
  }

  public Collection(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public Collection(int id, String name, List<Book> books, String access) {
    this.id = id;
    this.name = name;
    this.books = books;
    this.access = access;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }
  
  public String getAccess() {
    return access;
  }

  public void setAccess(String access) {
    this.access = access;
  }

}