package com.example.urlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.urlconnection.sendrequest.SendGetAndPost;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
{
    EditText username,password;
    String home_url;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiKeyDao key=new ApiKeyDao(this);
        //Toast.makeText(this, "user"+key.retrieveApi().status, Toast.LENGTH_SHORT).show();
        if(key.retrieveApi()==null) {
            setContentView(R.layout.activity_main);
        }
        else if(key.retrieveApi().getStatus()==1) //1== Existing user
        {
            Intent profile=new Intent(this,HomeFeed.class);
            startActivity(profile);
        }
        else if(key.retrieveApi().getStatus()==0) // 0= new user
        {
            Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
            Intent profile=new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(profile);
        }
        else
        {
            setContentView(R.layout.activity_main);
        }
        home_url=this.getString(R.string.home_url);

    }
    public void login(View view)
    {
        LoginAsync l=new LoginAsync();
        btn=findViewById(R.id.login);
        btn.setText("Validating...");
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        try {
           String result= l.execute(home_url+"/loginrequest",username.getText().toString(),password.getText().toString()).get();
            Log.i("Resposne",result+"");
            Toast.makeText(this,   result, Toast.LENGTH_SHORT).show();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class LoginAsync extends AsyncTask<String, Void, String>
    {
        HashMap<String,String> h;
        @Override
        protected String doInBackground(String... urls) {
            h=new HashMap<>();
            h.put("username",urls[1]);
            h.put("password",urls[2]);
            SendGetAndPost request=new SendGetAndPost();
            String result=request.sendPost(h,urls);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                JSONObject obj=new JSONObject(s);
                String status=obj.getString("status");
                String apikey=obj.getString("apikey");
                String userid=obj.getString("userid");
                ApiModel model=new ApiModel(apikey,Integer.parseInt(status),Integer.parseInt(userid));
                if(Integer.parseInt(status)!=2) {
                    ApiKeyDao key=new ApiKeyDao(MainActivity.this);
                    key.storeApi(model);
                    if(Integer.parseInt(status)==1) {
                        Toast.makeText(MainActivity.this, "Logged in Successfully...", Toast.LENGTH_SHORT).show();
                        Intent profile=new Intent(MainActivity.this,HomeFeed.class);
                        startActivity(profile);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Welcome, New user created...", Toast.LENGTH_SHORT).show();
                        Intent profile=new Intent(MainActivity.this,ProfileActivity.class);
                        startActivity(profile);
                    }
                }
                else
                {
                    btn.setText("Try again");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        }


    }

}