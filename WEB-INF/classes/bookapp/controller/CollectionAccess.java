package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.CollectionDB;

public class CollectionAccess extends HttpServlet {
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {

    HttpSession session = request.getSession();
    CollectionDB collectionDB = new CollectionDB();

    Integer userId = (Integer)session.getAttribute("userId");

    Integer collectionId = Integer.parseInt(request.getParameter("collectionId"));
    Integer requestingUserId = Integer.parseInt(request.getParameter("userId"));
    String actionType = (String)request.getParameter("actionType");

    collectionDB.setUserId(userId);
    
    if(actionType.equals("allow")) {
      collectionDB.allowCollectionAccess(requestingUserId, collectionId);
    } else if(actionType.equals("reject")) {
      collectionDB.rejectCollectionAccess(requestingUserId, collectionId);
    }

		response.sendRedirect(request.getContextPath() + "/books");
	}
}