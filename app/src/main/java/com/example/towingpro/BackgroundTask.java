package com.example.towingpro;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask<String,Void,String> {
    Context ctx;
    BackgroundTask(Context ctx)
    {

        this.ctx=ctx;
    }
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://10.0.2.2/webapp/register.php";
        String log_url = "http://10.0.2.2/webapp/login.php";
        String method = params[0];
        if (method.equals("register"))
        {
            String first_name = params[2];
            String last_name = params[3];
            String email = params[4];
            String date = params[5];
            String password = params[6];
            //String cpassword = params[6];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
    String data = URLEncoder.encode("first_name","UTF-8") +"="+URLEncoder.encode(first_name,"UTF-8")+"&"+
                URLEncoder.encode("last_name","UTF-8") +"="+URLEncoder.encode(last_name,"UTF-8")+"&"+
                URLEncoder.encode("email","UTF-8") +"="+URLEncoder.encode(email,"UTF-8")+"&"+
                URLEncoder.encode("date","UTF-8") +"="+URLEncoder.encode(date,"UTF-8")+"&"+
                URLEncoder.encode("password","UTF-8") +"="+URLEncoder.encode(password,"UTF-8");
                //URLEncoder.encode("cpassword","UTF-8") +"="+URLEncoder.encode(cpassword,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Registration Successful";
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }
}
