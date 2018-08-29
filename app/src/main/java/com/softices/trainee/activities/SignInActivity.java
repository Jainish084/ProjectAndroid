package com.softices.trainee.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softices.trainee.R;
import com.softices.trainee.database.DbHelper;

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
                clickedSignIn();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}