package com.example.smartpantrymanager;

//Importing the necessary libraries
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

//Creating the pantry class for the pantry management system
public class PANTRY_ACTIVITY extends AppCompatActivity {
    //Creating the button used to add a new ingredient
    private Button btnAddIngredient;
    //Creating the text used when the pantry is empty
    private TextView txtEmptyPantry;
    //Creating the RecyclerView used to display ingredients
    private RecyclerView recyclerViewIngredients;
    //Creating the database helper
    private DatabaseHelper databaseHelper;
    //Creating the list used to store ingredients
    private ArrayList<Ingredient> ingredientList;
    //Creating the ingredient adapter
    private IngredientAdapter ingredientAdapter;

    //Creating the method that runs when the pantry screen opens
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Starting the activity
        super.onCreate(savedInstanceState);
        //Connecting the Java file to the pantry XML layout
        setContentView(R.layout.activity_pantry);
        //Connecting the add ingredient button from the XML layout
        btnAddIngredient =
                findViewById(R.id.btnAddIngredient);
        //Connecting the empty pantry message from the XML layout
        txtEmptyPantry =
                findViewById(R.id.txtEmptyPantry);
        //Connecting the RecyclerView from the XML layout
        recyclerViewIngredients =
                findViewById(R.id.recyclerViewIngredients);

        //Creating the database connection
        databaseHelper = new DatabaseHelper(this);
        //Creating the layout used to display ingredients in a list
        recyclerViewIngredients.setLayoutManager(
                new LinearLayoutManager(this)
        );

        //Creating an action for the add ingredient button
        btnAddIngredient.setOnClickListener(v -> {
            //Creating an intent to open the add ingredient screen
            Intent intent = new Intent(
                    PANTRY_ACTIVITY.this,
                    AddIngredientActivity.class
            );
            //Opening the add ingredient screen
            startActivity(intent);
        });

        //Loading the ingredients from the database
        loadIngredients();
    }

    //Creating a method to load ingredients from the database
    private void loadIngredients() {
        //Getting all the ingredients from the database
        ingredientList =
                databaseHelper.getAllIngredients();
        //Checking if the pantry is empty
        if(ingredientList.isEmpty()){
            //Showing the empty pantry message
            txtEmptyPantry.setVisibility(
                    View.VISIBLE
            );
            //Hiding the ingredient list
            recyclerViewIngredients.setVisibility(
                    View.GONE
            );
        }else{
            //Hiding the empty pantry message
            txtEmptyPantry.setVisibility(
                    View.GONE
            );
            //Showing the ingredient list
            recyclerViewIngredients.setVisibility(
                    View.VISIBLE
            );
            //Creating the ingredient adapter
            ingredientAdapter = new IngredientAdapter(
                    ingredientList,
                    //Reloading the pantry when an ingredient changes
                    () -> loadIngredients()
            );
            //Connecting the adapter to the RecyclerView
            recyclerViewIngredients.setAdapter(
                    ingredientAdapter
            );
        }
    }
    //Creating the method that runs when returning to the pantry screen
    @Override
    protected void onResume(){
        //Running the normal onResume method
        super.onResume();
        //Reloading the ingredients from the database
        loadIngredients();
    }
}