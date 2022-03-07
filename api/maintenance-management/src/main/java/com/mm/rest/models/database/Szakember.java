/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.models.database;

import com.mm.rest.models.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


/**
 *
 * @author david
 */
//@Entity Class name = table name
@Entity
@Table(name = "szakember")
public class Szakember {
    private Integer id;
    private String felhasznalonev;
    private String nev;
    private String szerep;
    private String jelszo;
    private Integer munkaido;
    private Integer kepesitesId;

    public Szakember() {
    }
    
    public Szakember(Integer id, String felhasznalonev, String nev, String szerep, String jelszo, Integer munkaido, Integer kepesitesId) {
        this.id = id;
        this.felhasznalonev = felhasznalonev;
        this.nev = nev;
        this.szerep = szerep;
        this.jelszo = jelszo;
        this.munkaido = munkaido;
        this.kepesitesId = kepesitesId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFelhasznalonev() {
        return felhasznalonev;
    }

    public void setFelhasznalonev(String felhasznalonev) {
        this.felhasznalonev = felhasznalonev;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getSzerep() {
        return szerep;
    }

    public void setSzerep(String szerep) {
        this.szerep = szerep;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public Integer getMunkaido() {
        return munkaido;
    }

    public void setMunkaido(Integer munkaido) {
        this.munkaido = munkaido;
    }

    public Integer getKepesitesId() {
        return kepesitesId;
    }

    public void setKepesitesId(Integer kepesitesId) {
        this.kepesitesId = kepesitesId;
    }

   
    
}
