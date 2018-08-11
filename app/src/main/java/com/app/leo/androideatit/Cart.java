package com.app.leo.androideatit;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.leo.androideatit.Common.Common;
import com.app.leo.androideatit.Database.Database;
import com.app.leo.androideatit.Model.Order;
import com.app.leo.androideatit.Model.Request;
import com.app.leo.androideatit.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    FButton btnPlace;

    List<Order>cart=new ArrayList<>();

    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //init Firebase
           database=FirebaseDatabase.getInstance();
           requests =database.getReference("Requests");

           //init
        recyclerView=findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice=findViewById(R.id.totalPriceCart);
        btnPlace=findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             showAlertDialog();

            }
        });

        loadListFood();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step");
        alertDialog.setMessage("Enter your Address: ");

        final EditText editAddress=new EditText(Cart.this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        editAddress.setLayoutParams(lp);
        alertDialog.setView(editAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Create Request
                Request request=new Request(
                  Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        editAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );

                //Submit to Firebase
                //we will using System.CurrentMilli to key
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);

                //Delete cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this,"Thank you, Order Place",Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }

    private void loadListFood() {
        cart=new Database(this).getCarts();
        adapter=new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //calculate total price
        int total=0;
        for(Order order:cart)
        {
            int price= Integer.parseInt(order.getPrice());
            int quantity= Integer.parseInt(order.getQuantity());

            total+= price*quantity;

            //total+=((Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity())));
        }
        Locale locale=new Locale("en","US");
        NumberFormat fmt=NumberFormat.getCurrencyInstance();
        txtTotalPrice.setText(fmt.format(total));



    }
}
