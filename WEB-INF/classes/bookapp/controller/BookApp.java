package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.BookDB;
import bookapp.bean.Book;

public class BookApp extends HttpServlet{
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    List<Book> books = (List<Book>)request.getAttribute("bookList");
    List<Book> favorites = (List<Book>)request.getAttribute("favoriteList");

    request.setAttribute("books", books);
    request.setAttribute("favorites", favorites);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}