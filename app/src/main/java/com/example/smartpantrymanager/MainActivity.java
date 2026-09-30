package com.example.smartpantrymanager;

//Importing the necessary libraries
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

//Creating the main class
public class MainActivity extends AppCompatActivity {
    //Creating the button to open the pantry management section
    private Button btnPantry;
    //Creating the button to open the suggested recipes section
    private Button btnRecipes;
    //Creating the button to open the settings section
    private Button btnSettings;

    //Creating the method that runs when the application opens
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Starting the activity
        super.onCreate(savedInstanceState);
        //Connecting the Java file to the main XML layout
        setContentView(R.layout.activity_main);

        //Connecting the buttons
        //Connecting the pantry button from the XML layout
        btnPantry =
                findViewById(
                        R.id.btnPantry
                );
        //Connecting the recipes button from the XML layout
        btnRecipes =
                findViewById(
                        R.id.btnRecipes
                );
        //Connecting the settings button from the XML layout
        btnSettings =
                findViewById(
                        R.id.btnSettings
                );
        // Creating an action for the pantry button
        btnPantry.setOnClickListener(v -> {
            //Creating an Intent to open the pantry management screen
            Intent intent =
                    new Intent(
                            MainActivity.this,
                            PANTRY_ACTIVITY.class
                    );
            //Opening the pantry management screen
            startActivity(intent);
        });

        //Creating an action for the recipes button
        btnRecipes.setOnClickListener(v -> {
            //Creating an Intent to open the suggested recipes screen
            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SuggestedRecipesActivity.class
                    );
            //Opening the suggested recipes screen
            startActivity(intent);
        });

        //Creating an action for the settings button
        btnSettings.setOnClickListener(v -> {
            //Creating an Intent to open the settings screen
            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SettingsActivity.class
                    );
            //Opening the settings screen
            startActivity(intent);
        });
    }
}