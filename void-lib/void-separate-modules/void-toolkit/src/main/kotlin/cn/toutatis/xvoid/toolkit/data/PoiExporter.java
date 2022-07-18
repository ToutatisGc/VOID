package cn.toutatis.xvoid.toolkit.data;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;

/**
 * <p>
 *     POI导出工具类
 *     可以导出Excel、CSV、JSON、XML等格式的文件
 * <p>
 * @author Toutatis_Gc
 * @since  0.0.0-ALPHA
 */
public class PoiExporter {

    public static final Integer DEFAULT_MEM_ROW_SIZE = 1000;

    public static void export() {
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
    }

}
