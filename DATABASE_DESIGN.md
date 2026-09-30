# Smart Pantry Manager - Database Design

## Database Overview

Smart Pantry Manager uses SQLite as its local database system.

SQLite was selected because it provides persistent local storage directly on the Android device and does not require an external database server or internet connection.

## DatabaseHelper

The DatabaseHelper class is responsible for creating and managing the application's SQLite database.

The application database contains three main tables:

- Ingredients
- Recipes
- Recipe Ingredients

## Ingredients Table

The Ingredients table stores ingredients added to the user's pantry.

The information stored includes:

- Ingredient ID
- Ingredient name
- Quantity
- Unit
- Optional expiry date

The ingredient ID acts as the unique identifier for each pantry ingredient.

The application supports Create, Read, Update and Delete operations on pantry ingredients.

## Recipes Table

The Recipes table stores the recipes available within the application.

Recipe information includes:

- Recipe ID
- Recipe name
- Description
- Cooking instructions

The application contains 20 preloaded recipes.

## Recipe Ingredients Table

The Recipe Ingredients table stores the ingredients required for each recipe.

Information includes:

- Recipe ID
- Ingredient name
- Required quantity
- Required unit

The Recipe ID connects each ingredient requirement to its corresponding recipe.

## Database Relationships

A recipe can require multiple ingredients.

The Recipes table therefore has a relationship with the Recipe Ingredients table through the Recipe ID.

This allows the application to retrieve all ingredient requirements belonging to a particular recipe.

## Pantry CRUD Operations

The application supports the following pantry database operations:

### Create
New ingredients can be added to the pantry.

### Read
Stored ingredients can be retrieved and displayed using a RecyclerView.

### Update
Existing ingredient information can be edited.

### Delete
Ingredients can be removed from the pantry.

## Persistent Storage

SQLite stores the pantry information locally on the Android device.

This means pantry ingredients remain available after the application is closed and reopened.

## Recipe Matching

The application retrieves pantry ingredients and recipe requirements from the database.

The available pantry quantities are compared with the required recipe quantities.

A recipe is only suggested when every required ingredient is available in a sufficient quantity.