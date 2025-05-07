/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockmanager.User.dao;

import com.stockmanager.User.controller.SessionListener;
import com.stockmanager.User.model.User;

import java.util.List;
import java.util.ArrayList;
import javax.sql.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author sanuja
 */
public class UserDAO{
    private DataSource dataSource;
    
    public UserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public List<User> getUsers() {
        
        List<User> usersList = new ArrayList<>();
        
        try (Connection conn = dataSource.getConnection()) {
            String sql = "select * from users";
            Statement stmt = conn.createStatement();
            
            ResultSet results = stmt.executeQuery(sql);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            while (results.next()) {
                int id = results.getInt("id");
                String firstName = results.getString("first_name");
                String lastName = results.getString("last_name");
                String userName = results.getString("user_name");
                String role = results.getString("role");                
                boolean isActive = SessionListener.isUserOnline(userName);
                
                String loginStatus;
                
                if (isActive) {
                    loginStatus = "Online";
                } else {
                    loginStatus = "Offline";
                }
                
                Timestamp timestamp = results.getTimestamp("last_login_date");
                String lastLoginDateTime;
                
                if (timestamp != null) {
                    LocalDateTime lastlogin = timestamp.toLocalDateTime();
                    lastLoginDateTime = lastlogin.format(formatter);
                } else {
                    lastLoginDateTime = "-";
                }    
                
                usersList.add(new User(firstName, id, lastName, userName, role, loginStatus, lastLoginDateTime));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return usersList;
        }
    }
    
    public boolean addUser(User user) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "insert into users(first_name, last_name, user_name, password, role)"
                    + "values(?,?,?,?,?)";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getUserName());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            
            int rowsInserted = stmt.executeUpdate();
            
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteUser(int userId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "delete from users where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            
            int rowsAffected = stmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean validateUser(User user) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "select * from users where user_name = ? and password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            
            ResultSet results = stmt.executeQuery();
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void setLoginTime(String username) {
        LocalDateTime loginDate = LocalDateTime.now();
        
        String sql = "update users set last_login_date = ? where user_name = ?";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(loginDate));
            stmt.setString(2, username);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
