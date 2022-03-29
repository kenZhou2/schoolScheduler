package com.example.schoolscheduler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SchedulePage extends AppCompatActivity {
    FirebaseConnection connection = new FirebaseConnection("saved-data/schoolscheduler");
    DatabaseReference coursesRef = connection.ref.child("courses");
    Query coursesQuery = coursesRef.orderByKey();
    List<Course> courses;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_page_layout);
        rv = findViewById(R.id.courseList);
        courses = new ArrayList<>();

        FloatingActionButton addClassButton = findViewById(R.id.addClassFab);

        addClassButton.setOnClickListener(view -> {
            Intent intent = new Intent(SchedulePage.this, NewCourse.class);
            startActivity(intent);
        });

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onStart() {
        super.onStart();
        attachRecyclerViewAdapter();
    }

    private void attachRecyclerViewAdapter() {
        final RecyclerView.Adapter adapter = adapter();

        rv.setAdapter(adapter);
    }

    public static class ScheduleHolder extends RecyclerView.ViewHolder {
        private TextView courseName, time, days;

        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName);
            time = itemView.findViewById(R.id.time);
            days = itemView.findViewById(R.id.days);
        }

        @SuppressLint("SetTextI18n")
        private void bind(@NonNull Course course) {
            courseName.setText(course.getName());
            time.setText(course.getStartTime() + " - " + course.getEndTime());
            StringBuilder sb = new StringBuilder();
            for (String day : course.getDays()) {
                sb.append(day).append(" ");
            }
            days.setText(sb.toString());
        }
    }

    protected RecyclerView.Adapter adapter() {
        FirebaseRecyclerOptions<Course> options =
                new FirebaseRecyclerOptions.Builder<Course>()
                .setQuery(coursesQuery, Course.class)
                .setLifecycleOwner(this)
                .build();

        return new FirebaseRecyclerAdapter<Course, ScheduleHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ScheduleHolder holder, int position, @NonNull Course model) {
                holder.bind(model);
            }

            @NonNull
            @Override
            public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.schedule_row, parent, false);
                return new ScheduleHolder(view);
            }
        };
    }
}
