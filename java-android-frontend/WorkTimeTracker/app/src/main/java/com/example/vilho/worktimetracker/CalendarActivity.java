package com.example.vilho.worktimetracker;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class CalendarActivity extends AppCompatActivity {

    CalendarView calendar;
    int year1;
    int month1;
    int day1;
    String response1;
    TextView tv;
    TextView tv2;
    TextView tv3;
    JSONObject obj;
    private final String USER_AGENT = "AndroidDevice";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendar = (CalendarView) findViewById(R.id.calendar);
        tv = (TextView) findViewById(R.id.calendarTextName);
        tv2 = (TextView) findViewById(R.id.calendarTextSubject);
        tv3 = (TextView) findViewById(R.id.calendarTextStartTime);
        CalendarView.OnDateChangeListener listener = new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(CalendarActivity.this,dayOfMonth+"."+month+"."+year,Toast.LENGTH_SHORT).show();
                year1=year;
                month1=month+1;
                day1=dayOfMonth;
                tv.setText("");
                tv2.setText("");
                tv3.setText("");
                new FetchWork().execute();
            }
        };
        calendar.setOnDateChangeListener(listener);
    }




    private class FetchWork extends AsyncTask<URL, String, String> {
        @Override
        protected String doInBackground(URL... params) {

            try {
                URL url = new URL("http://192.168.8.103:8080/workForm/"+day1+"."+month1+"."+year1+".");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("User-Agent", USER_AGENT);

                int responseCode = urlConnection.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                urlConnection.connect();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                final StringBuffer response = new StringBuffer();



                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);

                }

                in.close();

                response1=response.toString();

                obj = new JSONObject(response1);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            tv.setText("Name: "+obj.getString("name").toString());
                            tv2.setText("Subject: "+obj.getString("subject").toString());
                            tv3.setText("StartTime: "+obj.getString("startTime").toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }  catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (JSONException e) {
                e.printStackTrace();
            }

            return "";
        }
    }

}
