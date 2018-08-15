package com.app.leo.androideatit.Service;

import com.app.leo.androideatit.Common.Common;
import com.app.leo.androideatit.Model.Token;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String  tokenRefresh = FirebaseInstanceId.getInstance().getToken();
        if (Common.currentUser !=null)
        {
            updateTokentoFirebaase(tokenRefresh);
        }


    }

    private void updateTokentoFirebaase(String tokenRefresh) {
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        DatabaseReference tokenRef= db.getReference("Tokens");

        Token token=new Token(tokenRefresh,false);//false beacuse this token send from client
        tokenRef.child(Common.currentUser.getPhone()).setValue(token);

    }
}
