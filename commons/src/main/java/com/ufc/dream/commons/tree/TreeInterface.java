package com.ufc.dream.commons.tree;

import java.util.Comparator;

/**
 * @author 思维穿梭
 */
public interface TreeInterface<T> extends Comparator <T> {

    public boolean parent(T t);

    public boolean first();
}
