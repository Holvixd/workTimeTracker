package com.example.vilho.worktimetracker;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The RegisterActivity activity is used to register in to the app.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   4.0
 */
public class RegisterActivity extends AppCompatActivity {
    EditText eName;
    EditText eUserName;
    EditText ePassword;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        eName = (EditText) findViewById(R.id.regName);
        eUserName = (EditText) findViewById(R.id.regUserName);
        ePassword = (EditText) findViewById(R.id.regPassword);
        button = (Button) findViewById(R.id.regButton);

    }

    public void onClick(View v){
        String name = eName.getText().toString();
        String userName = eUserName.getText().toString();
        String password = ePassword.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        finish();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("Register failed, username already in use").setNegativeButton("Retry",null).create().show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener =new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("Register server is down. Try again later.").setNegativeButton("Ok",null).create().show();

            }
        };

        RegisterRequest registerRequest = new RegisterRequest(name,userName,password, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);



    }
}
