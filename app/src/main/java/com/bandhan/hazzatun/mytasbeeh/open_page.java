package com.bandhan.hazzatun.mytasbeeh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class open_page extends AppCompatActivity{

     Database db;
     CustomAdapter data;
     ListView lv;
     viewConst dataModel;
     TextView edit_button;
   // String ccid;
    String countId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_page);


        db = new Database(this);
        lv = (ListView) findViewById(R.id.list1);
        edit_button=(TextView) findViewById(R.id.edit_openPage);
        final ArrayList<viewConst> contacts = new ArrayList<>(db.getUser());
        data=new CustomAdapter(getApplicationContext(), contacts);
       // db.getAllData();

        fillListView();
      //  resume_count();

    }


    private void fillListView() {


  lv.setItemsCanFocus(true);
        lv.setAdapter(data);
      //  data.notifyDataSetChanged();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                dataModel = data.getItem(position);
                //final String selected =(String) (lv.getItemAtPosition(position));
                assert dataModel != null;
                final String counts=dataModel.get_counts();
                countId= dataModel.get_id();
                final String cname=dataModel.get_name();


                //Toast.makeText(context,cid,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("counts", counts);
                i.putExtra("cID", countId);
                i.putExtra("cName", cname);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {


                dataModel = data.getItem(pos);
                //final String selected =(String) (lv.getItemAtPosition(position));
                assert dataModel != null;
                final String counts=dataModel.get_counts();
                countId= dataModel.get_id();
                final String cname=dataModel.get_name();


                AlertDialog.Builder builder = new AlertDialog.Builder((Context)open_page.this);
                final EditText editText1 = new EditText((Context)open_page.this);
                //EditText editText2 = new EditText((Context)open_page.this);
                builder.setTitle("Edit or Delete");
                builder.setMessage("enter new name")
                        .setCancelable(false).setView((View)editText1)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface param2DialogInterface, int param2Int) {



                                String name= editText1.getText().toString();
                                boolean upd=db.updateData(countId, name);

                                if(upd==true) {
                                    Toast.makeText((Context) open_page.this, "updated", Toast.LENGTH_LONG).show();
                                    startActivity(getIntent());
                                }
                                else
                                    Toast.makeText((Context)open_page.this, "failed to update", Toast.LENGTH_LONG).show();


                                // Toast.makeText((Context)open_page.this, "hello", Toast.LENGTH_LONG).show();
                                // param2DialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface param2DialogInterface, int param2Int) {


                                boolean del=db.deleteName(countId);
                                if(del) {
                                    Toast.makeText((Context) open_page.this, "deleted", Toast.LENGTH_LONG).show();
                                    startActivity(getIntent());
                                }
                                else
                                    Toast.makeText((Context)open_page.this, "failed to delete", Toast.LENGTH_LONG).show();
                                //param2DialogInterface.cancel();
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                                Toast.makeText((Context)open_page.this, "Cancel", Toast.LENGTH_LONG).show();
                                param2DialogInterface.cancel();
                            }
                        });
                builder.create().show();


               // return false;
                return true;
            }
        });

    }






}