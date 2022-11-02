package cn.toutatis.xvoid.compress;

import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import cn.toutatis.xvoid.toolkit.file.image.CompressConfig;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Toutatis_Gc
 * 图片压缩工具
 */
public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static CompressConfig.CompressContent config;

    static {
        config = new CompressConfig(null).getConfig();
    }

    private final Element element = Element.INSTANCE;

    @Override
    public void start(Stage primaryStage) {
        BorderPane rootStackPane = new BorderPane();

        int fileViewWidth = Math.max(config.getFileViewWidth(), 300);
        double width = config.getWidth();
        int widthCount = (int) width;
        if (fileViewWidth != 0){
            double v =  300 + fileViewWidth;
            widthCount = (int) v;
        }

        Scene mainScene = new Scene(rootStackPane,widthCount, config.getHeight());
        mainScene.getStylesheets().add("app.css");
//        初始化顶部菜单
        element.initMenu(primaryStage,rootStackPane);

//       初始化文件列表
        element.initFileListView(primaryStage,rootStackPane);

//        初始化右侧设置
        element.initRightConfig(primaryStage,rootStackPane);

//        初始化场景
        this.initStage(primaryStage);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static CompressConfig.CompressContent getConfig(){
        return config;
    }

    private void initStage(Stage primaryStage){
        primaryStage.setTitle(config.getTitle());
        String icon = FileToolkit.getThreadPath() + "/favicon.png";
        try (FileInputStream fileInputStream = new FileInputStream(icon)){
            primaryStage.getIcons().add(new Image(fileInputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setResizable(config.getResizable());
    }
}
