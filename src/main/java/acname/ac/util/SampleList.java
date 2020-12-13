package acname.ac.util;

import java.util.LinkedList;

/**
 * LinkedList's performance on add and remove is better than Arraylist, but worse on get and set methods.
 * <p>
 * Define a sample size, Once it reaches the cap, It'll automatically reset itself
 * <p>
 * Boolean updateInsteadOfClear will simply remove the first element And add the new one
 *
 * @author Nik
 */
public final class SampleList<T> extends LinkedList<T> {

    private final int sampleSize;
    private final boolean updateInsteadOfClear;

    public SampleList(int sampleSize) {
        this.sampleSize = sampleSize;
        this.updateInsteadOfClear = false;
    }

    public SampleList(int sampleSize, boolean updateInsteadOfClear) {
        this.sampleSize = sampleSize;
        this.updateInsteadOfClear = updateInsteadOfClear;
    }

    @Override
    public boolean add(T t) {
        if (super.size() >= this.sampleSize) {
            if (this.updateInsteadOfClear) {
                super.removeFirst();
            } else super.clear();
        }

        return super.add(t);
    }

    public boolean isCollected() {
        return size() >= this.sampleSize;
    }
}