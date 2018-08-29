package com.softices.trainee.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {
    public static void savePreferences(Context context, boolean login, String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_login", login);
        editor.putString("email", email);
        editor.commit();
    }

    public static void clearPreferences(Context context, boolean login, String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_login", login);
        editor.putString("email", email);
        editor.clear();
        editor.commit();
    }


}
