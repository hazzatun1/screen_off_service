package com.bandhan.hazzatun.mytasbeeh;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> list;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder evh = new MyViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = list.get(position);

        //holder.Id.setText(user.get_id());
        holder.Name.setText(user.get_name());
        holder.Count.setText(user.get_counts());
        holder.Date.setText(user.get_date());
        holder.itemView.setTag(holder);
        String gTarget = user.get_target();
        if(!gTarget.equals("0")) {
            holder.itemView.setBackgroundColor(Color.GREEN);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("cID",list.get(position).get_id());
                intent.putExtra("cName",list.get(position).get_name());
                intent.putExtra("counts", list.get(position).get_counts());
                intent.putExtra("date", list.get(position).get_date());
                intent.putExtra("tcounts", list.get(position).get_target());
                intent.putExtra("email", list.get(position).get_email());
                context.startActivity(intent);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Delete the zikr");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                        if (list != null) {
                            removeItem(position);
                            list.clear();
                            list.addAll(list);
                        }
                        else {
                            list = list;
                        }

                    }
                })

                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                                Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show();
                                param2DialogInterface.cancel();
                            }
                        });
                builder.create().show();
                return true;
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeItem(int position) {
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        assert user1 != null;
        DatabaseReference reference = (DatabaseReference) FirebaseDatabase.getInstance()
                .getReference("MyDigitalCounter")
                .child(user1.getUid()).child(list.get(position).get_name());

        reference.removeValue();

       // list.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Name, Count, Date;

        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            //Id = itemView.findViewById(R.id.id);
            Name = itemView.findViewById(R.id.name);
            Count = itemView.findViewById(R.id.count);
            Date = itemView.findViewById(R.id.date);
            itemView.setClickable(true);
        }

    }

}