package com.example.GameOfThrones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;


public class Lannister extends AppCompatActivity {
    private static final String TAG = "Lannister";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lannister);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list_lann);
        List<Data> data = fill_with_data();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ListAdapter customAdapter = new ListAdapter(data, Lannister.this);
        recyclerView.setAdapter(customAdapter);
    }

    public List<Data> fill_with_data() {

        List<Data> data = new ArrayList<>();

        data.add(new Data( R.drawable.tywald_lannister, "Tywald Lannister"));
        data.add(new Data( R.drawable.tion_lannister, "Tion Lannister"));
        data.add(new Data( R.drawable.tytos_lannister, "Tytos Lannister"));
        data.add(new Data( R.drawable.tywin_lannister, "Tywin Lannister"));
        data.add(new Data( R.drawable.kevan_lannister, "Kevan Lannister"));
        data.add(new Data( R.drawable.cersei_lannister, "Cersei Lannister"));
        data.add(new Data( R.drawable.jaime_lannister, "Jaime Lannister"));
        data.add(new Data( R.drawable.tyrion_lannister, "Tyrion Lannister"));
        data.add(new Data( R.drawable.lancel_lannister, "Lancel Lannister"));
        data.add(new Data( R.drawable.martyn_lannister, "Martyn Lannister"));
        data.add(new Data( R.drawable.joffrey_baratheon, "Joffrey Baratheon"));
        data.add(new Data( R.drawable.myrcella_baratheon, "Myrcella Baratheon"));
        data.add(new Data( R.drawable.tommen_baratheon, "Tommen Baratheon"));

        return data;
    }
}
