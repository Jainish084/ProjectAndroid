package com.softices.trainee.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.softices.trainee.R;
import com.softices.trainee.database.DbHelper;

public class SplashActivity extends AppCompatActivity {

    public DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isUserLogin()) {
                    Intent intent = new Intent(SplashActivity.this, Dashboard.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this,
                            SignInActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 1000);
    }

    private boolean isUserLogin() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("is_login", false);
    }
}
