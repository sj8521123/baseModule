package com.sj.basemodule;

import java.util.Iterator;

import androidx.annotation.NonNull;

public class ForEachTest2<T> implements Iterable<T>, Iterator<T> {
    public Object[] atr;
    int currentPos = -1;
    int privincePos = Integer.MAX_VALUE;

    public ForEachTest2(int size) {
        this.atr = new Object[size];
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return this;
    }

    public void resetIterator() {
        currentPos = -1;
        privincePos = Integer.MAX_VALUE;
    }

    @Override
    public boolean hasNext() {
        return currentPos < atr.length - 1;
    }

    @Override
    public T next() {
        return (T) atr[++currentPos];
    }

    @Override
    public void remove() {
        if (currentPos < 0 || currentPos > atr.length || currentPos == privincePos) {
            throw new IllegalStateException("error");
        }
        Object[] old = atr;
        privincePos = currentPos;
        atr = new Object[old.length - 1];
        int j = 0;
        for (int i = 0; i < old.length; i++) {
            if (i != privincePos) {
                atr[j++] = old[i];
            }
        }
        privincePos = --currentPos;
        old = null;
    }

}
