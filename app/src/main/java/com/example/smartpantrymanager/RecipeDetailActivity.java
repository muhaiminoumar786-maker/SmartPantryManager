package com.example.smartpantrymanager;

//Importing the necessary libraries
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
//Creating the RecipeDetailActivity class
public class RecipeDetailActivity extends AppCompatActivity {

    //Creating the text fields used to display recipe information
    private TextView txtRecipeDetailName;
    private TextView txtRecipeDetailDescription;
    private TextView txtRecipeIngredients;
    private TextView txtRecipeInstructions;

    //Creating the database helper
    private DatabaseHelper databaseHelper;
    //Creating the method that runs when the recipe details screen opens
    @Override
    protected void onCreate(Bundle savedInstanceState){
        //Starting the activity
        super.onCreate(savedInstanceState);
        //Connecting the Java file to the recipe details XML layout
        setContentView(R.layout.activity_recipe_detail);

        //Connecting the text fields from the XML layout
        txtRecipeDetailName =
                findViewById(R.id.txtRecipeDetailName);
        txtRecipeDetailDescription =
                findViewById(R.id.txtRecipeDetailDescription);
        txtRecipeIngredients =
                findViewById(R.id.txtRecipeIngredients);
        txtRecipeInstructions =
                findViewById(R.id.txtRecipeInstructions);

        //Creating the database connection
        databaseHelper =
                new DatabaseHelper(this);
        //Getting the recipe information sent from the previous screen
        int recipeId =
                getIntent().getIntExtra(
                        "recipe_id",
                        -1
                );
        String recipeName =
                getIntent().getStringExtra(
                        "recipe_name"
                );
        String recipeDescription =
                getIntent().getStringExtra(
                        "recipe_description"
                );
        String recipeInstructions =
                getIntent().getStringExtra(
                        "recipe_instructions"
                );

        //Displaying the recipe name
        txtRecipeDetailName.setText(
                recipeName
        );
        //Displaying the recipe description
        txtRecipeDetailDescription.setText(
                recipeDescription
        );
        //Displaying the recipe instructions
        txtRecipeInstructions.setText(
                recipeInstructions
        );
        //Loading the required ingredients
        loadRecipeIngredients(recipeId);
    }

    //Creating a method used to display the required ingredients
    private void loadRecipeIngredients(int recipeId){
        //Getting the required ingredients from the database
        ArrayList<RecipeIngredient> ingredientList =
                databaseHelper.getRecipeIngredients(
                        recipeId
                );
        //Creating a StringBuilder to create the ingredient list
        StringBuilder ingredientText =
                new StringBuilder();

        //Checking every required ingredient
        for (RecipeIngredient ingredient :
                ingredientList){
            //Adding a bullet point
            ingredientText.append("• ");
            //Adding the required quantity
            ingredientText.append(
                    formatQuantity(
                            ingredient.getQuantity()
                    )
            );
            //Adding a space
            ingredientText.append(" ");
            //Adding the unit
            ingredientText.append(
                    ingredient.getUnit()
            );
            //Adding a space
            ingredientText.append(" ");

            //Adding the ingredient name
            ingredientText.append(
                    ingredient.getIngredientName()
            );
            //Moving to the next line
            ingredientText.append("\n");
        }

        //Displaying the ingredient list
        txtRecipeIngredients.setText(
                ingredientText.toString()
        );
    }

    //Creating a method to display quantities neatly
    private String formatQuantity(double quantity) {
        //Checking if the quantity is a whole number
        if(quantity == Math.floor(quantity)){
            //Removing the unnecessary decimal
            return String.valueOf(
                    (int) quantity
            );
        }
        //Returning the decimal quantity
        return String.valueOf(
                quantity
        );
    }
}