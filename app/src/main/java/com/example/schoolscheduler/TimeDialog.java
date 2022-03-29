package com.example.schoolscheduler;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;


import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class TimeDialog extends DialogFragment {
    private static final String TAG = "TimeDialog";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //get hour and minute of day using a calendar util
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(),hour,minute,android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

}
