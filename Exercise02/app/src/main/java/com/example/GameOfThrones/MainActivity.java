package com.example.GameOfThrones;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton pushStark = (ImageButton)findViewById(R.id.stark_button);
        pushStark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Stark.class);
                startActivity(intent);
            }
        });

        ImageButton pushLann = (ImageButton)findViewById(R.id.lannister_button);
        pushLann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Lannister.class);
                startActivity(intent);
            }
        });
    }
}
