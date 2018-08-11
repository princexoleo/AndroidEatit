package com.app.leo.androideatit.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.leo.androideatit.Interface.ItemClickListner;
import com.app.leo.androideatit.R;

/**
 * Created by Guest User on 3/26/2018.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView food_name;
    public ImageView food_image;
    private ItemClickListner itemClickListner;
    public FoodViewHolder(View itemView) {
        super(itemView);
        food_name=itemView.findViewById(R.id.food_name);
        food_image=itemView.findViewById(R.id.food_image);//////imageView??
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(itemView,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
