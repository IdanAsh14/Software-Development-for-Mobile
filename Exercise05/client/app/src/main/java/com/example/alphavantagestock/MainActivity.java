package com.example.alphavantagestock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button fetchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchButton = findViewById(R.id.fetch_button);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                fetchStock(view);
            }
        });
    }

    public void fetchStock(final View view) {
        final StockFetcher fetcher = new StockFetcher(view.getContext());
        final String stockName = ((EditText)findViewById(R.id.edit_stock)).getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching price for " + stockName + "...");
        progressDialog.show();

        fetcher.dispatchRequest(stockName ,new StockFetcher.StockResponseListener() {
            @Override
            public void onResponse(StockFetcher.StockResponse response) {
                progressDialog.hide();

                if (response.isError) {
                    Toast.makeText(view.getContext(), "Error while fetching stock price", Toast.LENGTH_LONG);
                    return;
                }

                ((TextView)MainActivity.this.findViewById(R.id.text_name)).setText(response.name);
                ((TextView)MainActivity.this.findViewById(R.id.text_price)).setText(String.valueOf(response.price) + "$");

            }
        });
    }
}
