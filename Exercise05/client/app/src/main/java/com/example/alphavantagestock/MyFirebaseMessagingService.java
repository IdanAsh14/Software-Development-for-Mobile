package com.example.alphavantagestock;


import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private final static String REQUEST_URL = "http://10.0.0.131:8080/";
    private static final String USERNAME = "username"; // this would be derived from the user data in a real application!
    private RequestQueue _queue;

    @Override
    public void onCreate(){
        super.onCreate();
        _queue =  Volley.newRequestQueue(this);
    }

    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        Log.e(TAG, "send Registration To Server");
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, REQUEST_URL + USERNAME + "/token", requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Token saved successfully");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Failed to save token - " + error);
                    }
                });

        _queue.add(req);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Log.d(TAG, "name: " + remoteMessage.getData().get("name"));
            Log.d(TAG, "price: " + remoteMessage.getData().get("price"));
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String currentTime = sdf.format(new Date());
            Log.d(TAG, "time: " + currentTime);
            Intent intent = new Intent();
            intent.putExtra("stock name", remoteMessage.getData().get("name"));
            intent.putExtra("price", remoteMessage.getData().get("price"));
            intent.putExtra("time", currentTime);
            intent.setAction("com.my.app.onMessageReceived");
            sendBroadcast(intent);
        }
    }
}
