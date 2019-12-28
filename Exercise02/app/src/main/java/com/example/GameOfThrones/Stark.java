package com.example.GameOfThrones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;


public class Stark extends AppCompatActivity {
    private static final String TAG = "Stark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stark);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list_stark);
        List<Data> data = fill_with_data();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ListAdapter customAdapter = new ListAdapter(data, Stark.this);
        recyclerView.setAdapter(customAdapter);
    }

    public List<Data> fill_with_data() {

        List<Data> data = new ArrayList<>();

        data.add(new Data( R.drawable.rickard_stark, "Rickard Stark"));
        data.add(new Data( R.drawable.brandon_stark, "Brandon Stark"));
        data.add(new Data( R.drawable.catelyn_stark, "Catelyn Stark"));
        data.add(new Data( R.drawable.eddard_stark, "Eddard Stark"));
        data.add(new Data( R.drawable.lyanna_stark, "Lyanna Stark"));
        data.add(new Data( R.drawable.benjen_stark, "Benjen Stark"));
        data.add(new Data( R.drawable.robb_stark, "Robb Stark"));
        data.add(new Data( R.drawable.talisa_stark, "Talisa Stark"));
        data.add(new Data( R.drawable.sansa_stark, "Sansa Stark"));
        data.add(new Data( R.drawable.arya_stark, "Arya Stark"));
        data.add(new Data( R.drawable.bran_stark, "Bran Stark"));
        data.add(new Data( R.drawable.rickon_stark, "Rickon Stark"));
        data.add(new Data( R.drawable.jon_snow, "Jon Snow"));

        return data;
    }
}
