/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.helper;

/**
 *
 * @author david
 */
public class JwtProperties {
    
    private int id;
    private String role;
    
    public JwtProperties(String id, String role) {
        this.id = Integer.parseInt(id);
        this.role = role;
    }

    public JwtProperties(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }
    
    public String getStringId() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setStringId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
