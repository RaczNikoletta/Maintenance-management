/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 *
 * @author david
 */
//@Entity Class name = table name
@Entity
@Table(name = "testtable")
public class TestModel {
    //Változó nevek = táblában lévő oszlop nevek
    //H amás név akkor a változó getter elé @Column(name = "Oszlop nev")
    private Integer tid;
    private String name;
    private String kepzes;
    private String password;

    @Id // mert ez az ID
    @Column(name = "id")//táblában lévő mező neve más mint a java osztályban
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Mert Id auto incrementelve van
    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
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
