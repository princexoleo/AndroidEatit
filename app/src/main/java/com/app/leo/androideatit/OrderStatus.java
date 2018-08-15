package com.app.leo.androideatit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.app.leo.androideatit.Common.Common;
import com.app.leo.androideatit.Interface.ItemClickListner;
import com.app.leo.androideatit.Model.Request;
import com.app.leo.androideatit.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;


    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //init firebase database
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        recyclerView=findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        //if we start Orderstatus acctivity from Home
        //we will not put any extra
        if(getIntent().getExtras()==null)
        {
            loadOrders(Common.currentUser.getPhone());


        }else
        {
            loadOrders(getIntent().getStringExtra("userPhone"));
        }




    }

    private void loadOrders(String phone) {
        adapter=new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone")
                .equalTo(phone)

        ) {
            @Override
            protected void populateViewHolder(final OrderViewHolder viewHolder, final Request model, int position) {

                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());

               viewHolder.setItemClickListner(new ItemClickListner() {
                   @Override
                   public void onClick(View view, int position, boolean isLongClick) {
                       //
                       Toast.makeText(OrderStatus.this, "your order status is: "+Common.convertCodeToStatus(model.getStatus()), Toast.LENGTH_SHORT).show();
                   }
               });
            }
        };
        recyclerView.setAdapter(adapter);

    }





}
