package com.bandhan.hazzatun.mytasbeeh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TasbeehCount.db";
    public static final String TABLE_NAME = "Counters";
    public static final String COL_1 = "CountId";
    public static final String COL_2 = "CountName";
    public static final String COL_3 = "Counts";
    public static final String COL_4 = "Date";
    public static final String COL_5 = "Target";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 6);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " (CountId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CountName TEXT UNIQUE NOT NULL, Counts TEXT NOT NULL Default 0, Date TEXT NOT NULL Default 0, " +
                "Target TEXT NOT NULL Default 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    public boolean addName(String cname, String count, String Date, String target) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, cname);
        contentValues.put(COL_3, count);
        contentValues.put(COL_4, Date);
        contentValues.put(COL_5, target);
        long res = db.insert(TABLE_NAME, null, contentValues);


        if ((int) res <= 0) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }


    public boolean updTarget(String cid, String cname, String count, String Date, String Target) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, cname);
        contentValues.put(COL_3, count);
        contentValues.put(COL_4, Date);
        contentValues.put(COL_5, Target);
        db.update("Counters", contentValues, "CountId = ?", new String[]{cid});


        return true;
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select target from " + TABLE_NAME, null);
    }



    public boolean updateCount(String cid, String Name, String count, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, Name);
        contentValues.put(COL_3, count);
        contentValues.put(COL_4, date);

        db.update("Counters", contentValues, "CountId = ?", new String[]{cid});
        return true;
    }


    public boolean deleteName(String cid) {
        SQLiteDatabase db = this.getWritableDatabase();


        long hello = db.delete("Counters", "CountId = ?", new String[]{cid});
        return hello > 0;

    }


    public ArrayList<viewConst> getUser() {
        ArrayList<viewConst> arrayList = new ArrayList();
        Cursor cursor = getWritableDatabase().rawQuery("SELECT  * FROM Counters", null);
        if (cursor.moveToFirst())
            do {
                viewConst viewConst = new viewConst();
                viewConst.set_id(cursor.getString(0));
                viewConst.set_name(cursor.getString(1));
                viewConst.set_counts(cursor.getString(2));
                viewConst.set_date(cursor.getString(3));
                viewConst.set_target(cursor.getString(4));
                arrayList.add(viewConst);
            } while (cursor.moveToNext());
        return arrayList;
    }



}
