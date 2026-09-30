# Smart Pantry Manager - User Guide

## Introduction

Smart Pantry Manager is an Android application that allows users to manage pantry ingredients and discover recipes that can be prepared using the ingredients they currently have available.

## Main Dashboard

When the application is opened, the Main Dashboard provides access to the main sections of the application:

- My Pantry
- Suggested Recipes
- Settings

## Managing Pantry Ingredients

Select **My Pantry** from the Main Dashboard to view the ingredients currently stored in the pantry.

### Adding an Ingredient

1. Open My Pantry.
2. Select the Add Ingredient option.
3. Enter the ingredient name.
4. Enter the quantity.
5. Enter the measurement unit.
6. Optionally select an expiry date.
7. Select Save Ingredient.

The ingredient will then appear in the pantry.

### Editing an Ingredient

1. Open My Pantry.
2. Select the ingredient that needs to be changed.
3. Edit the required information.
4. Select Update Ingredient.

The updated information will be saved to the database.

### Deleting an Ingredient

An existing ingredient can be deleted from the pantry when it is no longer required.

After deletion, the pantry list is refreshed to display the remaining ingredients.

## Viewing Suggested Recipes

Select **Suggested Recipes** from the Main Dashboard.

The application compares the ingredients stored in the pantry with the ingredients required by the available recipes.

A recipe is displayed only when all required ingredients are available in sufficient quantities.

## Recipe Quantity Matching

The application checks both ingredient availability and quantity.

For example, if a recipe requires 200 ml of milk but only 100 ml is available, the recipe will not be suggested.

## Unit Conversion

The application supports compatible measurement conversions during recipe matching.

For example:

- 0.2 L is equivalent to 200 ml.
- 1 kg is equivalent to 1000 g.

This allows recipes to match even when compatible measurement units are entered differently.

## Viewing Recipe Details

Select a recipe from Suggested Recipes to open its detailed information.

The Recipe Details screen displays:

- Recipe name
- Description
- Required ingredients
- Required quantities
- Cooking instructions

## Settings

The Settings screen allows basic application preferences to be stored.

Users can save a profile name and an expiry reminder preference.

The expiry reminder setting is stored as a preference. Notification-based expiry reminders are not currently implemented.

## Data Storage

Pantry information is stored locally using SQLite.

This allows saved ingredients to remain available after the application is closed and reopened.

Application preferences are stored using SharedPreferences.