package cn.toutatis.xvoid.compress;

import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import cn.toutatis.xvoid.toolkit.file.image.CompressConfig;
import cn.toutatis.xvoid.toolkit.file.image.ImageCompressToolKit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Element {
//    单例
    INSTANCE;

    private final CompressConfig.CompressContent config = MainApplication.config;

    private final ObservableList<File> listData = FXCollections.observableArrayList();

    private static final Font noticeFont = Font.font(16);

    private Button saveButton;

    private Text status;

    /**
     * 初始化菜单栏
     * @param primaryStage
     * @param rootStackPane
     */
    public void initMenu(Stage primaryStage, BorderPane rootStackPane){
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        rootStackPane.setTop(menuBar);

        Menu fileMenu = new Menu("菜单");
        fileMenu.getStyleClass().add("top-menu");
        MenuItem openFileChooser = new MenuItem("打开文件");
        openFileChooser.setOnAction(actionEvent ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择图片文件");
            fileChooser.setInitialDirectory(new File(config.getLastChooseDir()));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("图片资源 JPG/JPEG/PNG", "*.jpg","*.png","*.jpeg"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
            );
            List<File> fileList = fileChooser.showOpenMultipleDialog(primaryStage);
            if (fileList != null){
                this.listData.clear();
                this.listData.addAll(fileList);
                status.setText("加入 "+ fileList.size()+" 个文件.");
            }
        });

        MenuItem exitMenuItem = new MenuItem("退出");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());

        fileMenu.getItems().addAll(openFileChooser, new SeparatorMenuItem(), exitMenuItem);

        Menu help = new Menu("帮助");
        MenuItem explain = new MenuItem("说明文档");
        explain.setOnAction(action ->{
            Stage stage = new Stage(StageStyle.UTILITY);
            StackPane stackPane = new StackPane();
            Text text = new Text("此压缩工具由Toutatis_Gc创建,gitee账号为Toutatis_Gc.\n" +
                    "最后更新时间:2020-01-30");
            text.setWrappingWidth(350);
            stackPane.getChildren().addAll(text);
            Scene scene = new Scene(stackPane,400,100);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("说明文档");
            stage.show();
        });
        help.getItems().add(explain);

        menuBar.getMenus().addAll(fileMenu,help);
    }

    public void initFileListView(Stage primaryStage, BorderPane rootStackPane){

        ListView<File> fileListView = new ListView<>(listData);
        fileListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        fileListView.setOnDragOver(dragEvent -> {
            if (dragEvent.getDragboard().hasFiles()){
                dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }else {
                dragEvent.acceptTransferModes(TransferMode.NONE);
            }
        });

        fileListView.setOnDragDropped(event -> {
            List<File> files = event.getDragboard().getFiles();
            if (files != null && files.size() > 0){
                Stream<File> fileStream = files.stream().filter(file -> {
                    boolean isFile = file.isFile();
                    if (isFile) {
                        String fileSuffix = FileToolkit.getFileSuffix(file.getName());
                        return FileToolkit.isImg(fileSuffix);
                    } else {
                        return false;
                    }
                });
                List<File> collect = fileStream.collect(Collectors.toList());
                status.setText("新增 "+ collect.size()+" 个文件.");
                listData.addAll(collect);
            }
        });

//        fileListView.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
//            @Override
//            public ListCell<File> call(ListView<File> param) {
//                ListCell listCell = new ListCell() {
//                    @Override
//                    protected void updateItem(Object item, boolean empty) {
//                        File file = (File) item;
//                        if (!empty) {
//                            HBox hbox = new HBox();
//                            Separator verticalSeparator_0 = new Separator(Orientation.VERTICAL);
//                            Separator verticalSeparator_1 = new Separator(Orientation.VERTICAL);
//                            Text fileName = new Text(file.getName());
//                            Insets insets = new Insets(0, 5, 0, 5);
//                            verticalSeparator_0.setPadding(insets);
//                            verticalSeparator_1.setPadding(insets);
//                            Button deleteButton = new Button("×");
//                            deleteButton.setOnAction(actionEvent ->{
//                                int fileHash = file.hashCode();
//                                for (int i = 0; i < listData.size(); i++) {
//                                    File tmp = listData.get(i);
//                                    int tmpHash = tmp.hashCode();
//                                    if (fileHash == tmpHash){
//                                        listData.remove(i);
//                                        break;
//                                    }
//                                }
////                                fileListView.n
//                            });
////                            deleteButton.setPadding(new Insets(0,10,0,10));
//                            Text absolutePath = new Text(file.getAbsolutePath());
//                            hbox.getChildren().addAll(deleteButton,verticalSeparator_0,fileName,verticalSeparator_1,absolutePath);
//                            this.setGraphic(hbox);
//                        }
//                        super.updateItem(item, empty);
//                    }
//                };
//                return listCell;
//            }
//        });

        HBox topTool = new HBox();
        Label label = new Label("当前选择文件");
        label.setPadding(new Insets(5,5,10,5));
        Button deleteAll = new Button("删除全部");
        deleteAll.setOnAction(action -> listData.clear());
        Button deleteSelect = new Button("删除选中");
        deleteSelect.setOnAction(action ->{
            ObservableList<File> selectedItems = fileListView.getSelectionModel().getSelectedItems();
            listData.removeAll(selectedItems);
        });
        topTool.getChildren().addAll(label,deleteAll,deleteSelect);

        VBox fileView = new VBox(topTool, fileListView);
        fileView.setPadding(new Insets(15));
        int fileViewWidth = (int) config.getFileViewWidth();
        if (fileViewWidth != 0){
            fileView.setMinWidth(fileViewWidth);
        }

        fileView.maxHeightProperty().bind(rootStackPane.heightProperty());
        rootStackPane.setLeft(fileView);
    }

    public void initRightConfig(Stage primaryStage, BorderPane rootStackPane){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Text configTitle = new Text("配置项");
        configTitle.setFont(Font.font("宋体", FontWeight.BOLD, 20));
        grid.add(configTitle, 0, 0, 2, 1);

        Label savePath = new Label("保存路径:");
        grid.add(savePath, 0, 1);

        TextField choosePathText = new TextField(config.getLastSaveDir());
        choosePathText.textProperty().addListener((observable, oldValue, newValue) -> {
            choosePathText.setText(config.getLastSaveDir());
        });
        choosePathText.setDisable(false);
        grid.add(choosePathText, 1, 1);

        Button pathChooseButton = new Button("选择");
        pathChooseButton.setOnAction( actionEvent ->{
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("选择保存路径");
            File chooseDir = directoryChooser.showDialog(primaryStage);
            if (chooseDir != null && chooseDir.isDirectory()){
                config.setLastSaveDir(chooseDir.getAbsolutePath());
                config.saveConfig();
                choosePathText.setText(chooseDir.getAbsolutePath());
            }
        });
        grid.add(pathChooseButton, 2, 1);

        Label renameLabel = new Label("文件命名:");
        grid.add(renameLabel, 0, 2);

        ToggleGroup renameGroup = new ToggleGroup();
        RadioButton radioButton1 = new RadioButton("UUID");
        radioButton1.setPadding(new Insets(0,10,0,0) );
        radioButton1.setUserData("UUID");
        radioButton1.setToggleGroup(renameGroup);
        radioButton1.setSelected(true);
        RadioButton radioButton2 = new RadioButton("源文件名");
        radioButton2.setToggleGroup(renameGroup);
        radioButton2.setUserData("SOURCE");
        HBox buttonGroup = new HBox(radioButton1,radioButton2);
        renameGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            config.setSaveFileRenameType((String) newValue.getUserData());
            config.saveConfig();
        });
        grid.add(buttonGroup, 1, 2);

        Label differentRegularLabel = new Label("图片规格:");
        grid.add(differentRegularLabel, 0, 3);



        Label zipType = new Label("压缩方式:");
        zipType.setDisable(!config.getDifferentRegular());
        grid.add(zipType, 0, 4);

        ToggleGroup zipGroup = new ToggleGroup();

        LinkedHashMap<String, String> zipMap = new LinkedHashMap<>(3);
        zipMap.put("0","全选");
        zipMap.put("1","质量压缩");
        zipMap.put("2","分辨率压缩");

        HBox toggleGroup = new HBox();
        toggleGroup.setDisable(!(Boolean) config.getDifferentRegular());
        zipMap.keySet().forEach(item ->{
            ToggleButton type = new ToggleButton(zipMap.get(item));
            type.setUserData(item);
            type.setToggleGroup(zipGroup);
            String zipTypeValue = config.getZipType();
            if (item.equals(zipTypeValue)){
                type.setSelected(true);
            }
            toggleGroup.getChildren().add(type);
        });
        zipGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            config.setZipType((String) newValue.getUserData());
            config.saveConfig();
        });

        grid.add(toggleGroup,1, 4);

        Label zipTimes = new Label("压缩次数:");
        zipTimes.setDisable(!config.getDifferentRegular());
        grid.add(zipTimes, 0, 5);

        TextField zipTimesField = new TextField(String.valueOf(config.getZipTimes()));
        zipTimesField.setDisable(!config.getDifferentRegular());
        Tooltip zimNotice = new Tooltip("最高支持压缩10次");
        zimNotice.setFont(noticeFont);

        zipTimesField.setTooltip(zimNotice);
        Pattern ONLY_DIG_REX = Pattern.compile("^[0-9]*$");
        zipTimesField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!"".equals(newValue)){
                if (ONLY_DIG_REX.matcher(newValue).matches()){
                    int i = Integer.parseInt(newValue);
                    if (i > 10){
                        i = 10;
                    }
                    zipTimesField.setText(String.valueOf(i));
                    config.setZipTimes(i);
                }else {
                    zipTimesField.setText("1");
                    config.setZipTimes(1);
                }
            }else {
                zipTimesField.setText("1");
                config.setZipTimes(1);
            }
            config.saveConfig();
        });
        grid.add(zipTimesField,1,5);

        Label fileOps = new Label("文件操作:");
        grid.add(fileOps, 0, 6);

        CheckBox retainSourceCheck = new CheckBox("是否保留源文件");
        retainSourceCheck.setSelected(config.getRetainSourceFile());
        grid.add(retainSourceCheck,1,6);

        CheckBox differentRegularCheckBox = new CheckBox("是否生成不同分辨率规格");
        differentRegularCheckBox.setSelected(config.getDifferentRegular());
        Tooltip tooltip = new Tooltip("生成不同分辨率的压缩图");
        tooltip.setFont(noticeFont);
        differentRegularCheckBox.setTooltip(tooltip);
        differentRegularCheckBox.selectedProperty().addListener((ov, oldVal, newVal) -> {
            toggleGroup.setDisable(!newVal);
            zipType.setDisable(!newVal);
            zipTimes.setDisable(!newVal);
            zipTimesField.setDisable(!newVal);
            config.setDifferentRegular(newVal);
            config.saveConfig();
        });

        grid.add(differentRegularCheckBox,1,3);

        saveButton = new Button("生成图片");
        saveButton.setFont(Font.font("宋体", FontWeight.LIGHT, 30));
        saveButton.setMinSize(250,100);
        saveButton.setId("save-button");
        saveButton.setOnAction(action ->{
            saveButton.setDisable(true);
            differentStandardThumbnail(listData,status,saveButton);
        });
        grid.add(saveButton, 0, 12,2,2);
        status = new Text("等待加入图片");
        Label currentStatusLabel = new Label("当前状态:");
        grid.add(currentStatusLabel, 0, 15);
        grid.add(status, 1, 15);

        rootStackPane.setCenter(grid);
    }

    public void differentStandardThumbnail(List<File> fileList, Text status, Button saveButton) {
        List<File> distinctFileList =
                fileList.stream()
                        .distinct()
                        .filter(file -> file.exists() && file.isFile() && FileToolkit.isImg(FileToolkit.getFileSuffix(file.getName())))
                        .collect(Collectors.toList());
        if (distinctFileList.size() > 0){
            status.setText("待生成 "+distinctFileList.size()+ " 个图像资源.");
            Integer zipTimes = config.getZipTimes();
            String lastSaveDir = config.getLastSaveDir();
            if (!"".equals(lastSaveDir)){
                File file = new File(lastSaveDir);
                if (!file.exists()){
                    file.mkdir();
                }
                if (file.exists() && file.isDirectory()){
                    status.setText("目标地址校验成功.[√]");
                }else {
                    status.setText("保存目录错误.[目标地址不是目录×]");
                    saveButton.setDisable(false);
                    return;
                }
            }else {
                saveButton.setDisable(false);
                status.setText("保存目录错误.[空文件夹×]");
                return;
            }
            AtomicInteger count = new AtomicInteger();
            distinctFileList.forEach(item ->{
                String suffix = item.getName().substring(item.getName().lastIndexOf(".") + 1);
                String nameType = config.getSaveFileRenameType();
                String fileName = "UNKNOWN-"+item.hashCode();
                int point = item.getName().lastIndexOf(".");
                if ("UUID".equals(nameType)){
                    fileName = UUID.randomUUID().toString().replace("-","");
                }else if ("SOURCE".equals(nameType)){
                    fileName = item.getName().substring(0,point);
                }
                if (config.getDifferentRegular()){
                    double avg = 0.8D / zipTimes;
                    for (Integer i = 1; i <= zipTimes; i++) {
                        double currentQuality = i * avg;
//                        组合文件目录
                        switch (config.getZipType()){
                            case "1":
                                File quality = concatFileName(lastSaveDir, fileName, "Q", i, suffix);
                                status.setText("正在压缩"+item.getName()+"[Q"+i+"]");
                                try {
                                    ImageCompressToolKit.linearCompression(item,quality,0,0,Float.parseFloat(String.valueOf(currentQuality)));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "2":
                                File ratio = concatFileName(lastSaveDir, fileName, "R", i, suffix);
                                status.setText("正在压缩"+item.getName()+"[R"+i+"]");
                                try {
                                    ImageCompressToolKit.thumbnail(item,ratio,currentQuality);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "0":
                            default:
                                try {
                                    File all_ratio = concatFileName(lastSaveDir, fileName, "R", i, suffix);
                                    ImageCompressToolKit.thumbnail(item,all_ratio,currentQuality);
                                    status.setText("正在压缩"+item.getName()+"[R"+i+"]");
                                    File all_quality = concatFileName(lastSaveDir, fileName, "Q", i, suffix);
                                    ImageCompressToolKit.linearCompression(item,all_quality,0,0,Float.parseFloat(String.valueOf(currentQuality)));
                                    status.setText("正在压缩"+item.getName()+"[Q"+i+"]");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        count.getAndIncrement();
                    }
                }else {
                    try {
                        ImageCompressToolKit.thumbnail(item,concatFileName(lastSaveDir,fileName,"0",0,suffix),0.5);
                        ImageCompressToolKit.linearCompression(item,concatFileName(lastSaveDir,fileName,"0",0,suffix),0,0,0.5F);
                        status.setText("正在压缩"+item.getName()+"[0]");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!config.getRetainSourceFile()){
                    item.delete();
                }
            });
            status.setText("压缩已完成. ["+count.get()+"]");
            saveButton.setDisable(false);
        }else {
            status.setText("可执行图片数量为0.[×]");
            saveButton.setDisable(false);
        }
    }
    private File concatFileName(String dir,String fileName,String middle,int currentTimes,String suffix){
        StringBuilder builder = new StringBuilder(dir);
        builder.append("\\");
        builder.append(fileName);
        builder.append("_").append(middle).append("_");
        builder.append(currentTimes);
        builder.append(".");
        builder.append(suffix);
        return new File(builder.toString());
    }

}
