# Smart Pantry Manager - Project Structure

## Application Architecture

Smart Pantry Manager is an Android application developed using Java and XML. The application uses SQLite for local database storage and SharedPreferences for storing basic application settings.

## Main Application Components

### MainActivity

MainActivity acts as the main dashboard of the application.

It provides navigation to:

- My Pantry
- Suggested Recipes
- Settings

Android Intents are used to move between the different activities.

### PANTRY_ACTIVITY

PANTRY_ACTIVITY manages the user's pantry.

Its main responsibilities include:

- Loading ingredients from the SQLite database
- Displaying ingredients using a RecyclerView
- Displaying an empty pantry message when no ingredients exist
- Opening the Add Ingredient screen
- Refreshing the pantry when ingredient information changes

### AddIngredientActivity

AddIngredientActivity is responsible for adding and editing pantry ingredients.

The user can enter:

- Ingredient name
- Quantity
- Unit
- Optional expiry date

Input validation is used before ingredient information is saved.

### SuggestedRecipesActivity

SuggestedRecipesActivity displays recipes that can be prepared using the ingredients currently available in the pantry.

Only recipes where all required ingredients are available in sufficient quantities are displayed.

### RecipeDetailActivity

RecipeDetailActivity displays detailed information about a selected recipe.

This includes:

- Recipe name
- Description
- Required ingredients
- Required quantities
- Cooking instructions

### SettingsActivity

SettingsActivity allows basic application preferences to be stored.

SharedPreferences is used to save the profile name and expiry reminder preference.

## Database

DatabaseHelper manages the SQLite database used by the application.

The database stores:

- Pantry ingredients
- Recipes
- Recipe ingredient requirements

The pantry functionality supports Create, Read, Update and Delete operations.

## RecyclerView

RecyclerView is used to display dynamic lists within the application.

IngredientAdapter manages the pantry ingredient list.

RecipeAdapter manages the suggested recipe list.

## Recipe Matching

The recipe matching system compares the ingredients required by each recipe with the ingredients available in the pantry.

A recipe is only suggested when every required ingredient is available in a sufficient quantity.

The system also supports compatible unit conversions such as:

- Litres to millilitres
- Millilitres to litres
- Kilograms to grams
- Grams to kilograms

Simple singular and plural ingredient names are also normalised during matching.

## Data Persistence

SQLite provides persistent storage for pantry and recipe information.

SharedPreferences provides persistent storage for basic application settings.

This allows information to remain available when the application is closed and reopened.