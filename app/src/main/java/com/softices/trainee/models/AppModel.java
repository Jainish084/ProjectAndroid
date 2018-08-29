package com.softices.trainee.models;

public class AppModel {

    public static String firstName, lastName, email, mobileNumber, gender, password;

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        AppModel.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        AppModel.lastName = lastName;
    }

    public static String getMobileNumber() {
        return mobileNumber;
    }

    public static void setMobileNumber(String mobileNumber) {
        AppModel.mobileNumber = mobileNumber;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        AppModel.email = email;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        AppModel.gender = gender;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        AppModel.password = password;
    }
}
