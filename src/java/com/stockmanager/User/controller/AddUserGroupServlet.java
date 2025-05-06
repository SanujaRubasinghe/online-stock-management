/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.stockmanager.User.controller;

import com.stockmanager.User.dao.UserGroupDAO;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.sql.DataSource;

/**
 *
 * @author sanuja
 */
public class AddUserGroupServlet extends HttpServlet {

    @Resource(name="jdbc/inventorydb")
    private DataSource dataSource;
    
    private UserGroupDAO userGroupDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userGroupDAO = new UserGroupDAO(dataSource);
    }
            
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String groupName = request.getParameter("group-name");
        String accessLevel = request.getParameter("access-level");
        
        boolean isAdded = userGroupDAO.addUserGroup(groupName, accessLevel);
        
        if (isAdded) {
            response.sendRedirect("user-groups");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            request.setAttribute("errorMsg", "Failed to add user group to the database");
            dispatcher.forward(request, response);
        }
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
