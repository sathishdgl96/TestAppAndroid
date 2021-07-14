package com.example.urlconnection.sendrequest;

import android.util.Log;

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

public class SendGetAndPost
{
    public String sendPost(HashMap<String,String>h,String urls[])
    {
        HttpURLConnection conn;
        int status;
        String result="";
        try{
            String urlParameters  = getPostDataString(h);
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = urls[0];
            URL url            = new URL( request );
            conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
            wr.write( postData );
            InputStream stream = conn.getInputStream();
            InputStreamReader reader=new InputStreamReader(stream);
            int data=reader.read();
            while(data!=-1)
            {
                char current =(char)data;
                result+=current;
                data=reader.read();
            }
        }
        catch(Exception e)
        {
            System.out.println("Error Occured"+e);
        }

        return result;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.i("Called",result.toString());
        return result.toString();
    }
}
