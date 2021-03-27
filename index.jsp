<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="path" scope="session" value="${pageContext.request.contextPath}"/>
<c:set var="queryString" scope="session" value="${pageContext.request.getQueryString()}"/>
<c:set var="genres" value="${genres}" scope="request"/>

<%
  String role = (String)session.getAttribute("role");
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
      <jsp:include page="components/header.jsp" />

      <div class="row">

      <c:if test="${role == 'admin'}">
        <c:import url="components/sidebar.jsp"/>
      </c:if>

        <c:import url="components/main.jsp" />
      </div>
    </div>

    <jsp:include page="components/modals.jsp" />

    <script src="${path}/js/materialize.min.js"></script>
    <script src="${path}/js/script.js" defer></script>
    
  </body>
</html>
