package com.example.schoolscheduler.TaskPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import androidx.fragment.app.DialogFragment;

import com.example.schoolscheduler.CalendarPage;
import com.example.schoolscheduler.CreateTask;
import com.example.schoolscheduler.R;
import com.example.schoolscheduler.SQLDatabase;
import com.example.schoolscheduler.TimeDialog;
import com.example.schoolscheduler.general;
import com.example.schoolscheduler.settings;
import com.facebook.stetho.Stetho;
import com.google.android.material.navigation.NavigationView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.Calendar;

public class AddNewTask extends AppCompatActivity {
    EditText Title,Subject, Type, Details;
    TextView Due, AlarmDate;
    int y = 0;
    int mon = 0;
    int d =0;
    int h = 0;
    int m = 0;
    private final String DB_NAME = "MyDBB.db";
    private String TABLE_NAME = "MyTablee";
    private final int DB_VERSION = 1;
    private static final String TAG = "Task";
    private TextView date, alarmdate, alarmtime;
    private DatePickerDialog.OnDateSetListener dateListener, alarmdateListener;
    static String alarm_id, static_due ;
    SQLDatabase DB;
    Switch ring_switch;
    boolean is_ringtone = false;
    //@SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch noti_switch;
    boolean is_notification = false;
    Switch vi_switch;
    boolean is_vibrate = false;
    TextView selected;
    //String try = "12/8/2020";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_task);
/* switch to notification setting but it is not working

        noti_switch = (Switch) findViewById(R.id.notification_switch);
        noti_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    is_notification = true;
                }
            }
        });

        ring_switch = (Switch) findViewById(R.id.ringtone_switch);
        ring_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    is_ringtone = true;
                }
            }
        });

        vi_switch = (Switch) findViewById(R.id.vibrate_switch);
        vi_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    is_vibrate = true;
                }
            }
        });
         */


///////open the database and add data/////////
        Stetho.initializeWithDefaults(this);
        DB = new SQLDatabase(this, DB_NAME, null, DB_VERSION, TABLE_NAME);
        DB.checkTable();
        Cancel();
        SaveInfo();


///////due-datepicker/////////
        date = (TextView) findViewById(R.id.new_date);
        Button date_button = (Button) findViewById(R.id.new_date_picker_button);
        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddNewTask.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
       dateListener = new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker datePicker, int year, int month, int day) {
               String dat = (month+1) + "/" + day +"/"+ year;
               date.setText(dat);
               //Log.d(TAG, "OnDateSet" +i +"/"+i1+"/"+i2);
           }
       };

///////alarm-datepicker/////////
        alarmdate = (TextView) findViewById(R.id.alarm_date_view);
        Button alarm_date_button = (Button) findViewById(R.id.alarm_date_picker_button);
        alarm_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddNewTask.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        alarmdateListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
/*
        //code for Navigation Drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {     //Opens the drawer
                //If Home button is pressed
                if(item.getItemId()== R.id.nav_home){
                    Toast.makeText(AddNewTask.this,"Home",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNewTask.this, general.class);
                    startActivity(intent);
                }
                //If Calendar is pressed
                if(item.getItemId()== R.id.nav_calendar){
                    Toast.makeText(AddNewTask.this,"Calendar",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNewTask.this,CalendarPage.class);
                    startActivity(intent);
                }
                //If Task is pressed
                if(item.getItemId()==R.id.nav_task){
                    Toast.makeText(AddNewTask.this,"Tasks",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNewTask.this, Task.class);
                    startActivity(intent);
                }
                //If Setting is pressed
                if(item.getItemId()==R.id.nav_settings){
                    Toast.makeText(AddNewTask.this,"Settings",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNewTask.this, settings.class);
                    startActivity(intent);
                }


                //Closers the drawer when done
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });



 */


        alarmdateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String dat = (month+1) + "/" + day +"/"+ year;
                alarmdate.setText(dat);
                y = year;
                mon = month;
                d = day;
                //Log.d(TAG, "OnDateSet" +i +"/"+i1+"/"+i2);
            }
        };


///////alarm-timepicker/////////
        alarmtime = (TextView) findViewById(R.id.alarm_time_view);
        Button alarm_time_button = (Button) findViewById(R.id.alarm_time_picker_button);
        alarm_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(
                        AddNewTask.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                alarmtime.setText(selectedHour + ":" + selectedMinute);
                                h = selectedHour;
                                m =selectedMinute;
                            }
                        }, hour, minute, true);
                dialog.setTitle("Select Time");
                dialog.show();
            }
        });
    }

    public void Cancel(){
        Button cancel = findViewById(R.id.new_canceladdbutton);
        cancel.setOnClickListener(view -> {
            Intent backtomain = new Intent(this, Task.class);
            startActivity(backtomain);
        });
    }


//////save information/////////
    public void SaveInfo() {
        Button save = findViewById(R.id.new_savechangebutton);
        Title = findViewById(R.id.new_title);
        Subject = findViewById(R.id.new_subject_s);
        Type = findViewById(R.id.new_type_s);
        Due = findViewById(R.id.new_date);
        Details = findViewById(R.id.new_details);
        AlarmDate = findViewById(R.id.alarm_date_view);

        save.setOnClickListener(view -> {
            String t = Title.getText().toString();
            String du = Due.getText().toString();
            String ad = AlarmDate.getText().toString();
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            if (t == null || t.equals("")){ //no task title
                Toast.makeText(AddNewTask.this,"Task Title cannot be blank", Toast.LENGTH_SHORT).show();
            }
            else if(du == null || du.equals("") || du.equals("00/00/00")){ //no due date
                Toast.makeText(AddNewTask.this,"Due date cannot be blank", Toast.LENGTH_SHORT).show();
            }
            else if (getyear(du)< year || getmonth(du) < month || getdate(du) < day){
                Toast.makeText(AddNewTask.this,"Due date already passed!", Toast.LENGTH_SHORT).show();
            }
            else {
                DB.addData(Title.getText().toString(),
                        Subject.getText().toString(),
                        Type.getText().toString(),
                        Due.getText().toString(),
                        Details.getText().toString());
                if (ad != null && !ad.equals("") && !ad.equals("00/00/00")) { //user set alarm
                    //static id and static due will be used in static class AlarmReceiver
                    alarm_id = Title.getText().toString();
                    static_due = Due.getText().toString();
                    createNotificationChannel();
                    //save alarm time to AlarmManager
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(System.currentTimeMillis());
                    cal.clear();
                    cal.set(Calendar.YEAR, y);
                    cal.set(Calendar.MONTH, mon);
                    cal.set(Calendar.DAY_OF_MONTH, d);
                    cal.set(Calendar.HOUR_OF_DAY, h);
                    cal.set(Calendar.MINUTE, m);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
///////alarm manager///////////////////
                    //notification switch is not working
                    //if(is_notification){
                        Intent intent = new Intent(this, AlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                   // }
                }
                //String tryy = "12/10/2020";
                //String nn = String.valueOf(getmonth(tryy));
                Toast.makeText(AddNewTask.this, "Task Added!", Toast.LENGTH_SHORT).show();
                Intent backtomain = new Intent(this, Task.class);
                startActivity(backtomain);
            }
        });

    }

///////notification channel/////////
    private void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "ll";
            int importance = NotificationManager.IMPORTANCE_LOW;
            //each channel should have a unique channel ID
            NotificationChannel Channel = new NotificationChannel(alarm_id, name, importance);
            Channel.enableLights(true)
            //other features....
            //....
            //....
            ;
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(Channel);
        }

    }

////////broadcast:how notifications look like/////////
    public static class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(arg0, alarm_id)
                    .setTicker("CSE442 School Scheduler")
                    .setContentText("Due : "+static_due)
                    .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                    .setContentTitle(alarm_id);//alarm_id is the task title

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(arg0);
            notificationManager.notify(0x101,builder.build()); //an app only has one notify id
        }

    }

    public int getdate(String str){
        int num = 0;
        if (str.charAt(1) == '/') {
            if (str.charAt(3) == '/') {
                num = Integer.parseInt(String.valueOf(str.charAt(2)));
            } else {
                num = Integer.parseInt(String.valueOf(str.charAt(2))) * 10 + Integer.parseInt(String.valueOf(str.charAt(3)));
            }
        }
        if (str.charAt(2) == '/'){
            if (str.charAt(4) == '/'){
                num = Integer.parseInt(String.valueOf(str.charAt(3)));
            }
            else{
                num = Integer.parseInt(String.valueOf(str.charAt(3)))*10+ Integer.parseInt(String.valueOf(str.charAt(4)));
            }
        }
        return num;
    }

    public int getyear(String str){
        int num = 0;
        num = Integer.parseInt(str.substring(str.length() - 4));
        return num;
    }

    public int getmonth(String str){
        int num = 0;
        if (str.charAt(1) == '/') {
            num = Integer.parseInt(String.valueOf(str.charAt(0)));
        }
        else{
            num = Integer.parseInt(String.valueOf(str.charAt(0)))*10+ Integer.parseInt(String.valueOf(str.charAt(1)));
        }
        return num;
    }


}
