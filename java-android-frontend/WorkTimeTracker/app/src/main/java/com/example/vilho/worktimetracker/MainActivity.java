package com.example.vilho.worktimetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText("Welcome, "+getIntent().getStringExtra("name")+"!");
    }

    /**
     * Switches to form activity.
     *
     * @param v              Clicked element
     * @since                1.0
     */
    public void goToForm(View v){
        Intent i = new Intent(MainActivity.this, FormActivity.class);
        i.putExtras(getIntent().getExtras());
        startActivity(i);
    }

    /**
     * Switches to calendar activity.
     *
     * @param v              Clicked element
     * @since                2.0
     */
    public void goToCalendar(View v){
        Intent i = new Intent(MainActivity.this, CalendarActivity.class);
        i.putExtras(getIntent().getExtras());
        startActivity(i);
    }

    /**
     * Logs out and switches to login activity.
     *
     * @since                4.0
     */
    public void logOut(){
        finish();
    }

    /**
     * Prepares the menu bar.
     *
     * @param menu           Menu element
     * @since                4.0
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        return true;
    }

    /**
     * Creates the menu bar.
     *
     * @param menu           Menu element
     * @return               A boolean which is always true
     * @since                4.0
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    /**
     * Reacts to a menu item click.
     *
     * @param item           Menu bar item
     * @return               A boolean which is always true
     * @since                4.0
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case (R.id.logout):
                logOut();
                return true;

        }

        return true;
    }

}
