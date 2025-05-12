package com.StockManagement.generatePDF;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

@WebServlet("/generate-pdf")
public class GeneratePdfServlet extends HttpServlet {

    private static final String IMAGE_UPLOAD_DIR = "C:/upload/images";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        response.setContentType("application/pdf");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("DB CONNECTED...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supplier_db", "root", "Safiya@123");
            PreparedStatement ps = con.prepareStatement("SELECT sid, name, last_modified_time, supplier_image FROM supplier_c WHERE last_modified_time BETWEEN ? AND ?")
        ) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilFrom = sdf.parse(fromDate);
            java.util.Date utilTo = sdf.parse(toDate);
            ps.setDate(1, new java.sql.Date(utilFrom.getTime()));
            ps.setDate(2, new java.sql.Date(utilTo.getTime()));

            ResultSet rs = ps.executeQuery();

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLUE);
            Paragraph title = new Paragraph("Supplier Report from " + fromDate + " to " + toDate, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            PdfPTable table = new PdfPTable(4);//column 4 i javid
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            String[] headers = {"Supplier ID", "Name", "Last Modified Date", "Supplier Image"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            while (rs.next()) {
                table.addCell(rs.getString("sid"));
                table.addCell(rs.getString("name"));
                table.addCell(rs.getString("last_modified_time"));

                String imageName = rs.getString("supplier_image");
                File imageFile = new File(IMAGE_UPLOAD_DIR + "/" + imageName);

                if (imageFile.exists()) {
                    Image image = Image.getInstance(imageFile.getAbsolutePath());
                    image.scaleToFit(60f, 60f);
                    PdfPCell imageCell = new PdfPCell(image, true);
                    imageCell.setPadding(5);
                    imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    imageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(imageCell);
                } else {
                    table.addCell("No Image");
                }
            }

            document.add(table);
            document.close();
            rs.close();
        } catch (Exception e) {
            throw new ServletException("PDF generation error", e);
        }
    }
}
