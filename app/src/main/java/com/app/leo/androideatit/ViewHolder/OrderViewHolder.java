package com.app.leo.androideatit.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.leo.androideatit.Interface.ItemClickListner;
import com.app.leo.androideatit.R;

/**
 * Created by Guest User on 4/16/2018.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtOrderId,txtOrderStatus,txtOrderAddress,txtOrderPhone;

    private ItemClickListner itemClickListner;
    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderAddress=itemView.findViewById(R.id.order_address);
        txtOrderStatus=itemView.findViewById(R.id.order_status);
        txtOrderPhone=itemView.findViewById(R.id.order_phone);
        txtOrderId=itemView.findViewById(R.id.order_name);

       itemView.setOnClickListener(this);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(itemView,getAdapterPosition(),false);

    }
}
