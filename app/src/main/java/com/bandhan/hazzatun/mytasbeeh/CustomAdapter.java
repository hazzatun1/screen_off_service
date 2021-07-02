package com.bandhan.hazzatun.mytasbeeh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<viewConst>{
private Context context;

     ArrayList<viewConst> mcontact;

    private TextView itemListText;
    private Button itemButton;

   // Database db;
    viewConst data;
    Holder viewHolder;
    //public myInterface myInterface;



    public Context getContext() {
        return context;
    }


    public CustomAdapter(Context context, ArrayList<viewConst> dcontact){
        super(context, R.layout.activity_open_page, dcontact);
        this.context=context;
        this.mcontact=dcontact;
    }



    public  class  Holder{

        TextView nameFV;
        TextView nameSV;
        TextView phoneV;
        TextView idview;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.save_list, parent, false);

            viewHolder.idview = (TextView) convertView.findViewById(R.id.id);
            viewHolder.nameFV = (TextView) convertView.findViewById(R.id.name);
            viewHolder.nameSV = (TextView) convertView.findViewById(R.id.count);
            viewHolder.phoneV = (Button) convertView.findViewById(R.id.edit_open_page);

            convertView.setTag(viewHolder);

        }

        else {
            viewHolder = (Holder) convertView.getTag();
        }

        final String countId = data.get_id();
        viewHolder.idview.setText(data.get_id() + ": ");
        final String cname = data.get_name();
        viewHolder.nameFV.setText(data.get_name());
        final String cid = data.get_counts();
        viewHolder.nameSV.setText(cid);


       // viewHolder.phoneV.setTextColor(Color.RED);




        return convertView;


    }

    @Nullable
    @Override
    public viewConst getItem(int position) {
        return super.getItem(position);
    }
}
