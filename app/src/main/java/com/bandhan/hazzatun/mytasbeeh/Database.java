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

    public boolean updateCount(String cid, String count ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Counts",count);

        db.update("Counters", contentValues, "CountId = ?", new String[] {cid});
        return true;
    }


    public Integer deleteName(String cid ){
        SQLiteDatabase db = this.getWritableDatabase();


        return db.delete("Counters","CountId = ?", new String[] {cid});

    }



    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                                      String dbfield, String fieldValue) {
        SQLiteDatabase sqldb = getWritableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where " + COL_1 + " = " + fieldValue;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public String getCountId(String paramString1, String paramString2) {
        SQLiteDatabase sQLiteDatabase = getReadableDatabase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT CountId  FROM Counters WHERE CountName = '");
        stringBuilder.append(paramString1);

        stringBuilder.append("' ");
        Cursor cursor = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
        return cursor.moveToFirst() ? cursor.getString(cursor.getColumnIndex("CountId")) : null;
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
