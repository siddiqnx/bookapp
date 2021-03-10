package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.FavoriteDB;
import bookapp.bean.Favorite;

public class FavoriteAdd extends HttpServlet {
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {

    FavoriteDB db = new FavoriteDB();
    HttpSession session = request.getSession();

    Integer bookId = Integer.parseInt(request.getParameter("bookId"));
    Integer userId = (Integer)session.getAttribute("userId");
    db.setUserId(userId);

    Favorite newFavorite = new Favorite(userId, bookId);
    db.addFavorite(newFavorite);

		response.sendRedirect(request.getContextPath() + "/books");
	}
}