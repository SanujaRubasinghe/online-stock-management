package com.StockManagement.SupplierView;

import com.StockManagement.SuppliersList.Supplier;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SupplierServlet")
public class SupplierServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/supplier_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "Safiya@123";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            switch (action == null ? "list" : action) {
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteSupplier(request, response);
                    break;
                default:
                    listSuppliers(request, response);
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("update".equals(action)) {
                updateSupplier(request, response);
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listSuppliers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Supplier> supplierList = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
                String sql = "SELECT * FROM supplier_c";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    Supplier supplier = new Supplier();
                    supplier.setSupplierId(rs.getString("sid"));
                    supplier.setName(rs.getString("name"));
                    supplier.setEmail(rs.getString("email"));
                    supplier.setPhone(rs.getString("phone"));
                    supplier.setCompany(rs.getString("company_name"));
                    supplier.setCategory(rs.getString("category"));
                    supplier.setQuantity(rs.getInt("Quantity"));
                    supplier.setStatus();
                    supplierList.add(supplier);
                }

                request.setAttribute("supplierList", supplierList);
                request.getRequestDispatcher("suppliers.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error listing suppliers", e);
        }
    }

    private void deleteSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
                String sql = "DELETE FROM supplier_c WHERE sid = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error deleting supplier", e);
        }

        response.sendRedirect("SupplierServlet");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
                String sql = "SELECT * FROM supplier_c WHERE sid = ? ;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Supplier supplier = new Supplier();
                    supplier.setSupplierId(rs.getString("sid"));
                    supplier.setName(rs.getString("name"));
                    supplier.setEmail(rs.getString("email"));
                    supplier.setPhone(rs.getString("phone"));
                    supplier.setCompany(rs.getString("company_name"));
                    supplier.setCategory(rs.getString("category"));
                    supplier.setQuantity(rs.getInt("Quantity"));
                    supplier.setStatus();

                    request.setAttribute("supplier", supplier);
                    request.getRequestDispatcher("editSupplier.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error loading supplier for edit", e);
        }
    }

    private void updateSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("sid");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String company = request.getParameter("company");
        String category = request.getParameter("category");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
                String sql = "UPDATE supplier_c SET name=?, email=?, phone=?, company_name=?, category=?, Quantity=? WHERE sid=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setString(4, company);
                ps.setString(5, category);
                ps.setInt(6, quantity);
                ps.setString(7, id);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error updating supplier", e);
        }

        response.sendRedirect("SupplierServlet");
    }
}
