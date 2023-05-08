package com.ufc.dream.web_start.commons;

import com.ufc.dream.commons.tree.TreeNode;
import com.ufc.dream.commons.tree.TreeUtils;

import java.util.ArrayList;
import java.util.List;

public class TestListToTree {

    public static void main(String[] args) {
        testTree();

    }

    public static  void testTree(){
        List<Zoneno> list = new ArrayList<>();
        Zoneno zonenoP = new Zoneno();
        zonenoP.setCode("1");
        zonenoP.setOrder(0);
        list.add(zonenoP);

        Zoneno zonenoP1 = new Zoneno();
        zonenoP1.setCode("2");
        zonenoP1.setOrder(1);
        list.add(zonenoP1);


        Zoneno zonenoPc = new Zoneno();
        zonenoPc.setCode("11");
        zonenoPc.setOrder(0);
        zonenoPc.setParent("1");
        list.add(zonenoPc);


        Zoneno zonenoPc1 = new Zoneno();
        zonenoPc1.setCode("12");
        zonenoPc1.setOrder(1);
        zonenoPc1.setParent("1");
        list.add(zonenoPc1);


        Zoneno zonenoPcc = new Zoneno();
        zonenoPcc.setCode("111");
        zonenoPcc.setOrder(0);
        zonenoPcc.setParent("11");
        list.add(zonenoPcc);

        TreeNode<Zoneno> zonenoTreeNode = TreeUtils.toTree(list);
        System.out.println(zonenoTreeNode.getIndex());

    }
}
