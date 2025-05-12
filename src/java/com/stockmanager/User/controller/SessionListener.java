/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.stockmanager.User.controller;

import javax.servlet.annotation.WebListener;

import java.util.HashSet;
import java.util.Collections;
import java.util.Set;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author sanuja
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    private static final Set<String> activeUsers = Collections.synchronizedSet(new HashSet<>());
    
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String userName = (String) event.getSession().getAttribute("username");
        if (userName != null) {
            activeUsers.remove(userName);
        }
    }
    
    public static void addUser(String userName) {
        activeUsers.add(userName);
    }
    
    public static void removeUser(String userName) {
        activeUsers.remove(userName);
    }
    
    public static boolean isUserOnline(String username) {
        return activeUsers.contains(username);
    }
    
    public static Set<String> getOnlineUsers() {
        return new HashSet<>(activeUsers);
    }
}
