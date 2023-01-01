package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.Server;
import com.example.yopo.tasks.GetByUsernameFromFirestoreTask;

import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    // field variables
    private Button register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.start_register);
        login = findViewById(R.id.start_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}