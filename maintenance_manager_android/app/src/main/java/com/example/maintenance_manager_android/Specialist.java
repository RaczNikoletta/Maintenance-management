package com.example.maintenance_manager_android;

public class Specialist {
    private Integer id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }

    public Integer getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(Integer qualificationId) {
        this.qualificationId = qualificationId;
    }

    private String username;
    private String name;
    private String role;
    private String password;
    private Integer workingHours;
    private Integer qualificationId;

    public Specialist() {
    }

    public Specialist(Integer id, String felhasznalonev, String nev, String szerep, String jelszo, Integer munkaido, Integer kepesitesId) {
        this.id = id;
        this.username = felhasznalonev;
        this.name = nev;
        this.role = szerep;
        this.password = jelszo;
        this.workingHours = munkaido;
        this.qualificationId = kepesitesId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
