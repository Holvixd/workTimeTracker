package com.example.vilho.worktimetracker;

/**
 * Created by Vilho on 19.4.2017.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Vilho on 19.4.2017.
 */

public class DateEndPickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        TextView tv1 = (TextView) getActivity().findViewById(R.id.endDate);
        Date date = new Date(year-1900,month,day);
        month++;
        Toast.makeText(getActivity(),date.toString(),Toast.LENGTH_SHORT).show();
        tv1.setText(day+"."+month+"."+year);
    }
}