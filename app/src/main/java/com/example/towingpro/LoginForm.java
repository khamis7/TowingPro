 package com.example.towingpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

 public class LoginForm extends AppCompatActivity {
    //EditText Username, Password;
    //public Button button;
   // public Button button1;

    EditText editTextemail, editTextpassword;
    Button buttonLogin;
    Button buttonSignup;
    //ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        editTextemail =  findViewById(R.id.email);
        editTextpassword =  findViewById(R.id.password);
        buttonLogin = findViewById(R.id.button3);
        buttonSignup = findViewById(R.id.button4);
        //progressBar = findViewById(R.id.progress);
        //Button button1 ;

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationForm.class);
                startActivity(intent);
                finish();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String email, password;

                email = String.valueOf(editTextemail.getText());
                password = String.valueOf(editTextpassword.getText());

                if(!email.equals("") && !password.equals("")) {
                    //progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "email";
                            field[1] = "password";
                            String[] data = new String[2];
                            data[0] = email;
                            data[1] = password;
                            PutData putData = new PutData("http://192.168.43.127/LoginRegister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    //progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    Intent intent = new Intent(getApplicationContext(),Services.class);
                                    startActivity(intent);
//                                    if (result.equals("Login Success")){
//                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(getApplicationContext(), Services.class);
//                                        startActivity(intent);
//                                        finish();
//
//                                    }
//                                    else {
//                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//                                    }
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


        //
//    {
//
//
//    }

//        button1 = (Button) findViewById(R.id.button4);


    }
}