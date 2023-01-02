package com.example.futurebankgrupo1.api;

public class Bill {

    private int id;
    private double value;
    private String dueDate;
    private boolean isPaid;
    private boolean isOverdue;

    public Bill(){

    }

    public Bill(int id, double value, String dueDate, boolean isPaid, boolean isOverdue) {
        this.id = id;
        this.value = value;
        this.dueDate = dueDate;
        this.isPaid = isPaid;
        this.isOverdue = isOverdue;
    }

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public boolean isOverdue() {
        return isOverdue;
    }
}


class Purchase{
    private int id;

    private Number value;
    private String date;
    private String store;

    public Purchase(int id, Number value, String date, String store) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.store = store;
    }

    public int getId() {
        return id;
    }

    public Number getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    public String getStore() {
        return store;
    }

}
