package com.app.leo.androideatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.leo.androideatit.Interface.ItemClickListner;
import com.app.leo.androideatit.Model.Food;
import com.app.leo.androideatit.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId="";

    FirebaseRecyclerAdapter<Food,FoodViewHolder>adapter;
    //Search Bar function
    FirebaseRecyclerAdapter<Food,FoodViewHolder>searchAdapter;
    List<String> suggestList=new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //init Firebase Database...
        database=FirebaseDatabase.getInstance();
        foodList=database.getReference("Foods");

        recyclerView=findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //receive Intent...
        if(getIntent()!=null)
        {
            categoryId=getIntent().getStringExtra("CategoryId");
        }
        if(!categoryId.isEmpty() ) //&& categoryId !=null
        {
            loadListFood(categoryId);
        }

        //Search....
        materialSearchBar=findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter food name");
        //materialSearchBar.setSpeechMode(false);no need cause we already set in XML file .!!
        loadSuggestion();//write method to load Suggest from Firebase database
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);////
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //when user type food name , we will change suggest list

                List<String>suggest=new ArrayList<>();
                for (String search:suggestList)//loop in suggest List
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when sear bar is close
                // Restore Original suggest Adapter...
                if(!enabled)
                {
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //when search finish
                //show result of search

                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("Name").equalTo(text.toString())// compare Food Name//////
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);
                final Food local=model;
                viewHolder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();//there is problem local.getName???????????
                        //Start new Acitvity
                        Intent foodDetail=new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",searchAdapter.getRef(position).getKey());//send Food ID to new Acitivity
                        startActivity(foodDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);//set adapter for recycler View
    }

    private void loadSuggestion() {
        foodList.orderByChild("MenuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren() )
                        {
                            Food item=postSnapshot.getValue(Food.class);
                            suggestList.add(item.getName());//Add Name of Food for suggest list

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void loadListFood(String categoryId) {
        adapter=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("menuId").equalTo(categoryId))
        {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
             viewHolder.food_name.setText(model.getName());
             Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);
             final Food local=model;
             viewHolder.setItemClickListner(new ItemClickListner() {
                 @Override
                 public void onClick(View view, int position, boolean isLongClick) {
                     Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();//there is problem local.getName???????????
                     //Start new Acitvity
                     Intent foodDetail=new Intent(FoodList.this,FoodDetail.class);
                     foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());//send Food ID to new Acitivity
                     startActivity(foodDetail);
                 }
             });
            }
        };
        //Set adapter
        recyclerView.setAdapter(adapter);
    }
}
