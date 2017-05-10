package com.example.vilho.worktimetracker;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * The LoginRequest class is used when making a login request object in LoginActivity.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   4.0
 */

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://koti.tamk.fi/~c4vstenm/Login2.php";
    private Map<String, String> params;

    /**
     * Constructs the request.
     *
     *
     * @param username       Username provided by user
     * @param password       Password provided by user
     * @param listener       Listens the response given by login server
     * @param errorListener  Listens the errors given by login server
     * @since                4.0
     */
    public LoginRequest(String username, String password, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, errorListener);
        params=new HashMap<>();
        params.put("username",username);
        params.put("password",password);
    }

    /**
     * Maps the params.
     *
     * @return               Mapped params
     * @since                4.0
     */
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
