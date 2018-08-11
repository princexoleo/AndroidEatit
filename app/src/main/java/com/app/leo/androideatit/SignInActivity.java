package com.app.leo.androideatit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.leo.androideatit.Common.Common;
import com.app.leo.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    EditText editPhone,editPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editPassword=findViewById(R.id.editPass);
        editPhone=findViewById(R.id.editPhone);
        btnSignIn=findViewById(R.id.btnSignIn);

        //init Firebase Database

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog=new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //check if user not exists in database

                        if(dataSnapshot.child(editPhone.getText().toString()).exists()) {
                            //Get user information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(editPassword.getText().toString())) {
                                user.setPhone(editPhone.getText().toString()); //set user phone numer
                                Toast.makeText(SignInActivity.this, "Sign in successfully !", Toast.LENGTH_SHORT).show();
                                Intent homeIntent=new Intent(SignInActivity.this,Home.class);
                                Common.currentUser=user;
                                startActivity(homeIntent);
                                finish();
                            } else {
                                Toast.makeText(SignInActivity.this, "Sign in failed !!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignInActivity.this, "Users doesn't exists !!!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
