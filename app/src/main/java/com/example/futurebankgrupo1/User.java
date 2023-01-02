package com.example.futurebankgrupo1;


public class User {
    private int id;
    private String name,
            cpf,
            birthDate,
            email,
            phone,
            password;
    private Adress adress;


//    public User(){
//    }

//    public User(int id, String name, String cpf, String birthDate, String email, String phone, String password, Adress adress) {
//        this.id = id;
//        this.name = name;
//        this.cpf = cpf;
//        this.birthDate = birthDate;
//        this.email = email;
//        this.phone = phone;
//        this.password = password;
//        this.adress = adress;
//    }

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }
}


