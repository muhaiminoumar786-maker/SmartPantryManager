package com.example.smartpantrymanager;

//Creating the Recipe class
public class Recipe{
    //Creating the variables used to store recipe information
    private int id;
    private String name;
    private String description;
    private String instructions;

    //Creating the Recipe constructor
    public Recipe(
            int id,
            String name,
            String description,
            String instructions){
        //Storing the recipe ID
        this.id = id;
        //Storing the recipe name
        this.name = name;
        //Storing the recipe description
        this.description = description;
        //Storing the recipe instructions
        this.instructions = instructions;
    }

    //Creating a method to get the recipe ID
    public int getId() {
        return id;
    }

    //Creating a method to get the recipe name
    public String getName() {
        return name;
    }

    //Creating a method to get the recipe description
    public String getDescription() {
        return description;
    }

    //Creating a method to get the recipe instructions
    public String getInstructions() {
        return instructions;
    }

    //Creating a method to change the recipe ID
    public void setId(int id) {
        this.id = id;
    }

    //Creating a method to change the recipe name
    public void setName(String name) {
        this.name = name;
    }

    //Creating a method to change the recipe description
    public void setDescription(String description) {
        this.description = description;
    }

    //Creating a method to change the recipe instructions
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}