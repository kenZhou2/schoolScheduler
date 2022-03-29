package com.example.schoolscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ringtones extends AppCompatActivity {
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    MediaPlayer song;
    FirebaseDatabase database;
    DatabaseReference reference;
    Member member;
    int ringtoneID = 0;
    Button btn;
    TextView selected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringtones);
        btn = findViewById(R.id.save_ringtone);
        selected = findViewById(R.id.choose_ringtone);

        DatabaseReference getRingTone = FirebaseDatabase.getInstance().getReference().child("ringtoneID");
        getRingTone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String displayRingTone = "Ringtone ";
                for(DataSnapshot i : snapshot.getChildren()){

                    selected.setText(displayRingTone + i.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        member = new Member();
//        reference = database.getInstance().getReference().child("Spinner");

        spinner = (Spinner)findViewById(R.id.listofRingTones);
        adapter = ArrayAdapter.createFromResource(this,R.array.songs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemIdAtPosition(position) != 0) {
                    Toast.makeText(getBaseContext(), parent.getItemAtPosition(position).toString() + " selected", Toast.LENGTH_LONG).show();
                    if(position == 1) {
                        ringtoneID = position;
                        selected.setText(parent.getItemAtPosition(position).toString());
                        if(song == null)
                        {
                            song = MediaPlayer.create(ringtones.this,R.raw.ringtone1);
                            song.start();
                        }
                        else{
                            song.release();
                            song = MediaPlayer.create(ringtones.this,R.raw.ringtone1);
                            song.start();
                        }
                    }
                    if(position == 2) {
                        ringtoneID = position;
                        selected.setText(parent.getItemAtPosition(position).toString());
                        if(song == null)
                        {
                            song = MediaPlayer.create(ringtones.this,R.raw.ringtone2);
                            song.start();
                        }
                        else{
                            song.release();
                            song = MediaPlayer.create(ringtones.this,R.raw.ringtone2);
                            song.start();
                        }
                    }
                    if(position == 3) {
                        ringtoneID = position;
                        selected.setText(parent.getItemAtPosition(position).toString());
                        if(song == null)
                        {
                            song = MediaPlayer.create(ringtones.this,R.raw.ringtone3);
                            song.start();
                        }
                        else{
                            song.release();
                            song = MediaPlayer.create(ringtones.this,R.raw.ringtone3);
                            song.start();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    maxid = (int) snapshot.getChildrenCount();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("ringtoneID");
                Member helper = new Member(ringtoneID);

                reference.setValue(helper);


//                member.setSpinner(spinner.getSelectedItem().toString());
//                Toast.makeText(ringtones.this,"ringtone saved",Toast.LENGTH_LONG).show();
//
//                reference.child(String.valueOf(maxid +1)).setValue(member);
                startActivity(new Intent(ringtones.this,notification_setting.class));
            }
        });
    }
}