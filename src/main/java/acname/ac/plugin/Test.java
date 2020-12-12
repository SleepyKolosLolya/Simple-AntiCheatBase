package acname.ac.plugin;

public class Test {

    public static void main(String[] args) {
        System.out.println(.42F);

    }

    public static int random(double min, double max) {
        return (int) ((int) (Math.random() * ((max - min) + 1)) + min);
    }

    public static float floatPart(float a) {
        return (float) (a - Math.floor(a));
    }

    public static boolean contains(final int[] array, final int v) {
        for (int i : array) {
            if (i == v) return true;
        }
        return false;
    }

    public static boolean isPrecision(float a) {
        boolean var = false;
        String value = floatPart(a) + "";
        int last = -1, pre = 0, i = 2;
        while (i < value.length() - 1) {
            if (last == value.charAt(i) && contains(new int[]{'3', '6', '9'}, value.charAt(i)))
                if (pre++ > 1) var = true;
            last = value.charAt(i);
            i++;
        }
        return var;
    }

    /**
     * Java method to calculate lowest common multiplier of two numbers
     *
     * @param a
     * @param b
     * @return LCM of two numbers
     */
    public static long LCM(float a, float b) {
        return (long) ((a * b) / GCF(a, b));
    }

    /**
     * Java method to calculate greatest common factor of two numbers
     *
     * @param a
     * @param b
     * @return GCF of two numbers using Euclid's algorithm
     */
    public static long GCF(float a, float b) {
        if (b == 0F) {
            return (long) a;
        } else {
            return (GCF(b, a % b));
        }
    }

}
