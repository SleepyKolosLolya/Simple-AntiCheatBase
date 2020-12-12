package acname.ac.api.util;

public class LimitedDouble<E extends Number> {

    final Number min;
    final Number max;
    Number value;

    public LimitedDouble(E min1, E max1, E value) {
        this.min = min1;
        this.max = max1;
        this.value = value;
        fix();
    }

    public LimitedDouble(Float min1, double max1) {
        this.min = min1;
        this.max = max1;
        this.value = min1;
    }

    public void addValue(Number val) {
        this.value = (Double) this.value + (Double) val;
        fix();
    }

    public void reset() {
        value = min;
    }

    public Number getValue() {
        return value;
    }

    public Number getMin() {
        return min;
    }

    public Number getMax() {
        return max;
    }

    void fix() {
        value = Math.min((Double) max, Math.max((Double) min, (Double) value));
    }

}
