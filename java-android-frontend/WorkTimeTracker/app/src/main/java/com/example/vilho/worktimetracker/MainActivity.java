package com.example.vilho.worktimetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * The MainActivity activity is the "homepage" of the app.
 * User can show the calendar, enter new working data or logout from the MainActivity.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   1.0
 */
public class MainActivity extends AppCompatActivity {
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText("Welcome, "+getIntent().getStringExtra("name")+"!");
    }

    public void goToForm(View v){
        Intent i = new Intent(MainActivity.this, FormActivity.class);
        i.putExtras(getIntent().getExtras());
        startActivity(i);
    }

    public void goToCalendar(View v){
        Intent i = new Intent(MainActivity.this, CalendarActivity.class);
        i.putExtras(getIntent().getExtras());
        startActivity(i);
    }
    public void logOut(View v){
        finish();
    }

}
