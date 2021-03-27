package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.CollectionDB;

public class CollectionBookDelete extends HttpServlet {
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    
    CollectionDB db = new CollectionDB();
    HttpSession session = request.getSession();

    Integer collectionId = Integer.parseInt(request.getParameter("collectionId"));
    Integer bookId = Integer.parseInt(request.getParameter("bookId"));
    Integer userId = (Integer)session.getAttribute("userId");
    db.setUserId(userId);

    db.deleteBookFromCollection(collectionId, bookId);

		response.sendRedirect(request.getContextPath() + "/books");
	}
}