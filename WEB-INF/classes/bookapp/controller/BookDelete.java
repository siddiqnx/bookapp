package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.BookDB;
import bookapp.bean.Book;

public class BookDelete extends HttpServlet{
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    BookDB db = new BookDB();

    Integer id = Integer.parseInt(request.getParameter("id"));

    db.removeBook(id);

		response.sendRedirect(request.getContextPath() + "/books");
	}
}