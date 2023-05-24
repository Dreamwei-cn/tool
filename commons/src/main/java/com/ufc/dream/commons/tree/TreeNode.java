package com.ufc.dream.commons.tree;

import lombok.Data;

import java.util.List;

/**
 * @author 思维穿梭
 */
@Data
public class TreeNode <T> {

    private T value;

    private int index;

    private List<TreeNode<T>> childrens;
}
