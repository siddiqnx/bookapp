package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.*;
import bookapp.bean.*;

import bookapp.elasticsearch.ElasticSearch;

public class BookAdd extends HttpServlet {
  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    
    BookDB bookDB = new BookDB();
    GenreDB genreDB = new GenreDB();

    String title = request.getParameter("title");
    String summary = request.getParameter("summary");
    String author = request.getParameter("author");
    String publisher = request.getParameter("publisher");
    Integer publishedDate = 
      request.getParameter("published_date").isEmpty()
        ? 0 
        : Integer.parseInt(request.getParameter("published_date"));
    Integer genreId = Integer.parseInt(request.getParameter("genre"));
    String genreName = request.getParameter("genre_name");
    Boolean isFavorite = Boolean.FALSE;

    if(genreId == 0) {
      Genre genre = new Genre(genreName);
      genreId = genreDB.addGenre(genre);
    }

    Book newBook = new Book(
      title,
      summary,
      author,
      publisher,
      publishedDate,
      genreId,
      genreName,
      isFavorite
    );

    Integer bookId = bookDB.addBook(newBook);
    
    if(bookId != -1) {
      ElasticSearch es = new ElasticSearch();
      es.indexBook(bookId, summary);
    }

		response.sendRedirect(request.getContextPath() + "/books");
	}
}