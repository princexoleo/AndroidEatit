package com.app.leo.androideatit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.leo.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {

    MaterialEditText editPhone,editName,editPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editName=findViewById(R.id.editName);
        editPhone=findViewById(R.id.editPhone);
        editPassword=findViewById(R.id.editPass);
        btnSignUp=findViewById(R.id.btnSignUp);

        //init Firebase Database

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog=new ProgressDialog(SignUpActivity.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check user phone if already in database

                        if(dataSnapshot.child(editPhone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Phone Number already register.. !!!", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            mDialog.dismiss();
                            String phone_number=editPhone.getText().toString();

                            User user=new User(editName.getText().toString(),editPassword.getText().toString());
                            if(phone_number.length()<11)
                            {
                                Toast.makeText(SignUpActivity.this, "Invalid Phone number!!!please try a valid Phone Number", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                table_user.child(phone_number).setValue(user);
                                Toast.makeText(SignUpActivity.this, "Sign Up successful !!!", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

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
