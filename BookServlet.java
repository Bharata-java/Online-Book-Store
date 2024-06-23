package servlet;

import model.Book;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dao.BookDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;
    public void init() {
        bookDAO = new BookDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = bookDAO.getAllBooks();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Books</title></head><body>");
        out.println("<h1>Available Books</h1>");
        out.println("<table border='1'><tr><th>Title</th><th>Author</th><th>Price</th><th>Description</th><th>Action</th></tr>");
        for (Book book : books) {
            out.println("<tr><td>" + book.getTitle() + "</td><td>" + book.getAuthor() + "</td><td>"
           + book.getPrice() + "</td><td>" );
            out.println("<td><a href='cart?action=add&bookId=" + book.getId() + "'>Add to Cart</a></td></tr>");
        }
        out.println("</table>");
        out.println("</body></html>");
    }
}
