package com.example.alphavantagestock;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SendTokenToServer {

    private RequestQueue _queue;
    private static final String TAG = "SendTokenToServer";
    private final static String REQUEST_URL = "http://10.0.0.131:8080/";

    public class TokenResponse {
        public boolean isError;
        public int userID;

        public TokenResponse(boolean isError, int userID) {
            this.isError = isError;
            this.userID = userID;
        }
    }

    public interface TokenResponseListener {
        public void onResponse(TokenResponse response);
    }

    public SendTokenToServer(Context context) {
        _queue = Volley.newRequestQueue(context);
    }

    private TokenResponse createErrorResponse() {
        return new TokenResponse(true,  0);
    }

    public void dispatchRequest(String token, int id, final TokenResponseListener listener) {
        Log.e(TAG, "send Registration To Server");
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("token", token);
            requestObject.put("id", id);
        }
        catch (JSONException e) {
            listener.onResponse(createErrorResponse());
            return;
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, REQUEST_URL + id + "/token", requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Token saved successfully");
                        try {
                            TokenResponse res = new TokenResponse(false,
                                    response.getInt("id"));
                            listener.onResponse(res);
                        }
                        catch (JSONException e) {
                            listener.onResponse(createErrorResponse());
                        }
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
}


