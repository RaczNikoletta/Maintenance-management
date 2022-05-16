package com.example.maintenance_manager_android.model;

public class EmployeeModel {

    private Integer id;
    private String felhasznalonev;
    private String nev;
    private String szerep;
    private Integer kepesitesId;

    public EmployeeModel(){}

    public EmployeeModel(Integer id, String felhasznalonev, String nev, String szerep, Integer kepesitesId) {

        this.id = id;
        this.felhasznalonev = felhasznalonev;
        this.nev = nev;
        this.szerep = szerep;
        this.kepesitesId = kepesitesId;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) { this.id = id; }

    public String getFelhasznalonev() { return felhasznalonev; }
    public void setFelhasznalonev(String felhasznalonev) { this.felhasznalonev = felhasznalonev; }

    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }

    public String getSzerep() { return szerep; }
    public void setSzerep(String szerep) {this.szerep = szerep; }

    public Integer getKepesitesId() {return kepesitesId; }
    public void setKepesitesId(Integer kepesitesId) {this.kepesitesId = kepesitesId; }
}
