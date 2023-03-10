package com.ufc.dream.commons.csv;

import java.util.ArrayList;
import java.util.List;

public abstract class CsvEventListener <T>{
    private List<T> list = new ArrayList<>();

    private Long fileLength;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getFileLength() {
        return fileLength;
    }

    public void setFileLength(Long fileLength) {
        this.fileLength = fileLength;
    }

    public abstract void invoke(T obj);

    public abstract void doAfter();
}
