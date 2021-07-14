package com.example.urlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_feed);
    }

    public void logoutsession(View view)
    {
        ApiKeyDao api=new ApiKeyDao(this);
        ProfileDao dao=new ProfileDao(this);
        api.deleteAll();
        dao.deleteAll();
        Intent main=new Intent(this,MainActivity.class);
        startActivity(main);
    }

    public void goToProfile(View view)
    {
        Intent profile = new Intent(this,ProfileActivity.class);
        startActivity(profile);

    }
}