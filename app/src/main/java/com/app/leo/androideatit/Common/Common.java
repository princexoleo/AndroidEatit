package com.app.leo.androideatit.Common;

import com.app.leo.androideatit.Model.User;
import com.app.leo.androideatit.Remote.APIService;
import com.app.leo.androideatit.Remote.FCMRetrofitClient;
import com.app.leo.androideatit.Remote.RetrofitClient;

/**
 * Created by Guest User on 3/23/2018.
 */

public class Common {
    public static User currentUser;

    private static final String BASE_URL="https://fcm.googleapis.com/";

    public static APIService getFCMService()
    {
        return FCMRetrofitClient.getClient(BASE_URL).create(APIService.class);

    }

    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
        {
            return "Placed";
        }
        else if (status.equals("1"))
        {
            return "On my way";
        }
        else {
            return "Shipped";
        }
    }
}
