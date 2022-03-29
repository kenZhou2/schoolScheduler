package com.example.schoolscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.schoolscheduler.TaskPage.Task;
import com.google.android.material.navigation.NavigationView;

public class general extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        Button profile = (Button)findViewById(R.id.profileButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(general.this,profile.class));
            }
        });
        Button toCal = (Button)findViewById(R.id.generalTocalender);
        toCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(general.this,CalendarPage.class);
                startActivity(intent);
            }
        });
        //Button to task page
        Button genToTask = (Button)findViewById(R.id.generalTotask);
        genToTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(general.this, Task.class);
                startActivity(intent);
            }
        });
        Button toSch = (Button)findViewById(R.id.sche);
        toSch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(general.this,SchedulePage.class);
                startActivity(intent);
            }
        });



        //code for Navigation Drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {     //Opens the drawer
                //If Home button is pressed
                if(item.getItemId()== R.id.nav_home){
                    Toast.makeText(general.this,"Home",Toast.LENGTH_SHORT).show();
                }
                //If Calendar is pressed
                if(item.getItemId()== R.id.nav_calendar){
                    Toast.makeText(general.this,"Calendar",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(general.this,CalendarPage.class);
                    startActivity(intent);
                }
                //If Task is pressed
                if(item.getItemId()==R.id.nav_task){
                    Toast.makeText(general.this,"Tasks",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(general.this,Task.class);
                    startActivity(intent);
                }
                //If Setting is pressed
                if(item.getItemId()==R.id.nav_settings){
                    Toast.makeText(general.this,"Settings",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(general.this, settings.class);
                    startActivity(intent);
                }


                //Closers the drawer when done
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }
}