<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
          <select name="genre" class="genre-select">
            <option value="" disabled selected>Choose your option</option>
            <option value="0">+ Create Genre</option>
            <c:forEach items="${genres}" var="genre">
              <option value="${genre.id}">${genre.name}</option>
            </c:forEach>
          </select>
          <label for="genre">Genre</label>
        </div>
      </div>

      <div class="genre-custom-input hide row">
        <div class="input-field col s12">
          <input name="genre_name" id="genre-name" type="text" class="validate">
          <label for="genre-name">New Genre Name</label>
        </div>
      </div>
      <button type="submit" class="waves-effect waves-light btn">Add Book</button>

    </form>
  </div>
</aside>