package com.app.leo.androideatit.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.leo.androideatit.Interface.ItemClickListner;
import com.app.leo.androideatit.R;

/**
 * Created by Guest User on 3/23/2018.
 */

public class MenuViewHolder extends ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView;

    private ItemClickListner itemClickListner;

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    public MenuViewHolder(View itemView) {
        super(itemView);

        txtMenuName=itemView.findViewById(R.id.menu_name);
        imageView=itemView.findViewById(R.id.menu_image);//////imageView??
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(itemView,getAdapterPosition(),false);
    }
}
