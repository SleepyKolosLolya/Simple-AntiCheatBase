package acname.ac.util;

import com.google.common.collect.Iterables;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class MathUtil {

    //---------------------------------{ быстрее чем на java 8 }---------------------------------
    private static final double DEGREES_TO_RADIANS = 0.017453292519943295;
    private static final double RADIANS_TO_DEGREES = 57.29577951308232;
    private static final double TWO_MINUS_600 = 0x1.0p-600;
    private static final double TWO_PLUS_600 = 0x1.0p+600;
    private static final int SIGNIFICAND_WIDTH = 53;
    private static final int EXP_BIAS = 1023;
    private static final long EXP_BIT_MASK = 0x7FF0000000000000L;
    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private MathUtil() { /*хуй*/}

    public static float gcd0(float a, float b) {
        return gcd0(((int) a), ((int) b));
    }

    /**
     * - Example: double hypotABC = MathUtil.hypot(3, 5, 6);
     *
     * @param value - an array with double values to produce hypot method
     * @author koloslolya
     */
    public static double hypot(double... value) {
        double total = 0.0D;
        for (double val : value) total += val * val;
        return Math.sqrt(total);
    }

    /**
     * @param limit - Minimum gcd value, after which recursive method returns value
     * @param a     - First value for gcd calculation
     * @param b     - Second value for gcd calculation
     * @author koloslolya
     */
    public static long gcd(long limit, long a, long b) {
        return b <= limit ? a : MathUtil.gcd(limit, b, a % b);
    }

    /**
     * @param iterable - Iterable<? extends Number> from which to get the average
     * @return (double) returns the average of the list
     * @author koloslolya
     */
    public static double average(final Iterable<? extends Number> iterable) {
        long size = Iterables.size(iterable);
        if (size == 0) throw new IllegalArgumentException("Iterable is empty");
        double n = 0.0;
        for (Number number : iterable) n += number.doubleValue();
        return n / size;
    }

    /**
     * Calculates sin with bukkit math helper
     *
     * @param radians - Requires a value in radians
     * @return float sin(radians)
     * @author koloslolya
     */
    public static float sin(float radians) {
        try {
            Class<?> local = ReflectionUtils.getNMSClass("MathHelper");
            assert local != null;
            return (float) local.getMethod("sin", Float.TYPE).invoke(local, radians);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return (float) Math.sin(radians);
    }

    /**
     * Calculates cos with bukkit math helper
     *
     * @param radians - Requires a value in radians
     * @return float cos(radians)
     * @author koloslolya
     */
    public static float cos(float radians) {
        try {
            Class<?> local = ReflectionUtils.getNMSClass("MathHelper");
            assert local != null;
            return (float) local.getMethod("cos", Float.TYPE).invoke(local, radians);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return (float) Math.cos(radians);
    }

    public static strictfp double hypot(double x, double y) {
        double a = Math.abs(x);
        double b = Math.abs(y);

        if (!Double.isFinite(a) || !Double.isFinite(b)) {
            if (a == INFINITY || b == INFINITY) {
                return INFINITY;
            } else {
                return a + b; // Propagate NaN significand bits
            }
        }

        if (b > a) {
            double tmp = a;
            a = b;
            b = tmp;
        }
        assert a >= b;

        // Doing bitwise conversion after screening for NaN allows
        // the code to not worry about the possibility of
        // "negative" NaN values.

        // Note: the ha and hb variables are the high-order
        // 32-bits of a and b stored as integer values. The ha and
        // hb values are used first for a rough magnitude
        // comparison of a and b and second for simulating higher
        // precision by allowing a and b, respectively, to be
        // decomposed into non-overlapping portions. Both of these
        // uses could be eliminated. The magnitude comparison
        // could be eliminated by extracting and comparing the
        // exponents of a and b or just be performing a
        // floating-point divide.  Splitting a floating-point
        // number into non-overlapping portions can be
        // accomplished by judicious use of multiplies and
        // additions. For details see T. J. Dekker, A Floating-Point
        // Technique for Extending the Available Precision,
        // Numerische Mathematik, vol. 18, 1971, pp.224-242 and
        // subsequent work.

        int ha = __HI(a);        // high word of a
        int hb = __HI(b);        // high word of b

        if ((ha - hb) > 0x3c00000) {
            return a + b;  // x / y > 2**60
        }

        int k = 0;
        if (a > 0x1.00000_ffff_ffffp500) {   // a > ~2**500
            // scale a and b by 2**-600
            ha -= 0x25800000;
            hb -= 0x25800000;
            a = a * TWO_MINUS_600;
            b = b * TWO_MINUS_600;
            k += 600;
        }
        double t1, t2;
        if (b < 0x1.0p-500) {   // b < 2**-500
            if (b < Double.MIN_NORMAL) {      // subnormal b or 0 */
                if (b == 0.0) {
                    return a;
                }
                t1 = 0x1.0p1022;   // t1 = 2^1022
                b *= t1;
                a *= t1;
                k -= 1022;
            } else {            // scale a and b by 2^600
                ha += 0x25800000;       // a *= 2^600
                hb += 0x25800000;       // b *= 2^600
                a = a * TWO_PLUS_600;
                b = b * TWO_PLUS_600;
                k -= 600;
            }
        }
        // medium size a and b
        double w = a - b;
        if (w > b) {
            t1 = __HI(0, ha);
            t2 = a - t1;
            w = Math.sqrt(t1 * t1 - (b * (-b) - t2 * (a + t1)));
        } else {
            double y1, y2;
            a = a + a;
            y1 = __HI(0, hb);
            y2 = b - y1;
            t1 = __HI(0, ha + 0x00100000);
            t2 = a - t1;
            w = Math.sqrt(t1 * y1 - (w * (-w) - (t1 * y2 + t2 * b)));
        }
        if (k != 0) {
            return powerOfTwoD(k) * w;
        } else {
            return w;
        }
    }

    private static double __HI(double x, int high) {
        long transX = Double.doubleToRawLongBits(x);
        return Double.longBitsToDouble((transX & 0x0000_0000_FFFF_FFFFL) |
                (((long) high)) << 32);
    }

    private static int __HI(double x) {
        long transducer = Double.doubleToRawLongBits(x);
        return (int) (transducer >> 32);
    }

    static double powerOfTwoD(int n) {
        assert (n >= Double.MIN_EXPONENT && n <= Double.MAX_EXPONENT);
        return Double.longBitsToDouble((((long) n + (long) EXP_BIAS) <<
                (SIGNIFICAND_WIDTH - 1))
                & EXP_BIT_MASK);
    }

    public static double toRadians(double angdeg) {
        return angdeg * DEGREES_TO_RADIANS;
    }

    public static double toDegrees(double angrad) {
        return angrad * RADIANS_TO_DEGREES;
    }

    public static int gcd0(int a, int b) {
        if (b == 0) {
            return a;
        }
        if (a == 0) {
            return b;
        }
        // Right shift a & b till their last bits equal to 1.
        int aZeros = Integer.numberOfTrailingZeros(a);
        int bZeros = Integer.numberOfTrailingZeros(b);
        a >>>= aZeros;
        b >>>= bZeros;

        int t = (Math.min(aZeros, bZeros));

        while (a != b) {
            if ((a + 0x80000000) > (b + 0x80000000)) {  // a > b as unsigned
                a -= b;
                a >>>= Integer.numberOfTrailingZeros(a);
            } else {
                b -= a;
                b >>>= Integer.numberOfTrailingZeros(b);
            }
        }
        return a << t;
    }

    public static class RandomUtils {
        //Канкаррент рандом

        public static int randomInt(int bound) {
            return ThreadLocalRandom.current().nextInt(bound);
        }

        public static int randomInt(int origin, int bound) {
            return ThreadLocalRandom.current().nextInt(origin, bound);
        }

        public static double randomDouble() {
            return ThreadLocalRandom.current().nextDouble();
        }

        public static double randomDouble(double bound) {
            return ThreadLocalRandom.current().nextDouble(bound);
        }

        public static double randomDouble(double origin, double bound) {
            return ThreadLocalRandom.current().nextDouble(origin, bound);
        }

        public static float randomFloat() {
            return ThreadLocalRandom.current().nextFloat();
        }

        public static float randomFloat(float bound) {
            if (bound <= 0.0F) {
                throw new IllegalArgumentException("граница должна быть положительной");
            }
            float result = ThreadLocalRandom.current().nextFloat() * bound;
            return (result < bound) ? result : // correct for rounding
                    Float.intBitsToFloat(Float.floatToIntBits(bound) - 1);
        }

        public static float randomFloat(float origin, float bound) {
            if (origin >= bound) {
                throw new IllegalArgumentException("граница должна быть больше начала");
            }
            float r = ThreadLocalRandom.current().nextFloat();
            if (origin < bound) {
                r = r * (bound - origin) + origin;
                if (r >= bound) {
                    r = Float.intBitsToFloat(Float.floatToIntBits(bound) - 1);
                }
            }
            return r;
        }

        public static boolean randomBoolean() {
            return ThreadLocalRandom.current().nextBoolean();
        }

    }

    /**
     * A separate class containing information about the player's velocity
     *
     * @author koloslolya
     */
    public static class Velocity {

        double x;
        double y;
        double z;

        public Velocity(double x, double y, double z) {
            this.x = x / 8000D;
            this.y = y / 8000D;
            this.z = z / 8000D;
        }

        public Velocity(Vector vector) {
            this.x = vector.getX();
            this.y = vector.getY();
            this.z = vector.getZ();
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public Vector getVector() {
            return new Vector(x, y, z);
        }

        public double getVelocityXZ() {
            return MathUtil.hypot(x, z);
        }

        public double getXYZHypot() {
            return MathUtil.hypot(x, y, z);
        }

        public double getVelocityAbsY() {
            return Math.abs(y);
        }

    }
}
