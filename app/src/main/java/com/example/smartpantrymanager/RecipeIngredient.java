package com.example.smartpantrymanager;

//Creating the RecipeIngredient class
public class RecipeIngredient{
    //Creating the variables used to store recipe ingredient information
    private int id;
    private int recipeId;
    private String ingredientName;
    private double quantity;
    private String unit;

    //Creating the RecipeIngredient constructor
    public RecipeIngredient(
            int id,
            int recipeId,
            String ingredientName,
            double quantity,
            String unit){

        //Storing the recipe ingredient ID
        this.id = id;
        //Storing the ID of the recipe
        this.recipeId = recipeId;
        //Storing the ingredient name
        this.ingredientName = ingredientName;
        //Storing the quantity required
        this.quantity = quantity;
        //Storing the unit used for the ingredient
        this.unit = unit;
    }

    //Creating a method to get the recipe ingredient ID
    public int getId() {
        return id;
    }

    //Creating a method to get the recipe ID
    public int getRecipeId() {
        return recipeId;
    }

    //Creating a method to get the ingredient name
    public String getIngredientName() {
        return ingredientName;
    }

    //Creating a method to get the required quantity
    public double getQuantity() {
        return quantity;
    }

    //Creating a method to get the ingredient unit
    public String getUnit() {
        return unit;
    }

    //Creating a method to change the recipe ingredient ID
    public void setId(int id) {
        this.id = id;
    }

    //Creating a method to change the recipe ID
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    //Creating a method to change the ingredient name
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    //Creating a method to change the required quantity
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    //Creating a method to change the ingredient unit
    public void setUnit(String unit) {
        this.unit = unit;
    }
}