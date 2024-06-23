package servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import dao.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
  
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("register")) {
            registerUser(request, response);
        } else if (action.equals("login")) {
            loginUser(request, response);
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        try {
            userDAO.registerUser(user);
            response.sendRedirect("login.html");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
    PrintWriter pw=response.getWriter();
        User user = userDAO.loginUser(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("books");
        } else {
        	
        	pw.println(("<h1 bgcolor='black'> Please provide valid  Credidantals <h1>"));
        	RequestDispatcher rd=request.getRequestDispatcher("login.html");
        	try {
				rd.include(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
        }
        }
        }
    }


