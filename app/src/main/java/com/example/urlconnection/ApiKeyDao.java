package com.example.urlconnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ApiKeyDao extends SQLiteOpenHelper
{

    public ApiKeyDao(@Nullable Context context) {
        super(context, "StoreApiKey.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query="create table StoreApiKey (apikey text, status number,userid number)";
        db.execSQL(query);

    }

    public boolean storeApi(ApiModel model)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        deleteAll();
        ContentValues ct=new ContentValues();
        ct.put("apikey",model.apikey);
        ct.put("status",model.status);
        ct.put("userid",model.userid);
        db.insert("StoreApiKey",null,ct);
        db.close();
        return true;
    }
    public ApiModel retrieveApi()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM StoreApiKey";
        Cursor cursor = db.rawQuery(query,null);
        System.out.println("Cursor results:"+cursor);
        if( cursor.moveToFirst()) {
            String apikey = cursor.getString(0);
            int status = cursor.getInt(1);
            int userid=cursor.getInt(2);
            ApiModel obj = new ApiModel(apikey, status,userid);
            cursor.close();
            db.close();
            return obj;
        }
        return null;
    }
    public boolean deleteAll()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("StoreApikey", null, null) > 0;
    }
    public boolean updateStatus(int status)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String strSQL = "UPDATE StoreApiKey SET status ="+status ;
        db.execSQL(strSQL);
        return true;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
