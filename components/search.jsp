<% 
  String sTitle = request.getParameter("title") != null ? request.getParameter("title") : "";
  String sAuthor = request.getParameter("author") != null ? request.getParameter("author") : "";
  String sPublisher = request.getParameter("publisher") != null ? request.getParameter("publisher") : "";
  String sPublishedDate = request.getParameter("published_date") != null ? request.getParameter("published_date") : "";
  String sGenre = request.getParameter("genre") != null ? request.getParameter("genre") : "";
  String sKeywords = request.getParameter("keywords") != null ? request.getParameter("keywords") : "";
%>

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