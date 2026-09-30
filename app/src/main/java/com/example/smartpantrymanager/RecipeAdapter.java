package com.example.smartpantrymanager;

//Importing the necessary libraries
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

//Creating the adapter used to display recipes
public class RecipeAdapter
        extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    //Creating the list used to store recipes
    private ArrayList<Recipe> recipeList;
    //Creating the context used to open another activity
    private Context context;

    //Creating the RecipeAdapter
    public RecipeAdapter(
            ArrayList<Recipe> recipeList) {
        //Storing the recipe list
        this.recipeList =
                recipeList;
    }

    //Creating the layout for each recipe
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType){

        //Getting the application context
        context =
                parent.getContext();
        //Connecting the adapter to the recipe item XML layout
        View view = LayoutInflater
                .from(context)
                .inflate(
                        R.layout.item_recipe,
                        parent,
                        false
                );
        //Returning the recipe ViewHolder
        return new RecipeViewHolder(
                view
        );
    }

    //Displaying the recipe information
    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position){
        //Getting the recipe at the current position
        Recipe recipe =
                recipeList.get(
                        position
                );
        //Displaying the recipe name
        holder.txtRecipeName.setText(
                recipe.getName()
        );
        //Displaying the recipe description
        holder.txtRecipeDescription.setText(
                recipe.getDescription()
        );

        //Creating an action for the view recipe button
        holder.btnViewRecipe.setOnClickListener(v -> {
            //Creating an Intent to open the recipe details screen
            Intent intent =
                    new Intent(
                            context,
                            RecipeDetailActivity.class
                    );

            //Sending the recipe ID
            intent.putExtra(
                    "recipe_id",
                    recipe.getId()
            );
            //Sending the recipe name
            intent.putExtra(
                    "recipe_name",
                    recipe.getName()
            );
            //Sending the recipe description
            intent.putExtra(
                    "recipe_description",
                    recipe.getDescription()
            );
            //Sending the recipe instructions
            intent.putExtra(
                    "recipe_instructions",
                    recipe.getInstructions()
            );
            //Opening the recipe details screen
            context.startActivity(
                    intent
            );
        });
    }

    //Getting the number of recipes in the list
    @Override
    public int getItemCount(){
        //Returning the number of recipes
        return recipeList.size();
    }

    //Creating the ViewHolder used for each recipe
    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder{
        //Creating the recipe name text
        TextView txtRecipeName;
        //Creating the recipe description text
        TextView txtRecipeDescription;
        //Creating the view recipe button
        Button btnViewRecipe;


        //Creating the RecipeViewHolder
        public RecipeViewHolder(
                @NonNull View itemView){
            //Starting the ViewHolder
            super(itemView);
            //Connecting the recipe name
            txtRecipeName =
                    itemView.findViewById(
                            R.id.txtRecipeName
                    );

            //Connecting the recipe description
            txtRecipeDescription =
                    itemView.findViewById(
                            R.id.txtRecipeDescription
                    );

            //Connecting the view recipe button
            btnViewRecipe =
                    itemView.findViewById(
                            R.id.btnViewRecipe
                    );
        }
    }
}