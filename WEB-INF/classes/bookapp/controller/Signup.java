package bookapp.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import bookapp.database.UserDB;
import bookapp.bean.User;

public class Signup extends HttpServlet {
  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    
    UserDB db = new UserDB();

    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String role = "user";

    User newUser = new User(
      name,
      email,
      password,
      role
    );

    boolean isUserAdded = db.addUser(newUser);

		if(isUserAdded) {
      HttpSession session = request.getSession();
      session.setAttribute("name", newUser.name);
      session.setAttribute("role", newUser.role);
      response.sendRedirect(request.getContextPath() + "/books");
      return;
    }

    response.sendRedirect(request.getContextPath() + "/signup.jsp");
	}
}