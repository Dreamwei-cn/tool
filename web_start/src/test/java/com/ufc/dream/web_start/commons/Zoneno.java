package com.ufc.dream.web_start.commons;

import com.ufc.dream.commons.tree.TreeInterface;
import lombok.Data;


@Data
public class Zoneno implements TreeInterface<Zoneno> {

    private String name;

    private String code;

    private String parent;

    private int order;

    @Override
    public int compare(Zoneno o1, Zoneno o2) {
        int order1 = o1.getOrder();
        int order2 = o2.getOrder();
        if (order1 == order2){
            return 0;
        }
        if (order1 > order2){
            return 1;
        }
        return  -1;
    }

    @Override
    public boolean parent(Zoneno p) {
        String parent = p.getCode();
        String parentC = this.getParent();
        return parent == parentC || parent.equals(parentC);
    }

    @Override
    public boolean first() {
        return this.getParent() == null;
    }
}
