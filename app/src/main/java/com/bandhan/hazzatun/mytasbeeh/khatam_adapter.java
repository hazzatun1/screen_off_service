package com.bandhan.hazzatun.mytasbeeh;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class khatam_adapter extends RecyclerView.Adapter<khatam_adapter.MyViewHolder> {

    Context context;
    ArrayList<KhatamUser> list;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public khatam_adapter(Context context, ArrayList<KhatamUser> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.khatam_list, parent, false);
        MyViewHolder evh = new MyViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        KhatamUser user = list.get(position);

        //holder.Id.setText(user.get_id());
        holder.Name.setText(user.getK_count_name());
        holder.Members.setText(user.getK_acc_email());
        holder.Date.setText(user.getDate());
        holder.target.setText(user.getK_target());
        holder.itemView.setTag(holder);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KhotomAccount.class);
                intent.putExtra("cName",list.get(position).getK_count_name());
                intent.putExtra("target",list.get(position).getK_target());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Delete the khatam group");

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
        //assert user1 != null;
        DatabaseReference reference = (DatabaseReference) FirebaseDatabase.getInstance()
                .getReference("MyDigitalCounter")
                .child("Group").child(list.get(position).getK_count_name());

        reference.removeValue();

        // list.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Name, Members, Date, target;

        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            Name = itemView.findViewById(R.id.name);
            Members = itemView.findViewById(R.id.count);
            target = itemView.findViewById(R.id.tgt);
            Date = itemView.findViewById(R.id.date);
            itemView.setClickable(true);

        }

    }

}
