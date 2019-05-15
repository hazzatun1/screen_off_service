package com.bandhan.hazzatun.mytasbeeh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

    public class Database extends SQLiteOpenHelper {
        public static final String DATABASE_NAME ="TasbeehCount.db";
        public static final String TABLE_NAME ="Counters";
        public static final String COL_1 ="CountId";
        public static final String COL_2 ="CountName";
        public static final String COL_3 ="Counts";




        public Database(Context context) {
            super(context, DATABASE_NAME, null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE registeruser " +
                    "(ID INTEGER PRIMARY  KEY AUTOINCREMENT, CountName TEXT, Counts TEXT)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }

      public long addName(CountContructor countcons ){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("CountName",countcons.get_name());
            contentValues.put("Counts",countcons.get_count());

            long res = db.insert("Counters",null,contentValues);
            db.close();
            return  res;
        }




        public boolean checkName(String name){
            String[] columns = { COL_1 };
            SQLiteDatabase db = getReadableDatabase();
            String selection = COL_2 + "=?";
            String[] selectionArgs = { name};
            Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
            int count = cursor.getCount();
            cursor.close();
            db.close();

            if(count>0)
                return  true;
            else
                return  false;
        }

        public List<CountContructor> getAllCountss() {
            List<CountContructor> contactList = new ArrayList<CountContructor>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_NAME;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    CountContructor contact = new CountContructor();
                    contact.set_id(Integer.parseInt(cursor.getString(0)));
                    contact.set_name(cursor.getString(1));
                    contact.set_count(Integer.parseInt(cursor.getString(2)));

                    // Adding contact to list
                    contactList.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            return contactList;
        }


    }

