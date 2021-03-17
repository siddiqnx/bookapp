package bookapp.bean;

public class Book {
  public int id;
  public String title;
  public String summary;
  public String author;
  public String publisher;
  public int publishedDate;
  public String genre;
  public boolean isFavorite;
  
  public Book(
    int id,
    String title,
    String summary,
    String author,
    String publisher,
    int publishedDate,
    String genre,
    boolean isFavorite
  ) {
    this.id = id;
    this.summary = summary;
    this.title = title;
    this.author = author;
    this.publisher = publisher;
    this.publishedDate = publishedDate;
    this.genre = genre;
    this.isFavorite = isFavorite;
  }
  public Book(
    String title,
    String summary,
    String author,
    String publisher,
    int publishedDate,
    String genre,
    boolean isFavorite
  ) {
    this.title = title;
    this.summary = summary;
    this.author = author;
    this.publisher = publisher;
    this.publishedDate = publishedDate;
    this.genre = genre;
    this.isFavorite = isFavorite;
  }

  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
  
  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }
  
  public int getPublishedDate() {
    return publishedDate;
  }

  public void setPublishedDate(int publishedDate) {
    this.publishedDate = publishedDate;
  }
  
  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }
  
  public boolean getIsFavorite() {
    return isFavorite;
  }

  public void setIsFavorite(boolean isFavorite) {
    this.isFavorite = isFavorite;
  }
}