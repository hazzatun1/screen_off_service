package com.bandhan.hazzatun.mytasbeeh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

 ArrayList<User> list;


    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = list.get(position);

        holder.Id.setText(user.get_id());
        holder.Name.setText(user.get_name());
        holder.Count.setText(user.get_counts());
        holder.Date.setText(user.get_date());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Id, Name, Count, Date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Id = itemView.findViewById(R.id.id);
            Name = itemView.findViewById(R.id.name);
            Count = itemView.findViewById(R.id.count);
            Date = itemView.findViewById(R.id.date);

        }
    }



}
