package com.example.schoolscheduler.TaskPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.schoolscheduler.CalendarPage;
import com.example.schoolscheduler.CreateTask;

import androidx.appcompat.widget.Toolbar;


import com.example.schoolscheduler.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.provider.Settings;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolscheduler.SQLDatabase;
import com.example.schoolscheduler.general;
import com.example.schoolscheduler.settings;
import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class Task extends AppCompatActivity  {
    String TAG = Task.class.getSimpleName() + "My";
    private final String DB_NAME = "MyDBB.db";
    private String TABLE_NAME = "MyTablee";
    private final int DB_VERSION = 1;
    SQLDatabase DB;
    ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
    ArrayList<ArrayList<String>> todayList = new ArrayList<>();
    ArrayList<ArrayList<String>> tomList = new ArrayList<>();
    ArrayList<ArrayList<String>> next7List = new ArrayList<>();
    ArrayList<String> getNowArray = new ArrayList<>();
    private int Todaylist_size;
    private ListView lv1, lv2,lv3;
    private ArrayList<Model> modelArrayList;
    private CustomAdapterr customAdapter1, customAdapter2,customAdapter3;
    private MenuItem submitOp;
    private MenuItem deleteOp;
    private ArrayList<String> id_del;
    private ArrayList<Integer> ttt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_main_task_page);

        //code for Navigation Drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {     //Opens the drawer
                //If Home button is pressed
                if(item.getItemId()== R.id.nav_home){
                    Toast.makeText(Task.this,"Home",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Task.this, general.class);
                    startActivity(intent);
                }
                //If Calendar is pressed
                if(item.getItemId()== R.id.nav_calendar){
                    Toast.makeText(Task.this,"Calendar",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Task.this, CalendarPage.class);
                    startActivity(intent);
                }
                //If Task is pressed
                if(item.getItemId()==R.id.nav_task){
                    Toast.makeText(Task.this,"Tasks",Toast.LENGTH_SHORT).show();
                }
                //If Setting is pressed
                if(item.getItemId()==R.id.nav_settings){
                    Toast.makeText(Task.this,"Settings",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Task.this, settings.class);
                    startActivity(intent);
                }


                //Closers the drawer when done
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });




        //work db
        Stetho.initializeWithDefaults(this);
        DB = new SQLDatabase(this, DB_NAME, null, DB_VERSION, TABLE_NAME);
        DB.checkTable();
        arrayList = DB.showOne();
        if (arrayList.size() > 0) {
            //check date for all tasks
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            String dat = (month+1) + "/" + day +"/"+ year;
            for (int i = 0; i < arrayList.size(); i++){
                String duedate = arrayList.get(i).get(4);
                if (duedate.equals(dat)){
                    todayList.add(arrayList.get(i));
                }
                if (getdate(duedate)-day == 1 ){
                    tomList.add(arrayList.get(i));
                }
                if (getdate(duedate)-day < 8 && getdate(duedate)-day > 1){
                    next7List.add(arrayList.get(i));
                }
            }
            if (todayList.size() > 0) {
                //list out all tasks
                lv1 = (ListView) findViewById(R.id.lv);
                ViewGroup.LayoutParams params = lv1.getLayoutParams();
                //modelArrayList = getModel(false);

                //Change the height of ListView
                params.height = todayList.size() * 195;

                lv1.setLayoutParams(params);
                lv1.requestLayout();
                customAdapter1 = new CustomAdapterr(this, todayList);

                lv1.setAdapter(customAdapter1);
            }
            if (tomList.size() > 0) {
                //list out all tasks
                lv2 = (ListView) findViewById(R.id.list_tom);
                ViewGroup.LayoutParams params = lv2.getLayoutParams();
                //modelArrayList = getModel(false);

                //Change the height of ListView
                params.height = tomList.size() * 195;

                lv2.setLayoutParams(params);
                lv2.requestLayout();
                customAdapter2 = new CustomAdapterr(this, tomList);
                lv2.setAdapter(customAdapter2);
            }
            if (next7List.size() > 0) {
                //list out all tasks
                lv3 = (ListView) findViewById(R.id.list_next_7);
                ViewGroup.LayoutParams params = lv3.getLayoutParams();
                //modelArrayList = getModel(false);
                //Change the height of ListView
                params.height = next7List.size() * 195;

                lv3.setLayoutParams(params);
                lv3.requestLayout();
                customAdapter3 = new CustomAdapterr(this, next7List);
                lv3.setAdapter(customAdapter3);
            }

        }
        //add button
        fab();
        //toolbar
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        id_del = new ArrayList<>();
        ttt = new ArrayList<>();

    }

    //get the model list
    private ArrayList<Model> getModel(boolean isSelect, ArrayList<ArrayList<String>> arr) {
        ArrayList<Model> list = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            Model model = new Model();
            model.setSelected(isSelect);
            model.mseti(arr.get(i).get(0));
            model.msetn(arr.get(i).get(1));
            model.msets(arr.get(i).get(2));
            model.msett(arr.get(i).get(3));
            model.msetdu(arr.get(i).get(4));
            model.msetd(arr.get(i).get(5));
            list.add(model);
        }
        return list;
    }

    //FAB
    public void fab() {
        final Boolean[] isAllFabsVisible = new Boolean[1];
        FloatingActionButton AddFab = findViewById(R.id.add_fab);
        // FAB button
        FloatingActionButton TaskFab = findViewById(R.id.add_task_fab);
        TextView TaskActionText = findViewById(R.id.add_task_action_text);

        TaskFab.setVisibility(View.GONE);
        TaskActionText.setVisibility(View.GONE);
        isAllFabsVisible[0] = false;
        AddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible[0]) {
                            TaskFab.show();
                            TaskActionText.setVisibility(View.VISIBLE);
                            isAllFabsVisible[0] = true;
                        } else {
                            TaskFab.hide();
                            TaskActionText.setVisibility(View.GONE);
                            isAllFabsVisible[0] = false;
                        }
                    }
                });
        TaskFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openNewActivity();
                    }
                });
    }

    //open Edit page
    public void onItemClick(View view) {
        Intent intent = new Intent(getApplicationContext(), EditTaskContent.class);
        startActivity(intent);
    }
    //open Add page
    public void openNewActivity() {
        Intent intent = new Intent(this, AddNewTask.class);
        startActivity(intent);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.todo, menu);
        deleteOp = menu.findItem(R.id.delete);
        submitOp = menu.findItem(R.id.submit);
        deleteOp.setVisible(false);
        submitOp.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String tt = "";
        if (id == R.id.delete) {
            for (int k=0; k<id_del.size();k++) {
                //tt = tt + id_del.get(k);
                DB.delete(id_del.get(k));
                int position = ttt.get(k);
                   //arrayList.remove(position);
                //arrayList.clear();
                //modelArrayList.clear();

            }
            arrayList = DB.showOne();
            for (int k=0; k<arrayList.size();k++){
                tt = tt + arrayList.get(k).get(1);
            }
                //customAdapter.notifyDataSetChanged();
            //Toast.makeText(Task.this, tt, Toast.LENGTH_LONG).show();


            Intent intent = new Intent(this, Task.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.submit) {
            //Toast.makeText(Task.this, "Act clicked", Toast.LENGTH_LONG).show();
            backtomain();
            deleteOp.setVisible(false);
            submitOp.setVisible(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void backtomain() {
        Intent intent = new Intent(this, Task.class);
        startActivity(intent);
    }

    private class CustomAdapterr extends BaseAdapter {
        private Context context;
        public ArrayList<ArrayList<String>> arrayList;
        public  ArrayList<Model> modelArrayList;

        public CustomAdapterr(Context context, ArrayList<ArrayList<String>> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
            //this.arrayList = DB.showOne();
            //Todaylist_size = arrayList.size();
            this.modelArrayList = getModel(false, this.arrayList);
        }

        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return modelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return modelArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.lv_item, null, true);
                holder.CheckBox = (CheckBox) convertView.findViewById(R.id.cb);
                holder.TaskTitleView = (TextView) convertView.findViewById(R.id.task_title);
                holder.TaskDateView = (TextView) convertView.findViewById(R.id.lv_date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.TaskTitleView.setText(modelArrayList.get(position).getTask()[1]);
            holder.TaskDateView.setText(modelArrayList.get(position).getTask()[4]);
            holder.CheckBox.setChecked(modelArrayList.get(position).getSelected());
            Global mApp = ((Global)getApplicationContext());
            holder.TaskTitleView.setOnClickListener((v)->{
                getNowArray.clear();
                try {
                    mApp.seti(modelArrayList.get(position).getTask()[0]);
                    mApp.setn(modelArrayList.get(position).getTask()[1]);
                    mApp.sets(modelArrayList.get(position).getTask()[2]);
                    mApp.sett(modelArrayList.get(position).getTask()[3]);
                    mApp.setdu(modelArrayList.get(position).getTask()[4]);
                    mApp.setd(modelArrayList.get(position).getTask()[5]);

                    Intent intent = new Intent(getApplicationContext(), EditTaskContent.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d(TAG, "error: " + e.getMessage());
                }
            });
            holder.CheckBox.setTag(R.integer.one, convertView);
            holder.CheckBox.setTag(position);
            holder.CheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteOp.setVisible(true);
                    submitOp.setVisible(true);
                    id_del.add(modelArrayList.get(position).getTask()[0]);
                    ttt.add(position);
                }
            });
            return convertView;
        }
        private class ViewHolder {
            protected CheckBox CheckBox;
            private TextView TaskTitleView;
            private TextView TaskDateView;
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
}
// create an action bar button

//for (int k=0; k<id_del.size();k++){
//DB.delete(id_del.get(k));
//Iterator itr = arrayList.iterator();
//while (itr.hasNext())
//{
// ArrayList<String> x = (ArrayList<String>)itr.next();
//if (x < 10)
//itr.remove();
//}
//arrayList.remove(position);
//arrayList = DB.showOne();
//customAdapter.notifyItemRemoved(position);
//}
//id_del.clear();



