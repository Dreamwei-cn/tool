package com.ufc.dream.commons.csv;

import java.util.List;

/**
 * @author 四维穿梭
 *  简单事件
 */
public class CsvSimpleListener  extends CsvEventListener<List<String>>{

    @Override
    public void invoke(List<String> obj) {
        this.getList().add(obj);
    }

    @Override
    public void doAfter() {

    }
}
