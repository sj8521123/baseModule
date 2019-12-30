package com.sj.basemodule;

import java.util.Iterator;

import androidx.annotation.NonNull;

public class ForEachTest<E> implements Iterable<E>, Iterator<E> {
    Object[] arr;
    //当前访问的元素在数组的下标
    int curIndex = -1;
    //最后一个调用next方法所得元素所在数组下标
    int previousIndex = Integer.MAX_VALUE;

    public ForEachTest(int size) {
        this.arr = new Object[size];
    }

    //重置迭代器
    public void resetIterator() {
        curIndex = -1;
        previousIndex = Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return curIndex < arr.length - 1;
    }

    @Override
    public E next() {
        if (curIndex >= arr.length - 1) {
            throw new IllegalStateException("index is out of bound of array");
        }
        return (E) arr[++curIndex];
    }

    @Override
    public void remove() {
        if (curIndex < 0 || curIndex >= arr.length || curIndex == previousIndex) {
            throw new IllegalStateException("there is not " +
                    "any element can be remote");
        }
        previousIndex = curIndex;
        Object[] laseArr = arr;
        int j = 0;
        arr = new Object[arr.length - 1];
        for (int i = 0; i < laseArr.length; i++) {
            if (i != previousIndex) {
                arr[j++] = laseArr[i];
            }
        }
        previousIndex = --curIndex;
        laseArr = null;

    }
}
