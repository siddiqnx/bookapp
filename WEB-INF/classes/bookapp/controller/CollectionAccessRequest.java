package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.CollectionDB;

public class CollectionAccessRequest extends HttpServlet {
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {

    HttpSession session = request.getSession();
    CollectionDB collectionDB = new CollectionDB();

    Integer collectionId = Integer.parseInt(request.getParameter("collectionId"));
    Integer userId = (Integer)session.getAttribute("userId");
    
    collectionDB.setUserId(userId);
    
    collectionDB.requestCollectionAccess(collectionId);

		response.sendRedirect(request.getContextPath() + "/books");
	}
}