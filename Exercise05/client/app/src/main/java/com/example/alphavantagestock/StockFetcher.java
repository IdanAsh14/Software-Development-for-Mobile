package com.example.alphavantagestock;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class StockFetcher {

    private RequestQueue _queue;

    private final static String REQUEST_URL = "http://10.0.0.131:8080/stock";

    public class StockResponse {
        public boolean isError;
        public String name;
        public double price;

        public StockResponse(boolean isError, String name, double price) {
            this.isError = isError;
            this.name = name;
            this.price = price;
        }

    }

    public interface StockResponseListener {
        public void onResponse(StockResponse response);
    }

    public StockFetcher(Context context) {
        _queue = Volley.newRequestQueue(context);
    }

    private StockResponse createErrorResponse() {
        return new StockResponse(true, null, 0);
    }

    public void dispatchRequest(String stockName, final StockResponseListener listener) {
        JSONObject postBody = new JSONObject();
        try {
            postBody.put("stock", stockName);
        }
        catch (JSONException e) {
            listener.onResponse(createErrorResponse());
            return;
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, REQUEST_URL, postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            StockResponse res = new StockResponse(false,
                                    response.getJSONObject("Global Quote").getString("01. symbol"),
                                    response.getJSONObject("Global Quote").getDouble("05. price"));
                            listener.onResponse(res);
                        }
                        catch (JSONException e) {
                            listener.onResponse(createErrorResponse());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponse(createErrorResponse());
            }
        });

        _queue.add(req);
    }
}
