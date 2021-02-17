package com.bandhan.hazzatun.mytasbeeh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="TasbeehCount.db";
    public static final String TABLE_NAME ="Counters";
    public static final String COL_1 ="CountId";
    public static final String COL_2 ="CountName";
    public static final String COL_3 ="Counts";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Counters " +//exec means execute, parameter db
                "(CountId INTEGER PRIMARY  KEY AUTOINCREMENT, CountName TEXT, Counts TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addName(String cname, String count ){
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

    public String getCounts(String cid ){

        SQLiteDatabase db = getReadableDatabase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT Counts FROM Counters WHERE CountId = '");
        stringBuilder.append(cid);
        stringBuilder.append("' ");
        Cursor cursor = db.rawQuery(stringBuilder.toString(), null);
        return cursor.moveToFirst() ? cursor.getString(cursor.getColumnIndex("Counts")) : null;
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

}
