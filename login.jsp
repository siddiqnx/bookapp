<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="path" scope="session" value="${pageContext.request.contextPath}"/>

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
  
    <div class="layout layout--login">
      <h3>Login to BookApp</h3>

      <!-- Add Book Form -->
      <form id="login" action="${path}/login" method="POST">

        <div class="row">
          <div class="input-field col s12">
            <input name="email" id="email" type="email" class="validate">
            <label for="email">Email ID</label>
          </div>
        </div>

        <div class="row">
          <div class="input-field col s12">
            <input name="password" id="password" type="password" class="validate">
            <label for="password">Password</label>
          </div>
        </div>
        <div class="row form-footer">
          <a href="${path}/signup.jsp" class="col s6 waves-effect waves-teal btn-flat grey lighten-5">Sign Up</a>
          <button type="submit" class="col s6 waves-effect waves-light btn">Log In</button>
        </div>
      </form>
    </div>
    <script src="${path}/js/materialize.min.js"></script>
  </body>
</html>
