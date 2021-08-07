package com.bandhan.hazzatun.mytasbeeh;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class open_page extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    public static final CharSequence[] DAYS_OPTIONS = new CharSequence[0];


    private Handler mHandler = new Handler();
    private NotificationManagerCompat notificationManager;

    //used for register alarm manager
    PendingIntent pendingIntent;
    //used to store running alarmmanager instance
    //AlarmManager alarmManager;
    //Callback function for Alarmmanager event
    BroadcastReceiver mReceiver;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_page);

        notificationManager = NotificationManagerCompat.from(this);

        date = findViewById(R.id.date);


        db = new Database(this);
        lv = findViewById(R.id.list1);

        final ArrayList<viewConst> contacts = new ArrayList<>(db.getUser());
        data = new CustomAdapter(getApplicationContext(), contacts);

        fillListView();



        if (getIntent().hasExtra("setAlarm") ) {

            cname = getIntent().getStringExtra("setAlarm");
            long_press_alarm();





        }
        else{
            long_press_del();
        }


    }



    private void fillListView() {


        lv.setItemsCanFocus(true);
        lv.setAdapter(data);
       // lv.getAdapter().getItemAt(position);


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

    private void long_press_del(){


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //@SuppressLint("ResourceAsColor")
            @Override
            public boolean onItemLongClick(final AdapterView<?> arg0, View view,
                                           final int pos, long id) {

                dataModel = data.getItem(pos);

                assert dataModel != null;

                AlertDialog.Builder builder = new AlertDialog.Builder(open_page.this);

                builder.setTitle("Delete the zikr");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {


                        String data_del= dataModel.get_id();

                        boolean del = db.deleteName(data_del);
                        if (del) {
                            Toast.makeText(open_page.this, "deleted", Toast.LENGTH_LONG).show();

                            recreate();

                        } else
                            Toast.makeText(open_page.this, "failed to delete", Toast.LENGTH_LONG).show();

                    }

                })

                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                                Toast.makeText(open_page.this, "Cancel", Toast.LENGTH_LONG).show();
                                param2DialogInterface.cancel();
                            }
                        });
                builder.create().show();

                return true;

            }

        });



    }





    private void long_press_alarm(){
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //@SuppressLint("ResourceAsColor")
            @Override
            public boolean onItemLongClick(final AdapterView<?> arg0, final View view,
                                           final int pos, long id) {

                dataModel = data.getItem(pos);

                assert dataModel != null;

                final AlertDialog.Builder builder = new AlertDialog.Builder(open_page.this);
                final EditText editText3 = new EditText(open_page.this);

                builder.setTitle("Alarm setting");

                builder.setPositiveButton("setAlarm", new DialogInterface.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {





                    }

                })
                        .setNegativeButton("removeAlarm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                                Toast.makeText(open_page.this, "Cancel", Toast.LENGTH_LONG).show();
                                param2DialogInterface.cancel();
                              //  mMediaPlayer.stop();
                              //  finish();
                              //  return true;
                               // stopRepeating();
                            }
                        })

                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                                Toast.makeText(open_page.this, "Cancel", Toast.LENGTH_LONG).show();
                                param2DialogInterface.cancel();
                            }
                        });
                builder.create().show();

                return true;

            }

        });




    }


     @RequiresApi(api = Build.VERSION_CODES.O)
     private void createNotification() {

         // intent triggered, you can add other intent for other actions
         Intent i = new Intent(this, MainActivity.class);
         PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);

         //Notification sound
         try {
             Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
             r.play();
         } catch (Exception e) {
             e.printStackTrace();
         }

         // this is it, we'll build the notification!
         // in the addAction method, if you don't want any icon, just set the first param to 0
         Intent intent=new Intent(getApplicationContext(),MainActivity.class);
         String CHANNEL_ID="MYCHANNEL";
         NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_LOW);
         PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
         Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)

                 .setContentTitle("MyCounter Reminder")
                 .setContentText("This is your time to prayer and Zikr: "+dataModel.get_name())
                 .setContentIntent(pendingIntent)
                 .addAction(android.R.drawable.sym_action_chat,"Title",pendingIntent)
                 .setChannelId(CHANNEL_ID)
                 .setSmallIcon(android.R.drawable.sym_action_chat)
                 .build();


         NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
         notificationManager.createNotificationChannel(notificationChannel);
         notificationManager.notify(1,notification);
 }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}




