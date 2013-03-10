
import java.io.File;

public class Utils {

    public static void die(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static void ok(String message) {
        System.err.println(message);
        System.exit(0);
    }

    public static String getSelfDir() {
        String filePath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File fileInfo = new File(filePath);
        return fileInfo.getParent();
    }

}

