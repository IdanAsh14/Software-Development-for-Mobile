package com.example.alphavantagestock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button fetchButton;
    private String mToken;
    private int mUserID;
    private MyBroadcastReceiver mReceiver = new MyBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrieveToken();

        fetchButton = findViewById(R.id.fetch_button);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                fetchStock(view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.my.app.onMessageReceived");
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void fetchStock(final View view) {
        final StockFetcher fetcher = new StockFetcher(view.getContext());
        final String stockName = ((EditText)findViewById(R.id.edit_stock)).getText().toString();

        fetcher.dispatchRequest(stockName , mUserID, new StockFetcher.StockResponseListener() {
            @Override
            public void onResponse(StockFetcher.StockResponse response) {
                if (response.isError) {
                    Toast.makeText(view.getContext(), "Error while fetching stock price", Toast.LENGTH_LONG);
                    return;
                }
            }
        });
    }

    public void retrieveToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        mToken = task.getResult().getToken();
                        fetchToken(getApplicationContext(),mToken);
                        // Log
                        String msg = getString(R.string.msg_token_fmt, mToken);
                        Log.e(TAG, msg);
                    }
                });
    }

    public void fetchToken(final Context context, String token) {
        final SendTokenToServer fetcher = new SendTokenToServer(context);
        fetcher.dispatchRequest(token , mUserID,new SendTokenToServer.TokenResponseListener() {
            @Override
            public void onResponse(SendTokenToServer.TokenResponse response) {

                if (response.isError) {
                    Toast.makeText(context, "Error while fetching stock price", Toast.LENGTH_LONG);
                    return;
                }
                mUserID = response.userID;
                Log.e(TAG, String.valueOf(response.userID));
            }
        });
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            String name = extras.getString("name");
            String price = extras.getString("price");
            String time = extras.getString("time");
            updateView(name, price, time);// update your textView in the main layout
        }
    }

    private void updateView(String stock, String price, String time){
        ((TextView)MainActivity.this.findViewById(R.id.text_name)).setText(stock);
        ((TextView)MainActivity.this.findViewById(R.id.text_price)).setText(price + '$');
        ((TextView)MainActivity.this.findViewById(R.id.text_last_update)).setText(time);
    }
}
