package com.example.urlconnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ProfileDao extends SQLiteOpenHelper {
    public ProfileDao(@Nullable Context context) {
        super(context, "UserProfile.db", null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query="create table UserProfile (name text, organization text, phone text)";
        db.execSQL(query);
    }

    public boolean insertRecord(ProfileModel mv)
    {
        SQLiteDatabase db= getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",mv.getName());
        cv.put("organization",mv.getOrganization());
        cv.put("phone",mv.getPhone());
        db.delete("UserProfile",null,null);
        db.insert("UserProfile",null,cv);
        db.close();
        return true;
    }
    public ProfileModel retrieveRecord()
    {
        SQLiteDatabase db=getReadableDatabase();
        ProfileModel mv=new ProfileModel();
        String query="SELECT * FROM UserProfile";
        Cursor cursor=db.rawQuery(query,null);
       if( cursor.moveToFirst())
           {
            mv.setName(cursor.getString(0));
            mv.setOrganization(cursor.getString(1));
            mv.setPhone(cursor.getString(2));
        }
       db.close();
       return mv;
    }
    public boolean deleteAll()
    {
        SQLiteDatabase db= getWritableDatabase();
        db.delete("UserProfile",null,null);
        db.close();
        return true;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
