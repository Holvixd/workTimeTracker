package com.example.vilho.worktimetracker;

import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


public class CalendarActivity extends AppCompatActivity {

    MaterialCalendarView calendar;
    int year1;
    int month1;
    int day1;
    String response1;
    TextView tv;
    TextView tv2;
    TextView tv3;
    JSONObject obj;
    List<Date> dates;
    private final String USER_AGENT = "AndroidDevice";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendar = (MaterialCalendarView) findViewById(R.id.calendar);
        tv = (TextView) findViewById(R.id.calendarTextName);
        tv2 = (TextView) findViewById(R.id.calendarTextSubject);
        tv3 = (TextView) findViewById(R.id.calendarTextStartTime);
        calendar.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        //calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        dates = new ArrayList<>();
        setSelectedDates();
        OnDateSelectedListener listener3 = new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year=date.getYear();
                int month=date.getMonth()+1;
                int dayOfMonth=date.getDay();
                Toast.makeText(CalendarActivity.this,dayOfMonth+"."+month+"."+year,Toast.LENGTH_SHORT).show();
                year1=year;
                month1=month;
                day1=dayOfMonth;
                tv.setText("");
                tv2.setText("");
                tv3.setText("");
                new FetchWork().execute();
                setSelectedDates();
            }
        };

        calendar.setOnDateChangedListener(listener3);
    }

    public void setSelectedDates(){
        //new FetchDates().execute();
        Date date = new Date();
        date.setYear(2017-1900);
        date.setMonth(5-1);
        date.setDate(2);
        calendar.setSelectedDate(date);
        Toast.makeText(CalendarActivity.this,calendar.getSelectedDate().toString(),Toast.LENGTH_SHORT).show();
    }


    private class FetchWork extends AsyncTask<URL, String, String> {
        @Override
        protected String doInBackground(URL... params) {

            try {
                URL url = new URL("http://192.168.8.102:8080/workForm/"+day1+"."+month1+"."+year1+".");
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

    private class FetchDates extends AsyncTask<URL, String, String> {
        @Override
        protected String doInBackground(URL... params) {

            try {
                URL url = new URL("http://192.168.8.102:8080/dates");
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
