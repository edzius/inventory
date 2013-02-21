package inv.storage;

import java.util.StringTokenizer;

public abstract class ItemParser {

    private static final float DEFAULT_FLOAT = -1.0f;
    private static final int DEFAULT_INT = -1;
    private static final String DEFAULT_STRING= "";
    private StringTokenizer scanner;

    ItemParser(String line) {
        scanner = new StringTokenizer(line, "|");
    }

    protected int nextInt() {
        String value = nextString();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    protected float nextFloat() {
        String value = nextString();
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }

    protected boolean nextBoolean() {
        String value = nextString();
        if (value.equals("y") || value.equals("X"))
            return true;
        return false;
    }


    protected String nextString() {
        if (!scanner.hasMoreTokens())
            return DEFAULT_STRING;

        String value = scanner.nextToken().trim();
        return value;
    }   

};
