package pl.edu.uj.project.core.model;

/**
 * @author Anton Bondarenko
 * @version 1.0 {@code b.anton.m.1986@gmail.com}
 */
public class Util {
    protected Util() {
    }

    public static void throwIAE(boolean whenTrue, String withMsg) {
        withMsg = withMsg != null ? withMsg : "";
        if (whenTrue) throw new IllegalArgumentException(withMsg);
    }

    public static void throwIAEWhenNull(Object object) {
        if (object == null) throw new IllegalArgumentException("Argument is NULL");
    }
}
