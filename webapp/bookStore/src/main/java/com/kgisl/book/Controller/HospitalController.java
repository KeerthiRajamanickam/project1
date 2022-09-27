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
import com.kgisl.book.entity.Hospital;
public class HospitalController extends HttpServlet {
    Hospital hospitals = null;
    List<Hospital> lhospital = null;
 @Override
 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     doGet(req, resp);
     System.out.println("do post method");
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
                    insertHospital(request, response);
                    break;
                case "/delete":
                    //deleteHospital(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateHospital(request, response);
                    break;
                default:
                    listHospital(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    public void listHospital(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/hospital_management?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "")) {
            ArrayList<Hospital> hospital = new ArrayList<Hospital>();
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery("select * from hospital");
            while (rset.next()) {
                hospital.add(new Hospital(rset.getString("hospital_name"), rset.getString("location"),
                        rset.getString("contno"),rset.getString("website")));
            }
            // String s = "BatMan";
            // req.setAttribute("s", s);
            stmt.close();
            con.close();
            req.setAttribute("hospital", hospital);
            RequestDispatcher dis = req.getRequestDispatcher("hospitallist.jsp");
            dis.forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("hospitalform.jsp");
        dispatcher.forward(request, response);
    }
    public void insertHospital(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/hospital_management?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "")) {
            String hospital_name = req.getParameter("hospital_name");
            String location = req.getParameter("location");
            String contno = req.getParameter("contno");
            String website = req.getParameter("website");
            Hospital hospitals = new Hospital(hospital_name, location, contno, website);
           // books = new Book(title, author, price);
            String sql = "INSERT INTO book (hospital_name,location,contno,website) VALUES (?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, hospitals.gethospital_name());
            statement.setString(2, hospitals.getLocation());
            statement.setString(3, hospitals.getContno());
            statement.setString(4,hospitals.getWebsite());
            statement.executeUpdate();
            statement.close();
            con.close();
            resp.sendRedirect("/list");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // public void deleteHospital(HttpServletRequest req, HttpServletResponse resp)
    //         throws SQLException, IOException, ServletException {
    //     try (Connection con = DriverManager.getConnection(
    //             "jdbc:mysql://127.0.0.1/hospital_management?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
    //             "root", "")) {
    //         int hospital_id = Integer.parseInt(req.getParameter("hospital_id"));
    //         Hospital hospitals = new Hospital(hospital_id());            String sql = "DELETE FROM hospital where hospital_id = ?";
    //         PreparedStatement statement = con.prepareStatement(sql);
    //         statement.setInt(1, hospitals.gethospital_id());
    //         statement.executeUpdate();
    //         statement.close();
    //         con.close();
    //         resp.sendRedirect("/list");
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }
    public void updateHospital(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/hospital_management?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "")) {
            int hospital_id = Integer.parseInt(req.getParameter("hospital_id"));
            String hospital_name = req.getParameter("hospital_name");
            String location = req.getParameter("location");
            String contno = req.getParameter("contno");
            String website = req.getParameter("website");
            hospitals = new Hospital(hospital_name, location, contno, website);
            String sql = "UPDATE hospital SET hospital_name = ?, location = ?, contno = ?, website = ?";
            sql += " WHERE hospital_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, hospitals.gethospital_name());
            statement.setString(2, hospitals.getLocation());
            statement.setString(3, hospitals.getContno());
            statement.setString(4, hospitals.getWebsite());
            statement.setInt(5, hospitals.gethospital_id());
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
                "jdbc:mysql://127.0.0.1/hospital_management?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "")) {
            int hospital_id = Integer.parseInt(request.getParameter("hospital_id"));
            Hospital hospital = null;
            String sql = "SELECT * FROM hospital WHERE hospital_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, hospital_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String hospital_name = resultSet.getString("hospital_name");
                String location = resultSet.getString("location");
                String contno = resultSet.getString("contno");
                String website = resultSet.getString("website");
                hospital = new Hospital(hospital_name, location, contno, website);
            }
            resultSet.close();
            statement.close();
            con.close();
            RequestDispatcher dispatcher = request.getRequestDispatcher("hospitalform.jsp");
            request.setAttribute("hospital", hospital);
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}