package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.CollectionDB;

public class CollectionDelete extends HttpServlet {
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    
    CollectionDB db = new CollectionDB();
    HttpSession session = request.getSession();

    Integer id = Integer.parseInt(request.getParameter("id"));
    Integer userId = (Integer)session.getAttribute("userId");

    db.setUserId(userId);

    db.deleteCollection(id);

		response.sendRedirect(request.getContextPath() + "/books");
	}
}