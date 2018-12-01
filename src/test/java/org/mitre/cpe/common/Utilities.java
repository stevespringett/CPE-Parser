package org.mitre.cpe.common;

import java.text.ParseException;

/**
 * A collection of utility functions for use with the org.mitre.cpe.matching and
 * org.mitre.cpe.naming packages.
 * 
 * @see <a href="http://cpe.mitre.org">cpe.mitre.org</a> for more information. 
 * @author Joshua Kraunelis
 * @email jkraunelis@mitre.org
 */
public class Utilities {

    /**
     * Concatenates an arbitrary number of strings, in the given order.
     * @param strings strings to be concatenated
     * @return a single string representing all the arguments, concatenated  
     */
    public static String strcat(String... strings) {
        String s = "";
        for (int i = 0; i != strings.length; i++) {
            s = s.concat(strings[i]);
        }
        return s;
    }

    /**
     * Extracts the characters between b and e, from the string s.
     * @param s the string which the substring should be extracted from     
     * @param b beginning index
     * @param e ending index
     * @return the characters between index b and index e
     */
    public static String substr(String s, int b, int e) {
        return s.substring(b, e);
    }

    /**
     * Returns the number of characters in the given string.
     * @param s the string 
     * @return the number of characters in s
     */
    public static int strlen(String s) {
        return s.length();
    }

    /**
     * Searches a string for a character starting at a given offset.  
     * Returns the offset of the character if found, -1 if not found.
     * 
     * @param s string to be searched
     * @param chr character to search for
     * @param off offset to start at
     * @return the integer offset of the character, or -1
     */
    public static int strchr(String s, String chr, int off) {
        return s.indexOf(chr, off);
    }

    /**
     * Converts all alphabetic characters in a String to lowercase.
     * @param s string to convert to lowercase
     * @return lowercase version of s
     */
    public static String toLowercase(String s) {
        return s.toLowerCase();
    }

    /**
     * Searches a string for the first occurrence of another string, starting 
     * at a given offset.  
     * @param str1 String to search.
     * @param str2 String to search for.
     * @param off Integer offset or -1 if not found.
     */
    public static int indexOf(String str1, String str2, int off) {
        return str1.indexOf(str2, off);
    }

    /**
     * Searches string for special characters * and ?
     * @param string String to be searched
     * @return true if string contains wildcard, false otherwise
     */
    public static boolean containsWildcards(String string) {
        if (string.contains("*") || string.contains("?")) {
            if (!(string.contains("\\"))) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Checks if given number is even or not
     * @param num number to check
     * @return true if number is even, false if not
     */
    public static boolean isEvenNumber(int num) {
        if (num % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Counts the number of escape characters in the string beginning and ending
     * at the given indices
     * @param str string to search
     * @param start beginning index
     * @param end ending index
     * @return number of escape characters in string
     */
    public static int countEscapeCharacters(String str, int start, int end) {
        int result = 0;
        boolean active = false;
        int i = 0;
        while (i < end) {
            if (active && (i >= start)) {
                result = result + 1;
            }
            i = i + 1;
        }
        return result;
    }

    /**
     * Searches a string for the first unescaped colon and returns the index of
     * that colon
     * @param str string to search
     * @return index of first unescaped colon, or 0 if not found
     */
    public static int getUnescapedColonIndex(String str) {
        boolean found = false;
        int colon_idx = 0;
        int start_idx = 0;
        // Find the first non-escaped colon.
        while (!found) {
            colon_idx = str.indexOf(":", start_idx + 1);
            // If no colon is found, return 0.
            if (colon_idx == -1) {
                return 0;
            }
            // Peek at character before colon.
            if ((str.substring(colon_idx - 1, colon_idx)).equals("\\")) {
                // If colon is escaped, keep looking.
                start_idx = colon_idx;
            } else {
                found = true;
            }
        }
        return colon_idx;
    }

    /**
     * Returns true if the string contains only 
     * alphanumeric characters or the underscore character,
     * false otherwise.
     * @param c the string in question
     * @return true if c is alphanumeric or underscore, false if not
     */
    public static boolean isAlphanum(String c) {
        if (c.matches("[a-zA-Z0-9]") || c.equals("_")) {
            return true;
        }
        return false;
    }

    /**
     * Returns a copy of the given string in reverse order
     * @param s the string to be reversed
     * @return a reversed copy of s
     */
    public static String reverse(String s) {
        return new StringBuffer(s).reverse().toString();
    }

    /**
     * This function is not part of the reference implementation pseudo code 
     * found in the CPE 2.3 specification.  It enforces two rules in the 
     * specification: 
     *   URI must start with the characters "cpe:/"
     *   A URI may not contain more than 7 components
     * If either rule is violated, a ParseException is thrown.
     */
    public static void validateURI(String in) throws ParseException {
        // make sure uri starts with cpe:/
        if (!in.toLowerCase().startsWith("cpe:/")) {
            throw new ParseException("Error: URI must start with 'cpe:/'.  Given: " + in, 0);
        }
        // make sure uri doesn't contain more than 7 colons
        int count = 0;
        for (int i = 0; i != in.length(); i++) {
            if (in.charAt(i) == ':') {
                count++;
            }
        }
        if (count > 7) {
            throw new ParseException("Error parsing URI.  Found " + (count - 7) + " extra components in: " + in, 0);
        }
    }

    /**
     * This function is not part of the reference implementation pseudo code 
     * found in the CPE 2.3 specification.  It enforces three rules found in the
     * specification:
     *    Formatted string must start with the characters "cpe:2.3:"
     *    A formatted string must contain 11 components
     *    A formatted string must not contain empty components
     * If any rule is violated, a ParseException is thrown.
     */
    public static void validateFS(String in) throws ParseException {
        if (!in.toLowerCase().startsWith("cpe:2.3:")) {
            throw new ParseException("Error: Formatted String must start with \"cpe:2.3\". Given: " + in, 0);
        }
        // make sure fs contains exactly 12 unquoted colons
        int count = 0;
        for (int i = 0; i != in.length(); i++) {
            if (in.charAt(i) == ':') {
                if (in.charAt(i - 1) != '\\') {
                    count++;
                }
                if ((i + 1) < in.length() && in.charAt(i + 1) == ':') {
                    throw new ParseException("Error parsing formatted string.  Found empty component", 0);
                }
            }
        }
        if (count > 12) {
            int extra = (count - 12);
            StringBuffer s = new StringBuffer("Error parsing formatted string.  Found " + extra + " extra component");
            if (extra > 1) {
                s.append("s");
            }
            s.append(" in: " + in);
            throw new ParseException(s.toString(), 0);
        }
        if (count < 12) {
            int missing = (12 - count);
            StringBuffer s = new StringBuffer("Error parsing formatted string. Missing " + missing + " component");
            if (missing > 1) {
                s.append("s");
            }
            throw new ParseException(s.toString(), 0);
        }
    }
}
