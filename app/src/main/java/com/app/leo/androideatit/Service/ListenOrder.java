package com.app.leo.androideatit.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.leo.androideatit.Common.Common;
import com.app.leo.androideatit.Model.Request;
import com.app.leo.androideatit.OrderStatus;
import com.app.leo.androideatit.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListenOrder extends Service implements ChildEventListener {
    FirebaseDatabase db;
    DatabaseReference requestRef;

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseDatabase.getInstance();
        requestRef = db.getReference("Requets");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestRef.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
          //
        Request request = dataSnapshot.getValue(Request.class);

        showNotification(dataSnapshot.getKey(),request);
    }

    private void showNotification(String key, Request request) {

        Intent intent =new Intent(getBaseContext(), OrderStatus.class);
        intent.putExtra("userPhone",request.getPhone());
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),
                0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

      //  NotificationCompat.Builder builder= new NotificationCompat.Builder(getBaseContext());

        Notification.Builder builder= new Notification.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("L Resturant")
                .setContentInfo("Your order was updated !!")
                .setContentText("Order #"+key+" was update to "+ Common.convertCodeToStatus(request.getStatus()))
                .setContentIntent(pendingIntent)
                .setContentInfo("info")
                .setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager notificationManager =(NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
      try{
          notificationManager.notify(1,builder.build());
      }catch (NullPointerException e)
      {
          Log.d("ListenerOrder","onNotify: null pointer exception: "+e.getMessage());
      }


    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
