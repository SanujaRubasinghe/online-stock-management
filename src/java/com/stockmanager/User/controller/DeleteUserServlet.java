/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.stockmanager.User.controller;

import com.stockmanager.User.dao.UserDAO;

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
public class DeleteUserServlet extends HttpServlet {

    private String userIdStr;
    private int userId;
    private UserDAO userDAO;
    
    @Resource(name="jdbc/inventorydb")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init(); 
        userDAO = new UserDAO(dataSource);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        userIdStr = request.getParameter("id");
        userId = Integer.parseInt(userIdStr);
        
        boolean isDeleted = userDAO.deleteUser(userId);
        if (isDeleted) {
            response.sendRedirect("show-users");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            request.setAttribute("error", "Failed to delete User");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
