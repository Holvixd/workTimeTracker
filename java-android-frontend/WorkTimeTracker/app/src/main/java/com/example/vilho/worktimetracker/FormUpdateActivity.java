package com.example.vilho.worktimetracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class FormUpdateActivity extends AppCompatActivity {
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
    Button update;
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
        update = (Button) findViewById(R.id.submitWork);
        update.setText("Update work");
        ed1.setText(getIntent().getStringExtra("name"));
        ed2.setText(getIntent().getStringExtra("company"));
        ed3.setText(getIntent().getStringExtra("subject"));
        tv1.setText(getIntent().getStringExtra("startTime"));
        tv2.setText(getIntent().getStringExtra("startDate"));
        tv3.setText(getIntent().getStringExtra("endTime"));
        tv4.setText(getIntent().getStringExtra("endDate"));


    }

    public void showStartTimePickerDialog(View v) {
        DialogFragment newFragment = new StartTimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    public void showEndTimePickerDialog(View v) {
        DialogFragment newFragment = new EndTimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    public void showDateStartPickerDialog(View v) {
        DialogFragment newFragment = new DateStartPickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void showDateEndPickerDialog(View v) {
        DialogFragment newFragment = new DateEndPickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void submit(View v) {
        ed1Text = ed1.getText().toString();
        ed2Text = ed2.getText().toString();
        ed3Text = ed3.getText().toString();
        tv1Text = tv1.getText().toString();
        tv2Text = tv2.getText().toString();
        tv3Text = tv3.getText().toString();
        tv4Text = tv4.getText().toString();
        new SendWork().execute();

    }



    private class SendWork extends AsyncTask<URL, String, String> {
        @Override
        protected String doInBackground(URL... params) {

            String workForm="{name: \""+ed1Text
                    +"\", subject: \""+ed2Text
                    +"\", company: \""+ed3Text
                    +"\", startTime: \""+tv1Text
                    +"\", startDate: \""+tv2Text
                    +"\", endTime: \""+tv3Text
                    +"\", endDate: \""+tv4Text+"\"}";
            try {
                JSONObject workObj = new JSONObject(workForm);
                URL url = new URL("http://192.168.8.103:8080/update/"+getIntent().getLongExtra("id",0));
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
                         Toast.makeText(FormUpdateActivity.this,"Work succesfully updated",Toast.LENGTH_SHORT).show();
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
