# Smart Pantry Manager - Features

## 1. Pantry Management

The application allows users to manage ingredients stored in their pantry.

Users can:

- Add ingredients
- Edit ingredients
- Delete ingredients
- Store ingredient quantities
- Store measurement units
- Store optional expiry dates

## 2. Input Validation

The Add/Edit Ingredient screen validates user input before information is saved.

The application checks that:

- An ingredient name has been entered
- A quantity has been entered
- The quantity is a valid number
- The quantity is greater than zero
- A measurement unit has been entered

## 3. Expiry Date Selection

Users can optionally select an expiry date using an Android date picker.

Past dates cannot be selected.

## 4. Persistent Pantry Storage

Pantry ingredients are stored using SQLite.

This allows pantry information to remain available after the application is closed and reopened.

## 5. Suggested Recipes

The application analyses the ingredients available in the pantry and displays recipes that can currently be prepared.

A recipe is only suggested when all required ingredients are available in sufficient quantities.

## 6. Quantity Checking

The recipe matching system compares the available quantity of each pantry ingredient with the quantity required by a recipe.

Recipes with insufficient ingredient quantities are excluded.

## 7. Unit Conversion

Compatible measurement units can be converted during recipe matching.

Examples include:

- Litres and millilitres
- Kilograms and grams

## 8. Ingredient Name Matching

The matching system handles simple naming differences such as singular and plural ingredient names.

## 9. Recipe Details

Users can select a suggested recipe to view additional information, including:

- Recipe name
- Description
- Required ingredients
- Quantities
- Cooking instructions

## 10. Settings

The Settings screen allows basic user preferences to be saved using SharedPreferences.

The application stores:

- Profile name
- Expiry reminder preference

The expiry reminder preference is stored, but notification-based expiry reminders are not currently implemented.

## 11. Navigation

The main dashboard provides access to:

- My Pantry
- Suggested Recipes
- Settings

Android Intents are used to navigate between application activities.