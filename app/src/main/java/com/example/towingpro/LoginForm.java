 package com.example.towingpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginForm extends AppCompatActivity {
    EditText Username, Password;
    //public Button button;
    //public Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
         Username = (EditText) findViewById(R.id.log_username);
         Password = (EditText) findViewById(R.id.loginpassword);

        //Button button3 = (Button) findViewById(R.id.button3);
        //admin and admin


//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (Username.getText().toString().equals("admin") && Password.getText().toString().equals("admin")) {
//                    Intent intent = new Intent(LoginForm.this,Services.class);
//                    startActivity(intent);
//                    //correct
//                    Toast.makeText(LoginForm.this, "Login successful", Toast.LENGTH_SHORT).show();
//
//
//                } else
//                    //incorrect
//                    Toast.makeText(LoginForm.this, "Login Failed", Toast.LENGTH_SHORT).show();
//
//            }
//        });

//        button1 = (Button) findViewById(R.id.button4);
//
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginForm.this,RegistrationForm.class);
//                startActivity(intent);
//            }
//        });
    }


    //
//    {
//
//
//    }
    public void custLog(View view) {
        String emaill = Username.getText().toString();
        String pass_word = Password.getText().toString();
        String type = "login";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(type, emaill,  pass_word);
    }
    public void custReg(View view){
        startActivity(new Intent(this,RegistrationForm.class));
    }
}