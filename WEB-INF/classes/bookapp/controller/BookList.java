package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.*;
import bookapp.bean.Book;

public class BookList extends HttpServlet {
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {

    BookDB bookDB = new BookDB();
    FavoriteDB favoriteDB = new FavoriteDB();
    HttpSession session = request.getSession();
    List<Book> books = null;
    List<Book> favorites = null;

    String sortBy = (String)request.getParameter("sortby");
    String order = (String)request.getParameter("order");
    
    Integer userId = (Integer)session.getAttribute("userId");
    
    bookDB.setUserId(userId);
    favoriteDB.setUserId(userId);

    if(order != null) {
      order = order.toUpperCase();
    }

    if(sortBy != null) {
      books = bookDB.getAllBooksSorted(sortBy, order);
    } else {
      books = bookDB.getAllBooks();
    }
    
    favorites = favoriteDB.getUserFavorites();

    request.setAttribute("bookList", books);
    request.setAttribute("favoriteList", favorites);
		request.getRequestDispatcher("/bookapp").forward(request, response);
	}
}