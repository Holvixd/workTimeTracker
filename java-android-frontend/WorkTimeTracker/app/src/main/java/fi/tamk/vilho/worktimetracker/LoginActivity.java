package fi.tamk.vilho.worktimetracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The FormActivity activity is used to log in to the app.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   4.0
 */
public class LoginActivity extends AppCompatActivity {
    EditText eUserName;
    EditText ePassword;
    TextView click;
    Button button;

    /**
     * Creates the view.
     *
     *
     * @param savedInstanceState        Saved states
     * @since                           4.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eUserName = (EditText) findViewById(R.id.logUserName);
        ePassword = (EditText) findViewById(R.id.logPassword);
        button = (Button) findViewById(R.id.logButton);
        click = (TextView) findViewById(R.id.logInfo2);
        click.setTextColor(Color.parseColor("#0000EE"));
    }

    /**
     * Tries to log in the user.
     *
     *
     * @param v        Clicked object
     * @since          4.0
     */
    public void onClick(View v){

        final String username = eUserName.getText().toString();
        final String password = ePassword.getText().toString();
        if(username.contains("ä")||username.contains("ö")||username.contains("å")
                ||password.contains("ä")||password.contains("ö")||password.contains("å")){
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Sorry the app doesnt support skandinavian letters").setNegativeButton("Retry",null).create().show();
        }else{
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if(success){
                            String name = jsonObject.getString("name");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("userName", username);
                            startActivity(intent);
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Invalid username or password").setNegativeButton("Retry",null).create().show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Response.ErrorListener errorListener =new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Login server is down. Try again later.").setNegativeButton("Ok",null).create().show();

                }
            };
            LoginRequest loginRequest = new LoginRequest(username, password, responseListener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }


    }

    /**
     * Switches to register activity.
     *
     *
     * @param v        Clicked object
     * @since          4.0
     */
    public void goToRegister(View v){
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    /**
     * Logs in using the public user.
     *
     *
     * @param v        Clicked object
     * @since          4.0
     */
    public void debug(View v){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("name", "PublicTestUser");
        intent.putExtra("userName", "PublicTestUser");
        startActivity(intent);
    }
}
