/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockmanager.User.dao;

import com.stockmanager.User.model.UserGroup;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author sanuja
 */
public class UserGroupDAO {
    private DataSource dataSource;
    
    public UserGroupDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public List<UserGroup> getUserGroups() {
        List<UserGroup> userGroupList = new ArrayList<>();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        try (Connection conn = dataSource.getConnection()) {
            String sql = "select * from user_groups";
            Statement stmt = conn.createStatement();
            
            ResultSet results = stmt.executeQuery(sql);
            while(results.next()) {
                
                int id = results.getInt("id");
                String groupName = results.getString("groupName");
                String accessLevel = results.getString("accessLevel");
                
                Timestamp timestamp = results.getTimestamp("dateAdded");
                String createDate;
                LocalDateTime lastlogin = timestamp.toLocalDateTime();
                createDate = lastlogin.format(formatter);
                
                userGroupList.add(new UserGroup(id, groupName, accessLevel, createDate));
            }
            
            return userGroupList;
        } catch (SQLException e) {
            e.printStackTrace();
            return userGroupList;
        }
    }
    
    public boolean addUserGroup(String groupName, String accessLevel) {
        try (Connection conn = dataSource.getConnection()) {
            
            String sql = "insert into user_groups(groupName, accessLevel) values(?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, groupName);
            stmt.setString(2, accessLevel);
            
            int results = stmt.executeUpdate();
            return results > 0;
            
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
}
