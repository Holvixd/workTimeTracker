package com.example.vilho.worktimetracker;

import android.content.Intent;
import android.database.DataSetObserver;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;
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


/**
 * The CalendarActivity activity shows a calendar filled with workdata.
 * It also shows work done on a specific day if its clicked.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   2.0
 */
public class CalendarActivity extends AppCompatActivity {

    MaterialCalendarView calendar;
    String year1;
    String month1;
    String day1;
    String response1;
    String response2;
    TextView tv;
    TextView tv2;
    TextView tv3;
    JSONArray obj;
    JSONArray obj2;
    List<Date> dates;
    List<String> dates2;
    ListView listView;
    ArrayAdapter arrayAdapter;
    List<JSONObject> jsonList;
    List<String> jsonListSubject;
    private final String USER_AGENT = "AndroidDevice";


    /**
     * Creates the view.
     *
     *
     * @param savedInstanceState        Saved states
     * @since                           2.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendar = (MaterialCalendarView) findViewById(R.id.calendar);
        /*
        tv = (TextView) findViewById(R.id.calendarTextName);
        tv2 = (TextView) findViewById(R.id.calendarTextSubject);
        tv3 = (TextView) findViewById(R.id.calendarTextStartTime);
        */
        calendar.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        dates = new ArrayList<>();
        dates2 = new ArrayList<>();
        new FetchDates().execute();
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        jsonList = new ArrayList<>();
        jsonListSubject = new ArrayList<>();
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CalendarActivity.this,ShowWorkActivity.class);
                try {
                    i.putExtras(getIntent().getExtras());
                    i.putExtra("name",jsonList.get(position).getString("name"));
                    i.putExtra("company",jsonList.get(position).getString("company"));
                    i.putExtra("subject",jsonList.get(position).getString("subject"));
                    i.putExtra("startTime",jsonList.get(position).getString("startTime"));
                    i.putExtra("startDate",jsonList.get(position).getString("startDate"));
                    i.putExtra("endTime",jsonList.get(position).getString("endTime"));
                    i.putExtra("endDate",jsonList.get(position).getString("endDate"));
                    i.putExtra("id",jsonList.get(position).getLong("id"));
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        listView.setOnItemClickListener(listener);

        OnDateSelectedListener listener3 = new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year=date.getYear();
                int month=date.getMonth()+1;
                int dayOfMonth=date.getDay();
                if (dayOfMonth<10){
                    day1 = "0"+dayOfMonth;
                }else{
                    day1=dayOfMonth+"";
                }
                if (month<10){
                    month1 = "0"+month;
                }else{
                    month1=month+"";
                }
                if (year<10){
                    year1 = "0"+year;
                }else{
                    year1=year+"";
                }
                new FetchWork().execute();
                setSelectedDates();
            }
        };

        calendar.setOnDateChangedListener(listener3);
    }

    /**
     * Highlights the dates that have been worked on.
     *
     *
     * @since                2.0
     */
    public void setSelectedDates(){
        calendar.clearSelection();
        for(int i = 0; i < dates.size(); i++){
            calendar.setDateSelected(dates.get(i),true);
        }
    }


    /**
     * The FetchWork class has the conversation with
     * backend to get workdata of a specific day.
     *
     * @author  Vilho Stenman
     * @version 4.0
     * @since   2.0
     */
    private class FetchWork extends AsyncTask<URL, String, String> {

        /**
         * Gets data from backend.
         *
         * @param params         Url addresses
         * @since                2.0
         */
        @Override
        protected String doInBackground(URL... params) {

            try {
                URL url = new URL("http://46.101.111.83:8008/workForm/"+getIntent().getStringExtra("userName")+"/"+day1+"."+month1+"."+year1+".");
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
                obj = new JSONArray(response1);
                jsonList.clear();
                jsonListSubject.clear();
                for (int i = 0; i < obj.length(); i++) {
                    jsonList.add(obj.getJSONObject(i));
                    jsonListSubject.add(obj.getJSONObject(i).getString("subject"));
                }
                arrayAdapter = new ArrayAdapter(CalendarActivity.this,R.layout.items,jsonListSubject);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(arrayAdapter);
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

    /**
     * The FetchWork class has the conversation with
     * backend to get dates that have been worked on.
     *
     * @author  Vilho Stenman
     * @version 4.0
     * @since   2.0
     */
    private class FetchDates extends AsyncTask<URL, String, String> {

        /**
         * Gets data from backend.
         *
         * @param params         Url addresses
         * @return               nothing
         * @since                2.0
         */
        @Override
        protected String doInBackground(URL... params) {

            try {
                URL url = new URL("http://46.101.111.83:8008/workForm/");
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

                response2 = response.toString();

                obj2 = new JSONArray(response2);

                for (int i = 0; i < obj2.length(); i++) {
                    if(getIntent().getStringExtra("userName").equals(obj2.getJSONObject(i).getString("userName").toString())){
                        dates2.add(obj2.getJSONObject(i).getString("startDate").toString());
                    }

                }
                for(int i = 0; i < dates2.size(); i++){
                    Date date=new Date();
                    date.setDate(Integer.parseInt(dates2.get(i).substring(0,2)));
                    date.setMonth(Integer.parseInt(dates2.get(i).substring(3,5))-1);
                    date.setYear(Integer.parseInt(dates2.get(i).substring(6,10))-1900);
                    dates.add(date);
                    if(i==dates2.size()-1){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setSelectedDates();
                            }
                        });
                    }
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "";
        }
    }

}
