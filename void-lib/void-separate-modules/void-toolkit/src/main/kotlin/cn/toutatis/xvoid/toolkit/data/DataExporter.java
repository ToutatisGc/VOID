package cn.toutatis.xvoid.toolkit.data;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.ReflectUtil;
import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;

/**
 * <p>
 *     数据导出工具类
 *     可以导出Excel、CSV、JSON、XML等格式的文件
 * <p>
 * @author Toutatis_Gc
 * @since  0.0.0-ALPHA
 */
public class DataExporter {

    private static Logger logger = LoggerToolkit.getLogger(DataExporter.class);

    public static Sheet defaultStyleSheet(Workbook workbook,String sheetName,PoiStandardModel model) {
        if (workbook instanceof SXSSFWorkbook) {
            ((SXSSFWorkbook) workbook).setCompressTempFiles(true);
        }
        CellStyle cellStyle = defaultStyleCell(workbook);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFont(font);
        SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet(sheetName);
        sheet.trackAllColumnsForAutoSizing();
        Row row = sheet.createRow(0);
        LinkedHashMap<String, String> headers = model.getHeaders();
        Object[] headersSet = headers.keySet().toArray();
        for (int i = 0; i < headersSet.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headers.get(headersSet[i]));
        }
        return sheet;
    }

    public static CellStyle defaultStyleCell(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLUE_GREY.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.BLUE_GREY.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLUE_GREY.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLUE_GREY.getIndex());
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    public static final Integer DEFAULT_THREAD_NUM = 5;

    public static String export(String fileName,String sheetName,PoiStandardModel model){
        List<?> data = model.getData();
        if (data.size() == 0) {
            logger.warn("[VOID-TOOLKIT]数据为空，无法导出");
            return null;
        }
        long startT = System.currentTimeMillis();
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(-1);
        SXSSFSheet sheet = (SXSSFSheet) defaultStyleSheet(sxssfWorkbook, sheetName, model);
        CellStyle cellStyle = defaultStyleCell(sxssfWorkbook);
        int threadNum = DEFAULT_THREAD_NUM;
        if (data.size() < threadNum) {
            threadNum = 1;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

        Collection<Callable<String>> callables = new ArrayList<>(threadNum);
        try {
            int taskLoad = data.size() / DEFAULT_THREAD_NUM;
            /*完整整除*/
            boolean canFull = data.size() % DEFAULT_THREAD_NUM == 0;
            for (int i = 0; i < DEFAULT_THREAD_NUM; i++) {
                int start = i * taskLoad;
                int end = (i +1) * taskLoad;
                System.err.println("start:"+ start +" end:"+end);
                Callable<String> callable = () -> {
                    logger.info("线程" + Thread.currentThread().getName() + "开始写入数据 [" + start + "-" + end+"]");
                    write(model,sheet,cellStyle, start, end);
                    logger.info("线程" + Thread.currentThread().getName() + "完成");
                    return "0";
                };
                callables.add(callable);
            }
            executorService.invokeAll(callables);
//            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
//                executorService.shutdownNow();
//            }
        } catch (Exception e) {
            executorService.shutdownNow();
            e.printStackTrace();
        }
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
//        logger.info("总行数:"+physicalNumberOfRows);
//        if (data.size() != physicalNumberOfRows-1){
//            for (int i = 0; i < data.size(); i++) {
//                Row row = sheet.getRow(i+1);
//                if (row == null){
//                    System.err.println("空"+i);
////                    write(model,sheet,cellStyle, i-1, i);
////                    System.err.println("补录"+i);
//                }
//            }
//        }
        FileOutputStream out = null;
        File file = new File(FileToolkit.INSTANCE.getRuntimePath(true) + "/"+fileName+".xlsx");
        try {
            System.err.println(file.getAbsolutePath());
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            out = new FileOutputStream(file);
            sxssfWorkbook.write(out);
            out.close();
            sxssfWorkbook.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.err.println("导出耗时：" + (end - startT) + "ms");
        return file.getAbsolutePath();
    }

    private static void write(PoiStandardModel model,Sheet sheet,CellStyle cellStyle,int start,int end){
        LinkedHashMap<String, String> headers = model.getHeaders();
        List<?> data = model.getData().subList(start, end);
        for (Object value : data) {
            synchronized (sheet){
                Row row = sheet.createRow(++start);
                List<Object> column = new ArrayList<>(headers.size());
                Object datum = value;
                if (datum instanceof JSONObject) {
                    JSONObject jsonObj = (JSONObject) datum;
                    headers.forEach((k, v) -> {
                        String o = jsonObj.getString(k);
                        if (o != null) {
                            column.add(o);
                        } else {
                            column.add("");
                        }
                    });
                } else if (datum instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) datum;
                    headers.forEach((k, v) -> {
                        Object o = map.get(k);
                        if (o != null) {
                            column.add(o);
                        } else {
                            column.add("");
                        }
                    });
                } else {
                    Field[] fields = ReflectUtil.getFields(datum.getClass());
                    for (Field field : fields) {
                        field.setAccessible(true);
                        try {
                            Object o = field.get(datum);
                            if (o != null) {
                                column.add(o);
                            } else {
                                column.add("");
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                ArrayList<Integer> colWidth = new ArrayList<>(column.size());
                for (int i = 0; i < column.size(); i++) {
                    colWidth.add(-1);
                }
                for (int i1 = 0; i1 < column.size(); i1++) {
                    Cell cell = row.createCell(i1);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(column.get(i1).toString());
                    if(colWidth.get(i1) == -1){
                        sheet.autoSizeColumn(i1);
                        int newWidth = sheet.getColumnWidth(i1) * 12 / 10;
                        sheet.setColumnWidth(i1,newWidth);
                        colWidth.set(i1,newWidth);
                    }else{
                        sheet.setColumnWidth(i1, colWidth.get(i1));
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        headers.put("id","ID");
        headers.put("name","姓名");
        headers.put("random","随机测试");
        List<JSONObject> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            JSONObject jsonObject = new JSONObject(2);
            jsonObject.put("id",i);
            jsonObject.put("name","张三"+i);
            jsonObject.put("random", new Random().nextDouble());
            data.add(jsonObject);
        }
        export("test","test",new PoiStandardModel(headers,data));
    }

}
