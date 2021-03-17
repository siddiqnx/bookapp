<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="path" scope="session" value="${pageContext.request.contextPath}"/>
<%
  String order = request.getParameter("order");
  String role = (String)session.getAttribute("role");
  
  if(order != null && order.equals("asc")) order = "desc";
  else order = "asc";
  
  String sTitle = request.getParameter("title") != null ? request.getParameter("title") : "";
  String sAuthor = request.getParameter("author") != null ? request.getParameter("author") : "";
  String sPublisher = request.getParameter("publisher") != null ? request.getParameter("publisher") : "";
  String sPublishedDate = request.getParameter("published_date") != null ? request.getParameter("published_date") : "";
  String sGenre = request.getParameter("genre") != null ? request.getParameter("genre") : "";
  String sKeywords = request.getParameter("keywords") != null ? request.getParameter("keywords") : "";
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>BookApp</title>

    <link href="${path}/css/materialize.min.css" rel="stylesheet" type="text/css" />
    <link href="${path}/css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>

    <div class="layout">
      <header id="header" class="row valign-wrapper">
        <h3 class="col s8 page-title valign">BookApp</h3>
        <div class="col s4 valign user-info">
          <span><img src="${path}/images/user_image.svg">${name}</span>
          <a href="${path}/logout" class="waves-effect waves-teal btn-flat grey lighten-5">Logout</a>
        </div>
      </header>
      <div class="row">
        <c:if test="${role == 'admin'}">

          <!-- Sidebar -->
          <aside id="sidebar" class="col s3">
            <div class="row">
              <header class="col s12">
                <h5>Add a Book</h5>
              </header>
            </div>
            <div class="row">

              <!-- Add Book Form -->
              <form id="add-book" class="col s10" action="${path}/books/add" method="POST">
                
                <div class="row">
                  <div class="input-field col s12">
                    <input name="title" id="title" type="text" class="validate">
                    <label for="title">Title</label>
                  </div>
                </div>
                
                <div class="row">
                  <div class="input-field col s12">
                    <textarea name="summary" id="summary" class="materialize-textarea"></textarea>
                    <label for="summary">Summary</label>
                  </div>
                </div>
        
                <div class="row">
                  <div class="input-field col s12">
                    <input name="author" id="author" type="text" class="validate">
                    <label for="author">Author</label>
                  </div>
                </div>
        
                <div class="row">
                  <div class="input-field col s12">
                    <input name="publisher" id="publisher" type="text" class="validate">
                    <label for="publisher">Publisher</label>
                  </div>
                </div>
        
                <div class="row">
                  <div class="input-field col s12">
                    <input name="published_date" id="published-date" type="text" class="validate">
                    <label for="published-date">Published Date</label>
                  </div>
                </div>
                <div class="row">
                  <div class="input-field col s12">
                    <input name="genre" id="genre" type="text" class="validate">
                    <label for="genre">Genre</label>
                  </div>
                </div>
                <button type="submit" class="waves-effect waves-light btn">Add Book</button>
              </form>
            </div>
          </aside>

        </c:if>
        <!-- Main Content -->
        <main id="books-table-container" class="col <%= (role != null && role.equals("admin")) ? "s9" : "s12" %>">
        
          <!-- Tab Bar -->
          <div class="row">
            <div class="col s12">
              <ul class="tabs">
                <li class="tab col s3"><a class="active" href="#all-books">All Books</a></li>
                <li class="tab col s3"><a href="#favorites">Favorites</a></li>
              </ul>
            </div>
            <div id="all-books" class="col s12">

              <div class="row search-container">
                <!-- Search Form -->
                <form id="search-book" class="col s12" action="${path}/books/search" method="GET">

                  <div class="row valign-wrapper">
                    <div class="valign input-field col s2">
                      <input value="<%= sTitle %>" name="title" id="s-title" type="text" class="validate">
                      <label for="s-title">Title</label>
                    </div>
                    
                    <div class="valign input-field col s2">
                      <input value="<%= sKeywords %>" name="keywords" id="s-keywords" type="text" class="validate">
                      <label for="s-keywords">Keyword</label>
                    </div>

                    <div class="valign input-field col s2">
                      <input value="<%= sAuthor %>" name="author" id="s-author" type="text" class="validate">
                      <label for="s-author">Author</label>
                    </div>

                    <div class="valign input-field col s2">
                      <input value="<%= sPublisher %>" name="publisher" id="s-publisher" type="text" class="validate">
                      <label for="s-publisher">Publisher</label>
                    </div>

                    <div class="valign input-field col s1">
                      <input value="<%= sPublishedDate %>" name="published_date" id="s-published-date" type="text" class="validate">
                      <label for="s-published-date">Year</label>
                    </div>

                    <div class="valign input-field col s1">
                      <input value="<%= sGenre %>" name="genre" id="s-genre" type="text" class="validate">
                      <label for="s-genre">Genre</label>
                    </div>
                    
                    <div class="valign input-field col s1">
                      <button type="submit" class="waves-effect waves-light btn">Search</button>
                    </div>
                    <div class="valign input-field col s1">
                      <button type="button" class="waves-effect waves-teal btn-flat grey lighten-5" onclick="window.location='${path}/books'">Clear</button>
                    </div>
                  </div>
                </form>
              </div>
              
              <div class="row">
                <table id="books-table" class="col s12">
                  <thead>
                    <tr>
                        <th><a href="${path}/books?sortby=title&order=<%= order %>">Title</a></th>
                        <th><a href="${path}/books?sortby=author&order=<%= order %>">Author</a></th>
                        <th><a href="${path}/books?sortby=publisher&order=<%= order %>">Publisher</a></th>
                        <th><a href="${path}/books?sortby=published_date&order=<%= order %>">Published Date</a></th>
                        <th><a href="${path}/books?sortby=genre&order=<%= order %>">Genre</a></th>
                    </tr>
                  </thead>

                  <tbody>
                    <c:forEach items="${books}" var ="book">
                      <tr id="${book.id}">
                        <td class='title'>${book.title}</td>
                        <td class='summary hide'>${book.summary}</td>
                        <td>${book.author}</td>
                        <td>${book.publisher}</td>
                        <td>${book.publishedDate}</td>
                        <td>${book.genre}</td>
                        <c:if test="${role == 'admin'}">
                          <td><a class='no-propagation' href="${path}/books/delete?id=${book.id}"><img class="delete-icon" src="${path}/images/delete_icon.svg" ></a></td>
                        </c:if>
                        <td>

                          <c:choose>
                            <c:when test="${book.isFavorite}">
                              <a class='no-propagation' href="${path}/favorites/delete?bookId=${book.id}">
                                <img
                                  class="favorite-icon"
                                  src="${path}/images/favorite_filled.svg"
                                >
                              </a>
                            </c:when>
                            <c:otherwise>
                              <a class='no-propagation' href="${path}/favorites/add?bookId=${book.id}">
                                <img
                                  class="favorite-icon"
                                  src="${path}/images/favorite_outline.svg"
                                >
                              </a>
                            </c:otherwise>
                          </c:choose>
                            

                        </td>
                      </tr>

                    </c:forEach>
                  </tbody>
                </table>

              </div>
            </div>

            <div id="favorites" class="col s12">
              <div class="row">
                <table id="favorites-table" class="col s12">
                  <thead>
                    <tr>
                        <th><a href="${path}/books?sortby=title&order=<%= order %>">Title</a></th>
                        <th><a href="${path}/books?sortby=author&order=<%= order %>">Author</a></th>
                        <th><a href="${path}/books?sortby=publisher&order=<%= order %>">Publisher</a></th>
                        <th><a href="${path}/books?sortby=published_date&order=<%= order %>">Published Date</a></th>
                        <th><a href="${path}/books?sortby=genre&order=<%= order %>">Genre</a></th>
                    </tr>
                  </thead>

                  <tbody>
                    <c:forEach items="${favorites}" var ="book">
                      <tr>
                        <td>${book.title}</td>
                        <td>${book.author}</td>
                        <td>${book.publisher}</td>
                        <td>${book.publishedDate}</td>
                        <td>${book.genre}</td>
                        <c:if test="${role == 'admin'}">
                          <td><a href="${path}/books/delete?id=${book.id}"><img class="delete-icon" src="${path}/images/delete_icon.svg" ></a></td>
                        </c:if>
                        <td>

                          
                          <c:choose>
                            <c:when test="${book.isFavorite}">
                              <a href="${path}/favorites/delete?bookId=${book.id}">
                                <img
                                  class="favorite-icon"
                                  src="${path}/images/favorite_filled.svg"
                                >
                              </a>
                            </c:when>
                            <c:otherwise>
                              <a href="${path}/favorites/add?bookId=${book.id}">
                                <img
                                  class="favorite-icon"
                                  src="${path}/images/favorite_outline.svg"
                                >
                              </a>
                            </c:otherwise>
                          </c:choose>
                            

                        </td>
                      </tr>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

        </main>
      </div>
    </div>
    
    <div id="summary-modal" class="modal">
      <div class="modal-content">
        <h5 class='modal-title'>Book Title</h5>
        <p class='modal-summary'>Book Summary</p>
      </div>
      <div class="modal-footer">
        <a href="#" class="modal-close waves-effect waves-green btn-flat">Close</a>
      </div>
    </div>
    
    <script src="${path}/js/materialize.min.js"></script>
    <script defer>
    

      
      document.addEventListener('DOMContentLoaded', function() {
        const tabEl = document.querySelector('.tabs');
        const bookTable = document.querySelector('#books-table');
        
        const summaryModal = document.querySelector('#summary-modal');
        
        M.Tabs.init(tabEl, {duration: 300});
        M.Modal.init(summaryModal);
        
        const modal = M.Modal.getInstance(summaryModal);
        
        bookTable.addEventListener('click', (e) => {
          const target = e.target.closest('tr');
          const bookTitle = target.querySelector('.title').textContent;
          const bookSummary = target.querySelector('.summary').textContent;
          
          const modalHeaderEl = summaryModal.querySelector('.modal-title');
          const modalContentEl = summaryModal.querySelector('.modal-summary');
          
          console.log(modalHeaderEl);
          
          modalHeaderEl.innerText = 'Summary for ' + bookTitle;
          modalContentEl.innerText = bookSummary;
          
          modal.open();
        });
        
        const noPropagationLinks = [...document.querySelectorAll(".no-propagation")];
        
        noPropagationLinks.forEach(link => link.addEventListener("click", (e) => {
          e.stopPropagation();
        }));

      });

    </script>

  </body>
</html>
