package com.bandhan.hazzatun.mytasbeeh;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<viewConst>{
private final Context context;

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

            viewHolder.idview = convertView.findViewById(R.id.id);
            viewHolder.nameFV = convertView.findViewById(R.id.name);
            viewHolder.nameSV = convertView.findViewById(R.id.count);
            viewHolder.phoneV = convertView.findViewById(R.id.date);

            convertView.setTag(viewHolder);

        }

        else {
            viewHolder = (Holder) convertView.getTag();
        }

        final String countId = data.get_id();

        viewHolder.idview.setText(countId + ": ");
        viewHolder.idview.setTextColor(Color.BLACK);
        final String cname = data.get_name();

        viewHolder.nameFV.setText(cname);
        viewHolder.nameFV.setTextColor(Color.BLACK);
        final String cid = data.get_counts();

        viewHolder.nameSV.setText(cid);
        viewHolder.nameSV.setTextColor(Color.BLACK);

        final String gdate = data.get_date();
        viewHolder.phoneV.setText(gdate);
        viewHolder.phoneV.setTextColor(Color.BLACK);

        return convertView;


    }

    @Nullable
    @Override
    public viewConst getItem(int position) {
        return super.getItem(position);
    }
}
