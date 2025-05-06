/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.stockmanager.User.controller;

import com.stockmanager.User.dao.UserGroupDAO;
import com.stockmanager.User.model.UserGroup;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

/**
 *
 * @author sanuja
 */
public class ShowUserGroupsServlet extends HttpServlet {

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
        
        List<UserGroup> userGroupList = new ArrayList<>();
        userGroupList = userGroupDAO.getUserGroups();
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-groups.jsp");
        request.setAttribute("userGroupList", userGroupList);
        dispatcher.forward(request, response);
        
    }

   
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
