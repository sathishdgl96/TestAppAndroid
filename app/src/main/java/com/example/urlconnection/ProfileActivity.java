package com.example.urlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urlconnection.sendrequest.SendGetAndPost;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {

    Button btn;
   EditText name,organization,phone;
   ProfileDao dao;
   ProfileModel mv;
   ApiKeyDao apiRepo;
    String home_url;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name= findViewById(R.id.name);
        organization=findViewById(R.id.organization);
        phone=findViewById(R.id.phone);
        dao=new ProfileDao(this);
        mv=dao.retrieveRecord();
        if(mv!=null) {
            //Toast.makeText(this, "Retrieved from db" + mv, Toast.LENGTH_SHORT).show();
            name.setText(mv.getName());
            organization.setText(mv.getOrganization());
            phone.setText(mv.getPhone());

        }


    }
    public void updateProfile(View view) throws ExecutionException, InterruptedException
    {
        home_url=this.getString(R.string.home_url);
        ProfileModel mv=new ProfileModel(name.getText().toString(),organization.getText().toString(),phone.getText().toString());
        dao.insertRecord(mv);
        Toast.makeText(this, "Updated successfully, Syncing online...", Toast.LENGTH_SHORT).show();
        SendData data=new SendData();
        apiRepo=new ApiKeyDao(this);
        String apikey= apiRepo.retrieveApi().getApikey();
        String url=home_url+"/profile/"+apiRepo.retrieveApi().getUserid()+"/update";
        String result=data.execute(url,name.getText().toString(),organization.getText().toString(),phone.getText().toString(),apikey).get();
        Toast.makeText(this, "result", Toast.LENGTH_SHORT).show();
        apiRepo.updateStatus(1);
        Intent profile=new Intent(this,HomeFeed.class);
        startActivity(profile);
    }


    public class SendData extends AsyncTask<String,Void, String>
    {
        HashMap<String,String> h;
        @Override
        protected String doInBackground(String... urls)
        {
            h=new HashMap<>();
            h.put("name",urls[1]);
            h.put("institution",urls[2]);
            h.put("phone",urls[3]);
            h.put("apikey",urls[4]);
            SendGetAndPost request=new SendGetAndPost();
            return request.sendPost(h,urls);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press again to exit...", Toast.LENGTH_SHORT).show();
    }
}