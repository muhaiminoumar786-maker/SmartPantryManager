package com.example.smartpantrymanager;

//Importing the necessary libraries
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

//Creating the DatabaseHelper class
public class DatabaseHelper extends SQLiteOpenHelper {
    //Creating the database name and version
    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 3;

    //Creating the ingredients table
    //Creating the ingredients table name
    public static final String TABLE_INGREDIENTS = "ingredients";
    //Creating the ingredients table column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_EXPIRY_DATE = "expiry_date";

    //Creating the recipes table
    //Creating the recipes table name
    public static final String TABLE_RECIPES = "recipes";
    //Creating the recipes table column names
    public static final String COLUMN_RECIPE_ID = "recipe_id";
    public static final String COLUMN_RECIPE_NAME = "recipe_name";
    public static final String COLUMN_RECIPE_DESCRIPTION = "description";
    public static final String COLUMN_RECIPE_INSTRUCTIONS = "instructions";

    //Creating the recipe ingredients table name
    public static final String TABLE_RECIPE_INGREDIENTS =
            "recipe_ingredients";
    //Creating the recipe ingredients table column names
    public static final String COLUMN_RECIPE_INGREDIENT_ID =
            "recipe_ingredient_id";
    public static final String COLUMN_RECIPE_INGREDIENT_RECIPE_ID =
            "recipe_id";
    public static final String COLUMN_RECIPE_INGREDIENT_NAME =
            "ingredient_name";
    public static final String COLUMN_RECIPE_INGREDIENT_QUANTITY =
            "quantity";
    public static final String COLUMN_RECIPE_INGREDIENT_UNIT =
            "unit";

    //Creating the database helper
    public DatabaseHelper(Context context){
        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }

    //Creating the database
    @Override
    public void onCreate(SQLiteDatabase db){
        //Creating the ingredients table
        String createIngredientsTable =
                "CREATE TABLE " + TABLE_INGREDIENTS + " (" +
                        COLUMN_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME +
                        " TEXT NOT NULL, " +
                        COLUMN_QUANTITY +
                        " REAL NOT NULL, " +
                        COLUMN_UNIT +
                        " TEXT NOT NULL, " +
                        COLUMN_EXPIRY_DATE +
                        " TEXT)";
        db.execSQL(createIngredientsTable);

        //Creating the recipes table
        String createRecipesTable =
                "CREATE TABLE " + TABLE_RECIPES + " (" +
                        COLUMN_RECIPE_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_RECIPE_NAME +
                        " TEXT NOT NULL, " +
                        COLUMN_RECIPE_DESCRIPTION +
                        " TEXT, " +
                        COLUMN_RECIPE_INSTRUCTIONS +
                        " TEXT NOT NULL)";
        db.execSQL(createRecipesTable);

        //Creating the recipe ingredients table
        String createRecipeIngredientsTable =
                "CREATE TABLE " +
                        TABLE_RECIPE_INGREDIENTS + " (" +
                        COLUMN_RECIPE_INGREDIENT_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_RECIPE_INGREDIENT_RECIPE_ID +
                        " INTEGER NOT NULL, " +
                        COLUMN_RECIPE_INGREDIENT_NAME +
                        " TEXT NOT NULL, " +
                        COLUMN_RECIPE_INGREDIENT_QUANTITY +
                        " REAL NOT NULL, " +
                        COLUMN_RECIPE_INGREDIENT_UNIT +
                        " TEXT NOT NULL, " +
                        "FOREIGN KEY (" +
                        COLUMN_RECIPE_INGREDIENT_RECIPE_ID +
                        ") REFERENCES " +
                        TABLE_RECIPES +
                        "(" +
                        COLUMN_RECIPE_ID +
                        "))";
        db.execSQL(createRecipeIngredientsTable);

        //Adding the starting recipes
        seedRecipes(db);
    }

    //Upgrading the database
    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {
        //Removing the old tables
        db.execSQL(
                "DROP TABLE IF EXISTS " +
                        TABLE_RECIPE_INGREDIENTS
        );
        db.execSQL(
                "DROP TABLE IF EXISTS " +
                        TABLE_RECIPES
        );
        db.execSQL(
                "DROP TABLE IF EXISTS " +
                        TABLE_INGREDIENTS
        );

        //Creating the tables again
        onCreate(db);
    }

    //Adding an ingredient
    public boolean addIngredient(
            String name,
            double quantity,
            String unit,
            String expiryDate) {
        //Opening the database
        SQLiteDatabase db =
                this.getWritableDatabase();
        //Creating the ingredient values
        ContentValues values =
                new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_UNIT, unit);
        values.put(COLUMN_EXPIRY_DATE, expiryDate);
        //Adding the ingredient
        long result = db.insert(
                TABLE_INGREDIENTS,
                null,
                values
        );
        //Closing the database
        db.close();
        //Returning true if successful
        return result != -1;
    }

    //Getting all the ingredients
    public ArrayList<Ingredient> getAllIngredients(){
        // Creating the ingredient list
        ArrayList<Ingredient> ingredientList =
                new ArrayList<>();
        //Opening the database
        SQLiteDatabase db =
                this.getReadableDatabase();
        //Getting all pantry ingredients
        Cursor cursor = db.query(
                TABLE_INGREDIENTS,
                null,
                null,
                null,
                null,
                null,
                COLUMN_ID + " DESC"
        );
        //Reading the ingredients
        if (cursor.moveToFirst()) {
            do {
                //Getting the ingredient ID
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_ID
                        )
                );
                //Getting the ingredient name
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_NAME
                        )
                );
                //Getting the quantity
                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_QUANTITY
                        )
                );
                //Getting the unit
                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_UNIT
                        )
                );
                //Getting the expiry date
                String expiryDate = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_EXPIRY_DATE
                        )
                );
                //Creating the ingredient object
                Ingredient ingredient =
                        new Ingredient(
                                id,
                                name,
                                quantity,
                                unit,
                                expiryDate
                        );
                //Adding the ingredient to the list
                ingredientList.add(
                        ingredient
                );
            }while (cursor.moveToNext());
        }
        //Closing the cursor
        cursor.close();
        //Closing the database
        db.close();
        //Returning the ingredient list
        return ingredientList;
    }
    //Updating an ingredient
    public boolean updateIngredient(
            int id,
            String name,
            double quantity,
            String unit,
            String expiryDate) {
        //Opening the database
        SQLiteDatabase db =
                this.getWritableDatabase();
        //Creating the updated values
        ContentValues values =
                new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_UNIT, unit);
        values.put(COLUMN_EXPIRY_DATE, expiryDate);
        //Updating the ingredient
        int result = db.update(
                TABLE_INGREDIENTS,
                values,
                COLUMN_ID + " = ?",
                new String[]{
                        String.valueOf(id)
                }
        );
        //Closing the database
        db.close();

        //Returning true if successful
        return result > 0;
    }

    //Deleting an ingredient
    public boolean deleteIngredient(int id){
        //Opening the database
        SQLiteDatabase db =
                this.getWritableDatabase();
        //Deleting the ingredient
        int result = db.delete(
                TABLE_INGREDIENTS,
                COLUMN_ID + " = ?",
                new String[]{
                        String.valueOf(id)
                }
        );
        //Closing the database
        db.close();
        //Returning true if successful
        return result > 0;
    }

    //Adding a recipe
    public long addRecipe(
            String name,
            String description,
            String instructions){
        //Opening the database
        SQLiteDatabase db =
                this.getWritableDatabase();
        //Creating the recipe values
        ContentValues values =
                new ContentValues();
        values.put(
                COLUMN_RECIPE_NAME,
                name
        );
        values.put(
                COLUMN_RECIPE_DESCRIPTION,
                description
        );
        values.put(
                COLUMN_RECIPE_INSTRUCTIONS,
                instructions
        );
        //Adding the recipe
        long recipeId = db.insert(
                TABLE_RECIPES,
                null,
                values
        );
        //Closing the database
        db.close();
        //Returning the recipe ID
        return recipeId;
    }

    //Adding a recipe ingredient
    public boolean addRecipeIngredient(
            int recipeId,
            String ingredientName,
            double quantity,
            String unit) {
        //Opening the database
        SQLiteDatabase db =
                this.getWritableDatabase();
        //Creating the values
        ContentValues values =
                new ContentValues();
        values.put(
                COLUMN_RECIPE_INGREDIENT_RECIPE_ID,
                recipeId
        );
        values.put(
                COLUMN_RECIPE_INGREDIENT_NAME,
                ingredientName
        );
        values.put(
                COLUMN_RECIPE_INGREDIENT_QUANTITY,
                quantity
        );
        values.put(
                COLUMN_RECIPE_INGREDIENT_UNIT,
                unit
        );
        //Adding the recipe ingredient
        long result = db.insert(
                TABLE_RECIPE_INGREDIENTS,
                null,
                values
        );
        //Closing the database
        db.close();
        //Returning true if successful
        return result != -1;
    }

    //Getting all the recipes
    public ArrayList<Recipe> getAllRecipes(){
        //Creating the recipe list
        ArrayList<Recipe> recipeList =
                new ArrayList<>();
        //Opening the database
        SQLiteDatabase db =
                this.getReadableDatabase();
        //Getting all recipes
        Cursor cursor = db.query(
                TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                COLUMN_RECIPE_NAME + " ASC"
        );
        //Reading the recipes
        if(cursor.moveToFirst()){
            do{
                //Getting the recipe ID
                int recipeId = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_RECIPE_ID
                        )
                );
                //Getting the recipe name
                String recipeName = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_RECIPE_NAME
                        )
                );
                //Getting the recipe description
                String description = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_RECIPE_DESCRIPTION
                        )
                );
                //Getting the recipe instructions
                String instructions = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_RECIPE_INSTRUCTIONS
                        )
                );
                //Creating the recipe object
                Recipe recipe =
                        new Recipe(
                                recipeId,
                                recipeName,
                                description,
                                instructions
                        );
                //Adding the recipe to the list
                recipeList.add(
                        recipe
                );
            }while(cursor.moveToNext());
        }
        //Closing the cursor
        cursor.close();
        //Closing the database
        db.close();
        //Returning all recipes
        return recipeList;
    }

    //Getting recipe ingredients
    public ArrayList<RecipeIngredient> getRecipeIngredients(
            int recipeId){
        //Creating the recipe ingredient list
        ArrayList<RecipeIngredient> recipeIngredientList =
                new ArrayList<>();
        //Opening the database
        SQLiteDatabase db =
                this.getReadableDatabase();
        //Getting the ingredients for the selected recipe
        Cursor cursor = db.query(
                TABLE_RECIPE_INGREDIENTS,
                null,
                COLUMN_RECIPE_INGREDIENT_RECIPE_ID + " = ?",
                new String[]{
                        String.valueOf(recipeId)
                },
                null,
                null,
                COLUMN_RECIPE_INGREDIENT_ID + " ASC"
        );
        //Reading the recipe ingredients
        if (cursor.moveToFirst()){
            do{
                //Getting the recipe ingredient ID
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_RECIPE_INGREDIENT_ID
                        )
                );
                //Getting the recipe ID
                int connectedRecipeId = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_RECIPE_INGREDIENT_RECIPE_ID
                        )
                );
                //Getting the ingredient name
                String ingredientName = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_RECIPE_INGREDIENT_NAME
                        )
                );
                //Getting the required quantity
                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_RECIPE_INGREDIENT_QUANTITY
                        )
                );
                //Getting the unit
                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_RECIPE_INGREDIENT_UNIT
                        )
                );
                //Creating the recipe ingredient object
                RecipeIngredient recipeIngredient =
                        new RecipeIngredient(
                                id,
                                connectedRecipeId,
                                ingredientName,
                                quantity,
                                unit
                        );
                //Adding the ingredient to the list
                recipeIngredientList.add(
                        recipeIngredient
                );
            }while(cursor.moveToNext());
        }

        //Closing the cursor
        cursor.close();
        //Closing the database
        db.close();
        //Returning the recipe ingredients
        return recipeIngredientList;
    }

    //Creating a method to get recipes that can be made
    public ArrayList<Recipe> getSuggestedRecipes(){
        //Creating a list to store suggested recipes
        ArrayList<Recipe> suggestedRecipes =
                new ArrayList<>();
        //Getting all recipes
        ArrayList<Recipe> allRecipes =
                getAllRecipes();
        //Getting all pantry ingredients
        ArrayList<Ingredient> pantryIngredients =
                getAllIngredients();
        //Checking the recipes
        for (Recipe recipe : allRecipes) {
            //Getting all ingredients required by the recipe
            ArrayList<RecipeIngredient> requiredIngredients =
                    getRecipeIngredients(
                            recipe.getId()
                    );
            //Starting by assuming the recipe can be made
            boolean canMakeRecipe = true;
            //Checking the required ingredients
            for(RecipeIngredient requiredIngredient :
                    requiredIngredients){
                //Creating a variable to store the total
                //available amount of this ingredient
                double totalAvailableQuantity = 0;
                //Checking the pantry ingredients
                for (Ingredient pantryIngredient :
                        pantryIngredients){
                    //Checking if the ingredient names match
                    if (ingredientNamesMatch(
                            pantryIngredient.getName(),
                            requiredIngredient.getIngredientName())) {
                        //Converting the pantry quantity into
                        //the unit required by the recipe
                        double convertedQuantity =
                                convertQuantity(
                                        pantryIngredient.getQuantity(),
                                        pantryIngredient.getUnit(),
                                        requiredIngredient.getUnit()
                                );
                        //Checking if the units are compatible
                        if (convertedQuantity >= 0) {
                            //Adding the amount to the total
                            totalAvailableQuantity +=
                                    convertedQuantity;
                        }
                    }
                }
                //Checking if enough of the ingredient is available
                if(totalAvailableQuantity <
                        requiredIngredient.getQuantity()) {
                    //The recipe cannot be made
                    canMakeRecipe = false;
                    //Stop checking this recipe
                    break;
                }
            }
            // Adding the recipe only when every ingredient has sufficient quantity
            if (canMakeRecipe){
                suggestedRecipes.add(
                        recipe
                );
            }
        }
        //Returning the suggested recipes
        return suggestedRecipes;
    }

    //Creating a method used to compare ingredient names
    private boolean ingredientNamesMatch(
            String pantryName,
            String recipeName) {
        //Checking that both names exist
        if (pantryName == null ||
                recipeName == null) {
            return false;
        }
        //Normalising the pantry ingredient name
        String firstName =
                normaliseIngredientName(
                        pantryName
                );
        //Normalising the recipe ingredient name
        String secondName =
                normaliseIngredientName(
                        recipeName
                );
        //Comparing the names
        return firstName.equals(
                secondName
        );
    }

    private String normaliseIngredientName(
            String name){
        //Removing spaces and converting to lowercase
        String normalisedName =
                name.trim().toLowerCase();
        //Handling eggs
        if (normalisedName.equals("eggs")) {
            return "egg";
        }
        //Handling tomatoes
        if (normalisedName.equals("tomatoes")) {
            return "tomato";
        }
        //Handling potatoes
        if (normalisedName.equals("potatoes")) {
            return "potato";
        }
        //Handling bananas
        if (normalisedName.equals("bananas")) {
            return "banana";
        }
        //Handling onions
        if (normalisedName.equals("onions")) {
            return "onion";
        }
        //Handling slices
        if (normalisedName.equals("slices")) {
            return "slice";
        }
        //Handling cloves
        if (normalisedName.equals("cloves")) {
            return "clove";
        }
        //Handling simple plural words
        if (normalisedName.endsWith("s")
                && normalisedName.length() > 1) {
            normalisedName =
                    normalisedName.substring(
                            0,
                            normalisedName.length() - 1
                    );
        }
        //Returning the normalised ingredient name
        return normalisedName;
    }

    //Doing the unit conversion
    private double convertQuantity(
            double quantity,
            String pantryUnit,
            String recipeUnit) {
        //Checking that both units exist
        if (pantryUnit == null ||
                recipeUnit == null) {
            return -1;
        }
        //Normalising the pantry unit
        String fromUnit =
                normaliseUnit(
                        pantryUnit
                );
        //Normalising the recipe unit
        String toUnit =
                normaliseUnit(
                        recipeUnit
                );
        //No conversion is required if the units are the same
        if(fromUnit.equals(toUnit)) {
            return quantity;
        }
        //Converting kilograms to grams
        if (fromUnit.equals("kg")
                && toUnit.equals("g")) {
            return quantity * 1000;
        }
        //Converting grams to kilograms
        if (fromUnit.equals("g")
                && toUnit.equals("kg")) {
            return quantity / 1000;
        }
        //Converting litres to millilitres
        if (fromUnit.equals("l")
                && toUnit.equals("ml")) {
            return quantity * 1000;
        }
        //Converting millilitres to litres
        if (fromUnit.equals("ml")
                && toUnit.equals("l")) {
            return quantity / 1000;
        }
        //Treating pieces as individual items
        if (fromUnit.equals("piece")
                && toUnit.equals("item")) {
            return quantity;
        }
        //Treating items as individual pieces
        if (fromUnit.equals("item")
                && toUnit.equals("piece")) {
            return quantity;
        }
        //Returning -1 when the units cannot be compared
        return -1;
    }

    //Normalising untis
    private String normaliseUnit(
            String unit){
        //Removing spaces and converting the unit to lowercase
        String normalisedUnit =
                unit.trim().toLowerCase();
        if(normalisedUnit.equals("gram")
                || normalisedUnit.equals("grams")) {
            return "g";
        }
        if (normalisedUnit.equals("kilogram")
                || normalisedUnit.equals("kilograms")
                || normalisedUnit.equals("kgs")) {
            return "kg";
        }
        if (normalisedUnit.equals("millilitre")
                || normalisedUnit.equals("millilitres")
                || normalisedUnit.equals("milliliter")
                || normalisedUnit.equals("milliliters")) {
            return "ml";
        }
        if (normalisedUnit.equals("litre")
                || normalisedUnit.equals("litres")
                || normalisedUnit.equals("liter")
                || normalisedUnit.equals("liters")) {
            return "l";
        }
        if (normalisedUnit.equals("items")) {
            return "item";
        }
        if (normalisedUnit.equals("pieces")) {
            return "piece";
        }
        if (normalisedUnit.equals("slices")) {
            return "slice";
        }
        if (normalisedUnit.equals("cloves")) {
            return "clove";
        }
        //Returning the normalised unit
        return normalisedUnit;
    }

    //Seeding the recipes
    private void seedRecipes(
            SQLiteDatabase db) {
        //Checking if recipes already exist
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " +
                        TABLE_RECIPES,
                null
        );
        //Creating the recipe count
        int recipeCount = 0;
        //Getting the number of recipes
        if (cursor.moveToFirst()) {
            recipeCount =
                    cursor.getInt(0);
        }
        //Closing the cursor
        cursor.close();
        //Stopping if recipes already exist
        if (recipeCount > 0) {
            return;
        }

        //Creating recipe 1
        long scrambledEggsId =
                insertRecipe(
                        db,
                        "Scrambled Eggs",
                        "Quick and simple scrambled eggs.",
                        "Beat the eggs with the milk. " +
                                "Melt the butter in a pan. " +
                                "Add the egg mixture and gently stir until cooked."
                );
        insertRecipeIngredient(
                db,
                scrambledEggsId,
                "Egg",
                2,
                "item"
        );
        insertRecipeIngredient(
                db,
                scrambledEggsId,
                "Milk",
                30,
                "ml"
        );
        insertRecipeIngredient(
                db,
                scrambledEggsId,
                "Butter",
                10,
                "g"
        );

        //Recipe 2
        long cheeseOmeletteId =
                insertRecipe(
                        db,
                        "Cheese Omelette",
                        "A simple omelette filled with cheese.",
                        "Beat the eggs. Melt the butter in a pan and add the eggs. " +
                                "Add the cheese and fold the omelette before serving."
                );
        insertRecipeIngredient(
                db,
                cheeseOmeletteId,
                "Egg",
                2,
                "item"
        );
        insertRecipeIngredient(
                db,
                cheeseOmeletteId,
                "Cheese",
                50,
                "g"
        );
        insertRecipeIngredient(
                db,
                cheeseOmeletteId,
                "Butter",
                10,
                "g"
        );

        //Recipe 3
        long frenchToastId =
                insertRecipe(
                        db,
                        "French Toast",
                        "Bread coated in an egg and milk mixture.",
                        "Beat the egg and milk together. " +
                                "Dip the bread into the mixture. " +
                                "Cook the bread in butter until golden on both sides."
                );
        insertRecipeIngredient(
                db,
                frenchToastId,
                "Bread",
                2,
                "slice"
        );
        insertRecipeIngredient(
                db,
                frenchToastId,
                "Egg",
                1,
                "item"
        );
        insertRecipeIngredient(
                db,
                frenchToastId,
                "Milk",
                50,
                "ml"
        );
        insertRecipeIngredient(
                db,
                frenchToastId,
                "Butter",
                10,
                "g"
        );

        // RECIPE 4
        long grilledCheeseId =
                insertRecipe(
                        db,
                        "Grilled Cheese",
                        "A toasted cheese sandwich.",
                        "Place the cheese between the bread slices. " +
                                "Spread butter on the outside and cook until golden."
                );
        insertRecipeIngredient(
                db,
                grilledCheeseId,
                "Bread",
                2,
                "slice"
        );
        insertRecipeIngredient(
                db,
                grilledCheeseId,
                "Cheese",
                50,
                "g"
        );
        insertRecipeIngredient(
                db,
                grilledCheeseId,
                "Butter",
                10,
                "g"
        );

        //RECIPE 5
        long tunaSandwichId =
                insertRecipe(
                        db,
                        "Tuna Sandwich",
                        "A quick tuna and mayonnaise sandwich.",
                        "Mix the tuna and mayonnaise together. " +
                                "Spread the mixture onto the bread and serve."
                );
        insertRecipeIngredient(
                db,
                tunaSandwichId,
                "Bread",
                2,
                "slice"
        );
        insertRecipeIngredient(
                db,
                tunaSandwichId,
                "Tuna",
                100,
                "g"
        );
        insertRecipeIngredient(
                db,
                tunaSandwichId,
                "Mayonnaise",
                20,
                "g"
        );

        //RECIPE 6
        long tomatoPastaId =
                insertRecipe(
                        db,
                        "Tomato Pasta",
                        "Simple pasta with a tomato sauce.",
                        "Cook the pasta. Cook the tomato and onion in oil. " +
                                "Add the cooked pasta and mix well."
                );
        insertRecipeIngredient(
                db,
                tomatoPastaId,
                "Pasta",
                200,
                "g"
        );
        insertRecipeIngredient(
                db,
                tomatoPastaId,
                "Tomato",
                2,
                "item"
        );
        insertRecipeIngredient(
                db,
                tomatoPastaId,
                "Onion",
                1,
                "item"
        );
        insertRecipeIngredient(
                db,
                tomatoPastaId,
                "Oil",
                15,
                "ml"
        );

        //RECIPE 7
        long garlicPastaId =
                insertRecipe(
                        db,
                        "Garlic Pasta",
                        "A simple pasta flavoured with garlic.",
                        "Cook the pasta. Fry the garlic gently in oil. " +
                                "Add the pasta and mix until coated."
                );
        insertRecipeIngredient(
                db,
                garlicPastaId,
                "Pasta",
                200,
                "g"
        );
        insertRecipeIngredient(
                db,
                garlicPastaId,
                "Garlic",
                2,
                "clove"
        );
        insertRecipeIngredient(
                db,
                garlicPastaId,
                "Oil",
                20,
                "ml"
        );

        //RECIPE 8
        long chickenRiceId =
                insertRecipe(
                        db,
                        "Chicken and Rice",
                        "A simple chicken and rice meal.",
                        "Cook the rice. Cook the chicken in oil until fully cooked. " +
                                "Serve the chicken with the rice."
                );
        insertRecipeIngredient(
                db,
                chickenRiceId,
                "Chicken",
                200,
                "g"
        );
        insertRecipeIngredient(
                db,
                chickenRiceId,
                "Rice",
                150,
                "g"
        );
        insertRecipeIngredient(
                db,
                chickenRiceId,
                "Oil",
                15,
                "ml"
        );

        //RECIPE 9
        long eggFriedRiceId =
                insertRecipe(
                        db,
                        "Egg Fried Rice",
                        "Rice fried with egg and soy sauce.",
                        "Cook the egg in oil. Add the cooked rice and soy sauce. " +
                                "Stir-fry everything together."
                );
        insertRecipeIngredient(
                db,
                eggFriedRiceId,
                "Rice",
                200,
                "g"
        );
        insertRecipeIngredient(
                db,
                eggFriedRiceId,
                "Egg",
                2,
                "item"
        );
        insertRecipeIngredient(
                db,
                eggFriedRiceId,
                "Soy Sauce",
                15,
                "ml"
        );
        insertRecipeIngredient(
                db,
                eggFriedRiceId,
                "Oil",
                15,
                "ml"
        );

        //RECIPE 10
        long mashedPotatoesId =
                insertRecipe(
                        db,
                        "Mashed Potatoes",
                        "Soft and creamy mashed potatoes.",
                        "Boil the potatoes until soft. " +
                                "Drain them and mash with the milk and butter."
                );
        insertRecipeIngredient(
                db,
                mashedPotatoesId,
                "Potato",
                3,
                "item"
        );
        insertRecipeIngredient(
                db,
                mashedPotatoesId,
                "Milk",
                100,
                "ml"
        );
        insertRecipeIngredient(
                db,
                mashedPotatoesId,
                "Butter",
                20,
                "g"
        );

        //RECIPE 11
        long chickenSandwichId =
                insertRecipe(
                        db,
                        "Chicken Sandwich",
                        "A simple chicken and mayonnaise sandwich.",
                        "Cook the chicken and slice it. " +
                                "Place it onto the bread, add mayonnaise " +
                                "and close the sandwich."
                );
        insertRecipeIngredient(
                db,
                chickenSandwichId,
                "Bread",
                2,
                "slice"
        );
        insertRecipeIngredient(
                db,
                chickenSandwichId,
                "Chicken",
                100,
                "g"
        );
        insertRecipeIngredient(
                db,
                chickenSandwichId,
                "Mayonnaise",
                20,
                "g"
        );

        //RECIPE 12
        long bananaOatsId =
                insertRecipe(
                        db,
                        "Banana Oats",
                        "Warm oats served with banana.",
                        "Cook the oats with milk until soft. " +
                                "Slice the banana and add it to the oats."
                );
        insertRecipeIngredient(
                db,
                bananaOatsId,
                "Oats",
                50,
                "g"
        );
        insertRecipeIngredient(
                db,
                bananaOatsId,
                "Milk",
                200,
                "ml"
        );
        insertRecipeIngredient(
                db,
                bananaOatsId,
                "Banana",
                1,
                "item"
        );

        //RECIPE 13
        long pancakesId =
                insertRecipe(
                        db,
                        "Pancakes",
                        "Simple homemade pancakes.",
                        "Mix the flour, egg and milk into a batter. " +
                                "Cook portions of the batter in a lightly oiled pan."
                );
        insertRecipeIngredient(
                db,
                pancakesId,
                "Flour",
                150,
                "g"
        );
        insertRecipeIngredient(
                db,
                pancakesId,
                "Egg",
                1,
                "item"
        );
        insertRecipeIngredient(
                db,
                pancakesId,
                "Milk",
                200,
                "ml"
        );
        insertRecipeIngredient(
                db,
                pancakesId,
                "Oil",
                10,
                "ml"
        );

        //RECIPE 14
        long bakedPotatoId =
                insertRecipe(
                        db,
                        "Baked Potato",
                        "A simple baked potato topped with cheese.",
                        "Bake the potato until soft. " +
                                "Cut it open, add butter and cheese, then serve."
                );
        insertRecipeIngredient(
                db,
                bakedPotatoId,
                "Potato",
                1,
                "item"
        );
        insertRecipeIngredient(
                db,
                bakedPotatoId,
                "Butter",
                10,
                "g"
        );
        insertRecipeIngredient(
                db,
                bakedPotatoId,
                "Cheese",
                30,
                "g"
        );

        //RECIPE 15
        long tomatoOmeletteId =
                insertRecipe(
                        db,
                        "Tomato Omelette",
                        "An egg omelette with tomato.",
                        "Beat the eggs. Chop the tomato. " +
                                "Cook the tomato in butter, add the eggs " +
                                "and cook until set."
                );
        insertRecipeIngredient(
                db,
                tomatoOmeletteId,
                "Egg",
                2,
                "item"
        );
        insertRecipeIngredient(
                db,
                tomatoOmeletteId,
                "Tomato",
                1,
                "item"
        );
        insertRecipeIngredient(
                db,
                tomatoOmeletteId,
                "Butter",
                10,
                "g"
        );

        //RECIPE 16
        long cheeseSandwichId =
                insertRecipe(
                        db,
                        "Cheese Sandwich",
                        "A quick cold cheese sandwich.",
                        "Place the cheese between the bread slices and serve."
                );
        insertRecipeIngredient(
                db,
                cheeseSandwichId,
                "Bread",
                2,
                "slice"
        );
        insertRecipeIngredient(
                db,
                cheeseSandwichId,
                "Cheese",
                50,
                "g"
        );

        //RECIPE 17
        long bananaPancakesId =
                insertRecipe(
                        db,
                        "Banana Pancakes",
                        "Simple pancakes made with banana.",
                        "Mash the banana and mix it with the egg and flour. " +
                                "Cook small portions in a lightly oiled pan."
                );
        insertRecipeIngredient(
                db,
                bananaPancakesId,
                "Banana",
                1,
                "item"
        );
        insertRecipeIngredient(
                db,
                bananaPancakesId,
                "Egg",
                1,
                "item"
        );
        insertRecipeIngredient(
                db,
                bananaPancakesId,
                "Flour",
                100,
                "g"
        );
        insertRecipeIngredient(
                db,
                bananaPancakesId,
                "Oil",
                10,
                "ml"
        );

        //RECIPE 18
        long tunaPastaId =
                insertRecipe(
                        db,
                        "Tuna Pasta",
                        "Pasta mixed with tuna and mayonnaise.",
                        "Cook the pasta and allow it to cool slightly. " +
                                "Add the tuna and mayonnaise and mix well."
                );
        insertRecipeIngredient(
                db,
                tunaPastaId,
                "Pasta",
                200,
                "g"
        );
        insertRecipeIngredient(
                db,
                tunaPastaId,
                "Tuna",
                100,
                "g"
        );
        insertRecipeIngredient(
                db,
                tunaPastaId,
                "Mayonnaise",
                30,
                "g"
        );

        //RECIPE 19
        long tomatoRiceId =
                insertRecipe(
                        db,
                        "Tomato Rice",
                        "Rice cooked with tomato and onion.",
                        "Cook the rice. Fry the onion and tomato in oil, " +
                                "then mix them with the cooked rice."
                );
        insertRecipeIngredient(
                db,
                tomatoRiceId,
                "Rice",
                200,
                "g"
        );
        insertRecipeIngredient(
                db,
                tomatoRiceId,
                "Tomato",
                2,
                "item"
        );
        insertRecipeIngredient(
                db,
                tomatoRiceId,
                "Onion",
                1,
                "item"
        );
        insertRecipeIngredient(
                db,
                tomatoRiceId,
                "Oil",
                15,
                "ml"
        );

        // RECIPE 20 - EGG SANDWICH
        long eggSandwichId =
                insertRecipe(
                        db,
                        "Egg Sandwich",
                        "A simple egg and mayonnaise sandwich.",
                        "Cook the eggs and allow them to cool. " +
                                "Mix them with mayonnaise and place " +
                                "the mixture between the bread."
                );
        insertRecipeIngredient(
                db,
                eggSandwichId,
                "Bread",
                2,
                "slice"
        );
        insertRecipeIngredient(
                db,
                eggSandwichId,
                "Egg",
                2,
                "item"
        );
        insertRecipeIngredient(
                db,
                eggSandwichId,
                "Mayonnaise",
                20,
                "g"
        );
    }

    //Creating the automatic insert recipe menu
    private long insertRecipe(
            SQLiteDatabase db,
            String name,
            String description,
            String instructions) {
        //Creating the recipe values
        ContentValues values =
                new ContentValues();
        //Adding the recipe information
        values.put(
                COLUMN_RECIPE_NAME,
                name
        );
        values.put(
                COLUMN_RECIPE_DESCRIPTION,
                description
        );
        values.put(
                COLUMN_RECIPE_INSTRUCTIONS,
                instructions
        );
        //Adding the recipe and returning its ID
        return db.insert(
                TABLE_RECIPES,
                null,
                values
        );
    }

    // AUTOMATIC RECIPE INGREDIENT INSERT METHOD
    private void insertRecipeIngredient(
            SQLiteDatabase db,
            long recipeId,
            String ingredientName,
            double quantity,
            String unit) {
        //Creating the recipe ingredient values
        ContentValues values =
                new ContentValues();
        //Adding the recipe ID
        values.put(
                COLUMN_RECIPE_INGREDIENT_RECIPE_ID,
                recipeId
        );
        //Adding the ingredient name
        values.put(
                COLUMN_RECIPE_INGREDIENT_NAME,
                ingredientName
        );

        //Adding the required quantity
        values.put(
                COLUMN_RECIPE_INGREDIENT_QUANTITY,
                quantity
        );
        //Adding the unit
        values.put(
                COLUMN_RECIPE_INGREDIENT_UNIT,
                unit
        );
        //Adding the recipe ingredient
        db.insert(
                TABLE_RECIPE_INGREDIENTS,
                null,
                values
        );
    }
}