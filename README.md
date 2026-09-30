# Smart Pantry Manager

## Overview

Smart Pantry Manager is an Android application designed to help users manage ingredients stored in their pantry and discover recipes that can be prepared using the ingredients they currently have.

The application was developed using Java and XML in Android Studio, with SQLite used for local data storage.

## Features

- Add pantry ingredients
- Edit existing ingredients
- Delete ingredients
- Store ingredient quantities and units
- Add optional expiry dates
- Save pantry information using SQLite
- Suggest recipes based on available pantry ingredients
- Strict recipe matching based on ingredient availability and quantity
- Support for compatible unit conversions such as litres and millilitres
- Display detailed recipe information
- Store basic user settings and preferences
- Persistent storage between application sessions

## Technologies Used

- Java
- XML
- Android Studio
- SQLite
- RecyclerView
- SharedPreferences
- Git
- GitHub

## Pantry Management

Users can add ingredients by entering:

- Ingredient name
- Quantity
- Unit
- Optional expiry date

Ingredients can later be edited or deleted from the pantry.

## Recipe Matching

The Smart Pantry Manager uses strict recipe matching.

A recipe is suggested only when all of its required ingredients are available in the user's pantry in sufficient quantities.

The matching system also handles compatible units. For example:

- 0.2 L can satisfy a requirement of 200 ml
- 1 kg can be converted to 1000 g

Simple ingredient naming differences, such as singular and plural forms, are also handled by the matching system.

## Recipe Database

The application contains 20 preloaded recipes. Each recipe stores:

- Recipe name
- Description
- Required ingredients
- Required quantities
- Units
- Cooking instructions

## Data Storage

SQLite is used to store pantry ingredients and recipe information locally on the Android device.

SharedPreferences is used to store user settings such as the profile name and expiry reminder preference.

## Application Screens

The application includes the following main screens:

1. Main Dashboard
2. My Pantry
3. Add/Edit Ingredient
4. Suggested Recipes
5. Recipe Details
6. Settings

## Running the Application

1. Clone or download the repository.
2. Open the project in Android Studio.
3. Allow Gradle to synchronise the project.
4. Select an Android emulator or compatible Android device.
5. Run the application.

## Testing

The application was tested for:

- Adding ingredients
- Editing ingredients
- Deleting ingredients
- Input validation
- SQLite persistence
- Strict recipe matching
- Quantity checking
- Unit conversion
- Recipe detail navigation
- Settings persistence
- General application navigation

## Known Limitation

The expiry reminder preference is stored in the application settings, but Android notification-based expiry reminders are not currently implemented.

## Purpose

The purpose of the Smart Pantry Manager is to provide a simple way for users to keep track of pantry ingredients and identify meals that can be prepared from ingredients they already have.