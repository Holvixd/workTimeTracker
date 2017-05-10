package com.example.vilho.worktimetracker;

import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The FormActivity activity shows a form that user can fill with workdata.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   1.0
 */
public class FormActivity extends AppCompatActivity {
    EditText ed1;
    String ed1Text;
    EditText ed2;
    String ed2Text;
    EditText ed3;
    String ed3Text;
    TextView tv1;
    String tv1Text;
    TextView tv2;
    String tv2Text;
    TextView tv3;
    String tv3Text;
    TextView tv4;
    String tv4Text;

    /**
     * Creates the view.
     *
     *
     * @param savedInstanceState        Saved states
     * @since                           1.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ed1=(EditText) findViewById(R.id.edName);
        ed2=(EditText) findViewById(R.id.edWork);
        ed3=(EditText) findViewById(R.id.edCompany);
        tv1=(TextView) findViewById(R.id.startTime);
        tv2=(TextView) findViewById(R.id.startDate);
        tv3=(TextView) findViewById(R.id.endTime);
        tv4=(TextView) findViewById(R.id.endDate);
        ed1.setText(getIntent().getStringExtra("name"));
    }

    /**
     * Shows the starting timepicker.
     *
     *
     * @param v       Current view
     * @since         1.0
     */
    public void showStartTimePickerDialog(View v) {
        DialogFragment newFragment = new StartTimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * Shows the ending timepicker.
     *
     *
     * @param v       Current view
     * @since         1.0
     */
    public void showEndTimePickerDialog(View v) {
        DialogFragment newFragment = new EndTimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * Shows the starting datepicker.
     *
     *
     * @param v       Current view
     * @since         1.0
     */
    public void showDateStartPickerDialog(View v) {
        DialogFragment newFragment = new DateStartPickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Shows the ending datepicker.
     *
     *
     * @param v       Current view
     * @since         1.0
     */
    public void showDateEndPickerDialog(View v) {
        DialogFragment newFragment = new DateEndPickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Prepares the data to be ready for submitting.
     *
     *
     * @param v       Current view
     * @since         1.0
     */
    public void submit(View v) {
        boolean success = checkInputs();
        if(ed2.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
            builder.setMessage("Please give a subject to your work.").setNegativeButton("Ok",null).create().show();
        }
        else if(success){
            ed1Text = ed1.getText().toString();
            ed2Text = ed2.getText().toString();
            ed3Text = ed3.getText().toString();
            tv1Text = tv1.getText().toString();
            tv2Text = tv2.getText().toString();
            tv3Text = tv3.getText().toString();
            tv4Text = tv4.getText().toString();
            new SendWork().execute();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
            builder.setMessage("Please select all times and dates.").setNegativeButton("Ok",null).create().show();
        }

    }

    /**
     * Checks the data to be ready for submitting.
     *
     *
     * @since         1.0
     */
    public boolean checkInputs(){
        boolean success = true;
        if(tv1.getText().toString().contains("t")){
            success = false;
        }
        if(tv2.getText().toString().contains("t")){
            success = false;
        }
        if(tv3.getText().toString().contains("t")){
            success = false;
        }
        if(tv4.getText().toString().contains("t")){
            success = false;
        }
        return success;
    }


    /**
     * The SendWork class has the conversation with
     * backend to post workdata to the database.
     *
     * @author  Vilho Stenman
     * @version 4.0
     * @since   1.0
     */
    private class SendWork extends AsyncTask<URL, String, String> {

        /**
         * Posts data to backend.
         *
         * @param params         Url addresses
         * @return               Sent json as string
         * @since                1.0
         */
        @Override
        protected String doInBackground(URL... params) {

            String workForm="{name: \""+ed1Text
                    +"\", subject: \""+ed2Text
                    +"\", company: \""+ed3Text
                    +"\", startTime: \""+tv1Text
                    +"\", startDate: \""+tv2Text
                    +"\", endTime: \""+tv3Text
                    +"\", endDate: \""+tv4Text
                    +"\", userName: \""+getIntent().getStringExtra("userName")+"\"}";
            try {
                JSONObject workObj = new JSONObject(workForm);
                URL url = new URL("http://46.101.111.83:8008/workForm");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setChunkedStreamingMode(0);
                urlConnection.connect();
                DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
                out.writeBytes(workObj.toString());
                out.flush();
                out.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(FormActivity.this,"Work succesfully submitted",Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }  catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return workForm;
        }
    }
}
