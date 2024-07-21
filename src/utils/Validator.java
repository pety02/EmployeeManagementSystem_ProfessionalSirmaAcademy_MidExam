package utils;

/**
 * Provides utility methods for validating strings based on regular expressions.
 */
public class Validator {

    /**
     * Checks if a given string matches a specified regular expression.
     * This method is used for validating input data against a pattern
     * defined by a regular expression.
     *
     * @param field the string to be validated
     * @param regex the regular expression pattern that the string must match
     * @return true if the string is not null and matches the regular expression;
     * false otherwise
     */
    public static boolean isValid(String field, String regex) {
        return field != null && field.matches(regex);
    }
}