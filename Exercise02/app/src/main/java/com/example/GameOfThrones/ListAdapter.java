package com.example.GameOfThrones;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter {
    List<Data> mDataSet;;
    Context context;

    public ListAdapter(List<Data> data, Context context) {
        this.mDataSet = data;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;

        public MyViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image);
            mTextView = (TextView) view.findViewById(R.id.name);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).mImageView.setImageResource(mDataSet.get(position).imageId);
        ((MyViewHolder) holder).mTextView.setText(mDataSet.get(position).txt);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}