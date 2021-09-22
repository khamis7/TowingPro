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

        EditText editTextFirstName, editTextLastName, editTextEmail, editTextDate, editTextPassword, editTextConfirmPassword;
         Button button2;
         ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        editTextFirstName = findViewById(R.id.first_name);
        editTextLastName = findViewById(R.id.last_name);
        editTextEmail = findViewById(R.id.email);
        editTextDate = findViewById(R.id.date);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.cpassword);

        button2 = findViewById(R.id.button2);
        progressBar = findViewById(R.id.progress);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name, last_name, email, date, password, cpassword;
                first_name = String.valueOf(editTextFirstName.getText());
                last_name = String.valueOf(editTextLastName.getText());
                email = String.valueOf(editTextEmail.getText());
                date = String.valueOf(editTextDate.getText());
                password = String.valueOf(editTextPassword.getText());
                cpassword = String.valueOf(editTextConfirmPassword.getText());

                 if(!first_name.equals("") && !last_name.equals("") && !email.equals("") && !date.equals("") && !password.equals("") && !cpassword.equals("")) {
                     progressBar.setVisibility(View.VISIBLE);
                     Handler handler = new Handler();
                     handler.post(new Runnable() {
                         @Override
                         public void run() {
                             String[] field = new String[6];
                             field[0] = "first_name";
                             field[1] = "last_name";
                             field[2] = "email";
                             field[3] = "date";
                             field[4] = "password";
                             field[5] = "cpassword";
                             String[] data = new String[6];
                             data[0] = first_name;
                             data[1] = last_name;
                             data[2] = email;
                             data[3] = date;
                             data[4] = password;
                             data[5] = cpassword;
                             PutData putData = new PutData("http://192.168.235.62/LoginReigster/signup.php", "POST", field, data);
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