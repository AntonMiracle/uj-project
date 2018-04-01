package pl.edu.uj.project.core;

/**
 * @author Anton Bondarenko
 * @version 1.0 {@code b.anton.m.1986@gmail.com}
 */
public class Util {
    protected Util() {
    }

    public static void throwIAE(boolean whenTrue, Object classWhereIAE, String methodName, String withMsg) throws IllegalArgumentException {
        withMsg = withMsg != null ? withMsg : "NO MSG";
        withMsg = createName(classWhereIAE, methodName) + " : " + withMsg;
        if (whenTrue) throw new IllegalArgumentException(withMsg);
    }

    public static void throwIAEWhenNull(Object object, Object classWhereIAE, String methodName) throws IllegalArgumentException {
        String msg = createName(classWhereIAE, methodName) + " : Argument is NULL";
        if (object == null) throw new IllegalArgumentException(msg);
    }

    private static String createName(Object classWhereIAE, String methodName) throws IllegalArgumentException {
        String className = classWhereIAE != null ? classWhereIAE.getClass().getName() : ":CLASS NULL:";
        methodName = methodName != null ? methodName : ":METHOD NAME NULL:";
        return className + "#" + methodName;
    }
}
