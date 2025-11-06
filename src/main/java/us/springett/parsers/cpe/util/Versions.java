package us.springett.parsers.cpe.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Versions {
    private Versions() {}

    /**
     * Method that splits versions for '.', '|', ':' and '-". Then if a token
     * start with a number and then contains letters, or starts with a letter and then contains numbers, it will split it too.
     * For example "12a" is split into ["12", "a"]. This is done to support correct
     * comparison of "5.0.3a", "5.0.9" and "5.0.30".
     *
     * @param s the string to split
     * @return a List of Comparable VersionParts containing the tokens to be compared.
     */
    public static List<VersionPart> splitVersion(String s) {
        if (s == null || s.isEmpty()) {
            return Collections.emptyList();
        }
        VersionParserState token = new VersionParserState();

        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (isSplitter(c)) {
                // Complete the current token; throwing away the current (splitter) char
                token.complete();
            } else if (isDigit(c) && token.mode.numeric()) {
                // Already in a numeric mode, just append
                token.appendDigit(c);
            } else if (isDigit(c) && !token.mode.numeric()) {
                // We're building a letter-based string token (like "rc") and hit a digit - split here
                token.complete();
                token.appendDigit(c);
            } else if (token.mode.numeric()) {
                // Non-digit, non splitter (a letter) in a numeric mode
                // Transition from numeric to letter - split here
                token.complete();
                token.append(c);
            } else {
                // Non-digit, non splitter (a letter) - just append
                token.append(c);
            }
        }
        token.complete();
        return token.parts;
    }

    private static boolean isSplitter(char c) {
        return c == '.' || c == '|' || c == ':' || c == '-';
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private enum VersionParserMode {
        String,
        Integer,
        IntegerAsString;

        boolean numeric() {
            return this == Integer || this == IntegerAsString;
        }

        VersionPart toPart(String val) {
            return this == Integer ? VersionPart.intPart(val) : VersionPart.strPart(val);
        }
    }

    private static class VersionParserState {
        private final List<VersionPart> parts = new ArrayList<>(3);
        private final StringBuilder token = new StringBuilder(3);
        private VersionParserMode mode = VersionParserMode.String;

        boolean empty() {
            return token.length() == 0;
        }

        void append(char c) {
            token.append(c);
        }

        void appendDigit(char c) {
            // When appending digits we switch mode if at the start of a new token
            if (empty()) {
                mode = c == '0' ? VersionParserMode.IntegerAsString : VersionParserMode.Integer;
            }
            append(c);
        }

        void complete() {
            if (!empty()) {
                parts.add(mode.toPart(token.toString()));
            }
            token.setLength(0);
            mode = VersionParserMode.String;
        }
    }

    public static class VersionPart implements Comparable<VersionPart> {
        private final String part;

        private VersionPart(String part) {
            this.part = part;
        }

        boolean compareAsInteger() {
            return false;
        }

        /*
         * Compares one version part to another. Only two integer parts can be compared to one other with numeric semantics
         * otherwise everything is compared as a string. This means, for example:
         *    intPart(1) > strPart(01)
         *    strPart(11) < strPart(9)
         *    intPart(11) > intPart(9)
         */
        @Override
        public int compareTo(VersionPart o) {
            return compareAsInteger() && o.compareAsInteger() ? compareToInteger(o) : part.compareTo(o.part);
        }

        private int compareToInteger(VersionPart o) {
            try {
                return Long.compare(Long.parseLong(part), Long.parseLong(o.part));
            } catch (NumberFormatException ignored) {
                // assume left or right is too big; compare as BigInteger
                return new BigInteger(part).compareTo(new BigInteger(o.part));
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            VersionPart that = (VersionPart) o;
            return compareAsInteger() == that.compareAsInteger() && Objects.equals(part, that.part);
        }

        @Override
        public int hashCode() {
            return Objects.hash(compareAsInteger(), part);
        }

        @Override
        public String toString() {
            return part + (compareAsInteger() ? "(int)" : "");
        }

        static VersionPart strPart(String val) {
            return new VersionPart(val);
        }

        static VersionPart intPart(String val) {
            return new VersionPart(val) {
                @Override
                boolean compareAsInteger() {
                    return true;
                }
            };
        }
    }

}
