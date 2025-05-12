package com.StockManagement.InventoryServlet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "InventoryFilter", urlPatterns = {"/InventoryFilter"})
public class InventoryFilter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            search(request, response);
        } catch (ParseException e) {
            throw new ServletException("Date parsing error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            generatePdfReport(request, response);
        } catch (Exception e) {
            throw new ServletException("PDF generation error", e);
        }
    }

    private void generatePdfReport(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, DocumentException, IOException, ServletException {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("MySQL JDBC Driver not found", e);
        }

        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String category = request.getParameter("category");
        String date_from = request.getParameter("date-from");
        String date_to = request.getParameter("date-to");
        String stockParam = request.getParameter("Stock");
        String unitPriceParam = request.getParameter("Unit_Price");

        int Stock = -1;
        double Unit_Price = -1.0;

        try {
            if (stockParam != null && !stockParam.trim().isEmpty()) {
                Stock = Integer.parseInt(stockParam);
            }
            if (unitPriceParam != null && !unitPriceParam.trim().isEmpty()) {
                Unit_Price = Double.parseDouble(unitPriceParam);
            }
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number format for Stock or Unit Price", e);
        }

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/supplier_db", "root", "Safiya@123")) {
            
            StringBuilder sql = new StringBuilder("SELECT * FROM supplier_products WHERE 1=1");
            
            if (productId != null && !productId.isEmpty()) {
                sql.append(" AND product_id = ?");
            }
            if (productName != null && !productName.isEmpty()) {
                sql.append(" AND product_name LIKE ?");
            }
            if (category != null && !category.isEmpty()) {
                sql.append(" AND category = ?");
            }
            if (date_from != null && !date_from.isEmpty() && 
                date_to != null && !date_to.isEmpty()) {
                sql.append(" AND added_date BETWEEN ? AND ?");
            }
            if (Stock >= 0) {
                sql.append(" AND Stock BETWEEN 0 AND ?");
            }
            if (Unit_Price >= 0) {
                sql.append(" AND Unit_Price BETWEEN 0.0 AND ?");
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                int paramIndex = 1;
                
                if (productId != null && !productId.isEmpty()) {
                    ps.setString(paramIndex++, productId);
                }
                if (productName != null && !productName.isEmpty()) {
                    ps.setString(paramIndex++, "%" + productName + "%");
                }
                if (category != null && !category.isEmpty()) {
                    ps.setString(paramIndex++, category);
                }
                if (date_from != null && !date_from.isEmpty() && 
                    date_to != null && !date_to.isEmpty()) {
                    ps.setString(paramIndex++, date_from + " 00:00:00");
                    ps.setString(paramIndex++, date_to + " 23:59:59");
                }
                if (Stock >= 0) {
                    ps.setInt(paramIndex++, Stock);
                }
                if (Unit_Price >= 0) {
                    ps.setDouble(paramIndex++, Unit_Price);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    response.setContentType("application/pdf");

                    Document document = new Document(PageSize.A4);
                    PdfWriter.getInstance(document, response.getOutputStream());
                    
                    document.open();
                    
                    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLUE);
                    Paragraph title = new Paragraph("Product Inventory Report", titleFont);
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);
                    document.add(new Paragraph(" "));

                    PdfPTable table = new PdfPTable(7);
                    table.setWidthPercentage(100);
                    table.setSpacingBefore(10f);
                    table.setSpacingAfter(10f);

                    Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
                    String[] headers = {"Product ID", "Product Name", "Category", 
                                      "Stock", "Unit Price", "Added Date", "Status"};
                    
                    for (String header : headers) {
                        PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    }

                    while (rs.next()) {
                        table.addCell(rs.getString("product_id"));
                        table.addCell(rs.getString("product_name"));
                        table.addCell(rs.getString("category"));
                        table.addCell(String.valueOf(rs.getInt("Stock")));
                        table.addCell(String.valueOf((float) rs.getDouble("Unit_Price")));
                        table.addCell(rs.getTimestamp("added_date").toString());
                        table.addCell(rs.getInt("Status") == 1 ? "Active" : "Inactive");
                    }

                    document.add(table);
                    document.close();
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }

    private void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("MySQL JDBC Driver not found", e);
        }
        
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/supplier_db", "root", "Safiya@123")) {

            String productId = request.getParameter("productId");
            String productName = request.getParameter("productName");
            String category = request.getParameter("category");
            String date_from = request.getParameter("date-from");
            String date_to = request.getParameter("date-to");
            String stockParam = request.getParameter("Stock");
            String unitPriceParam = request.getParameter("Unit_Price");

            int Stock = -1;
            double Unit_Price = -1.0;

            try {
                if (stockParam != null && !stockParam.trim().isEmpty()) {
                    Stock = Integer.parseInt(stockParam);
                }
                if (unitPriceParam != null && !unitPriceParam.trim().isEmpty()) {
                    Unit_Price = Double.parseDouble(unitPriceParam);
                }
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid number format for Stock or Unit Price", e);
            }

            StringBuilder sql = new StringBuilder("SELECT * FROM supplier_products WHERE 1=1");

            if (productId != null && !productId.isEmpty()) {
                sql.append(" AND product_id = ?");
            }
            if (productName != null && !productName.isEmpty()) {
                sql.append(" AND product_name LIKE ?");
            }
            if (category != null && !category.isEmpty()) {
                sql.append(" AND category = ?");
            }
            if (date_from != null && !date_from.isEmpty() &&
                    date_to != null && !date_to.isEmpty()) {
                sql.append(" AND added_date BETWEEN ? AND ?");
            }
            if (Stock >= 0) {
                sql.append(" AND Stock BETWEEN 0 AND ?");
            }
            if (Unit_Price >= 0) {
                sql.append(" AND Unit_Price BETWEEN 0.0 AND ?");
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                int paramIndex = 1;

                if (productId != null && !productId.isEmpty()) {
                    ps.setString(paramIndex++, productId);
                }
                if (productName != null && !productName.isEmpty()) {
                    ps.setString(paramIndex++, "%" + productName + "%");
                }
                if (category != null && !category.isEmpty()) {
                    ps.setString(paramIndex++, category);
                }
                if (date_from != null && !date_from.isEmpty() &&
                        date_to != null && !date_to.isEmpty()) {
                    ps.setString(paramIndex++, date_from + " 00:00:00");
                    ps.setString(paramIndex++, date_to + " 23:59:59");
                }
                if (Stock >= 0) {
                    ps.setInt(paramIndex++, Stock);
                }
                if (Unit_Price >= 0) {
                    ps.setDouble(paramIndex++, Unit_Price);
                }

                ResultSet rs = ps.executeQuery();
                request.setAttribute("results", rs);
                request.getRequestDispatcher("/inventoryReport.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }
}