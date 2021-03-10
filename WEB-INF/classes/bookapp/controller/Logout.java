package bookapp.controller;

import java.io.IOException;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;


public class Logout extends HttpServlet {
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {

    HttpSession session=request.getSession();  
    session.invalidate(); 

    response.sendRedirect(request.getContextPath() + "/login.jsp");
	}
}