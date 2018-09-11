package com.softices.trainee.methods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class L {
    public static boolean isValidFirstName(String firstName) {
        Pattern pattern;
        Matcher matcher;
        final String FIRSTNAME_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(FIRSTNAME_PATTERN);
        matcher = pattern.matcher(firstName);
        return matcher.matches();
    }

    public static boolean isValidLastName(String lastName) {
        Pattern pattern;
        Matcher matcher;
        final String LASTNAME_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(LASTNAME_PATTERN);
        matcher = pattern.matcher(lastName);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidMobileNumber(String MobileNumber) {
        Pattern pattern;
        Matcher matcher;
        final String MOBILENUMBER_PATTERN = "[0-9]{10}";
        pattern = Pattern.compile(MOBILENUMBER_PATTERN);
        matcher = pattern.matcher(MobileNumber);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[_A-Za-z0-9-])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}