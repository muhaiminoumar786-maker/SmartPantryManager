package com.example.smartpantrymanager;

// Importing the necessary libraries
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

//Creating the adapter used to display ingredients
public class IngredientAdapter
        extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{
    //Creating the list that will store the ingredients
    private ArrayList<Ingredient> ingredientList;
    //Creating the context used by the adapter
    private Context context;
    //Creating the database helper
    private DatabaseHelper databaseHelper;
    //Creating the listener used when an ingredient is deleted
    private OnIngredientChangedListener ingredientChangedListener;
    //Creating the interface used to notify the pantry screen
    public interface OnIngredientChangedListener{
        //Creating the method that runs when ingredient information changes
        void onIngredientChanged();
    }
    //Creating the adapter
    public IngredientAdapter(
            ArrayList<Ingredient> ingredientList,
            OnIngredientChangedListener ingredientChangedListener) {
        //Storing the ingredient list
        this.ingredientList = ingredientList;
        //Storing the ingredient changed listener
        this.ingredientChangedListener = ingredientChangedListener;
    }

    //Creating the layout for each ingredient
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType){
        //Getting the context from the parent
        context = parent.getContext();

        //Connecting to the SQLite database
        databaseHelper = new DatabaseHelper(context);
        //Connecting the adapter to the ingredient item XML layout
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    //Displaying the ingredient information
    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder,
            int position){
        //Getting the ingredient at the current position
        Ingredient ingredient = ingredientList.get(position);
        //Displaying the ingredient name
        holder.txtIngredientName.setText(
                ingredient.getName()
        );
        //Displaying the ingredient quantity and unit
        holder.txtIngredientQuantity.setText(
                ingredient.getQuantity() + " " + ingredient.getUnit()
        );
        //Getting the expiry date
        String expiryDate = ingredient.getExpiryDate();

        //Checking if an expiry date was entered
        if (expiryDate != null && !expiryDate.trim().isEmpty()) {
            //Displaying the expiry date
            holder.txtIngredientExpiry.setText(
                    "Expires: " + expiryDate
            );
        }else{
            //Displaying a message if there is no expiry date
            holder.txtIngredientExpiry.setText(
                    "No expiry date"
            );
        }

        //Creating an action for the edit button
        holder.btnEditIngredient.setOnClickListener(v -> {
            //Creating an intent to open the ingredient screen
            Intent intent = new Intent(
                    context,
                    AddIngredientActivity.class
            );
            //Sending the ingredient ID
            intent.putExtra(
                    "ingredient_id",
                    ingredient.getId()
            );
            //Sending the ingredient name
            intent.putExtra(
                    "ingredient_name",
                    ingredient.getName()
            );
            //Sending the ingredient quantity
            intent.putExtra(
                    "ingredient_quantity",
                    ingredient.getQuantity()
            );
            //Sending the ingredient unit
            intent.putExtra(
                    "ingredient_unit",
                    ingredient.getUnit()
            );
            //Sending the ingredient expiry date
            intent.putExtra(
                    "ingredient_expiry",
                    ingredient.getExpiryDate()
            );
            //Telling the screen that we are editing an ingredient
            intent.putExtra(
                    "edit_mode",
                    true
            );
            //Opening the ingredient screen
            context.startActivity(intent);
        });

        //Creating an action for the delete button
        holder.btnDeleteIngredient.setOnClickListener(v -> {
            //Creating the delete confirmation message
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(context);
            //Creating the confirmation message title
            builder.setTitle(
                    "Delete " + ingredient.getName() + "?"
            );
            //Creating the confirmation message
            builder.setMessage(
                    "Are you sure you want to delete this ingredient?"
            );
            //Creating the delete option
            builder.setPositiveButton("Delete", (dialog, which) -> {
                //Deleting the ingredient from the database
                boolean ingredientDeleted =
                        databaseHelper.deleteIngredient(
                                ingredient.getId()
                        );
                //Checking if the ingredient was successfully deleted
                if(ingredientDeleted){
                    //Displaying a success message
                    Toast.makeText(
                            context,
                            "Ingredient deleted successfully",
                            Toast.LENGTH_SHORT
                    ).show();
                    //Telling the pantry screen that an ingredient changed
                    if (ingredientChangedListener != null) {
                        ingredientChangedListener.onIngredientChanged();
                    }
                }else{
                    //Displaying an error message
                    Toast.makeText(
                            context,
                            "Ingredient could not be deleted",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
            //Creating the cancel option
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                //Closing the confirmation message
                dialog.dismiss();
            });
            //Displaying the confirmation message
            builder.show();
        });
    }

    //Getting the number of ingredients in the list
    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
    //Creating the ViewHolder for each ingredient
    public static class IngredientViewHolder
            extends RecyclerView.ViewHolder {
        //Creating the text fields used to display ingredient information
        TextView txtIngredientName;
        TextView txtIngredientQuantity;
        TextView txtIngredientExpiry;

        //Creating the edit and delete buttons
        Button btnEditIngredient;
        Button btnDeleteIngredient;
        //Creating the ViewHolder
        public IngredientViewHolder(@NonNull View itemView){
            super(itemView);
            //Connecting the ingredient name from the XML layout
            txtIngredientName =
                    itemView.findViewById(
                            R.id.txtIngredientName
                    );
            //Connecting the ingredient quantity from the XML layout
            txtIngredientQuantity =
                    itemView.findViewById(
                            R.id.txtIngredientQuantity
                    );
            //Connecting the ingredient expiry date from the XML layout
            txtIngredientExpiry =
                    itemView.findViewById(
                            R.id.txtIngredientExpiry
                    );
            //Connecting the edit button from the XML layout
            btnEditIngredient =
                    itemView.findViewById(
                            R.id.btnEditIngredient
                    );
            //Connecting the delete button from the XML layout
            btnDeleteIngredient =
                    itemView.findViewById(
                            R.id.btnDeleteIngredient
                    );
        }
    }
}