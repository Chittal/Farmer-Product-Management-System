package com.example.cute.farmerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomeFarmer extends AppCompatActivity {

    TextView proadd,proview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_farmer);

        proadd=(TextView) findViewById(R.id.addp);
        proview=(TextView) findViewById(R.id.viewp);

        proadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeFarmer.this,AddProduct.class));
            }
        });

        proview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeFarmer.this,ViewProduct.class));
            }
        });
    }
}
