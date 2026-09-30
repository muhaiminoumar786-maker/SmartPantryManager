package com.example.smartpantrymanager;

//Importing the necessary libraries
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

//Creating the SuggestedRecipesActivity class
public class SuggestedRecipesActivity extends AppCompatActivity {
    //Creating the RecyclerView used to display suggested recipes
    private RecyclerView recyclerViewRecipes;
    //Creating the text displayed when no recipes can be made
    private TextView txtNoRecipes;
    //Creating the database helper
    private DatabaseHelper databaseHelper;
    //Creating the list used to store suggested recipes
    private ArrayList<Recipe> recipeList;
    //Creating the recipe adapter
    private RecipeAdapter recipeAdapter;

    //Creating the method that runs when the screen opens
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Starting the activity
        super.onCreate(savedInstanceState);
        //Connecting the Java file to the XML layout
        setContentView(
                R.layout.activity_suggested_recipes
        );
        //Connecting the recipe RecyclerView
        recyclerViewRecipes =
                findViewById(
                        R.id.recyclerViewRecipes
                );
        //Connecting the no recipes message
        txtNoRecipes =
                findViewById(
                        R.id.txtNoRecipes
                );
        //Creating the database connection
        databaseHelper =
                new DatabaseHelper(this);
        //Creating the layout for the recipe list
        recyclerViewRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );
        //Loading the suggested recipes
        loadRecipes();
    }

    //Creating a method used to load suggested recipes
    private void loadRecipes(){
        //Getting only recipes that can be made
        //using the ingredients currently in the pantry
        recipeList =
                databaseHelper.getSuggestedRecipes();
        //Checking if no recipes can be made
        if(recipeList.isEmpty()){
            //Showing the no recipes message
            txtNoRecipes.setVisibility(
                    View.VISIBLE
            );
            //Hiding the recipe list
            recyclerViewRecipes.setVisibility(
                    View.GONE
            );
        }else{
            //Hiding the no recipes message
            txtNoRecipes.setVisibility(
                    View.GONE
            );
            //Showing the recipe list
            recyclerViewRecipes.setVisibility(
                    View.VISIBLE
            );
            //Creating the recipe adapter
            recipeAdapter =
                    new RecipeAdapter(
                            recipeList
                    );
            //Connecting the adapter to the RecyclerView
            recyclerViewRecipes.setAdapter(
                    recipeAdapter
            );
        }
    }

    //Refreshing the suggested recipes when returning to the screen
    @Override
    protected void onResume(){
        //Running the normal onResume method
        super.onResume();
        //Making sure the database helper is available
        if (databaseHelper != null){
            //Reloading the recipes
            loadRecipes();
        }
    }
}