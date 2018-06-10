package com.example.cute.farmerapp;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class IntroductionActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar2);
        //toolbar.setTitle("Instructions");

        button=(Button) findViewById(R.id.skip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroductionActivity.this,WelcomeFarmer.class));
            }
        });
    }
}
