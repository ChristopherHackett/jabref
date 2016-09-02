package net.sf.jabref.model.util;


public class ModelStringUtil {

    // Non-letters which are used to denote accents in LaTeX-commands, e.g., in {\"{a}}
    public static final String SPECIAL_COMMAND_CHARS = "\"`^~'=.|";


    public static String booleanToBinaryString(boolean expression) {
        return expression ? "1" : "0";
    }

    /**
     * Quote special characters.
     *
     * @param toQuote         The String which may contain special characters.
     * @param specials  A String containing all special characters except the quoting
     *                  character itself, which is automatically quoted.
     * @param quoteChar The quoting character.
     * @return A String with every special character (including the quoting
     * character itself) quoted.
     */
    public static String quote(String toQuote, String specials, char quoteChar) {
        if (toQuote == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        char c;
        boolean isSpecial;
        for (int i = 0; i < toQuote.length(); ++i) {
            c = toQuote.charAt(i);

            isSpecial = (c == quoteChar);
            // If non-null specials performs logic-or with specials.indexOf(c) >= 0
            isSpecial |= ((specials != null) && (specials.indexOf(c) >= 0));

            if (isSpecial) {
                result.append(quoteChar);
            }
            result.append(c);
        }
        return result.toString();
    }
}
