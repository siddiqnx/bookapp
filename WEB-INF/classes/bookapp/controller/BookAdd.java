package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.BookDB;
import bookapp.bean.Book;

import bookapp.elasticsearch.ElasticSearch;

public class BookAdd extends HttpServlet {
  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    
    BookDB db = new BookDB();

    String title = request.getParameter("title");
    String summary = request.getParameter("summary");
    String author = request.getParameter("author");
    String publisher = request.getParameter("publisher");
    Integer publishedDate = request.getParameter("published_date").isEmpty() ? 0 : Integer.parseInt(request.getParameter("published_date"));
    String genre = request.getParameter("genre");
    Boolean isFavorite = Boolean.FALSE;

    Book newBook = new Book(
      title,
      summary,
      author,
      publisher,
      publishedDate,
      genre,
      isFavorite
    );

    Integer bookId = db.addBook(newBook);
    
    if(bookId != -1) {
      ElasticSearch es = new ElasticSearch();
      es.indexBook(bookId, summary);
    }

		response.sendRedirect(request.getContextPath() + "/books");
	}
}