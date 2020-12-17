package acname.ac.util.data;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public final class HWID {

    public static final String HWID = getHWID();

    private static String getHWIDwithoutEncoding() {
        return System.getProperty("user.name") + System.getProperty("os.version") + System.getProperty("os.name") + System.getProperty("os.arch");
    }

    public static String getHWID() {
        return stringToHex(getHWIDwithoutEncoding());
    }

    public static String stringToHex(String base) {
        return String.format("%x", new BigInteger(1, base.getBytes(StandardCharsets.ISO_8859_1)));
    }

}
