/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.models;

/**
 *
 * @author david
 */
public class JsonUser {

  private int id;
  String name;

  public JsonUser() {
  }

  public JsonUser(int id, String name) {
      this.id = id;
      this.name = name;
  }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
