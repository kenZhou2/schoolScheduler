package com.example.schoolscheduler;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_Page extends AppCompatActivity {

    private final static String TAG = "Main_Page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button BtnToCal = findViewById(R.id.btnToCal);
        BtnToCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Page.this, CalendarPage.class);
                startActivity(intent);
            }
        });


    }
}