package fi.tamk.vilho.worktimetracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.sql.Date;
import java.util.Calendar;

/**
 * The DateStartPickerFragment fragment shows a datepicker for picking a work starting date.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   1.0
 */
public class DateStartPickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    /**
     * Creates the dialog.
     *
     *
     * @param savedInstanceState        Saved states
     * @return                          Created dialog
     * @since                           1.0
     */
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

    /**
     * Sets the textview's text to correspond the date that user has chosen.
     *
     *
     * @param view      Datepicker dialog
     * @param year      Year picked by user
     * @param month     Month picked by user
     * @param day       Day picked by user
     * @since           1.0
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        TextView tv1 = (TextView) getActivity().findViewById(R.id.startDate);
        Date date = new Date(year-1900,month,day);
        month++;
        String days;
        if (day<10){
            days = "0"+day;
        }else{
            days=day+"";
        }
        String months;
        if (month<10){
            months = "0"+month;
        }else{
            months=month+"";
        }
        String years;
        if (year<10){
            years = "0"+year;
        }else{
            years=year+"";
        }
        tv1.setText(days+"."+months+"."+years);
    }
}