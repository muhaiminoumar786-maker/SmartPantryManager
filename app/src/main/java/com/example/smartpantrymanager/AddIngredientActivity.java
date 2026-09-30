package com.example.smartpantrymanager;

// Importing the necessary libraries
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;

//Creating the class for the add and edit ingredient screen
public class AddIngredientActivity extends AppCompatActivity{
    //Creating the ingredient name input field
    private EditText edtIngredientName;
    //Creating the quantity input field
    private EditText edtQuantity;
    //Creating the unit input field
    private EditText edtUnit;
    //Creating the expiry date field
    private EditText edtExpiryDate;
    //Creating the screen title
    private TextView txtAddIngredientTitle;
    //Creating the save ingredient button
    private Button btnSaveIngredient;
    //Creating the database helper
    private DatabaseHelper databaseHelper;
    //Creating a variable to check if the screen is in edit mode
    private boolean editMode = false;
    //Creating a variable to store the ingredient ID
    private int ingredientId = -1;

    //Starting the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Connecting the Java file to the XML layout
        setContentView(
                R.layout.activity_add_ingredient
        );

        //Connecting the XML components
        //Connecting the screen title
        txtAddIngredientTitle =
                findViewById(
                        R.id.txtAddIngredientTitle
                );
        //Connecting the ingredient name input
        edtIngredientName =
                findViewById(
                        R.id.edtIngredientName
                );
        //Connecting the quantity input
        edtQuantity =
                findViewById(
                        R.id.edtQuantity
                );
        //Connecting the unit input
        edtUnit =
                findViewById(
                        R.id.edtUnit
                );
        //Connecting the expiry date field
        edtExpiryDate =
                findViewById(
                        R.id.edtExpiryDate
                );
        //Connecting the save ingredient button
        btnSaveIngredient =
                findViewById(
                        R.id.btnSaveIngredient
                );

        //Creating the database connection
        databaseHelper =
                new DatabaseHelper(this);
        editMode =
                getIntent().getBooleanExtra(
                        "edit_mode",
                        false
                );

        //Checking if an existing ingredient is being edited
        if (editMode) {
            // Getting the ingredient ID
            ingredientId =
                    getIntent().getIntExtra(
                            "ingredient_id",
                            -1
                    );
            //Getting the ingredient name
            String ingredientName =
                    getIntent().getStringExtra(
                            "ingredient_name"
                    );
            //Getting the ingredient quantity
            double ingredientQuantity =
                    getIntent().getDoubleExtra(
                            "ingredient_quantity",
                            0
                    );
            //Getting the ingredient unit
            String ingredientUnit =
                    getIntent().getStringExtra(
                            "ingredient_unit"
                    );
            //Getting the ingredient expiry date
            String ingredientExpiry =
                    getIntent().getStringExtra(
                            "ingredient_expiry"
                    );

            //Displaying the existing ingredient information
            //Displaying the ingredient name
            edtIngredientName.setText(
                    ingredientName
            );
            //Displaying the ingredient quantity
            edtQuantity.setText(
                    formatQuantity(
                            ingredientQuantity
                    )
            );
            //Displaying the ingredient unit
            edtUnit.setText(
                    ingredientUnit
            );
            //Checking if an expiry date exists
            if (ingredientExpiry != null &&
                    !ingredientExpiry.trim().isEmpty()) {
                //Displaying the expiry date
                edtExpiryDate.setText(
                        ingredientExpiry
                );
            }

            //Changing the screen for editing mode
            //Changing the screen heading
            txtAddIngredientTitle.setText(
                    "Edit Ingredient"
            );
            //Changing the activity title
            setTitle(
                    "Edit Ingredient"
            );
            //Changing the save button text
            btnSaveIngredient.setText(
                    "Update Ingredient"
            );
        }

        //Opening the date picker when the expiry field is pressed
        edtExpiryDate.setOnClickListener(v -> {
            // Opening the expiry date picker
            showDatePicker();
        });

        //Creating an action for the save ingredient button
        btnSaveIngredient.setOnClickListener(v -> {
            //Getting the ingredient name
            String ingredientName =
                    edtIngredientName
                            .getText()
                            .toString()
                            .trim();
            //Getting the quantity as text
            String quantityText =
                    edtQuantity
                            .getText()
                            .toString()
                            .trim();
            //Getting the unit
            String unit =
                    edtUnit
                            .getText()
                            .toString()
                            .trim();
            //Getting the expiry date
            String expiryDate =
                    edtExpiryDate
                            .getText()
                            .toString()
                            .trim();

            //Validating the ingredient name
            if (ingredientName.isEmpty()) {
                //Showing an error if there is no ingredient name
                edtIngredientName.setError(
                        "Please enter an ingredient name"
                );
                //Moving to the ingredient name field
                edtIngredientName.requestFocus();
                return;
            }

            //Validating the quantity
            if (quantityText.isEmpty()) {
                // Showing an error if quantity is empty
                edtQuantity.setError(
                        "Please enter a quantity"
                );
                //Moving to the quantity field
                edtQuantity.requestFocus();
                return;
            }

            //Validating the unit
            if (unit.isEmpty()) {
                //Showing an error if unit is empty
                edtUnit.setError(
                        "Please enter a unit"
                );
                //Moving to the unit field
                edtUnit.requestFocus();
                return;
            }

            //Converting the quantity
            double quantity;
            try {
                //Converting the entered quantity into a number
                quantity =
                        Double.parseDouble(
                                quantityText
                        );
            } catch (NumberFormatException e) {
                //Showing an error if the quantity is invalid
                edtQuantity.setError(
                        "Please enter a valid quantity"
                );
                return;
            }
            if (quantity <= 0) {
                //Showing an error
                edtQuantity.setError(
                        "Quantity must be greater than zero"
                );
                return;
            }

            //Editing an existing ingredient
            if (editMode) {
                //Updating the ingredient in SQLite
                boolean ingredientUpdated =
                        databaseHelper.updateIngredient(
                                ingredientId,
                                ingredientName,
                                quantity,
                                unit,
                                expiryDate
                        );
                //Checking if the ingredient was updated
                if (ingredientUpdated) {
                    //Showing a success message
                    Toast.makeText(
                            AddIngredientActivity.this,
                            "Ingredient updated successfully",
                            Toast.LENGTH_SHORT
                    ).show();
                    // Returning to the pantry
                    finish();
                } else {
                    //Showing an error message
                    Toast.makeText(
                            AddIngredientActivity.this,
                            "Ingredient could not be updated",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            } else {
                //Adding a new ingredient
                //Adding the ingredient to SQLite
                boolean ingredientAdded =
                        databaseHelper.addIngredient(
                                ingredientName,
                                quantity,
                                unit,
                                expiryDate
                        );
                //Checking if the ingredient was added
                if (ingredientAdded) {
                    //Showing a success message
                    Toast.makeText(
                            AddIngredientActivity.this,
                            "Ingredient saved successfully",
                            Toast.LENGTH_SHORT
                    ).show();
                    // Returning to the pantry
                    finish();
                } else {
                    //Showing an error message
                    Toast.makeText(
                            AddIngredientActivity.this,
                            "Ingredient could not be saved",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    //Creating a method used to display the expiry date picker
    private void showDatePicker(){
        //Getting the current date
        Calendar calendar =
                Calendar.getInstance();
        //Getting the current year
        int currentYear =
                calendar.get(
                        Calendar.YEAR
                );
        //Getting the current month
        int currentMonth =
                calendar.get(
                        Calendar.MONTH
                );
        //Getting the current day
        int currentDay =
                calendar.get(
                        Calendar.DAY_OF_MONTH
                );

        //Creating the date picker
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(
                        AddIngredientActivity.this,
                        (view, year, month, dayOfMonth) -> {
                            //Creating the selected date
                            String selectedDate =
                                    String.format(
                                            Locale.getDefault(),
                                            "%02d/%02d/%04d",
                                            dayOfMonth,
                                            month + 1,
                                            year
                                    );
                            //Displaying the selected expiry date
                            edtExpiryDate.setText(
                                    selectedDate
                            );
                        },
                        currentYear,
                        currentMonth,
                        currentDay
                );

        //Setting the earliest selectable date to today
        datePickerDialog
                .getDatePicker()
                .setMinDate(
                        System.currentTimeMillis()
                );
        //Showing the date picker
        datePickerDialog.show();
    }

    //Formatting quantities
    //Creating a method used to display quantities neatly
    private String formatQuantity(
            double quantity) {
        //Checking if the quantity is a whole number
        if (quantity ==
                Math.floor(quantity)) {
            //Returning the quantity without .0
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