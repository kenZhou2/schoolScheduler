package com.example.schoolscheduler.TaskPage;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolscheduler.SQLDatabase;
import com.example.schoolscheduler.TaskPage.Task;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.schoolscheduler.R;
import com.facebook.stetho.Stetho;

import java.util.Calendar;

public class EditTaskContent extends AppCompatActivity {
    private final String DB_NAME = "MyDBB.db";
    private String TABLE_NAME = "MyTablee";
    private final int DB_VERSION = 1;
    private TextView date;
    private DatePickerDialog.OnDateSetListener dateListener;
    SQLDatabase DB;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        Stetho.initializeWithDefaults(this);
        DB = new SQLDatabase(this, DB_NAME, null, DB_VERSION, TABLE_NAME);
        DB.checkTable();
        id = "";
        back();
        change();
        Cancel();

        //datepicker
        date = (TextView) findViewById(R.id.edit_date);
        Button date_button = (Button) findViewById(R.id.edit_date_picker_button);
        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditTaskContent.this,
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
    }
    public void change(){
        Global mApp = ((Global)getApplicationContext());
        String[] s = mApp.getS();
        EditText name = findViewById(R.id.edit_title);
        EditText sub = findViewById(R.id.edit_subject_s);
        EditText type = findViewById(R.id.edit_type_s);
        TextView due = findViewById(R.id.edit_date);
        EditText detail = findViewById(R.id.edit_details);
        id = s[0];
        name.setText(s[1]);
        sub.setText(s[2]);
        type.setText(s[3]);
        due.setText(s[4]);
        detail.setText(s[5]);
    }

    public void back(){
        Button save = findViewById(R.id.edit_savebutton);
        save.setOnClickListener(view -> {
            EditText Title = findViewById(R.id.edit_title);
            TextView Due = findViewById(R.id.edit_date);
            String t = Title.getText().toString();
            String du = Due.getText().toString();
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            if (t == null || t.equals("")){ //no task title
                Toast.makeText(this,"Task Title cannot be blank", Toast.LENGTH_SHORT).show();
            }
            else if(du == null || du.equals("") || du.equals("00/00/00")){ //no due date
                Toast.makeText(this,"Due date cannot be blank", Toast.LENGTH_SHORT).show();
            }
            else if (getyear(du)< year || getmonth(du) < month || getdate(du) < day){
                Toast.makeText(this,"Due date already passed!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(EditTaskContent.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                EditText name = findViewById(R.id.edit_title);
                EditText sub = findViewById(R.id.edit_subject_s);
                EditText type = findViewById(R.id.edit_type_s);
                TextView due = findViewById(R.id.edit_date);
                EditText detail = findViewById(R.id.edit_details);
                DB.modify(id
                        , name.getText().toString()
                        , sub.getText().toString()
                        , type.getText().toString()
                        , due.getText().toString()
                        , detail.getText().toString());
                open();
            }

                });


    }
    public void open() {
        Intent intent = new Intent(this, Task.class);
        startActivity(intent);
    }

    public void Cancel(){
        Button cancel = findViewById(R.id.edit_canceladdbutton);
        cancel.setOnClickListener(view -> {
            Intent backtomain = new Intent(this, Task.class);
            startActivity(backtomain);
        });
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
