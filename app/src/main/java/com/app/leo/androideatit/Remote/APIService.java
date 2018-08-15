package com.app.leo.androideatit.Remote;



import com.app.leo.androideatit.Model.MyResponse;
import com.app.leo.androideatit.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
               {
                       "Content-Type:application/json",
                       "Authorization:key=AAAAu5uqAiU:APA91bEet3zLzb9qdSwkPXEYei4TXusDywnItIY-XFpIRIg7HalmFHPdXtqkgmIY5qAMeuzNRXrHkDS21fpVVEVconYNSnH7R27hYjUcNJUj-484YH1CnBScpVJrqRkeeRauONHw8Oj-"

               }

            )

    @POST("fcm/send")
    Call<MyResponse>sendNotification(@Body Sender body);


}
