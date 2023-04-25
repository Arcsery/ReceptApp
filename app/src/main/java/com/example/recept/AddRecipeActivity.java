package com.example.recept;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.UUID;

public class AddRecipeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference items;
    String userId;
    String userName;

    String recipeId;
    EditText foodName;
    EditText smallDescription;
    EditText description;
    Button addRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        foodName = findViewById(R.id.foodName);
        smallDescription = findViewById(R.id.small_description);
        description = findViewById(R.id.description);
        addRecipeButton = findViewById(R.id.add_recipe_button);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getUid();
        items = db.collection("users");
        recipeId = UUID.randomUUID().toString();
        getUsername();
        addRecipe();
    }


    public void addRecipe(){
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(foodName.getText().toString().isEmpty() || smallDescription.getText().toString().isEmpty()|| description.getText().toString().isEmpty()){
                    Toast.makeText(AddRecipeActivity.this, "Kérlek az összes mezőt töltds ki!", Toast.LENGTH_SHORT).show();
                }else{
                    String mFoodname = foodName.getText().toString();
                    String mSmalldescription = smallDescription.getText().toString();
                    String mDescription = description.getText().toString();
                    Log.d("Activity", userName);
                    RecipeItem recipe = new RecipeItem(recipeId,userId,userName,mSmalldescription,mDescription,mFoodname,5,0);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db.collection("recipes").document(recipeId).set(recipe).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("Activity", "Skierült hozzáadni");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent returnIntent = new Intent();
                                            returnIntent.putExtra("result", "success");
                                            setResult(RESULT_OK, returnIntent);
                                            finish();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Activity", "gond van");
                                }
                            });
                        }
                    });
                    thread.start();
                }
            }
        });
    }

    public void getUsername(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                items.whereEqualTo("id", userId).get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        User user = documentSnapshot.toObject(User.class);
                        userName = user.name;
                    }
                });
            }
        });
        thread.start();
    }

}