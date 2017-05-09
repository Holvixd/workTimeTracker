package com.example.vilho.worktimetracker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * The StartTimePickerFragment fragment shows a timepicker for picking a work staring time.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   1.0
 */

public class StartTimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        TextView tv1 = (TextView) getActivity().findViewById(R.id.startTime);
        String hour;
        if (hourOfDay<10){
            hour = "0"+hourOfDay;
        }else{
            hour=hourOfDay+"";
        }
        String minutes;
        if (minute<10){
            minutes = "0"+minute;
        }else{
            minutes=minute+"";
        }
        tv1.setText(hour+":"+minutes);
    }
}
