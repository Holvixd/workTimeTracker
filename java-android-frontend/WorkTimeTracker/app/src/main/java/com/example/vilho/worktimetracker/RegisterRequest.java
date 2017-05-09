package com.example.vilho.worktimetracker;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * The RegisterRequest class is used when making a register request object in RegisterActivity.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   4.0
 */

public class RegisterRequest extends StringRequest{

    private static final String REGISTER_REQUEST_URL = "http://koti.tamk.fi/~c4vstenm/Register2.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, String password, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, errorListener);
        params=new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
