package com.example.towingpro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationForm extends Activity {


    EditText fname, lname, emaill, datee, pass;
    String first_name, last_name, email, date, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);


        fname = findViewById(R.id.first_name);
        lname = findViewById(R.id.last_name);
        emaill = findViewById(R.id.email);
        datee = findViewById(R.id.date);
        pass = findViewById(R.id.password);


    }


    public void custReg(View view) {
        first_name = fname.getText().toString();
        last_name = lname.getText().toString();
        email = emaill.getText().toString();
        date = datee.getText().toString();
        password = pass.getText().toString();

        String method = "register";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method, first_name, last_name, email, date, password);
        finish();
    }
}