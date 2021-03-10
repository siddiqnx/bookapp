package bookapp.bean;

public class Favorite {
  public int id;
  public int userId;
  public int bookId;
  
  public Favorite(
    int id,
    int userId,
    int bookId
  ) {
    this.id = id;
    this.userId = userId;
    this.bookId = bookId;
  }

  public Favorite(
    int userId,
    int bookId
  ) {
    this.userId = userId;
    this.bookId = bookId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getBookId() {
    return bookId;
  }

  public void setBookId(int bookId) {
    this.bookId = bookId;
  }
}