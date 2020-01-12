package com.example.alphavantagestock;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
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
    private static final String TAG = "StockFetcher";
    private final static String REQUEST_URL = "http://10.0.0.131:8080/stock";

    public StockFetcher(Context context) {
        _queue = Volley.newRequestQueue(context);
    }

    public class StockResponse {
        public boolean isError;

        public StockResponse(boolean isError) {
            this.isError = isError;
        }
    }

    public interface StockResponseListener {
        void onResponse(StockResponse response);
    }

    private StockResponse createErrorResponse() {
        return new StockResponse(true);
    }

    public void dispatchRequest(String stockName, int id, final StockResponseListener listener) {
        JSONObject postBody = new JSONObject();
        try {
            postBody.put("id", id);
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
                        Log.i(TAG, "POST Request sent successfully");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Failed to sent POST Request - " + error);
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        _queue.add(req);
    }
}
