package com.softices.trainee.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.softices.trainee.models.AppModel;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static String CREATE_TABLE_USER;

    //    Database name
    static String DATABASE_NAME = "TraineeDB";

    //    Talble name
    public static final String TABLE_NAME = "TABLE_USER";

    //      Fields for table
    public static final String USER_TABLE_COLUMN_FIRST_NAME = "user_first_name";
    public static final String USER_TABLE_COLUMN_LAST_NAME = "user_last_name";
    public static final String USER_TABLE_COLUMN_EMAIL = "user_email";
    public static final String USER_TABLE_COLUMN_MOBILE_NUMBER = "user_mobile_number";
    public static final String USER_TABLE_COLUMN_GENDER = "user_gender";
    public static final String USER_TABLE_COLUMN_PASSWORD = "user_password";

    //      Required resorces to manage database
    public static ContentValues cValues;
    private SQLiteDatabase db = null;
    private Cursor cursor;

    public DbHelper(Context context) {
        super(context, context.getExternalFilesDir(null).getAbsolutePath()
                + "/" + DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CREATE_TABLE_USER = "CREATE TABLE " + TABLE_NAME + "("
                + USER_TABLE_COLUMN_FIRST_NAME + " TEXT ,"
                + USER_TABLE_COLUMN_LAST_NAME + " TEXT , "
                + USER_TABLE_COLUMN_EMAIL + " TEXT PRIMARY KEY ,"
                + USER_TABLE_COLUMN_MOBILE_NUMBER + " LONG ,"
                + USER_TABLE_COLUMN_GENDER + " TEXT ,"
                + USER_TABLE_COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_TABLE_USER);
        System.out.println("Table is created...........................!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertDataIntoDatabase(String firstName, String lastName, String email,
                                          String mobileNumber, String gender, String password) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(USER_TABLE_COLUMN_FIRST_NAME, firstName);
            contentValues.put(USER_TABLE_COLUMN_LAST_NAME, lastName);
            contentValues.put(USER_TABLE_COLUMN_EMAIL, email);
            contentValues.put(USER_TABLE_COLUMN_MOBILE_NUMBER, mobileNumber);
            contentValues.put(USER_TABLE_COLUMN_GENDER, gender);
            contentValues.put(USER_TABLE_COLUMN_PASSWORD, password);

            db.insert(TABLE_NAME, null, contentValues);
            db.close();
            return true;
        } catch (Exception e) {
            Log.e("insertRecord", e + "");
            return false;
        }
    }

    public boolean checkUser(String email, String password) {
        // array of columns to fetch
        String[] columns = {
                USER_TABLE_COLUMN_EMAIL, USER_TABLE_COLUMN_PASSWORD
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = USER_TABLE_COLUMN_EMAIL + " = ? " + "AND "
                + USER_TABLE_COLUMN_PASSWORD + " = ? ";
        // selection argument
        String[] selectionArgs = {email, password};
        // query user table with condition
        /**
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor readUserData(String email) {
        String[] columns = {
                USER_TABLE_COLUMN_FIRST_NAME,
                USER_TABLE_COLUMN_LAST_NAME,
                USER_TABLE_COLUMN_EMAIL,
                USER_TABLE_COLUMN_MOBILE_NUMBER,
                USER_TABLE_COLUMN_GENDER
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = USER_TABLE_COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        return cursor;
    }

    public boolean updateUser(AppModel email) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(USER_TABLE_COLUMN_FIRST_NAME, email.getFirstName());
            values.put(USER_TABLE_COLUMN_LAST_NAME, email.getLastName());
            values.put(USER_TABLE_COLUMN_MOBILE_NUMBER, email.getMobileNumber());
            // updating row
            db.update(TABLE_NAME, values, USER_TABLE_COLUMN_EMAIL + " = ? ",
                    new String[]{String.valueOf(email.getEmail())});
            db.close();
            return true;
        } catch (Exception e) {
            Log.e("Update User", e + "");
            return false;
        }
    }

    public List<AppModel> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                USER_TABLE_COLUMN_EMAIL
        };
        // sorting orders
        String sortOrder =
                USER_TABLE_COLUMN_EMAIL + " ASC";
        List<AppModel> userList = new ArrayList<AppModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AppModel user = new AppModel();
                user.setEmail(cursor.getString(cursor.getColumnIndex(USER_TABLE_COLUMN_EMAIL)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return userList;
    }

    public boolean deleteUser(String email) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            // delete user record by id
            db.delete(TABLE_NAME, USER_TABLE_COLUMN_EMAIL + " = ?",
                    new String[]{email});
            db.close();
            return true;
        } catch (Exception e) {
            Log.e("DeleteRecord", e + "");
        }
        return false;
    }
}