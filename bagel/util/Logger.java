package bagel.util;

public class Logger {
    public static boolean enabled = true;

    public Logger() {
    }

    public static void info(String information) {
        if(Logger.enabled) {
            System.out.println("INFO: " + information);
        }

    }

    public static void warn(String warning) {
        if(Logger.enabled) {
            System.out.println("WARNING: " + warning);
        }

    }

    public static void error(String error, boolean exit) {
        if(Logger.enabled) {
            System.out.println("ERROR: " + error);
            if(exit) {
                System.exit(-1);
            }
        }

    }

    public static void success(String success) {
        if(Logger.enabled) {
            System.out.println("SUCCESS: " + success);
        }

    }

    public static void enable() {
        Logger.enabled = true;
    }

    public static void disable() {
        Logger.enabled = false;
    }
}
