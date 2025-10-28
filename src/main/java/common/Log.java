package common;

import org.apache.log4j.Logger;

public final class Log {
    private static final Logger logger = Logger.getLogger(Log.class);

    private Log() {}

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }
}
