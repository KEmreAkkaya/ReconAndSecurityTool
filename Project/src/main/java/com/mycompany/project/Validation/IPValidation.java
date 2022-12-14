package com.mycompany.project.Validation;

import java.util.regex.Pattern;

public class IPValidation {

    private static final Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static boolean validate(final String ip) {

        return PATTERN.matcher(ip).matches();
    }
}
