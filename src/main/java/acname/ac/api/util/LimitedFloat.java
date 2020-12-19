package acname.ac.api.util;

public class LimitedFloat {

    final float min;
    final float max;
    float value;

    public LimitedFloat(float min1, float max1, float value) {
        this.min = min1;
        this.max = max1;
        this.value = value;
        fix();
    }

    public LimitedFloat(Float min1, float max1) {
        this.min = min1;
        this.max = max1;
        this.value = min1;
    }

    public void addValue(float val) {
        this.value = this.value + val;
        fix();
    }

    public void reset() {
        value = min;
    }

    public float getValue() {
        return value;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    void fix() {
        value = Math.min(max, Math.max(min,  value));
    }

}
