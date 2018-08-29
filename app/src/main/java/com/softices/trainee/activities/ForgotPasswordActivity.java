package com.softices.trainee.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softices.trainee.R;

import static com.softices.trainee.methods.L.isValidPassword;

public class ForgotPasswordActivity extends AppCompatActivity {

    public EditText edt_newPassword, edt_confirmPassword;
    public Button resetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();
        resetPassword();
    }

    public void init() {
        edt_newPassword = (EditText) findViewById(R.id.edt_new_password);
        edt_confirmPassword = (EditText) findViewById(R.id.edt_new_confirm_password);
        resetPassword = (Button) findViewById(R.id.btn_reset_password);
    }

    public void resetPassword() {
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValidPassword(edt_newPassword.getText().toString())) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter valid " +
                            "Password", Toast.LENGTH_LONG).show();
                } else if (!isValidPassword(edt_confirmPassword.getText().toString())) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter valid " +
                            "Confirm Password", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Password Reset!",
                            Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
