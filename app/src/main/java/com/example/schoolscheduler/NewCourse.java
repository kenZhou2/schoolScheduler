package com.example.schoolscheduler;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/*
    This page is where the user will add new courses to the application. Currently, it asks for a
    course name, start and end time, and the days the course takes place.
 */

public class NewCourse extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Button setStartTime, setEndTime;
    TextView startTime, endTime;
    EditText nameField;
    ChipGroup daySelector;
    int selectedButton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Get saved instance state and set the layout of the page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_class);

        // Get Buttons from layout
        Button addClass = findViewById(R.id.addClass);
        setStartTime = findViewById(R.id.setCourseStartTimeButton);
        setEndTime = findViewById(R.id.setCourseEndTimeButton);

        startTime = findViewById(R.id.courseTimeStartView);
        endTime = findViewById(R.id.courseTimeEndView);

        // Get Text fields from layout
        nameField = findViewById(R.id.editCourseTitle);

        // Initialize list to use when we're done creating the course
        List<String> days = new ArrayList<>();
        daySelector = findViewById(R.id.DaySelector);

        // Click listener setups for start and end time dialog buttons
        setStartTime.setOnClickListener(view -> {
            selectedButton = 0; // Tell time dialog we clicked the start button
            DialogFragment startTimeDialog = new TimeDialog();
            startTimeDialog.show(getSupportFragmentManager(), "Start Time");
        });
        setEndTime.setOnClickListener(view -> {
            selectedButton = 1; // Tell time dialog we clicked the end button
            DialogFragment endTimeDialog = new TimeDialog();
            endTimeDialog.show(getSupportFragmentManager(), "End Time");
        });

        // Click listener for what is basically the 'submit' button on this page
        addClass.setOnClickListener(view -> {
            if (fieldsValid()) {
                List<Integer> ids = daySelector.getCheckedChipIds();

                // Get each day that was selected in the day selector
                for (Integer i : ids) {
                    Chip chip = findViewById(i);
                    days.add(chip.getText().toString());
                }

                Course course = new Course(nameField.getText().toString(),
                        startTime.getText().toString(),
                        endTime.getText().toString(),
                        days);

                FirebaseConnection connection =
                        new FirebaseConnection("saved-data/schoolscheduler");

                // Add the course to the courses list AND to the lists for the days of the week it's on
                DatabaseReference daysRef = connection.ref.child("week");
                DatabaseReference coursesRef = connection.ref.child("courses");

                coursesRef.child(nameField.getText().toString()).setValue(course);

                // Set course within the day table (e.g. the "Monday" table) for easy retrieval later.
                for (String day : days) {
                    DatabaseReference newDayRef = daysRef.child(day).push();
                    newDayRef.setValue(course);
                }

                // Build intent and send the user back to the schedule page
                Intent intent = new Intent(NewCourse.this, SchedulePage.class);
                startActivity(intent);
            }
        });
    }

    private boolean fieldsValid() {
        // Ensure the required fields were filled in
        if (nameField != null && (nameField.getText().toString().equals("")
                || nameField.getText().toString().equals(" "))) {
            Toast.makeText(NewCourse.this,
                    "Name can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        if (startTime.getText().equals(getString(R.string.no_time_set_string))
                || endTime.getText().equals(getString(R.string.no_time_set_string))) {
            Toast.makeText(NewCourse.this,
                    "Both start and end time should be set", Toast.LENGTH_LONG).show();
            return false;
        }
        if (daySelector.getChildCount() == 0) {
            Toast.makeText(NewCourse.this,
                    "One or more days must be selected.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    // Taken (and slightly modified) from calendar page's time calculation
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        String finalTime;
        String min = minute + "";

        if (minute < 10) {
            min = "0" + min;
        }
        if (hour == 0) {
            hour = 12;
            finalTime = hour + ":" + min + " AM";
        } else if (hour >= 13) {
            hour = hour - 12;
            finalTime = hour + ":" + min + " PM";
        } else if (hour == 12) {
            finalTime = hour + ":" + min + " PM";
        } else {
            finalTime = hour + ":" + min + " AM";
        }

        // Depending on which dialog is opened, set the appropriate TextView's data.
        if (selectedButton == 0) {
            startTime.setText(finalTime);
        } else {
            endTime.setText(finalTime);
        }
    }
}
