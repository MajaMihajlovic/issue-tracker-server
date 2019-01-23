package com.issuetracker.issuetracker.util;


import java.security.Timestamp;
import java.sql.Time;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {


    public static Boolean passwordChecking(String password) {
        if (password != null){
            System.out.println(password);
            String regex = "/^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$/";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(password);

            return matcher.matches();
        }
        return true;
    }
    public static Boolean stringLength(String text, Integer length) {
        if (text != null)
            return Integer.valueOf(text.length()).equals(length);
        return true;
    }

    public static Boolean stringMaxLength(String text, Integer maxLength) {
        if (text != null)
            return Integer.valueOf(text.length()).compareTo(maxLength) <= 0;
        return true;
    }

    public static Boolean binaryMaxLength(byte[] bytes, Long maxLength) {
        if (bytes != null)
            return Long.valueOf(bytes.length).compareTo(maxLength) <= 0;
        return true;
    }


    public static Boolean integerNotNegative(Integer number) {
        if (number != null)
            return number.compareTo(0) >= 0;
        return true;
    }

    public static Boolean doubleNotNegative(Double number) {

        if (number != null)
            return number.compareTo(0.0) >= 0;
        return true;
    }

    public static Integer dateCompare(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            return date1.compareTo(date2);
        }

        return null;
    }

    public static Integer timeCompare(Time time1, Time time2) {
        if (time1 != null && time2 != null) {
            return time1.compareTo(time2);
        }

        return null;
    }

    public static Boolean validateEmail(String email) {
        if (email != null) {
            String regex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);

            return matcher.matches();
        }

        return true;
    }
}
