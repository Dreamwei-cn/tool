package com.ufc.dream.web_start.commons;

import com.ufc.dream.commons.csv.CsvProperty;
import lombok.Data;

@Data
public class TestBean {

    @CsvProperty(index = 0)
    private String zone;

    @CsvProperty(index = 1)
    private String name;
    @CsvProperty(index = 2)
    private String start;
    @CsvProperty(index = 3)
    private String end;
    @CsvProperty(index = 4)
    private String length;
}
