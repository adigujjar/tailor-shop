package com.example.taylorshop.Models;

public class Customer{
    private String serial_number;
    private String name;
    private  String phone_number;

    public Customer() {
    }

    public Customer(String serial_number, String name, String phone_number) {
        this.serial_number = serial_number;
        this.name = name;
        this.phone_number = phone_number;
    }


    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
