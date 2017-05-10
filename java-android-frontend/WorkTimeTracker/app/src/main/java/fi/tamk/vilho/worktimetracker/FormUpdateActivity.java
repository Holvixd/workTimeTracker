package fi.tamk.vilho.worktimetracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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

/**
 * The FormActivity activity shows a form filled with workdata that user can edit.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   3.0
 */
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

    /**
     * Creates the view.
     *
     *
     * @param savedInstanceState        Saved states
     * @since                           3.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ed1=(EditText) findViewById(R.id.edName);
        ed2=(EditText) findViewById(R.id.edCompany);
        ed3=(EditText) findViewById(R.id.edWork);
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

    /**
     * Shows the starting timepicker.
     *
     *
     * @param v       Current view
     * @since         3.0
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
     * @since         3.0
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
     * @since         3.0
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
     * @since         3.0
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
     * @since         3.0
     */
    public void submit(View v) {
        if(ed3.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(FormUpdateActivity.this);
            builder.setMessage("Please give a subject to your work.").setNegativeButton("Ok",null).create().show();
        }
        else if(ed2.getText().toString().contains("ä")
                ||ed2.getText().toString().contains("ö")
                ||ed2.getText().toString().contains("å")
                ||ed3.getText().toString().contains("ä")
                ||ed3.getText().toString().contains("ö")
                ||ed3.getText().toString().contains("å")
                ||ed1.getText().toString().contains("ä")
                ||ed1.getText().toString().contains("ö")
                ||ed1.getText().toString().contains("å")){
            AlertDialog.Builder builder = new AlertDialog.Builder(FormUpdateActivity.this);
            builder.setMessage("Sorry the app doesnt support skandinavian letters").setNegativeButton("Ok",null).create().show();
        }
        else{
            ed1Text = ed1.getText().toString();
            ed2Text = ed2.getText().toString();
            ed3Text = ed3.getText().toString();
            tv1Text = tv1.getText().toString();
            tv2Text = tv2.getText().toString();
            tv3Text = tv3.getText().toString();
            tv4Text = tv4.getText().toString();
            new SendWork().execute();
        }
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
         * @since                3.0
         */
        @Override
        protected String doInBackground(URL... params) {

            String workForm="{name: \""+ed1Text
                    +"\", company: \""+ed2Text
                    +"\", subject: \""+ed3Text
                    +"\", startTime: \""+tv1Text
                    +"\", startDate: \""+tv2Text
                    +"\", endTime: \""+tv3Text
                    +"\", endDate: \""+tv4Text
                    +"\", userName: \""+getIntent().getStringExtra("userName")+"\"}";
            try {
                JSONObject workObj = new JSONObject(workForm);
                URL url = new URL("http://46.101.111.83:8008/update/"+getIntent().getLongExtra("id",0));
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
