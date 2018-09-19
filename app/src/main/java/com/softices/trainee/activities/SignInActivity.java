package com.softices.trainee.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.softices.trainee.R;
import com.softices.trainee.database.DbHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.softices.trainee.methods.L.isValidEmail;
import static com.softices.trainee.methods.L.isValidPassword;
import static com.softices.trainee.sharedpreferences.AppPreferences.savePreferences;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText edtEmail;
    public EditText edtPassword;
    public TextView txtForgotPassword;
    public Button btnSignIn;
    public TextView txtCreateAccount;
    public DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        init();
    }

    private void init() {
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        txtForgotPassword = (TextView) findViewById(R.id.txt_forgot_password);
        btnSignIn = (Button) findViewById(R.id.btn_signin);
        txtCreateAccount = (TextView) findViewById(R.id.txt_creat_account);
        txtForgotPassword.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        txtCreateAccount.setOnClickListener(this);
        dbHelper = new DbHelper(this);
        edtEmail.setText("aaa@aaa.aaa");
        edtPassword.setText("123456");
    }

    public void clickedSignIn() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (!isValidEmail(edtEmail.getText().toString())) {
            Toast.makeText(SignInActivity.this, "Please Enter Valid Email!",
                    Toast.LENGTH_LONG).show();
        } else if (!isValidPassword(edtPassword.getText().toString())) {
            Toast.makeText(SignInActivity.this, "Please Enter Valid Password",
                    Toast.LENGTH_LONG).show();
        } else if (dbHelper.checkUser(email, password)) {
            savePreferences(SignInActivity.this, true, edtEmail.getText().toString());
            Intent intent = new Intent(SignInActivity.this, Dashboard.class);
            startActivity(intent);
            finishAffinity();
        } else {
            Toast.makeText(SignInActivity.this, "Invalid Email or Password!",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_forgot_password:
                Intent forgotPassword = new Intent(SignInActivity.this,
                        ForgotPasswordActivity.class);
                startActivity(forgotPassword);
            case R.id.btn_signin:
//                clickedSignIn();
                getMethod();
                break;
            case R.id.txt_creat_account:
                Intent createAccount = new Intent(SignInActivity.this,
                        SignUpActivity.class);
                startActivity(createAccount);
                break;
            default:
                break;
        }
    }

    private void getMethod() {
        RequestQueue queue = Volley.newRequestQueue(this);  // this = context
        final String url = "https://reqres.in/api/users?page=2";
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.e("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);
    }

    public void postMethod(final String name, final String job) {
        RequestQueue queue = Volley.newRequestQueue(this);  // this = context
        final String url = "https://reqres.in/api/users";
        StringRequest postRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("job", job);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void putMethod(){
        RequestQueue queue = Volley.newRequestQueue(this);  // this = context
        final String url = "https://reqres.in/api/users/2";
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
//                        Log.d("Error.Response", response);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String> ();
                params.put("name", "Alif");
                params.put("domain", "http://itsalif.info");
                return params;
            }
        };
        queue.add(putRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}