package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.BookDB;
import bookapp.bean.Book;

public class BookAdd extends HttpServlet {
  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    
    BookDB db = new BookDB();

    String title = request.getParameter("title");
    String author = request.getParameter("author");
    String publisher = request.getParameter("publisher");
    Integer publishedDate = request.getParameter("published_date").isEmpty() ? 0 : Integer.parseInt(request.getParameter("published_date"));
    String genre = request.getParameter("genre");
    Boolean isFavorite = Boolean.FALSE;

    Book newBook = new Book(
      title,
      author,
      publisher,
      publishedDate,
      genre,
      isFavorite
    );

    db.addBook(newBook);

		response.sendRedirect(request.getContextPath() + "/books");
	}
}