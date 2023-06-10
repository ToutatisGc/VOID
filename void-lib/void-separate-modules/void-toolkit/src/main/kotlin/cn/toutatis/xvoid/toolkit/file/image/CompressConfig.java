package cn.toutatis.xvoid.toolkit.file.image;

import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Toutatis_Gc
 * 图片压缩配置
 */
public class CompressConfig {

    private final Logger logger = LoggerFactory.getLogger(CompressConfig.class);

    private String configName;

    private CompressConfig() {}

    public CompressConfig(String configName) {
        this.configName = configName;
    }

    /**
     * 配置内容
     */
    public class CompressContent {

        private String configPath;

        public CompressContent(String configPath) {
            this.configPath = configPath;
        }

        private String title = "XVOID-图片压缩工具";

        /**
         * 压缩宽度
         */
        private Integer width = 600;

        /**
         * 压缩高度
         */
        private Integer height = 400;

        /**
         * 窗口宽度
         */
        private Integer fileViewWidth = 0;

        /**
         * 不可缩放窗口
         */
        private Boolean resizable = false;

        /**
         * 生成不同的压缩规格
         */
        private Boolean differentRegular = true;

        /**
         * 保留源文件
         */
        private Boolean retainSourceFile = true;

        /**
         * 压缩类型
         * 0 为两种压缩全部使用
         * 1 为画质压缩
         * 2 为尺寸压缩
         */
        private String zipType = "0";

        /**
         * 压缩次数
         */
        private Integer zipTimes = 5;

        /**
         * 最后选择目录
         */
        private String lastChooseDir = null;

        /**
         * 最后保存目录
         */
        private String lastSaveDir = null;

        /**
         * 重命名规则
         * SOURCE 为源文件名
         * UUID 为UUID
         */
        private String saveFileRenameType = "UUID";

        private PictureQualityDistributionStrategy pictureQualityDistributionStrategy = PictureQualityDistributionStrategy.AVERAGE;

        public PictureQualityDistributionStrategy getPictureQualityDistributionStrategy() {
            return pictureQualityDistributionStrategy;
        }

        public void setPictureQualityDistributionStrategy(PictureQualityDistributionStrategy pictureQualityDistributionStrategy) {
            this.pictureQualityDistributionStrategy = pictureQualityDistributionStrategy;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Integer getFileViewWidth() {
            return fileViewWidth;
        }

        public void setFileViewWidth(Integer fileViewWidth) {
            this.fileViewWidth = fileViewWidth;
        }

        public Boolean getResizable() {
            return resizable;
        }

        public void setResizable(Boolean resizable) {
            this.resizable = resizable;
        }

        public Boolean getDifferentRegular() {
            return differentRegular;
        }

        public void setDifferentRegular(Boolean differentRegular) {
            this.differentRegular = differentRegular;
        }

        public Boolean getRetainSourceFile() {
            return retainSourceFile;
        }

        public void setRetainSourceFile(Boolean retainSourceFile) {
            this.retainSourceFile = retainSourceFile;
        }

        public String getZipType() {
            return zipType;
        }

        public void setZipType(String zipType) {
            this.zipType = zipType;
        }

        public Integer getZipTimes() {
            return zipTimes;
        }

        public void setZipTimes(Integer zipTimes) {
            this.zipTimes = zipTimes;
        }

        public String getLastChooseDir() {
            return lastChooseDir;
        }

        public void setLastChooseDir(String lastChooseDir) {
            this.lastChooseDir = lastChooseDir;
        }

        public String getLastSaveDir() {
            return lastSaveDir;
        }

        public void setLastSaveDir(String lastSaveDir) {
            this.lastSaveDir = lastSaveDir;
        }

        public String getSaveFileRenameType() {
            return saveFileRenameType;
        }

        public void setSaveFileRenameType(String saveFileRenameType) {
            this.saveFileRenameType = saveFileRenameType;
        }

        public void saveConfig() {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(configPath);
                IOUtils.write(JSON.toJSONString(this, true), fileOutputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public CompressContent getConfig(){
        String path = "compress-config.json";
        if (configName != null && !path.equals(configName)){
            path = FileToolkit.getThreadPath() + "/" + configName;
        }else{
            return new CompressContent(FileToolkit.getThreadPath() + "/" + path);
        }
        String fileContent = FileToolkit.getFileContent(new File(path));
        JSONObject payload = JSONObject.parseObject(fileContent);
        CompressContent compressContent = payload.toJavaObject(CompressContent.class);
        if (Validator.strIsBlank(compressContent.getLastChooseDir())){
            File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
            String desktopPath = desktopDir.getAbsolutePath();
            compressContent.setLastChooseDir(desktopPath);
            compressContent.saveConfig();
        }
        return compressContent;
    }

}
