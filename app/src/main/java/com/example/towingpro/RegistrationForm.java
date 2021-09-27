  package com.example.towingpro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.sql.Connection;

  public class RegistrationForm extends AppCompatActivity {

        EditText editTextfirst_name, editTextlast_name, editTextemail, editTextdate, editTextpassword;
         Button button2;
         ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        editTextfirst_name = findViewById(R.id.first_name);
        editTextlast_name = findViewById(R.id.last_name);
        editTextemail = findViewById(R.id.email);
        editTextdate = findViewById(R.id.date);
        editTextpassword = findViewById(R.id.password);
         progressBar = findViewById(R.id.progress);
        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final String first_name, last_name, email, date, password, cpassword;
            first_name = String.valueOf(editTextfirst_name.getText());
            last_name = String.valueOf(editTextlast_name.getText());
            email = String.valueOf(editTextemail.getText());
            date = String.valueOf(editTextdate.getText());
            password = String.valueOf(editTextpassword.getText());

                if(!first_name.equals("") && !last_name.equals("") && !email.equals("") && !date.equals("") && !password.equals("")) {
                 progressBar.setVisibility(View.VISIBLE);
                 Handler handler = new Handler();
                 handler.post(new Runnable() {
                     @Override
                     public void run() {
                         String[] field = new String[5];
                         field[0] = "first_name";
                         field[1] = "last_name";
                         field[2] = "email";
                         field[3] = "date";
                         field[4] = "password";
                         String[] data = new String[5];
                         data[0] = first_name;
                         data[1] = last_name;
                         data[2] = email;
                         data[3] = date;
                         data[4] = password;
                        // data[5] = cpassword;
                         PutData putData = new PutData("http://192.168.43.127/LoginRegister/signup.php", "POST", field, data);
                         if (putData.startPut()) {
                             if (putData.onComplete()) {
                                 progressBar.setVisibility(View.GONE);
                                 String result = putData.getResult();
                                 if (result.equals("Sign Up Success")){
                                     Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                     Intent intent = new Intent(getApplicationContext(), LoginForm.class);
                                     startActivity(intent);
                                     finish();

                                 }
                                 else {
                                     Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                 }
                             }
                         }
                     }
                 });
                }
                 else{
                     Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                 }
            }
        });


        }
    }