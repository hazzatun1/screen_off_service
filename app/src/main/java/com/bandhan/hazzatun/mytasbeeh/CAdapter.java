package com.bandhan.hazzatun.mytasbeeh;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


    public class CAdapter extends ArrayAdapter<CountContructor> {

        Context context;
        ArrayList<CountContructor> mcontact;


        public CAdapter(Context context, ArrayList<CountContructor> contact){
            super(context, R.layout.count_list, contact);
            this.context=context;
            this.mcontact=contact;
        }

        public  class  Holder{
            TextView nameFV;
            TextView nameSV;
            TextView phoneV;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position

            CountContructor data = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view

            Holder viewHolder; // view lookup cache stored in tag

            if (convertView == null) {


                viewHolder = new Holder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.count_list, parent, false);

                viewHolder.nameFV = (TextView) convertView.findViewById(R.id.txtView1);
                viewHolder.nameSV = (TextView) convertView.findViewById(R.id.txtView2);
                viewHolder.phoneV = (TextView) convertView.findViewById(R.id.txtView3);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (Holder) convertView.getTag();
            }


            viewHolder.nameFV.setText("No: "+data.get_id());
            viewHolder.nameSV.setText("Name: "+data.get_name());
            viewHolder.phoneV.setText("Count no: "+data.get_count());

            // Return the completed view to render on screen
            return convertView;
        }

    }
