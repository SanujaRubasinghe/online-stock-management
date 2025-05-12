package com.StockManagement.supplier_c;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/registration")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
public class Supplier_c extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String UPLOAD_DIR = "C:/upload/images";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().write("Supplier Registration Servlet is running. Use POST to submit data.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setBufferSize(200);

        System.out.println("Starting Supplier Registration...");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String company = request.getParameter("company");
        int experience = Integer.parseInt(request.getParameter("experience"));
        String category = request.getParameter("category");
        String address = request.getParameter("address");
        int Quantity = Integer.parseInt(request.getParameter("Quantity"));
        String sid = request.getParameter("supplierid");
        
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Company: " + company);
        System.out.println("Experience: " + experience);
        System.out.println("Category: " + category);
        System.out.println("Address: " + address);
        System.out.println("Quantity: " + Quantity);

        if (name == null || email == null || phone == null || company == null || experience == 0 || category == null || address == null || Quantity == 0) {
            response.getWriter().write("<h3>❌ Missing required fields!</h3>");
            System.out.println("❌ Missing required fields!");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/supplier_db";
        String user = "root";
        String password = "Safiya@123";
        String sql = "INSERT INTO supplier_c (name, email, phone, company_name, experience, category, address, Quantity, sid, supplier_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        /*MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(url);*/
        
        File directory = new File(UPLOAD_DIR);
        if(!directory.exists()){
            directory.mkdirs();//s is missing
        }
        
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        	response.getWriter().write("<h3>Successfull!!!</h3>");
        } catch (ClassNotFoundException e) {
        	response.getWriter().write("<h3>Jdbc not find</h3>");
        	response.getWriter().write("<h3>" + e.getMessage() + "</h3>");
        }
        
        try{
            Part image = request.getPart("Upload-Image");
            String img_name = Paths.get(image.getSubmittedFileName()).getFileName().toString();

            try(InputStream fileContent = image.getInputStream()) {
                Files.copy(fileContent, Paths.get(UPLOAD_DIR, img_name));
            }
        
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                System.out.println("✅ Database Connected Successfully!");
                response.getWriter().write("<h3>✅ Database Connected Successfully!</h3>");

                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, email);
                    pstmt.setString(3, phone);
                    pstmt.setString(4, company);
                    pstmt.setInt(5, experience);
                    pstmt.setString(6, category);
                    pstmt.setString(7, address);
                    pstmt.setInt(8, Quantity);
                    pstmt.setString(9, sid);
                    pstmt.setString(10, img_name);

                    int row = pstmt.executeUpdate();
                    if (row > 0) {
                        response.getWriter().write("<h3>✅ Insert Successful!</h3>");
                        System.out.println("✅ Insert Successful!");
                    } else {
                        response.getWriter().write("<h3>❌ Insert Failed!</h3>");
                        System.out.println("❌ Insert Failed!");
                    }
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("<h3>❌ Invalid experience value. Please enter a valid number.</h3>");
                e.printStackTrace();
            } catch (SQLException e) {
                response.getWriter().write("<h3>❌ SQL Error: " + e.getMessage() + "</h3>");
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
        }
        response.sendRedirect("http://localhost:8081/Supplier_Management_System1/ProductServlet");
        response.getWriter().write("</body></html>");
    }
}
