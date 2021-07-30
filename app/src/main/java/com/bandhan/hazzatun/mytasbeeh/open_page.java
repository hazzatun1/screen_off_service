package com.bandhan.hazzatun.mytasbeeh;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class open_page extends AppCompatActivity {

    Database db;
    CustomAdapter data;
    ListView lv;
    viewConst dataModel;
    String countId;
    TextView date;
    String formattedDate = "";
    String cname = "";
    String target = "";
    String count = "";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_page);


        date = findViewById(R.id.date);


        db = new Database(this);
        lv = findViewById(R.id.list1);

        final ArrayList<viewConst> contacts = new ArrayList<>(db.getUser());
        data = new CustomAdapter(getApplicationContext(), contacts);

        fillListView();



    }


    private void fillListView() {


        lv.setItemsCanFocus(true);
        lv.setAdapter(data);


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {


                dataModel = data.getItem(pos);

                assert dataModel != null;

                //   countId = dataModel.get_id();


                AlertDialog.Builder builder = new AlertDialog.Builder(open_page.this);

                builder.setTitle("Delete or set Target");

                builder.setPositiveButton("Set Target", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {

                        AlertDialog.Builder alert2 = new AlertDialog.Builder(open_page.this);
                        final EditText editText3 = new EditText(open_page.this);
                        alert2.setTitle("Set Target Limit");

                        alert2.setView(editText3);

                        alert2.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface param2DialogInterface, int param2Int) {

                                target = editText3.getText().toString();
                                Date c = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                formattedDate = df.format(c);
                                count = "0";
                                cname = dataModel.get_name();
                                countId = dataModel.get_id();

                                boolean istInsert = db.updTarget(countId, cname, count, formattedDate, target);

                                if (istInsert) {

                                    // lv.getChildAt(lv.getSelectedItemPosition()).setBackgroundColor(Color.RED);
                                    Toast.makeText(open_page.this, "target saved", Toast.LENGTH_LONG).show();
                                } else
                                    Toast.makeText(open_page.this, "target not saved", Toast.LENGTH_LONG).show();
                            }


                        });


                        alert2.setNegativeButton("Set Reminder", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface param2DialogInterface, int param2Int) {

                            }

                        });


                        alert2.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface param2DialogInterface, int param2Int) {


                                Toast.makeText(open_page.this, "Cancel", Toast.LENGTH_LONG).show();
                                param2DialogInterface.cancel();
                            }

                        });
                        alert2.create().show();


                    }

                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {


                        boolean del = db.deleteName(countId);
                        if (del) {
                            Toast.makeText(open_page.this, "deleted", Toast.LENGTH_LONG).show();
                            startActivity(getIntent());
                        } else
                            Toast.makeText(open_page.this, "failed to delete", Toast.LENGTH_LONG).show();

                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                        Toast.makeText(open_page.this, "Cancel", Toast.LENGTH_LONG).show();
                        param2DialogInterface.cancel();
                    }
                });
                builder.create().show();

                return true;

            }

        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                dataModel = data.getItem(position);
                assert dataModel != null; // assert ki
                count = dataModel.get_counts();
                countId = dataModel.get_id();
                cname = dataModel.get_name();
                target = dataModel.get_target();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("cID", countId);
                i.putExtra("cName", cname);
                i.putExtra("counts", count);
                i.putExtra("tcounts", target);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });


    }
}