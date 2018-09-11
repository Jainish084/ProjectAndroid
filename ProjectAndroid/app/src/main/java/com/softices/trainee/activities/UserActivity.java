package com.softices.trainee.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.softices.trainee.R;
import com.softices.trainee.adapter.UserAdapter;
import com.softices.trainee.database.DbHelper;

public class UserActivity extends AppCompatActivity {

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.email_list);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DbHelper(this);

        UserAdapter userAdapter = new UserAdapter(this, dbHelper, dbHelper.getAllUser());
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
