package com.moslemasaad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldsValidator {
    private static final String AGE_PATTERN = "\\d*";
    private static final Pattern ageAndSalaryPattern = Pattern.compile(AGE_PATTERN);

    private static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidNumber(String input) {
        if (input == null) return false;
        return ageAndSalaryPattern.matcher(input).matches();
    }
}
