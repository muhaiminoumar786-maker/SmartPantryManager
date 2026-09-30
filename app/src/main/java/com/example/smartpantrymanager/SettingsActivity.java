package com.example.smartpantrymanager;

// Importing the necessary libraries
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

//Creating the SettingsActivity class
public class SettingsActivity extends AppCompatActivity {
    //Creating the profile name input field
    private EditText edtProfileName;
    //Creating the expiry reminder switch
    private Switch switchExpiryReminders;
    //Creating the save settings button
    private Button btnSaveSettings;
    //Creating SharedPreferences to store the settings
    private SharedPreferences sharedPreferences;
    //Creating the SharedPreferences file name
    private static final String PREFERENCES_NAME =
            "SmartPantrySettings";

    //Creating the method that runs when the settings screen opens
    @Override
    protected void onCreate(Bundle savedInstanceState){
        //Starting the activity
        super.onCreate(savedInstanceState);

        //Connecting the Java file to the settings XML layout
        setContentView(R.layout.activity_settings);

        //Connecting the profile name field
        edtProfileName =
                findViewById(
                        R.id.edtProfileName
                );

        //Connecting the expiry reminder switch
        switchExpiryReminders =
                findViewById(
                        R.id.switchExpiryReminders
                );

        //Connecting the save settings button
        btnSaveSettings =
                findViewById(
                        R.id.btnSaveSettings
                );

        //Opening the SharedPreferences file
        sharedPreferences =
                getSharedPreferences(
                        PREFERENCES_NAME,
                        MODE_PRIVATE
                );

        //Loading previously saved settings
        loadSettings();

        //Creating an action for the save settings button
        btnSaveSettings.setOnClickListener(v -> {
            //Saving the settings
            saveSettings();
        });
    }

    //Creating a method used to load saved settings
    private void loadSettings(){
        //Getting the saved profile name
        String profileName =
                sharedPreferences.getString(
                        "profile_name",
                        ""
                );

        //Getting the saved expiry reminder setting
        boolean expiryReminders =
                sharedPreferences.getBoolean(
                        "expiry_reminders",
                        true
                );

        //Displaying the saved profile name
        edtProfileName.setText(
                profileName
        );
        //Displaying the saved expiry reminder setting
        switchExpiryReminders.setChecked(
                expiryReminders
        );
    }

    //Creating a method used to save settings
    private void saveSettings(){
        //Getting the profile name entered by the user
        String profileName =
                edtProfileName
                        .getText()
                        .toString()
                        .trim();

        //Checking if the profile name is empty
        if (profileName.isEmpty()) {
            //Showing an error
            edtProfileName.setError(
                    "Please enter your name"
            );
            return;
        }

        //Getting the expiry reminder setting
        boolean expiryReminders =
                switchExpiryReminders.isChecked();

        //Creating the SharedPreferences editor
        SharedPreferences.Editor editor =
                sharedPreferences.edit();

        //Saving the profile name
        editor.putString(
                "profile_name",
                profileName
        );

        //Saving the expiry reminder setting
        editor.putBoolean(
                "expiry_reminders",
                expiryReminders
        );

        //Saving the changes
        editor.apply();

        //Showing a success message
        Toast.makeText(
                SettingsActivity.this,
                "Settings saved successfully",
                Toast.LENGTH_SHORT
        ).show();
    }
}