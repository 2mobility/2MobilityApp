package com.mobility.a2mobilityapp.project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorEmail {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public boolean isEmail(final String email) {
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
