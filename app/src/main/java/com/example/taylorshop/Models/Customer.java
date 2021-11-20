package com.example.taylorshop.Models;

public class Customer{
    private String serial_number;
    private String name;
    private  String phone_number;
    private  String lengthSuit;
    private  String shoulderSuit;
    private  String armsSuit;
    private  String chestSuit;
    private  String backSuit;
    private  String neckSuit;
    private  String chestLoose;
    private  String backLoose;
    private  String suitFront;
    private  String trouserLength;
    private  String trouserEdge;
    private  String suitPocket;
    private  String suitModa;
    private  String suitExtraNotes;
    private  String key;

    public Customer() {
    }

    public Customer(String serial_number, String name, String phone_number,
                    String suitLength,
                    String shoulderSuit,
                    String armsSuit,
                    String chestSuit,
                    String backSuit,
                    String neckSuit,
                    String chestLoose,
                    String backLoose,
                    String suitFront,
                    String trouserLength,
                    String trouserEdge,
                    String suitPocket,
                    String suitModa,
                    String suitExtraNotes,
                    String key
    ) {
        this.serial_number = serial_number;
        this.name = name;
        this.phone_number = phone_number;
        this.lengthSuit = suitLength;
        this.shoulderSuit = shoulderSuit;
        this.armsSuit = armsSuit;
        this.chestSuit = chestSuit;
        this.backSuit = backSuit;
        this.neckSuit = neckSuit;
        this.chestLoose = chestLoose;
        this.backLoose = backLoose;
        this.suitFront = suitFront;
        this.trouserLength = trouserLength;
        this.trouserEdge = trouserEdge;
        this.suitPocket = suitPocket;
        this.suitModa = suitModa;
        this.suitExtraNotes = suitExtraNotes;
        this.key = key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
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

    public void setArmsSuit(String armsSuit) {
        this.armsSuit = armsSuit;
    }

    public String getArmsSuit() {
        return armsSuit;
    }

    public void setBackSuit(String backSuit) {
        this.backSuit = backSuit;
    }

    public String getBackSuit() {
        return backSuit;
    }

    public void setChestSuit(String chestSuit) {
        this.chestSuit = chestSuit;
    }

    public String getBackLoose() {
        return backLoose;
    }

    public void setBackLoose(String backLoose) {
        this.backLoose = backLoose;
    }

    public String getChestLoose() {
        return chestLoose;
    }

    public void setSuitExtraNotes(String suitExtraNotes) {
        this.suitExtraNotes = suitExtraNotes;
    }

    public String getSuitExtraNotes() {
        return suitExtraNotes;
    }

    public void setChestLoose(String chestLoose) {
        this.chestLoose = chestLoose;
    }

    public void setLengthSuit(String lengthSuit) {
        this.lengthSuit = lengthSuit;
    }

    public void setNeckSuit(String neckSuit) {
        this.neckSuit = neckSuit;
    }

    public void setShoulderSuit(String shoulderSuit) {
        this.shoulderSuit = shoulderSuit;
    }

    public void setSuitFront(String suitFront) {
        this.suitFront = suitFront;
    }

    public void setSuitPocket(String suitPocket) {
        this.suitPocket = suitPocket;
    }

    public void setTrouserLength(String trouserLength) {
        this.trouserLength = trouserLength;
    }

    public void setSuitModa(String suitModa) {
        this.suitModa = suitModa;
    }

    public void setTrouserEdge(String trouserEdge) {
        this.trouserEdge = trouserEdge;
    }

    public String getSuitFront() {
        return suitFront;
    }

    public String getShoulderSuit() {
        return shoulderSuit;
    }

    public String getSuitPocket() {
        return suitPocket;
    }

    public String getNeckSuit() {
        return neckSuit;
    }

    public String getSuitModa() {
        return suitModa;
    }

    public String getLengthSuit() {
        return lengthSuit;
    }

    public String getChestSuit() {
        return chestSuit;
    }

    public String getTrouserLength() {
        return trouserLength;
    }

    public String getTrouserEdge() {
        return trouserEdge;
    }
}

