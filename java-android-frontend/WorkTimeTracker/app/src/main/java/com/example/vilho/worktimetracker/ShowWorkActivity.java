package com.example.vilho.worktimetracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowWorkActivity extends AppCompatActivity {
    TextView showName;
    TextView showCompany;
    TextView showSubject;
    TextView showStartTime;
    TextView showStartDate;
    TextView showEndTime;
    TextView showEndDate;
    private final String USER_AGENT = "AndroidDevice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_work);
        showName = (TextView) findViewById(R.id.showName);
        showCompany = (TextView) findViewById(R.id.showCompany);
        showSubject = (TextView) findViewById(R.id.showSubject);
        showStartTime = (TextView) findViewById(R.id.showStartTime);
        showStartDate = (TextView) findViewById(R.id.showStartDate);
        showEndTime = (TextView) findViewById(R.id.showEndTime);
        showEndDate = (TextView) findViewById(R.id.showEndDate);
        showName.setText(getIntent().getStringExtra("name"));
        showCompany.setText(getIntent().getStringExtra("company"));
        showSubject.setText(getIntent().getStringExtra("subject"));
        showStartTime.setText(getIntent().getStringExtra("startTime"));
        showStartDate.setText(getIntent().getStringExtra("startDate"));
        showEndTime.setText(getIntent().getStringExtra("endTime"));
        showEndDate.setText(getIntent().getStringExtra("endDate"));
    }

    public void goToEdit(View v){
        Intent intent = new Intent(this,FormUpdateActivity.class);
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
    }
    public void goDelete(View v){
        new DeleteWork().execute();

    }

    private class DeleteWork extends AsyncTask<URL, String, String> {
        @Override
        protected String doInBackground(URL... params) {

            try {
                URL url = new URL("http://192.168.8.103:8080/delete/"+getIntent().getLongExtra("id",0));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("DELETE");
                urlConnection.setRequestProperty("User-Agent", USER_AGENT);
                int responseCode = urlConnection.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                urlConnection.connect();
                if(responseCode==200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ShowWorkActivity.this,"Work succesfully deleted",Toast.LENGTH_SHORT).show();
                        }
                    });

                    finish();
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ShowWorkActivity.this,"There was a problem deleting workdata",Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }  catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }
    }
}