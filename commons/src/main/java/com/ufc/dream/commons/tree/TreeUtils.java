package com.ufc.dream.commons.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 思维穿梭
 */
public class TreeUtils {


    /**
     * 递归最深层次  防止死循环
     */
    private static final int MAXDEEP = 10;

    public static <T extends TreeInterface> TreeNode<T> toTree(List<T> list){
        TreeNode<T> tree = new TreeNode<>();
        int deep = 1;
        tree.setIndex(0);
        List<TreeNode<T>> childs = new ArrayList<>();
        for (T t:list) {
            if (t.first()){
                TreeNode<T> child = new TreeNode<>();
                child.setValue(t);
                child.setIndex(deep);
                childs.add(child);
                addChild(child, list,1);
            }
        }
        tree.setChildrens(childs);
        return tree;
    }

    private static <T extends TreeInterface> void addChild(TreeNode<T> tree, List<T> list, int i) {
        if (i >= MAXDEEP) {
            return;
        }
        T p = tree.getValue();
        List<TreeNode<T>> childrens = tree.getChildrens();
        if (childrens == null){
            childrens = new ArrayList<>();
        }
        i ++;
        for (T t: list) {
            if (t.parent(p)){
                TreeNode<T> child = new TreeNode<>();
                child.setIndex(i);
                child.setValue(t);
                childrens.add(child);
                addChild(child, list, i);
            }
        }
        tree.setChildrens(childrens);

    }
}
