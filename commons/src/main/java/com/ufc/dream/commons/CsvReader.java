package com.ufc.dream.commons;

import com.ufc.dream.commons.csv.CsvReaderBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 四维穿梭
 */
public class CsvReader {

  public static CsvReaderBuilder read(){
      return new CsvReaderBuilder();
  }

}
