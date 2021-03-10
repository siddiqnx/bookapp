package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.UserDB;
import bookapp.bean.User;

public class Login extends HttpServlet {
  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    UserDB db = new UserDB();

    String email = (String)request.getParameter("email");
    String password = (String)request.getParameter("password");

    Optional<User> userOpt = db.verifyUser(email, password);

    if(userOpt.isPresent()) {
      User user = userOpt.get();
      HttpSession session = request.getSession();
      session.setAttribute("userId", user.id);
      session.setAttribute("name", user.name);
      session.setAttribute("role", user.role);
      response.sendRedirect(request.getContextPath() + "/books");
      return;
    }

    response.sendRedirect(request.getContextPath() + "/login.jsp");
	}
}