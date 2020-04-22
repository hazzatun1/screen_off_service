package com.bandhan.hazzatun.mytasbeeh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



    public class Database extends SQLiteOpenHelper {
        public static final String DATABASE_NAME ="TasbeehCount.db";
        public static final String TABLE_NAME ="Counters";
        public static final String COL_1 ="CountId";
        public static final String COL_2 ="CountName";
        public static final String COL_3 ="Counts";




        public Database(Context context) {

            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE Counters " +
                    "(CountId INTEGER PRIMARY  KEY AUTOINCREMENT, CountName TEXT, Counts TEXT)");

        }

      //  @Override
      //  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
      //      sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
       //     onCreate(sqLiteDatabase);
       // }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public boolean addName(String cname, String count){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("CountName",cname);
            contentValues.put("Counts",count);

            long res = db.insert("Counters",null, contentValues);
            if(res==-1){
                return false;
            }
            else{
                return true;
            }
        }
        public Cursor getAllData(){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
            return res;
        }
        public boolean updateName(String cid, String cname, String count ){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("CountId",cid);
            contentValues.put("CountName",cname);
            contentValues.put("Counts",count);

            db.update("Counters", contentValues, "CountId = ?", new String[] {cid});
            return true;
        }
        public Integer deleteName(String cid ){
            SQLiteDatabase db = this.getWritableDatabase();


            return db.delete("Counters","CountId = ?", new String[] {cid});

        }


        }
        

