package com.example.recept;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class RecipeDetealActivity extends AppCompatActivity {
    TextView mFoodName;
    TextView mUserName;
    TextView mInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_deteal);

        mFoodName = findViewById(R.id.recipe_name);
        mInstructions = findViewById(R.id.instructions);
        mUserName = findViewById(R.id.user_name);

        String foodname = getIntent().getStringExtra("FoodName");
        String username = getIntent().getStringExtra("Name");
        String instructions = getIntent().getStringExtra("Description");

        mFoodName.setText(foodname);
        mInstructions.setText(instructions);
        mUserName.setText(getString(R.string.food_name_text, username));


       /*

        mFoodName = mFoodName.findViewById(R.id.recipe_name);
        mInstructions = mInstructions.findViewById(R.id.instructions);
        mUserName = mUserName.findViewById(R.id.user_name);
        String foodname = getIntent().getStringExtra("FoodName");
        String username = getIntent().getStringExtra("Description");
        String instructions = getIntent().getStringExtra("Name");

        mFoodName.setText(foodname);
        mInstructions.setText(instructions);
        mUserName.setText(username);
        */
    }
}