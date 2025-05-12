package com.StockManagement.generatePDF;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "SupplierFilter", urlPatterns = {"/SupplierFilter"})
public class SupplierFilter extends HttpServlet {

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

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String companyName = request.getParameter("companyName");
        String category = request.getParameter("category");
        String dateFrom = request.getParameter("date-from");
        String dateTo = request.getParameter("date-to");
        String quantityParam = request.getParameter("quantity");
        String priceParam = request.getParameter("price");

        int quantity = -1;
        double price = -1.0;

        try {
            if (quantityParam != null && !quantityParam.trim().isEmpty()) {
                quantity = Integer.parseInt(quantityParam);
            }
            if (priceParam != null && !priceParam.trim().isEmpty()) {
                price = Double.parseDouble(priceParam);
            }
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number format for Quantity or Price", e);
        }

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/supplier_db", "root", "Safiya@123")) {
            
            StringBuilder sql = new StringBuilder("SELECT * FROM supplier_c WHERE 1=1");
            
            if (name != null && !name.isEmpty()) {
                sql.append(" AND name LIKE ?");
            }
            if (email != null && !email.isEmpty()) {
                sql.append(" AND email LIKE ?");
            }
            if (companyName != null && !companyName.isEmpty()) {
                sql.append(" AND company_name LIKE ?");
            }
            if (category != null && !category.isEmpty()) {
                sql.append(" AND category = ?");
            }
            if (dateFrom != null && !dateFrom.isEmpty() && 
                dateTo != null && !dateTo.isEmpty()) {
                sql.append(" AND last_modified_time BETWEEN ? AND ?");
            }
            if (quantity >= 0) {
                sql.append(" AND Quantity BETWEEN 0 AND ?");
            }
            if (price >= 0) {
                sql.append(" AND Price BETWEEN 0.0 AND ?");
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                int paramIndex = 1;
                
                if (name != null && !name.isEmpty()) {
                    ps.setString(paramIndex++, "%" + name + "%");
                }
                if (email != null && !email.isEmpty()) {
                    ps.setString(paramIndex++, "%" + email + "%");
                }
                if (companyName != null && !companyName.isEmpty()) {
                    ps.setString(paramIndex++, "%" + companyName + "%");
                }
                if (category != null && !category.isEmpty()) {
                    ps.setString(paramIndex++, category);
                }
                if (dateFrom != null && !dateFrom.isEmpty() && 
                    dateTo != null && !dateTo.isEmpty()) {
                    ps.setString(paramIndex++, dateFrom + " 00:00:00");
                    ps.setString(paramIndex++, dateTo + " 23:59:59");
                }
                if (quantity >= 0) {
                    ps.setInt(paramIndex++, quantity);
                }
                if (price >= 0) {
                    ps.setDouble(paramIndex++, price);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    response.setContentType("application/pdf");

                    Document document = new Document(PageSize.A4.rotate());
                    PdfWriter.getInstance(document, response.getOutputStream());
                    
                    document.open();
                    
                    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLUE);
                    Paragraph title = new Paragraph("Supplier Management Report", titleFont);
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);
                    document.add(new Paragraph(" "));

                    PdfPTable table = new PdfPTable(10); // 10 columns
                    table.setWidthPercentage(100);
                    table.setSpacingBefore(10f);
                    table.setSpacingAfter(10f);

                    Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
                    String[] headers = {"Supplier ID", "Name", "Email", "Phone", "Company", 
                                      "Category", "Address", "Quantity", "Price", "Last Modified"};
                    
                    for (String header : headers) {
                        PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    }

                    while (rs.next()) {
                        table.addCell(rs.getString("sid"));
                        table.addCell(rs.getString("name"));
                        table.addCell(rs.getString("email"));
                        table.addCell(rs.getString("phone"));
                        table.addCell(rs.getString("company_name"));
                        table.addCell(rs.getString("category"));
                        table.addCell(rs.getString("address"));
                        table.addCell(String.valueOf(rs.getInt("Quantity")));
                        table.addCell(String.format("%.2f", rs.getDouble("Price")));
                        table.addCell(rs.getTimestamp("last_modified_time").toString());
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
            e.printStackTrace();
        }
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/supplier_db", "root", "Safiya@123")) {

            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String companyName = request.getParameter("companyName");
            String category = request.getParameter("category");
            String dateFrom = request.getParameter("date-from");
            String dateTo = request.getParameter("date-to");
            String quantityParam = request.getParameter("quantity");
            String priceParam = request.getParameter("price");

            int quantity = -1;
            double price = -1.0;

            try {
                if (quantityParam != null && !quantityParam.trim().isEmpty()) {
                    quantity = Integer.parseInt(quantityParam);
                }
                if (priceParam != null && !priceParam.trim().isEmpty()) {
                    price = Double.parseDouble(priceParam);
                }
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid number format for Quantity or Price", e);
            }

            StringBuilder sql = new StringBuilder("SELECT * FROM supplier_c WHERE 1=1");

            if (name != null && !name.isEmpty()) {
                sql.append(" AND name LIKE ?");
            }
            if (email != null && !email.isEmpty()) {
                sql.append(" AND email LIKE ?");
            }
            if (companyName != null && !companyName.isEmpty()) {
                sql.append(" AND company_name LIKE ?");
            }
            if (category != null && !category.isEmpty()) {
                sql.append(" AND category = ?");
            }
            if (dateFrom != null && !dateFrom.isEmpty() &&
                    dateTo != null && !dateTo.isEmpty()) {
                sql.append(" AND last_modified_time BETWEEN ? AND ?");
            }
            if (quantity >= 0) {
                sql.append(" AND Quantity BETWEEN 0 AND ?");
            }
            if (price >= 0) {
                sql.append(" AND Price BETWEEN 0.0 AND ?");
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                int paramIndex = 1;

                if (name != null && !name.isEmpty()) {
                    ps.setString(paramIndex++, "%" + name + "%");
                }
                if (email != null && !email.isEmpty()) {
                    ps.setString(paramIndex++, "%" + email + "%");
                }
                if (companyName != null && !companyName.isEmpty()) {
                    ps.setString(paramIndex++, "%" + companyName + "%");
                }
                if (category != null && !category.isEmpty()) {
                    ps.setString(paramIndex++, category);
                }
                if (dateFrom != null && !dateFrom.isEmpty() &&
                        dateTo != null && !dateTo.isEmpty()) {
                    ps.setString(paramIndex++, dateFrom + " 00:00:00");
                    ps.setString(paramIndex++, dateTo + " 23:59:59");
                }
                if (quantity >= 0) {
                    ps.setInt(paramIndex++, quantity);
                }
                if (price >= 0) {
                    ps.setDouble(paramIndex++, price);
                }

                ResultSet rs = ps.executeQuery();
                request.setAttribute("results", rs);
                request.getRequestDispatcher("/supplierReport.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}