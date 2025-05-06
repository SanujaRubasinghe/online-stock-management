/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.stockmanager.User.controller;

import com.stockmanager.User.dao.UserDAO;
import com.stockmanager.User.model.User;
import java.util.*;
import javax.annotation.Resource;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sanuja
 */
public class ShowUsersServlet extends HttpServlet {
    
    @Resource(name="jdbc/inventorydb")
    private DataSource dataSource;
    
    private UserDAO userDAO;
    private List<User> userList;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO(dataSource);
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShowUsersServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShowUsersServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        userList = userDAO.getUsers();
        request.setAttribute("userList", userList);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/show-users.jsp");
        dispatcher.forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
