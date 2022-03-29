package com.example.schoolscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.schoolscheduler.TaskPage.Task;
import com.google.android.material.navigation.NavigationView;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_settings);

        //code for Navigation Drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {     //Opens the drawer
                //If Home button is pressed
                if(item.getItemId()== R.id.nav_home){
                    Toast.makeText(settings.this,"Home",Toast.LENGTH_SHORT).show();
                }
                //If Calendar is pressed
                if(item.getItemId()== R.id.nav_calendar){
                    Toast.makeText(settings.this,"Calendar",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(settings.this,CalendarPage.class);
                    startActivity(intent);
                }
                //If Task is pressed
                if(item.getItemId()==R.id.nav_task){
                    Toast.makeText(settings.this,"Tasks",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(settings.this, Task.class);
                    startActivity(intent);
                }
                //If Settings is pressed
                if(item.getItemId()==R.id.nav_settings){
                    Toast.makeText(settings.this,"Settings",Toast.LENGTH_SHORT).show();
                }

                //Closers the drawer when done
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        Button general = findViewById(R.id.generalButton);
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings.this,general.class));
            }
        });

        Button notification = findViewById(R.id.button4);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings.this,notification_setting.class));
            }
        });

    }
}