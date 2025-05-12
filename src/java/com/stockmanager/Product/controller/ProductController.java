/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.stockmanager.Product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stockmanager.Product.model.Product;
import com.stockmanager.Product.service.ProductService;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.sql.DataSource;

/**
 *
 * @author sanuja
 */
public class ProductController extends HttpServlet {
    
    @Resource(name="jdbc/inventorydb")
    private DataSource dataSource;
    
    private ProductService service;

    @Override
    public void init() throws ServletException {
        super.init();
        this.service = new ProductService(dataSource);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "list";
        
        switch (action) {
            case "getById": 
                String pc = request.getParameter("id");
                Product p = service.getById(pc);
                
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                
                String json = String.format(
                        "{\"product_code\":\"%s\",\"name\":\"%s\",\"desc\":\"%s\",\"price\":%.2f, \"quantity\":%d}",
                        p.getProductCode(),p.getName(),p.getDescription(),p.getPrice(),p.getQuantity()
                );
                
                out.print(json);
                out.flush();
                break;
            case "add-product":
                request.getRequestDispatcher("add-product-form.jsp").forward(request, response);
                break;
            case "edit-product":
                String id = request.getParameter("id");
                Product product = service.getById(id);
                request.setAttribute("product", product);
                request.getRequestDispatcher("edit-product.jsp").forward(request, response);
                break;
            case "delete-product":
                String delid = request.getParameter("id");
                service.delete(delid);
                response.sendRedirect("ProductController");
                break;
            default:
                List<Product> list = service.getAll();
                request.setAttribute("products", list);
                RequestDispatcher dispatcher = request.getRequestDispatcher("products.jsp");
                dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        String productCode;
        String name;
        String desc;
        double price;
        int quantity; 
        int stockLowLimit;
        
        Product product;
        
        switch (action) {
            case "add":
                productCode = request.getParameter("product-code");
                name = request.getParameter("name");
                desc = request.getParameter("desc");
                price = Double.parseDouble(request.getParameter("price"));
                quantity = Integer.parseInt(request.getParameter("quantity"));
                stockLowLimit = Integer.parseInt(request.getParameter("stock-low-limit"));

                product = new Product(productCode, name, desc, price, quantity, stockLowLimit);
                service.save(product);
                break;
            case "update":
                productCode = request.getParameter("product-code");
                name = request.getParameter("name");
                desc = request.getParameter("desc");
                price = Double.parseDouble(request.getParameter("price"));
                quantity = Integer.parseInt(request.getParameter("quantity"));
                stockLowLimit = Integer.parseInt(request.getParameter("stock-low-limit"));

                product = new Product(productCode, name, desc, price, quantity, stockLowLimit);
                service.update(product);
                break;
            case "delete-selected":
                String[] selectedProducts = request.getParameterValues("selectedProducts");
                
                if (selectedProducts != null && selectedProducts.length > 0) {
                    for (String pid: selectedProducts) {
                        if (pid != null && !pid.trim().isEmpty()) {
                            service.delete(pid);
                        }
                    }
                }
                break;
            default:
                response.sendRedirect("ProductController");     
        } 
        
        response.sendRedirect("ProductController");
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
