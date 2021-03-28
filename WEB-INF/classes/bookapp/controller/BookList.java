package bookapp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.BookDB;
import bookapp.bean.Book;

public class BookList extends HttpServlet {
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    
    HttpSession session = request.getSession();
    Integer userId = (Integer)session.getAttribute("userId");

    if(userId == null) {
      response.sendRedirect(request.getContextPath() + "/login.jsp");
      return;
    }

    BookDB bookDB = new BookDB();
    List<Book> books = null;

    String sortBy = (String)request.getParameter("sortby");
    String order = (String)request.getParameter("order");

    bookDB.setUserId(userId);

    if(order != null) {
      order = order.toUpperCase();
    }

    if(sortBy != null) {
      books = bookDB.getAllBooksSorted(sortBy, order);
    } else {
      books = bookDB.getAllBooks();
    }

    request.setAttribute("books", books);
		request.getRequestDispatcher("/bookapp").forward(request, response);
	}
}