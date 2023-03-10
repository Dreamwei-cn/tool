package com.ufc.dream.commons.csv;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 四维穿梭
 *
 *  csv 读取类
 */
public class CsvReaderBuilder {

    /**
     *  默认读取开始行
     */
    private int defaultRowNo = 1;


    private int buffSize = 1024 * 1024;

    /**
     *  文件分片大小
     */
    private int sliceSize = 10 * 1024 * 1024;

    private int rowNo;

    private List<String> head;

    private InputStream inputStream;

    private Long fileLength;

    private String charset = "GBK";

    private CsvEventListener listener = new CsvSimpleListener();


    public List<List<String>> doRead(){
        if (inputStream == null){
            return new ArrayList<>();
        }
        if (rowNo < 0 ) {
            setRowNo(defaultRowNo);
        }
        List<String> rowList = new ArrayList<>();
        listener.setFileLength(fileLength);
        readFile();
        listener.doAfter();
        return listener.getList();
    }

    public  void readFile(){
        List<List<String>> resultList = new ArrayList<>();
        FileInputStream fileInputStream = (FileInputStream) inputStream;
        FileChannel channel = fileInputStream.getChannel();
        int sliceNum = (int) (fileLength/sliceSize);
        if (fileLength % sliceSize > 0){
            sliceNum++;
        }
        int rowNum = 0;
        MappedByteBuffer mapBuffer =null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();){
           List<String> cellValueList = null;

            byte[] readBuff = new byte[buffSize];
            for (int i = 0; i < sliceNum; i++) {
                long mapSize = (i+1) * sliceSize > fileLength? fileLength-i*sliceSize : sliceSize;
                mapBuffer = channel.map(FileChannel.MapMode.READ_ONLY, i*sliceSize, mapSize);
                for (int offset = 0; offset <= mapSize; offset+=buffSize){
                    int readLength = 0;
                    if (offset + buffSize <= mapSize){
                        readLength = buffSize;
                    } else {
                        readLength = (int) mapSize - offset;
                    }
                    mapBuffer.get(readBuff, 0, readLength);

                    for (int j = 0; j < readLength; j++) {
                        byte tmp = readBuff[j];
                        if (tmp == '\n' || tmp == '\r'){
                            if (bos.size() != 0){
                                rowNum++;
                                // 清除表头
                                if (rowNum <= rowNo){
                                    bos.reset();
                                    continue;
                                }
                                cellValueList = toListString(bos.toByteArray());
                                resultList.add(cellValueList);
                                listener.invoke(cellValueList);
                                bos.reset();
                            }
                        } else {
                            bos.write(tmp);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null){
                    fileInputStream.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private List<String> toListString(byte[] bytes){
        if (bytes ==null || bytes.length <=0){
            return null;
        }
        String toStr = null;
        try {
            toStr = new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Arrays.asList(toStr.split(","));
    }

    public CsvEventListener getListener() {
        return listener;
    }

    public void setListener(CsvEventListener listener) {
        this.listener = listener;
    }

    public int getDefaultRowNo() {
        return defaultRowNo;
    }

    public CsvReaderBuilder setDefaultRowNo(int defaultRowNo) {
        this.defaultRowNo = defaultRowNo;
        return this;
    }

    public int getBuffSize() {
        return buffSize;
    }

    public CsvReaderBuilder setBuffSize(int buffSize) {
        this.buffSize = buffSize;
        return this;
    }

    public int getSliceSize() {
        return sliceSize;
    }

    public CsvReaderBuilder setSliceSize(int sliceSize) {
        this.sliceSize = sliceSize;
        return this;
    }

    public int getRowNo() {
        return rowNo;
    }

    public CsvReaderBuilder setRowNo(int rowNo) {
        this.rowNo = rowNo;
        return this;
    }

    public List<String> getHead() {
        return head;
    }

    public CsvReaderBuilder setHead(List<String> head) {
        this.head = head;
        return this;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public CsvReaderBuilder setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public Long getFileLength() {
        return fileLength;
    }

    public CsvReaderBuilder setFileLength(Long fileLength) {
        this.fileLength = fileLength;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public CsvReaderBuilder setCharset(String charset) {
        this.charset = charset;
        return this;
    }
}
