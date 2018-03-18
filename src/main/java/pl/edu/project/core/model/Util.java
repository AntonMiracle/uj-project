package pl.edu.project.core.model;

public class Util {
    public static void throwIAE(boolean when, String withMsg) {
        withMsg = withMsg != null ? withMsg : "";
        if (when) throw new IllegalArgumentException(withMsg);
    }

    public static void throwIAEWhenNull(Object object) {
        if (object == null) throw new IllegalArgumentException("Argument is NULL");
    }
}
