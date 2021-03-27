package bookapp.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.CollectionDB;
import bookapp.bean.Collection;

public class CollectionAdd extends HttpServlet {
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {

    CollectionDB db = new CollectionDB();
    HttpSession session = request.getSession();
    String collectionName = (String)request.getParameter("collection_name");
    String[] selectedBookIds = request.getParameterValues("selected_books");


    Integer userId = (Integer)session.getAttribute("userId");
    db.setUserId(userId);
    
    List<Integer> bookIds =
      Arrays.stream(selectedBookIds)
            .map(id -> Integer.parseInt(id))
            .collect(Collectors.toList());
    
    Integer newCollectionId = db.addCollection(collectionName);

    db.addBooksToCollection(newCollectionId, bookIds);

		response.sendRedirect(request.getContextPath() + "/books");
	}
}