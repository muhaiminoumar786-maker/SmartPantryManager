# Smart Pantry Manager - Testing

## Testing Overview

The Smart Pantry Manager application was tested to ensure that the main application features function correctly and that data remains available between application sessions.

## Pantry Management Testing

### Add Ingredient
- Opened the My Pantry screen.
- Selected the option to add an ingredient.
- Entered an ingredient name, quantity and unit.
- Saved the ingredient.
- Confirmed that the ingredient appeared in the pantry list.

**Result:** Passed

### Edit Ingredient
- Selected an existing pantry ingredient.
- Changed its stored information.
- Updated the ingredient.
- Confirmed that the new information appeared in the pantry.

**Result:** Passed

### Delete Ingredient
- Selected an existing ingredient for deletion.
- Confirmed the deletion.
- Verified that the ingredient was removed from the pantry.

**Result:** Passed

## Data Persistence Testing

Ingredients were added to the pantry before closing the application.

The application was reopened and the ingredients were still available.

**Result:** Passed

## Recipe Matching Testing

The Pancakes recipe was tested using the following ingredients:

- Flour - 150 g
- Egg - 1 item
- Milk - 200 ml
- Oil - 10 ml

The Pancakes recipe appeared in Suggested Recipes when all required ingredients were available.

**Result:** Passed

## Insufficient Quantity Testing

The quantity of Milk was reduced from 200 ml to 100 ml.

The Pancakes recipe was no longer suggested because the available quantity was below the recipe requirement.

**Result:** Passed

## Unit Conversion Testing

Milk was changed from:

200 ml

to:

0.2 L

The Pancakes recipe was suggested again because the application correctly recognised that 0.2 L is equivalent to 200 ml.

**Result:** Passed

## Recipe Detail Testing

A suggested recipe was selected.

The application successfully displayed the recipe details, including its required ingredients and cooking instructions.

**Result:** Passed

## Settings Testing

The Settings screen was opened and preferences were saved.

The screen was reopened to confirm that the saved settings remained available.

**Result:** Passed

## Navigation Testing

Navigation between the following screens was tested:

- Main Dashboard
- My Pantry
- Add/Edit Ingredient
- Suggested Recipes
- Recipe Details
- Settings

The application successfully navigated between the screens without crashing.

**Result:** Passed

## Known Limitation

The expiry reminder preference can be saved in Settings, but Android notification-based expiry reminders have not been implemented.