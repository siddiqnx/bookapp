package bookapp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.*;
import bookapp.bean.*;

public class BookApp extends HttpServlet{
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

    FavoriteDB favoriteDB = new FavoriteDB();
    GenreDB genreDB = new GenreDB();
    CollectionDB collectionDB = new CollectionDB();
    UserDB userDB = new UserDB();

    favoriteDB.setUserId(userId);
    collectionDB.setUserId(userId);
    userDB.setUserId(userId);
    genreDB.setUserId(userId);

    List<Book> favorites = null;
    List<Genre> genres = null;
    List<Collection> collections = null;
    List<CollectionRequest> collectionRequests = null;
    
    User user = userDB.getUserDetails();
    
    boolean isAdmin = user.role.equals("admin");

    if(isAdmin) {
      collections = collectionDB.getAllCollectionsWithBooks();
      collectionRequests = collectionDB.getCollectionAccessRequests();
    } else {
      collections = collectionDB.getAllowedCollectionsWithBooks();
    }

    favorites = favoriteDB.getUserFavorites();
    genres = genreDB.getAllGenres();

    request.setAttribute("favorites", favorites);
    request.setAttribute("genres", genres);
    request.setAttribute("collections", collections);
    request.setAttribute("collectionRequests", collectionRequests);

		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}