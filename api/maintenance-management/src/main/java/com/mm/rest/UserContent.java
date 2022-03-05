/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 * @author david
 */
public class UserContent {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("kepzes")
    private String kepzes;
    
    @JsonProperty("password")
    private String password;
    
    public UserContent() {
    }

    public UserContent(String name, String kepzes, String password) {
        this.name = name;
        this.kepzes = kepzes;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKepzes() {
        return kepzes;
    }

    public void setKepzes(String kepzes) {
        this.kepzes = kepzes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
