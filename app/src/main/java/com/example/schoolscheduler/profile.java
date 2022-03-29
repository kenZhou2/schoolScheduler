package com.example.schoolscheduler;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.schoolscheduler.TaskPage.Task;

public class profile extends AppCompatActivity {

    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    public static final String EXTRA_NUMBER = "com.example.application.example.EXTRA_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button done = (Button)findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        Button gtoT = (Button)findViewById(R.id.generalTotask);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent done = new Intent(profile.this, Task.class);
              startActivity(done);
            }
        });

        Button gtoc = (Button)findViewById(R.id.generalTocalender);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent done = new Intent(profile.this,settings.class);
//                startActivity(done);
            }
        });
    }

    public void goBack(){
        //Grab the text file you want to update
        EditText editName = (EditText) findViewById(R.id.editTextTextPersonName);
        String name = editName.getText().toString();

        //Pass the text as an integer for testing dont worry about this one
        EditText editNumber = (EditText) findViewById(R.id.editTextPhone);
        int number = Integer.parseInt(editNumber.getText().toString());


        Intent done = new Intent(profile.this,settings.class);
//        done.putExtra(Intent.EXTRA_TEXT, name);
//        done.putExtra(Intent.EXTRA_PHONE_NUMBER, name);
        startActivity(done);
    }
}