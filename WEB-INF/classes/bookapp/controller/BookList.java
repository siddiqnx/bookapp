package bookapp.controller;

import java.io.IOException;
import java.util.List;
import java.lang.Math;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.*;
import bookapp.bean.*;

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
    UserDB userDB = new UserDB();
    FavoriteDB favoriteDB = new FavoriteDB();
    GenreDB genreDB = new GenreDB();
    CollectionDB collectionDB = new CollectionDB();

    
    List<Book> books = null;
    List<Book> favorites = null;
    List<Genre> genres = null;
    final Integer BOOKS_PER_PAGE = 10;
    Integer currentPageNum;

    String sortBy = (String)request.getParameter("sortby");
    String order = (String)request.getParameter("order");

    if(request.getParameter("page") != null) {
      currentPageNum = Integer.parseInt(request.getParameter("page"));
    } else {
      currentPageNum = 1;
    }

    Integer numPages = new Double(Math.ceil(bookDB.getBooksCount() / Double.valueOf(BOOKS_PER_PAGE))).intValue();
    Integer offset = (currentPageNum - 1) * BOOKS_PER_PAGE;
    Integer limit = BOOKS_PER_PAGE;
    

    bookDB.setUserId(userId);
    favoriteDB.setUserId(userId);
    collectionDB.setUserId(userId);
    userDB.setUserId(userId);

    User user = userDB.getUserDetails();

    List<Collection> collections = null;
    List<CollectionRequest> collectionRequests = null;

    boolean isAdmin = user.role.equals("admin");
    
    if(isAdmin) {

      collections = collectionDB.getAllCollectionsWithBooks();
      collectionRequests = collectionDB.getCollectionAccessRequests();
    } else {
      collections = collectionDB.getAllowedCollectionsWithBooks();
    }

    if(order != null) {
      order = order.toUpperCase();
    }

    if(sortBy != null) {
      books = bookDB.getAllBooksSorted(sortBy, order, offset, limit);
    } else {
      books = bookDB.getAllBooks(offset, limit);
    }
    
    favorites = favoriteDB.getUserFavorites();
    genres = genreDB.getAllGenres();

    request.setAttribute("books", books);
    request.setAttribute("favorites", favorites);
    request.setAttribute("genres", genres);
    request.setAttribute("collections", collections);
    request.setAttribute("collectionRequests", collectionRequests);
    request.setAttribute("numPages", numPages);
    request.setAttribute("currentPageNum", currentPageNum);

		request.getRequestDispatcher("/bookapp").forward(request, response);
	}
}