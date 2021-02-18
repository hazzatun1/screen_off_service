package com.bandhan.hazzatun.mytasbeeh;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<viewConst> {
    private Context context;

     ArrayList<viewConst> mcontact;

    private TextView itemListText;
    private Button itemButton;

    Database db;
    viewConst data;
    Holder viewHolder;

    public CustomAdapter(Context context, ArrayList<viewConst> dcontact){
        super(context, R.layout.activity_open_page, dcontact);
        this.context=context;
        this.mcontact=dcontact;
    }

    public  class  Holder{

        TextView nameFV;
        TextView nameSV;
        Button phoneV;
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
            viewHolder.phoneV = (Button) convertView.findViewById(R.id.resume);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }

        final String countId=data.get_id();
        viewHolder.idview.setText(data.get_id()+": ");
        //viewHolder.idview.setTag(position);
        viewHolder.nameFV.setText("Name: "+data.get_name());
        final String cid=data.get_counts();
        viewHolder.nameSV.setText("Counts: "+cid);

        viewHolder.phoneV.setText("Resume");
        //int pos=position+1;

        //viewHolder.nameSV.setTag(data.get_counts());
        // Return the completed view to render on screen
       // return convertView;





        viewHolder.phoneV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                       //Toast.makeText(context,cid,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("counts",cid);
                i.putExtra("cID",countId);
                context.startActivity(i);
            }
        });


       // viewHolder.phoneV.setOnClickListener(new View.OnClickListener() {
      //     @Override
       //     public void onClick(View v) {
                //Toast.makeText(context,"Button Working",Toast.LENGTH_SHORT).show();


        //        String cid = String.valueOf(viewHolder.idview.getTag());
               // String count =  String.valueOf(db.getCounts(cid));

         //       Toast.makeText(context,cid,Toast.LENGTH_SHORT).show();
         //   }
       // });

        return convertView;


    }

    @Override
    public int getPosition(@Nullable viewConst item) {
        return super.getPosition(item);
    }

    @Nullable
    @Override
    public viewConst getItem(int position) {
        return super.getItem(position);
    }

}
