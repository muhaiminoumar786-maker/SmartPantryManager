package com.example.smartpantrymanager;

//Creating the ingredient class
public class Ingredient{
    private int id;
    private String name;
    private double quantity;
    private String unit;
    private String expiryDate;

    //Creating the constructor
    public Ingredient(int id, String name, double quantity,String unit, String expiryDate){
        this.id=id;
        this.name=name;
        this.quantity=quantity;
        this.unit=unit;
        this.expiryDate=expiryDate;
    }

    //Getting the ingredient ID
    public int getId(){
        return id;
    }
    //Getting the ingredient name
    public String getName(){
        return name;
    }
    //Getting the ingredient quantity
    public double getQuantity(){
        return quantity;
    }
    //Getting the ingredient unit
    public String getUnit(){
        return unit;
    }
    //Getting the expiry date
    public String getExpiryDate(){
        return expiryDate;
    }

    //Changing the ingredient ID
    public void setName(int id){
        this.id=id;
    }
    //Changing the ingredient name
    public void setName(String name) {
        this.name = name;
    }
    //Changing the ingredient quantity
    public void setQuantity(double quantity){
    this.quantity=quantity;
    }
    //Changing the ingredient unit
    public void setUnit(String unit){
        this.unit=unit;
    }
    // Changing the expiry date
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}