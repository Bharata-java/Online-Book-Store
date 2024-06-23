package dao;




import model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
	 private String jdbcURL = "jdbc:oracle:thin:@localhost :1521:xe";
	    private String jdbcUsername = "system";
	    private String jdbcPassword = "tiger";

    private static final String SELECT_ALL_BOOKS = "SELECT * FROM books";

    protected Connection getConnection() {
        Connection connection = null;
        try {
        	Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                books.add(new Book(id, title, author, price));
                System.out.println("7");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}



