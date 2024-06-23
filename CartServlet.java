package servlet;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.User;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private CartDAO cartDAO;

    public void init() {
        cartDAO = new CartDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Cart> cartItems = cartDAO.getCartItems(user.getId());
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Cart</title></head><body>");
            out.println("<h1>Your Cart</h1>");
            out.println("<table border='1'><tr><th>Title</th><th>Author</th><th>Price</th><th>Description</th></tr>");
            for (Cart cart : cartItems) {
                out.println("<tr><td>" + cart.getBook().getTitle() 
             + "</td><td>" + cart.getBook().getAuthor() + "</td><td>" 
           + cart.getBook().getPrice() + "</td><td>" );
            }
            out.println("</table>");
            out.println("</body></html>");
        } else {
        	  
            response.sendRedirect("login.html");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String action = request.getParameter("action");
            if (action.equals("add")) {
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                try {
                    cartDAO.addCartItem(user.getId(), bookId);
                    response.sendRedirect("cart");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            response.sendRedirect("login.html");
        }
    }
}


