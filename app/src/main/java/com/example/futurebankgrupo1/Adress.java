package com.example.futurebankgrupo1;

public class Adress {
    private int id;
    private String zipcode;
    private int number;
    private String publicPlace,
            district,
            city,
            state,
            country;

//    public Adress() {
//    }
//
//    public Adress(int id, String zipcode, int number, String publicPlace, String district, String city, String state, String country) {
//        this.id = id;
//        this.zipcode = zipcode;
//        this.number = number;
//        this.publicPlace = publicPlace;
//        this.district = district;
//        this.city = city;
//        this.state = state;
//        this.country = country;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPublicPlace() {
        return publicPlace;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
