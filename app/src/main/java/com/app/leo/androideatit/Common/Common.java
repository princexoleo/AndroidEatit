package com.app.leo.androideatit.Common;

import com.app.leo.androideatit.Model.User;

/**
 * Created by Guest User on 3/23/2018.
 */

public class Common {
    public static User currentUser;

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
