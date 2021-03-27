<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="path" scope="session" value="${pageContext.request.contextPath}"/>
<c:set var="queryString" scope="session" value="${pageContext.request.getQueryString()}"/>

<% 
  String role = (String)session.getAttribute("role");
  String order = request.getParameter("order");
 
  if(order != null && order.equals("asc")) order = "desc";
  else order = "asc";
%>

<main id="books-table-container" class="col <%= (role != null && role.equals("admin")) ? "s9" : "s12" %>">
  <!-- Tabs -->
  <div class="row">
    <div class="col s12 tab-bar">
      <ul class="tabs">
        <li class="tab col s3"><a class="active" href="#all-books">All Books</a></li>
        <li class="tab col s3"><a href="#favorites">Favorites</a></li>
        <li class="tab col s3"><a href="#library">Library</a></li>
      </ul>
    </div>
    <div id="all-books" class="col s12">
      
      <jsp:include page="search.jsp" />
      
      <div class="add-collection-container hide row">
        <form action="${path}/collections/add" id="add-collection" method="GET">
          <div class="row valign-wrapper">
          <div class="col s6"></div>
            <div class="valign input-field col s3 right">
              <input name="collection_name" id="collection-name" type="text" class="validate">
              <label for="collection-name">Collection Name</label>
            </div>
            <div class="valign input-field col s3 right">
              <button type="submit" class="add-collection-btn waves-effect waves-light btn">Add to Collection</button>
            </div>
          </div>
        </form>
      </div>
      
      <div class="row">
        <table id="books-table" class="col s12">
          <thead>
            <tr>
                <th></th>
                <th><a href="${path}/books?sortby=title&order=<%= order %>">Title</a></th>
                <th><a href="${path}/books?sortby=author&order=<%= order %>">Author</a></th>
                <th><a href="${path}/books?sortby=publisher&order=<%= order %>">Publisher</a></th>
                <th><a href="${path}/books?sortby=published_date&order=<%= order %>">Published Date</a></th>
                <th><a href="${path}/books?sortby=genre&order=<%= order %>">Genre</a></th>
            </tr>
          </thead>

          <tbody>
            <c:forEach items="${books}" var="book">
              <tr id="${book.id}">
                <td class="book-checkbox">
                  <label>
                    <input class="book-checkbox" class="no-propagation" form="add-collection"
                      type="checkbox" name="selected_books"
                      value="${book.id}"/>
                      <span class="book-checkbox" class="no-propagation"></span>
                  </label>
                </td>
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
        <ul class="pagination col s12">
          <c:choose>
            <c:when test="${currentPageNum != 1}">
                <li class="waves-effect"><a href="${path}/books?page=${currentPageNum - 1}"><img src="${path}/images/chevron_left.svg"></a></li>
            </c:when>
            <c:otherwise>
                <li class="disabled"><a href="#!"><img src="${path}/images/chevron_left.svg"></a></li>
            </c:otherwise>
          </c:choose>
          
          <c:forEach begin="1" end="${numPages}" var="i">
            <c:choose>
                <c:when test="${currentPageNum == i}">
                    <li class="active"><a href="#!">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li class="waves-effect"><a href="${path}/books?page=${i}">${i}</a></li>
                </c:otherwise>
            </c:choose>
          </c:forEach>
          
          <c:choose>
            <c:when test="${currentPageNum != numPages}">
                <li class="waves-effect"><a href="${path}/books?page=${Integer.parseInt(currentPageNum) + 1}"><img src="${path}/images/chevron_right.svg"></a></li>
            </c:when>
            <c:otherwise>
                <li class="disabled"><a href="#!"><img src="${path}/images/chevron_right.svg"></a></li>
            </c:otherwise>
          </c:choose>
        </ul>

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
    
    <div id="library" class="col s12">
      <c:if test="${role == 'admin'}">
        <ul class="collapsible popout">
          <li>
            <div class="collapsible-header">Pending Requests</div>
            <div class="collapsible-body">
              <c:choose>
              <c:when test="${collectionRequests.size() > 0}">
                <ul class="collection">
                  <c:forEach items="${collectionRequests}" var="request">
                    <li class="collection-item">
                      ${request.userName} is requesting access to ${request.collectionName}
                      <a href="${path}/collections/action?actionType=allow&userId=${request.userId}&collectionId=${request.collectionId}" class="secondary-content">
                        <img src="images/tick.svg">
                      </a>
                      <a href="${path}/collections/action?actionType=reject&userId=${request.userId}&collectionId=${request.collectionId}" class="secondary-content">
                        <img src="images/close.svg">
                      </a>
                    </li>
                  </c:forEach>
                </ul>
              </c:when>
              <c:otherwise>
                <p>You have no pending requests</p>
              </c:otherwise>
              </c:choose>
            </div>
          </li>
        </ul>
      </c:if>
      
      <h5>All Collections</h5>
      
      <ul class="collapsible popout">
        <c:forEach items="${collections}" var="collection">
          <li>
            <div class="collapsible-header">${collection.name}</div>
            <div class="collapsible-body row">
              <c:choose>
              
                <c:when test="${collection.access.equals('allow')}">
                  <table id="books-table" class="col s12">
                    <thead>
                      <tr>
                          <th>Title</th>
                          <th>Author</th>
                          <th>Publisher</th>
                          <th>Published Date</th>
                          <th>Genre</th>
                      </tr>
                    </thead>

                    <tbody>
                    
                      <c:forEach items="${collection.books}" var="book">
                        
                        <tr id="${book.id}">
                          <td class='title'>${book.title}</td>
                          <td class='summary hide'>${book.summary}</td>
                          <td>${book.author}</td>
                          <td>${book.publisher}</td>
                          <td>${book.publishedDate}</td>
                          <td>${book.genre}</td>
                          <c:if test="${role == 'admin'}">
                            <td><a class='no-propagation' href="${path}/collections/books/delete?collectionId=${collection.id}&bookId=${book.id}"><img class="delete-icon" src="${path}/images/close.svg" ></a></td>
                          </c:if>
                        </tr>

                      </c:forEach>
                    </tbody>
                  </table>
                </c:when>
                
                <c:when test="${collection.access.equals('request')}">
                  You have requested access to this collection. Please wait for admin's approval.
                </c:when>
                
                
                <c:otherwise>
                  <a href="${path}/collections/request?collectionId=${collection.id}" class="waves-effect waves-light btn">Request Access</a>
                </c:otherwise>
                
              </c:choose>

            </div>
          </li>
        </c:forEach>
      </ul>
    </div>

  </div>

</main>