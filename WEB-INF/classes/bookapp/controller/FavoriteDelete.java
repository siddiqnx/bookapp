package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.FavoriteDB;

public class FavoriteDelete extends HttpServlet {
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    
    FavoriteDB db = new FavoriteDB();
    HttpSession session = request.getSession();

    Integer bookId = Integer.parseInt(request.getParameter("bookId"));
    Integer userId = (Integer)session.getAttribute("userId");
    db.setUserId(userId);

    db.removeFavorite(bookId);

		response.sendRedirect(request.getContextPath() + "/books");
	}
}