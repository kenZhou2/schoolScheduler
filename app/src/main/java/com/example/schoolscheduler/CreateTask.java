package com.example.schoolscheduler;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import com.example.schoolscheduler.TaskPage.Task;
import com.google.android.material.navigation.NavigationView;


public class CreateTask extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    String timeAsString;
    int priorKey;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_task_layout);

        Button makeToListBtn = (Button) findViewById(R.id.makeToListBtn);
        Button makeToCalBtn = (Button) findViewById(R.id.makeToCalBtn);
        Button timePicker = (Button) findViewById(R.id.timeDialogBtn);

        makeToCalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTask.this,CalendarPage.class);
                startActivity(intent);
            }
        });
        makeToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent incomingDate = getIntent();
                String year,month,day;  //Strings for date
                year = incomingDate.getStringExtra("year");
                month = incomingDate.getStringExtra("month");
                day= incomingDate.getStringExtra("day");
                int yearInt,monthInt,dayInt;  //Int for data
                yearInt = Integer.parseInt(year);
                monthInt = Integer.parseInt(month);
                dayInt = Integer.parseInt(day);

                priorKey = priorKey + yearInt + monthInt + dayInt;

                Toast.makeText(CreateTask.this,priorKey+"++",Toast.LENGTH_SHORT);

                Intent intent = new Intent(CreateTask.this,Main_Page.class);
                startActivity(intent);
            }
        });
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimeDialog();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });


    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        String finalTime;
        String min = minute+"";
        priorKey =0;
        priorKey = hour+minute;

        // Prints out the right time as a Toast and sets the timeAsString variable
        if (minute<10){
            min = "0"+min;
        }
        if (hour == 0 ){
            hour = 12;
            String time = hour+":"+min+" AM";
            finalTime = time;
            Toast.makeText(CreateTask.this, time, Toast.LENGTH_LONG).show();

        }else if(hour >= 13){
            hour = hour - 12;
            String time = hour+":"+min+" PM";
            finalTime = time;
            Toast.makeText(CreateTask.this, time, Toast.LENGTH_LONG).show();
        }else if(hour==12){
            String time = hour+":"+min+" PM";
            finalTime = time;
            Toast.makeText(CreateTask.this, time, Toast.LENGTH_LONG).show();
        } else {
            String time = hour+":"+min+" AM";
            finalTime = time;
            Toast.makeText(CreateTask.this, time, Toast.LENGTH_LONG).show();
        }
        timeAsString = finalTime;
    }
}
