package cn.toutatis.xvoid.spring;

import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.sqlite.Meta;
import cn.toutatis.xvoid.sqlite.SQLiteConnectionFactory;
import cn.toutatis.xvoid.sqlite.SQLiteShell;
import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import org.slf4j.Logger;
import org.sqlite.SQLiteConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/**
 * 打包执行任务
 * @author Toutatis_Gc
 */
public class XvoidAntTask {

    private final Logger logger = LoggerToolkit.getLogger(this.getClass());

    public static SQLiteShell sqliteShell() throws FileNotFoundException {
        SQLiteConnectionFactory sqLiteConnectionFactory = new SQLiteConnectionFactory();
        URL resourcesFile = FileToolkit.getResourcesFile("sql/VOID.db");
        if (resourcesFile != null) {
            File dbFile = null;
            try {
                dbFile = new File(resourcesFile.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            SQLiteConnection createConnection = sqLiteConnectionFactory.createConnection(dbFile);
            return new SQLiteShell(createConnection);
        } else {
            String errorWithModule = LoggerToolkit.infoWithModule(Meta.MODULE_NAME, "SPRING", "缺失VOID数据库文件");
            throw new FileNotFoundException(errorWithModule);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        try {
            SQLiteShell sqLiteShell = sqliteShell();
            Map<String, Object> map = sqLiteShell.selectOneMap("SELECT * FROM VOID_CONTEXT WHERE KEY = 'AES_SECRET' AND MCH_ID = '%s'".formatted(StandardFields.VOID_BUSINESS_DEFAULT_CREATOR));
            // TODO 更新KEY
            System.err.println(map);
        }catch (Exception ignored){}
    }
}
