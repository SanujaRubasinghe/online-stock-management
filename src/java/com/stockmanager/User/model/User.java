/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockmanager.User.model;
import java.time.LocalDateTime;

/**
 *
 * @author sanuja
 */
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String role;
    private String activeStatus;
    private String loginDate;

    public User(int id, String firstName, String lastName, String userName, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
    
    public User(String firstName, int id, String lastName, String userName, String role, String activeStatus, String loginDate) {
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.userName = userName;
        this.role = role;
        this.activeStatus = activeStatus;
        this.loginDate = loginDate;
    }

    public User(String firstName, String lastName, String userName, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    public String getLoginDate() {
        return loginDate;
    }
    
    public String getActiveStatus() {
        return activeStatus;
    }
        
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
