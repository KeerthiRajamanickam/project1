package com.kgisl.book.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kgisl.book.entity.Book;
public class BookController extends HttpServlet {
    Book books = null;
    List<Book> lbook = null;
 @Override
 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     doGet(req, resp);
 }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertBook(request, response);
                    break;
                case "/delete":
                    deleteBook(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateBook(request, response);
                    break;
                default:
                    listBook(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    public void listBook(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/bookstore?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "")) {
            lbook = new ArrayList<Book>();
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery("select * from book");
            while (rset.next()) {
                lbook.add(new Book(rset.getInt("book_id"), rset.getString("title"), rset.getString("author"),
                        rset.getFloat("price")));
            }
            // String s = "BatMan";
            // req.setAttribute("s", s);
            stmt.close();
            con.close();
            req.setAttribute("lbook", lbook);
            RequestDispatcher dis = req.getRequestDispatcher("booklist.jsp");
            dis.forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("bookform.jsp");
        dispatcher.forward(request, response);
    }
    public void insertBook(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/bookstore?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "")) {
            String title = req.getParameter("title");
            String author = req.getParameter("author");
            float price = Float.parseFloat(req.getParameter("price"));
            books = new Book(title, author, price);
            String sql = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, books.getTitle());
            statement.setString(2, books.getAuthor());
            statement.setFloat(3, books.getPrice());
            statement.executeUpdate();
            statement.close();
            con.close();
            resp.sendRedirect("/list");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteBook(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/bookstore?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "")) {
            int id = Integer.parseInt(req.getParameter("id"));
            books = new Book(id);
            String sql = "DELETE FROM book where book_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, books.getId());
            statement.executeUpdate();
            statement.close();
            con.close();
            resp.sendRedirect("/list");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateBook(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/bookstore?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "")) {
            int id = Integer.parseInt(req.getParameter("id"));
            String title = req.getParameter("title");
            String author = req.getParameter("author");
            float price = Float.parseFloat(req.getParameter("price"));
            books = new Book(id, title, author, price);
            String sql = "UPDATE book SET title = ?, author = ?, price = ?";
            sql += " WHERE book_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, books.getTitle());
            statement.setString(2, books.getAuthor());
            statement.setFloat(3, books.getPrice());
            statement.setInt(4, books.getId());
            statement.executeUpdate();
            statement.close();
            con.close();
            resp.sendRedirect("/list");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/bookstore?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Book book = null;
            String sql = "SELECT * FROM book WHERE book_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                float price = resultSet.getFloat("price");
                book = new Book(id, title, author, price);
            }
            resultSet.close();
            statement.close();
            con.close();
            RequestDispatcher dispatcher = request.getRequestDispatcher("bookform.jsp");
            request.setAttribute("book", book);
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}