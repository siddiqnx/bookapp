package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.BookDB;
import bookapp.bean.Book;
import bookapp.elasticsearch.ElasticSearch;

import java.util.stream.*;

public class BookSearch extends HttpServlet {
  @Override
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {

    BookDB db = new BookDB();
    ElasticSearch es = new ElasticSearch();
    
    HttpSession session = request.getSession();
    
    String title = request.getParameter("title");
    String keywords = request.getParameter("keywords");
    String author = request.getParameter("author");
    String publisher = request.getParameter("publisher");
    String publishedDateStr = request.getParameter("published_date");
    String genre = request.getParameter("genre");

    Integer publishedDate = publishedDateStr.isEmpty() ? 0 : Integer.parseInt(publishedDateStr);

    Integer userId = (Integer)session.getAttribute("userId");
    db.setUserId(userId);

    List<Book> searchResult = db.searchBooks(
      title,
      author,
      publisher,
      publishedDate,
      genre
    );

    List<Integer> bookIdsMatchingKeywords = es.searchKeywordInSummary(keywords);

    searchResult = searchResult
                    .stream()
                    .filter(book -> bookIdsMatchingKeywords.contains(book.id))
                    .collect(Collectors.toList());

    request.setAttribute("bookList", searchResult);
		request.getRequestDispatcher("/bookapp").forward(request, response);
	}
}