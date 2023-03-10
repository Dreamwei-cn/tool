package com.ufc.dream.web_start.commons;

import com.ufc.dream.commons.CsvReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TestCsvImport {

    public static void main(String[] args) {
        File file = new File("F:\\考勤\\csvFile.csv");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            List<List<String>> lists = CsvReader
                    .read()
                    .setRowNo(1)
                    .setInputStream(inputStream)
                    .setFileLength(file.length()).setHead(new ArrayList<>())
                    .doRead();
            System.out.println(lists.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
