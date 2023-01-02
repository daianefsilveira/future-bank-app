package com.example.futurebankgrupo1;

public class InsurancePolicy {

    private int id;
    private String number;
    private String hiringDate;
    private double value;
    private String roles;

    public InsurancePolicy() {

    }

    public InsurancePolicy(int id, String number, String hiringDate, double value, String roles) {
        this.id = id;
        this.number = number;
        this.hiringDate = hiringDate;
        this.value = value;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(String hiringDate) {
        this.hiringDate = hiringDate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
