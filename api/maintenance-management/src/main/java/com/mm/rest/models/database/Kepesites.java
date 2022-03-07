/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.models.database;

import com.mm.rest.models.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author david
 */
//@Entity Class name = table name
@Entity
@Table(name = "kepesites")
public class Kepesites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer kepesites_id;
    
    private String kepesites_nev;

    public Kepesites(){}
    
    public Kepesites(String kepesites_nev) {
        this.kepesites_nev = kepesites_nev;
    }

    public Integer getKepesites_id() {
        return kepesites_id;
    }

    public void setKepesites_id(Integer kepesites_id) {
        this.kepesites_id = kepesites_id;
    }

    public String getKepesites_nev() {
        return kepesites_nev;
    }

    public void setKepesites_nev(String kepesites_nev) {
        this.kepesites_nev = kepesites_nev;
    }
    
}
