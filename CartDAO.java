package dao;

import model.Cart;
import model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
	 private String jdbcURL = "jdbc:oracle:thin:@localhost :1521:xe";
	    private String jdbcUsername = "system";
	    private String jdbcPassword = "tiger";

    private static final String INSERT_CART_SQL = "INSERT INTO cart (user_id, book_id) VALUES (?, ?)";
    private static final String SELECT_CART_ITEMS_BY_USER_ID = "SELECT c.id, b.id as book_id, b.title, b.author, b.price" +
                                                                "FROM cart c JOIN books b ON c.book_id = b.id WHERE c.user_id = ?";

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

    public void addCartItem(int userId, int bookId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CART_SQL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cart> getCartItems(int userId) {
        List<Cart> cartItems = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CART_ITEMS_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
               
                Book book = new Book(bookId, title, author, price);
                cartItems.add(new Cart(id, userId, book));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
}
