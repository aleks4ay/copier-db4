package ua.aleks4ay.log;

public class ClassNameUtil {
    private ClassNameUtil() {}

    public static String getCurrentClassName() {
        try {
            throw new RuntimeException();
        }
        catch (RuntimeException e) {
            return e.getStackTrace()[1].getClassName();
        }
    }

    public static String getPreviousClassName() {
        try {
            throw new RuntimeException();
        }
        catch (RuntimeException e) {
            return e.getStackTrace()[2].getClassName();
        }
    }

    public static String getPreviousPreviousClassName() {
        try {
            throw new RuntimeException();
        }
        catch (RuntimeException e) {
            return e.getStackTrace()[3].getClassName();
        }
    }
}
