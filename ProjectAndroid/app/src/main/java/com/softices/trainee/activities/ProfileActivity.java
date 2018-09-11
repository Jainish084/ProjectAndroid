package com.softices.trainee.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.softices.trainee.R;
import com.softices.trainee.database.DbHelper;
import com.softices.trainee.models.AppModel;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText firstName, lastName, profileEmail, mobileNumber;
    public RadioGroup radioGroup;
    public RadioButton radioBtnMale, radioBtnFemale;
    public Button btnSave;
    public DbHelper dbHelper;
    private String firstname, lastname, profileemail, mobilenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();

        setDefulatData();
    }

    public void init() {
        firstName = (EditText) findViewById(R.id.profile_edt_first_name);
        lastName = (EditText) findViewById(R.id.profile_edt_last_name);
        profileEmail = (EditText) findViewById(R.id.profile_edt_email);
        mobileNumber = (EditText) findViewById(R.id.profile_edt_mobile_number);
        radioGroup = (RadioGroup) findViewById(R.id.profile_radio_group);
        radioBtnMale = (RadioButton) findViewById(R.id.radio_btn_male);
        radioBtnFemale = (RadioButton) findViewById(R.id.radio_btn_female);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        firstname = firstName.getText().toString();
        lastname = lastName.getText().toString();
        profileemail = profileEmail.getText().toString();
        mobilenumber = mobileNumber.getText().toString();
        dbHelper = new DbHelper(this);
    }

    public void setDefulatData() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        String email = sharedPreferences.getString("email", "");
        Cursor cursor = dbHelper.readUserData(email);
        if (cursor.moveToFirst()) {
            do {
                firstname = cursor.getString(cursor.getColumnIndex
                        (dbHelper.USER_TABLE_COLUMN_FIRST_NAME));
                lastname = cursor.getString(cursor.getColumnIndex
                        (dbHelper.USER_TABLE_COLUMN_LAST_NAME));
                profileemail = cursor.getString(cursor.getColumnIndex
                        (dbHelper.USER_TABLE_COLUMN_EMAIL));
                mobilenumber = cursor.getString(cursor.getColumnIndex
                        (dbHelper.USER_TABLE_COLUMN_MOBILE_NUMBER));

            } while (cursor.moveToNext());
        }
        cursor.close();
        firstName.setText(firstname);
        lastName.setText(lastname);
        profileEmail.setText(profileemail);
        mobileNumber.setText(mobilenumber);
        if (radioGroup.equals("Male")) {
            radioBtnMale.setChecked(true);
        } else if (radioGroup.equals("Female")) {
            radioBtnFemale.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                saveUserData();
                break;
            default:
                break;
        }
    }

    private void saveUserData() {
        AppModel user = new AppModel();
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setEmail(profileEmail.getText().toString());
        user.setMobileNumber(mobileNumber.getText().toString());
        boolean isUpdate = dbHelper.updateUser(user);
        if (isUpdate){
            Toast.makeText(ProfileActivity.this,"Data is Updated",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(ProfileActivity.this,"Data is not Updated",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}