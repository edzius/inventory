
package cli;

import java.io.IOException;

import com.obixlabs.commons.io.InteractiveCommandLineReader;
import com.obixlabs.commons.io.FreeTextConsoleInputHandler;

public class CliTools {

    public static String readCLI(String caption, boolean required) {
        FreeTextConsoleInputHandler hndl = new FreeTextConsoleInputHandler(caption + ": ", "Invalid " + caption + ", repeat: ", !required);
        try {
            InteractiveCommandLineReader.prompt(hndl);
        } catch (IOException e) {
            return null;
        }
        return hndl.getInputValue();
    }

    public static String listCLI(String caption, Selector selector, boolean allowCustom) {
        int i;
        String text;
        FreeTextConsoleInputHandler hndl = new FreeTextConsoleInputHandler(caption + ": ", "Invalid " + caption + ", repeat: ", false);

        while (true) {

            for (i = 0; i < selector.size(); i++) {
                System.out.println(String.format("%d: %s", i + 1, selector.get(i)));
            }
            System.out.println(String.format("*: Custom entry"));

            try {
                InteractiveCommandLineReader.prompt(hndl);
            } catch (IOException e) {
                return null;
            }
            text = hndl.getInputValue();

            try {
                int value = Integer.parseInt(text);
                if (selector.size() < value - 1) {
                    System.err.println(String.format("Invalid index selected %d", value));
                    continue;
                }
                return selector.get(value - 1);
            } catch (Exception e) {}

            if (!allowCustom)
                continue;

            selector.add(text);
            return text;
        }
    }
}

