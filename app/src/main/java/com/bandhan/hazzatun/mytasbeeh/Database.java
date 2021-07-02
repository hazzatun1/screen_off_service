package com.bandhan.hazzatun.mytasbeeh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;

import static android.icu.text.MessagePattern.ArgType.SELECT;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="TasbeehCount.db";
    public static final String TABLE_NAME ="Counters";
    public static final String COL_1 ="CountId";
    public static final String COL_2 ="CountName";
    public static final String COL_3 ="Counts";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 3);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " (CountId INTEGER PRIMARY KEY AUTOINCREMENT, CountName TEXT UNIQUE NOT NULL, Counts TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    public boolean addName(String cname, String count ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, cname);
        contentValues.put(COL_3, count);

        long res = db.insert(TABLE_NAME, null, contentValues);


        if ((int)res <= 0) {
            db.close();
            return false;
        }
        else
            db.close();
            return true;
  //   return  (int) res == -1;
    }


    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }


    public boolean updateCount(String cid, String count ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Counts",count);

        db.update("Counters", contentValues, "CountId = ?", new String[] {cid});
        return true;
    }

    public boolean updateData(String cid, String name ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("CountName",name);
       // contentValues.put("Counts",count);
        db.update("Counters", contentValues, "CountId = ?", new String[] {cid});
        return true;
    }

    public boolean deleteName(String cid ){
        SQLiteDatabase db = this.getWritableDatabase();


        long hello = db.delete("Counters","CountId = ?", new String[] {cid});
        if(hello > 0)
            return true;
        else
            return false;

    }


    public boolean ifExists(String searchItem1, String searchItem2) {

        String[] columns = { COL_2, COL_3 };
        String selection = COL_1 + " =?";
        String[] selectionArgs = { searchItem1, searchItem2 };
        String limit = "1";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        if(cursor.moveToFirst())
        {
            db.close();
            return  true;
        }
        else
        {
            db.close();
            return false;
        }

        //boolean exists = (cursor.getCount() > 0);
       // cursor.close();
      //  return exists;
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
                arrayList.add(viewConst);
            } while (cursor.moveToNext());
        return arrayList;
    }


    public boolean updateNewData(String cid, String name, String count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("CountName ", name);
         contentValues.put("Counts ", count);
     long res = db.update("Counters ", contentValues, "CountId = ?", new String[] {cid});
        if ((int)res <= 0) {
            db.close();
            return false;
        }
        else
            db.close();
        return true;
    }




}
