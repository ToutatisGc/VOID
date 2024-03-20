package cn.toutatis.xvoid.creator;

import cn.toutatis.xvoid.creator.exception.ConfigFileMissingException;
import cn.toutatis.xvoid.creator.tools.ConfigurationTable;
import cn.toutatis.xvoid.creator.tools.ManifestToolkit;

import cn.toutatis.xvoid.creator.ui.ManifestDestinyComponent;
import cn.toutatis.xvoid.creator.ui.listener.PlaceholderFocusListener;
import com.formdev.flatlaf.FlatIntelliJLaf;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
 * @author Gc
 */
public class ManifestDestiny {


    public enum DirClassify{BLANK,POM}

    private static final Logger logger = Logger.getLogger(ManifestDestiny.class);
    /**
     * 初始化工具箱
     */
    private static final ManifestToolkit manifestToolkit = ManifestToolkit.getInstance();
    /**
     * 窗体配置
     */
    public static final Integer DIALOG_WEIGHT = 900;
    public static final Integer DIALOG_HEIGHT = 600;
    private static final String COPY_INFO = ConfigurationTable.COPY_RIGHT_INFO.getInfo();
    /**
     * 颜色配置
     */
    private static final Integer GREY_COLOR_BRUNET = ConfigurationTable.GREY_COLOR_BRUNET.getInt();
    private static final Integer GREY_COLOR_TINT = ConfigurationTable.GREY_COLOR_TINT.getInt();
    private static final Color PANEL_BACKGROUND_COLOR = new Color(GREY_COLOR_BRUNET, GREY_COLOR_BRUNET, GREY_COLOR_BRUNET);
    private static final Color PANEL_BORDER_COLOR = new Color(GREY_COLOR_TINT, GREY_COLOR_TINT, GREY_COLOR_TINT);

    /**
     * 项目路径相关获取配置
     */
    private static final String ROOT_PATH = manifestToolkit.getRootPath();
    private static final File CONFIG_FILE = new File(ROOT_PATH+"/config.properties");
    private static final Properties CONFIG_PROPERTIES = new Properties();

    public static String dirClassify;
    public static String initPath;
    private static Boolean initComplete = false;

    public static Vector<String> tableList = new Vector<>();


    /**
     * 初始化窗体
     */
    public static JFrame manifest;
    public static List<String> selectTableList = new ArrayList<>();


    static {
        try {
            //使用主题和初始化样式
            //二次开发或缺少依赖请把目录下的beautyeye_lnf.jar添加到lib
//            BeautyEyeLNFHelper.debug = false;
//            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
//            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            FlatIntelliJLaf.setup();
            CONFIG_PROPERTIES.load(new FileInputStream(CONFIG_FILE));
            logger.info("[初始化]加载config.properties配置文件完成.");
        } catch (IOException e) {
            try {
                throw new ConfigFileMissingException(ConfigFileMissingException.MISSING_MESSAGE,e.getCause());
            } catch (ConfigFileMissingException ex) {
                int confirmDialog = JOptionPane.showConfirmDialog(manifest, ConfigurationTable.MISSING_CONFIG_FILE_HINT.getInfo(),
                        ConfigurationTable.NOTIFY_WINDOW_TITLE.getInfo()
                        , JOptionPane.CLOSED_OPTION
                );
                if (confirmDialog == 0 || confirmDialog == -1){
                    logger.error("[初始化]配置文件丢失.");
                    System.exit(confirmDialog);
                }
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[初始化]主题加载失败.");
        }
        initPath = CONFIG_PROPERTIES.getProperty("currentChoosePath");
        logger.info("[初始化]ManifestDestiny初始化完成.");
    }

    public static Connection connect = null;
    private static SqlSession sqlExecutor = null;


    private static final Border lineBorder = BorderFactory.createLineBorder(PANEL_BORDER_COLOR);

    public static void main(String[] args) throws Exception {

        //窗体宽度和高度
         manifest = ManifestDestinyComponent.manifest(connect);

//         manifest.setContentPane;

        String databaseUrlConfig = CONFIG_PROPERTIES.getProperty("databaseUrl");
        String userConfig = CONFIG_PROPERTIES.getProperty("user");
        String passwordConfig = CONFIG_PROPERTIES.getProperty("password");

        if (manifestToolkit.isBlank(databaseUrlConfig,userConfig,passwordConfig)){
             openDatabaseConfigurationWindow(null);
        }else {
            if (ConfigurationTable.SUCCESSFUL_SIGN.getInfo().equals(manifestToolkit.getConfigProperties("lastTimeIsSuccessful"))){
                try{
                    connect = manifestToolkit.getConnect();
                    SqlSessionFactory sqlExecutorFactory = manifestToolkit.getSQLExecutorFactory();
                    sqlExecutor = sqlExecutorFactory.openSession(connect);
                    PreparedStatement preparedStatement = connect.prepareStatement("select table_name from information_schema.tables where table_schema= ?");
                    preparedStatement.setString(1,connect.getCatalog());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()){
                        tableList.add(resultSet.getString("table_name"));
                    }
                    logger.info("[初始化]数据库连接正常.[0]");
                }
                catch (Exception e){
                    logger.error("[异常]连接状态错误.[0]");
                    int close = JOptionPane.showConfirmDialog(manifest, ConfigurationTable.DATABASE_ERROR_MESSAGE.getInfo(), ConfigurationTable.DATABASE_CONFIGURATION_SOURCE_LABEL.getInfo(), JOptionPane.CLOSED_OPTION);
                    switch (close){
                        case -1:
                            logger.warn("[登出]连接错误并且关闭窗口.");
                            System.exit(0);
                            break;
                        case 0:
                            logger.warn("[警告]连接数据库尝试[0]");
                            openDatabaseConfigurationWindow(null);
                            break;
                        default:
                            logger.error("[异常]连接错误并且意外退出.");
                            System.exit(-1);
                    }
                }
            }else if (ConfigurationTable.FAILURE_SIGN.getInfo().equals(manifestToolkit.getConfigProperties("lastTimeIsSuccessful"))){
                openDatabaseConfigurationWindow(null);
            }else {
                logger.error("[异常]连接状态错误.[-1]");
                System.exit(-1);
            }
        }


//        左侧菜单
        JComponent leftPanel = ManifestDestinyComponent.leftPanel(tableList,selectTableList);
        leftPanel.setBorder(lineBorder);
        manifest.add(leftPanel, BorderLayout.WEST);
//        设置顶部菜单栏1
        JMenuBar menuBar = ManifestDestinyComponent.menuBar();
        manifest.setJMenuBar(menuBar);
//        右侧主页面
        JPanel mainPanel = ManifestDestinyComponent.mainPanel();
        mainPanel.setBorder(lineBorder);
        mainPanel.setBackground(PANEL_BACKGROUND_COLOR);
//        主界面内容
        JPanel mainContentPanel = ManifestDestinyComponent.mainContentPanel();
        mainPanel.add(mainContentPanel);
        manifest.add(mainPanel);
        JPanel copyRightPanel = ManifestDestinyComponent.copyRightPanel(COPY_INFO);
        manifest.add(copyRightPanel);
//        重新绘制窗口
        manifest.validate();
//        加载完成标志
        initComplete = true;
        logger.info("[初始化]框体加载完成.");
    }


    private static final Integer FIELD_WEIGHT = 30;
    /**
     * @param parentComponent 父组件，暂时无用
     */
    public static void openDatabaseConfigurationWindow(JComponent parentComponent){
        JDialog dataBaseConfigFrame = new JDialog(manifest,ConfigurationTable.LEFT_PANEL_DATABASE_CONFIGURATION_BUTTON.getInfo(),true);

        dataBaseConfigFrame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        dataBaseConfigFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String lastTimeIsSuccessful = manifestToolkit.getConfigProperties("lastTimeIsSuccessful");
                if ("1".equals(lastTimeIsSuccessful)){
                    dataBaseConfigFrame.dispose();
                }else{
                    super.windowClosing(e);
                }
            }
        });

        dataBaseConfigFrame.setLayout(new FlowLayout());
//        dataBaseConfigFrame.setLayout(null);
        JPanel databasePanel = new JPanel();
        JPanel databaseSource = new JPanel();
        JLabel databaseSource_label = new JLabel(ConfigurationTable.DATABASE_CONFIGURATION_SOURCE_LABEL.getInfo());
        databaseSource.add(databaseSource_label,BorderLayout.WEST);
        String databaseUrl = manifestToolkit.getConfigProperties("databaseUrl");
        JTextField databaseSource_field = new JTextField("".equals(databaseUrl) ? ConfigurationTable.DATA_SOURCE_LABEL.getInfo() : databaseUrl,FIELD_WEIGHT);
        databaseSource_field.addFocusListener(new PlaceholderFocusListener(databaseSource_field,ConfigurationTable.DATA_SOURCE_LABEL.getInfo()));
        databaseSource_field.setCaretPosition(0);
        databaseSource.add(databaseSource_field);

        JPanel databaseUserName = new JPanel();
        JLabel databaseUserName_label = new JLabel(ConfigurationTable.DATABASE_CONFIGURATION_USERNAME_LABEL.getInfo());
        databaseUserName.add(databaseUserName_label,BorderLayout.WEST);
        String user = manifestToolkit.getConfigProperties("user");
        JTextField databaseUserName_field = new JTextField("".equals(user) ? ConfigurationTable.DATA_USERNAME_LABEL.getInfo() : user,FIELD_WEIGHT);
        databaseUserName_field.addFocusListener(new PlaceholderFocusListener(databaseUserName_field,ConfigurationTable.DATA_USERNAME_LABEL.getInfo()));
        databaseUserName.add(databaseUserName_field);

        JPanel databasePassword = new JPanel();
        JLabel databasePassword_label = new JLabel(ConfigurationTable.DATABASE_CONFIGURATION_PASSWORD_LABEL.getInfo());
        String password = manifestToolkit.getConfigProperties("password");
        password = new String(Base64.getDecoder().decode(password.getBytes()));
        StringBuilder star = new StringBuilder();
        for (int i = 0; i < password.length(); i++) { star.append("*"); }
        JTextField jPasswordField = new JTextField("".equals(password) ? ConfigurationTable.DATA_PASSWORD_LABEL.getInfo() : star.toString(),FIELD_WEIGHT);
        jPasswordField.addFocusListener(new PlaceholderFocusListener(jPasswordField,ConfigurationTable.DATA_USERNAME_LABEL.getInfo()));
        databasePassword.add(databasePassword_label,BorderLayout.WEST);
        databasePassword.add(jPasswordField);


        databasePanel.add(databaseSource,BorderLayout.NORTH);
        databasePanel.add(databaseUserName,BorderLayout.NORTH);
        databasePanel.add(databasePassword,BorderLayout.NORTH);

        JButton saveButton = new JButton("保   存");
        saveButton.setEnabled(false);
        saveButton.addActionListener(actionEvent -> dataBaseConfigFrame.dispose());

        JButton testConnect  = new JButton("测试连接");
        testConnect.addActionListener(actionEvent -> {
            manifestToolkit.saveConfiguration("databaseUrl",databaseSource_field.getText().equals(ConfigurationTable.DATA_SOURCE_LABEL.getInfo())? "" : databaseSource_field.getText());
            manifestToolkit.saveConfiguration("user",databaseUserName_field.getText().equals(ConfigurationTable.DATA_USERNAME_LABEL.getInfo())? "" : databaseUserName_field.getText());
            String jPasswordFieldText = jPasswordField.getText();
            String starStr = (jPasswordFieldText.startsWith("*") && jPasswordFieldText.endsWith("*")) ? CONFIG_PROPERTIES.getProperty("password") : Base64.getEncoder().encodeToString(jPasswordFieldText.getBytes());
            manifestToolkit.saveConfiguration("password",jPasswordField.getText().equals(ConfigurationTable.DATA_PASSWORD_LABEL.getInfo())? "" : starStr);
            try {
                Map<String, Object> testMap = manifestToolkit.testConnect();
                Connection connection = (Connection) testMap.get("connection");
                connect = connection;
                SqlSessionFactory sqlExecutorFactory = manifestToolkit.getSQLExecutorFactory();
                sqlExecutor = sqlExecutorFactory.openSession(connect);
                String connectInfo = "[连接数据库成功]" + System.getProperty("line.separator") +
                        "[当前连接数据库 : " + connection.getCatalog() + " ]" + System.getProperty("line.separator") +
                        "[检索驱动等待请求时间 : " + connection.getNetworkTimeout() + " ms ]" + System.getProperty("line.separator");
                JOptionPane.showMessageDialog(manifest, connectInfo,"数据库连接-"+ConfigurationTable.NOTIFY_WINDOW_TITLE.getInfo(),JOptionPane.INFORMATION_MESSAGE);
                manifestToolkit.saveConfiguration("lastTimeIsSuccessful","1");
                manifestToolkit.saveConfiguration("dataSchema",connect.getCatalog());
                saveButton.setEnabled(true);
                connect = manifestToolkit.getConnect();
                sqlExecutorFactory = manifestToolkit.getSQLExecutorFactory();
                sqlExecutor = sqlExecutorFactory.openSession(connect);
                refreshTableList();
            } catch (SQLException e) {
                manifestToolkit.saveConfiguration("lastTimeIsSuccessful","0");
                manifestToolkit.saveConfiguration("databaseUrl","");
                manifestToolkit.saveConfiguration("user","");
                manifestToolkit.saveConfiguration("password","");
                e.printStackTrace();
                logger.error(e.getMessage());
                JOptionPane.showMessageDialog(manifest,e.getMessage(),ConfigurationTable.ERROR_WINDOW_TITLE.getInfo(),JOptionPane.ERROR_MESSAGE);
            }finally {
                if (connect != null){
                    try {
                        connect.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        logger.error(e.getMessage());
                    }
                }
            }
        });

        JButton exitButton = new JButton("退   出");
        exitButton.addActionListener(actionEvent -> {
            if (initComplete){ logger.info("[登出]正常退出."); }
            else { logger.warn("[登出]信息不正常.[连接信息不完全]"); }
            System.exit(0);
        });

        databasePanel.add(testConnect,BorderLayout.SOUTH);
        databasePanel.add(saveButton,BorderLayout.SOUTH);
        databasePanel.add(exitButton,BorderLayout.SOUTH);
        dataBaseConfigFrame.add(databasePanel);
        databasePanel.setPreferredSize(new Dimension(DIALOG_WEIGHT/2-FIELD_WEIGHT/2,DIALOG_HEIGHT/2-FIELD_WEIGHT));

        dataBaseConfigFrame.setResizable(false);
        dataBaseConfigFrame.setBounds(0, 0, DIALOG_WEIGHT/2, 250);
        dataBaseConfigFrame.setLocationRelativeTo(manifest);
        dataBaseConfigFrame.setVisible(true);
        dataBaseConfigFrame.setAlwaysOnTop(true);
    }

    public static void refreshTableList(){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connect.prepareStatement("select table_name from information_schema.tables where table_schema= ?");
            preparedStatement.setString(1,connect.getCatalog());
            ResultSet resultSet = preparedStatement.executeQuery();
            tableList.clear();
            while (resultSet.next()){
                tableList.add(resultSet.getString("table_name"));
            }
            ManifestDestinyComponent.changeListData(tableList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
