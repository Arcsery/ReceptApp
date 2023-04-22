package com.example.recept;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    private ArrayList<RecipeItem> mItemList;
    private RecipeAdapter mAdapter;

    private int gridNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();;

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemList = new ArrayList<>();

        mAdapter = new RecipeAdapter(this, mItemList);

        mRecyclerView.setAdapter(mAdapter);

        initializeData();

    }

    private void initializeData() {
        String[] itemsList = getResources().getStringArray(R.array.shopping_item_names);
        String[] itemsDescription = getResources().getStringArray(R.array.shopping_item_desc);
        String[] itemsFoodname = getResources().getStringArray(R.array.shopping_item_price);

        TypedArray itemsImageresource = getResources().obtainTypedArray(R.array.shopping_item_images);
        TypedArray itemsRate = getResources().obtainTypedArray(R.array.shopping_item_rates);

        mItemList.clear();
        for (int i = 0; i < itemsList.length; i++){
            mItemList.add(new RecipeItem(itemsList[i], itemsDescription[i], itemsFoodname[i], itemsRate.getFloat(i, 0), itemsImageresource.getResourceId(i, 0)));
        }

        itemsImageresource.recycle();

        mAdapter.notifyDataSetChanged();
    }
}