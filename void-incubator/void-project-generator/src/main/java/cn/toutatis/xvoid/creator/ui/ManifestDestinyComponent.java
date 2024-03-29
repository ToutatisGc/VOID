package cn.toutatis.xvoid.creator.ui;



import cn.toutatis.xvoid.creator.ManifestDestiny;
import cn.toutatis.xvoid.creator.mapper.BasicMapper;
import cn.toutatis.xvoid.creator.tools.ConfigurationTable;
import cn.toutatis.xvoid.creator.tools.ManifestToolkit;
import cn.toutatis.xvoid.creator.tools.Parameter;
import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.*;

/**
 * @author Gc
 *
 */
public class ManifestDestinyComponent {

    public static Logger logger = Logger.getLogger(ManifestDestinyComponent.class);

    public static ManifestToolkit manifestToolkit = ManifestToolkit.getInstance();

    public static Connection connect = ManifestDestiny.connect;

    public static JTextArea selectedTable = new JTextArea("",3,40);

    public static JList<String> leftList = new JList<String>();

    static {
        leftList.setModel(new DefaultListModel());
    }
    /**
     * @return 头部菜单栏
     */
    public static JMenuBar menuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("首选项");
//        JMenuItem newMenuItem = new JMenuItem("新建");
//        JMenuItem openMenuItem = new JMenuItem("打开");
        JMenuItem dataSourceMenu = new JMenuItem("数据源配置");
        dataSourceMenu.addActionListener(e -> ManifestDestiny.openDatabaseConfigurationWindow(null));
        JMenuItem exitMenu = new JMenuItem("退出");
        exitMenu.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(ManifestDestiny.manifest, "确认退出？", ConfigurationTable.NOTIFY_WINDOW_TITLE.getInfo(), JOptionPane.YES_NO_OPTION);
            if (confirm == 0){ ManifestDestinyComponent.systemAbort(ManifestDestiny.connect); }
        });
        fileMenu.add(dataSourceMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenu);
        menuBar.add(fileMenu);
        return menuBar;
    }

    /**
     * @return 右侧主窗口
     */
    public static JPanel mainPanel(){
        JPanel main = new JPanel(new BorderLayout());
        main.setPreferredSize(new Dimension(ManifestDestiny.DIALOG_WEIGHT-250, 500));
        JPanel chooseAppPath = chooseAppPath();
        main.add(chooseAppPath,BorderLayout.NORTH);
        return main;
    }

    /**
     * @return 左侧数据库表栏
     */
    public static JPanel leftPanel(Vector<String> tableList,List<String> selectTableList){
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel text = new JPanel();
        JLabel dataBaseLabel = new JLabel(ConfigurationTable.LEFT_PANEL_DATABASE_LIST_TITLE.getInfo());
        text.add(dataBaseLabel);
        leftPanel.add(text,BorderLayout.NORTH);
        changeListData(tableList);
        leftList.addListSelectionListener(selectionEvent -> {
            if (selectionEvent.getValueIsAdjusting()){
                selectTableList.clear();
                List selectedValues = leftList.getSelectedValuesList();
                for (Object selectedValue : selectedValues) {
                    selectTableList.add((String) selectedValue);
                }
                selectedTable.setText(selectTableList.toString().replace("[","").replace("]",""));
            }
        });
        leftList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (leftList.getSelectedIndex() != -1){
                    if (e.getClickCount() == 2){
                        BasicMapper mapper = (BasicMapper) manifestToolkit.getMapper(BasicMapper.class);
                        Parameter parameter = new Parameter();
                        parameter.put("tableName",leftList.getSelectedValue());
                        List<Map<String,Object>> tableInfo = mapper.getTableInfo(parameter);
                        Object[] columnNames = {"序号","字段名","数据类型","允许NULL","列键","字符集","注释"};
                        Object[][] rowData = new Object[tableInfo.size()][7];
                        for (int i = 0; i < tableInfo.size(); i++) {
                            Map fieldInfo = tableInfo.get(i);
                            rowData[i][0] = fieldInfo.get("ORDINAL_POSITION");
                            rowData[i][1] = fieldInfo.get("COLUMN_NAME");
                            rowData[i][2] = fieldInfo.get("COLUMN_TYPE");
                            rowData[i][3] = fieldInfo.get("IS_NULLABLE");
                            rowData[i][4] = "PRI".equals(fieldInfo.get("COLUMN_KEY")) ? "主键" : fieldInfo.get("COLUMN_KEY");
                            rowData[i][5] = fieldInfo.get("COLLATION_NAME");
                            rowData[i][6] = fieldInfo.get("COLUMN_COMMENT");
                        }
                        JDialog details = new JDialog(ManifestDestiny.manifest,ConfigurationTable.SELECTED_DATABASE_LIST_LABEL.getInfo()+"["+leftList.getSelectedValue()+"]",false);
                        details.addWindowFocusListener(new WindowAdapter() {
                            @Override
                            public void windowLostFocus(WindowEvent e) {
                                details.dispose();
                            }
                        });
                        // 创建内容面板
                        JPanel tablePanel = new JPanel(new BorderLayout());
                        JTable table = new JTable(rowData, columnNames);
                        JTableHeader tableHeader = table.getTableHeader();
                        tableHeader.setReorderingAllowed(false);
                        tableHeader.setResizingAllowed(true);
                        DefaultTableCellRenderer cellRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
                        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

                        // 设置表格中的数据居中显示
                        DefaultTableCellRenderer columnRenderer=new DefaultTableCellRenderer();
                        columnRenderer.setHorizontalAlignment(JLabel.CENTER);
                        table.setDefaultRenderer(Object.class,columnRenderer);
                        fitTableColumns(table);

                        // 把 表头 添加到容器顶部（使用普通的中间容器添加表格时，表头 和 内容 需要分开添加）
                        tablePanel.add(tableHeader, BorderLayout.NORTH);
                        // 把 表格内容 添加到容器中心

                        tablePanel.add(table, BorderLayout.CENTER);

                        details.add(tablePanel);
                        details.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        details.setResizable(false);
                        details.setBounds(0, 0, ManifestDestiny.DIALOG_WEIGHT, 50+tableInfo.size() * 30);
                        details.setLocationRelativeTo(ManifestDestiny.manifest);
                        details.setVisible(true);
                    }
                }
            }
        });
        JScrollPane leftScroll = new JScrollPane(leftList);
        leftPanel.add(leftScroll);
        JButton dataBaseConnectButton = new JButton(ConfigurationTable.LEFT_PANEL_DATABASE_CONFIGURATION_BUTTON.getInfo());
        dataBaseConnectButton.addActionListener(actionEvent -> {
            ManifestDestiny.openDatabaseConfigurationWindow(dataBaseConnectButton);
        });
        leftPanel.add(dataBaseConnectButton,BorderLayout.SOUTH);
        leftPanel.setPreferredSize(new Dimension(200, ManifestDestiny.DIALOG_HEIGHT-100));
        return leftPanel;
    }

    public static JFrame manifest(Connection connect) throws IOException, URISyntaxException {
        JFrame manifest = new JFrame(ConfigurationTable.APP_NAME.getInfo());
        File iconFile = new File(Objects.requireNonNull(FileToolkit.findFileInPossibleLocation("/img/logo.png")).toURI());
        byte[] iconBytes = Files.readAllBytes(iconFile.toPath());
        manifest.setIconImage(new ImageIcon(iconBytes).getImage());
        manifest.setLayout(new FlowLayout(FlowLayout.LEFT));
        // 显示该Frame
        manifest.setVisible(true);
        //禁止改变窗体大小
        manifest.setResizable(false);
        //设置窗体大小
        manifest.setSize(ManifestDestiny.DIALOG_WEIGHT, ManifestDestiny.DIALOG_HEIGHT);
        //设置窗体居中
        manifest.setLocationRelativeTo(null);
        //Point point = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        //manifest.setBounds(point.x - DIALOG_WHITE / 2, point.y - DIALOG_HEIGHT / 2, DIALOG_WHITE, DIALOG_HEIGHT);

        //关闭事件监听器
        manifest.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                ManifestDestinyComponent.systemAbort(connect);
            }
        });
        //窗体置顶
//        manifest.setAlwaysOnTop(true);
        return manifest;
    }

    /**
     * @return 右侧主窗口内容
     */
    public static JPanel mainContentPanel(){
        selectedTable.setWrapStyleWord(true);
        selectedTable.setLineWrap(true);
        selectedTable.setEditable(false);
        selectedTable.setBackground(Color.WHITE);
        JPanel content = new JPanel(new GridLayout(1,1));
        JTabbedPane jTabbedPane = new JTabbedPane();
        JPanel tableStructPanel = new TableStructWidget().rootPanel;
        JPanel advanceOptionsPanel = new AdvanceOptionsWidget().rootPanel;
        JPanel JpaEntityFormWidget = new JpaEntityFormWidget().rootPanel;
        jTabbedPane.addTab("表结构生成",tableStructPanel);
        jTabbedPane.addTab("JPA实体生成",JpaEntityFormWidget);
        jTabbedPane.addTab("高级配置",advanceOptionsPanel);
        content.add(jTabbedPane);
        return content;
    }

    // 根据内容自动调节表格的列宽度
    @SuppressWarnings("rawtypes")
    private static void fitTableColumns(JTable myTable)
    {
        myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JTableHeader header = myTable.getTableHeader();
        int rowCount = myTable.getRowCount();
        Enumeration columns = myTable.getColumnModel().getColumns();
        while(columns.hasMoreElements())
        {
            TableColumn column = (TableColumn)columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int)header.getDefaultRenderer().getTableCellRendererComponent
                    (myTable, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
            for(int row = 0; row < rowCount; row++)
            {
                int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent
                        (myTable, myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            header.setResizingColumn(column);
            column.setWidth(width+myTable.getIntercellSpacing().width);
        }
    }

    private static JPanel chooseAppPath(){
        JPanel textGroup = new JPanel(new BorderLayout());
        JLabel title = new JLabel(" "+ConfigurationTable.MAIN_PANEL_CHOOSE_TITLE.getInfo()+" ：");
        boolean blankPath = "".equals(ManifestDestiny.initPath);
        JTextField pathStr = new JTextField(blankPath ? ConfigurationTable.MAIN_PANEL_CHOOSE_PATH_BLANK.getInfo():ManifestDestiny.initPath,1);
        pathStr.setEnabled(false);
        JButton chooseButton = new JButton(ConfigurationTable.MAIN_PANEL_CHOOSE_BUTTON.getInfo());
        textGroup.add(title,BorderLayout.WEST);
        textGroup.add(pathStr,BorderLayout.CENTER);
        textGroup.add(chooseButton,BorderLayout.EAST);
        final String DIV_HEADER = "<html><div style='color:red;height:20px;line-height:20px;margin-left:5px'>";
        final String DIV_ENDED = "</div></html>";
        boolean initExist = new File(ManifestDestiny.initPath + "/pom.xml").exists();
        String initTip;
        if (blankPath){
            initTip = ConfigurationTable.BLANK_PATH_HINT.getInfo();
        }else if (initExist){
            initTip = ConfigurationTable.IS_APP_DIR_HINT.getInfo();
        }else {
            initTip = ConfigurationTable.NOT_APP_DIR_HINT.getInfo();
        }
        JLabel chooseInfo = new JLabel(DIV_HEADER+initTip+DIV_ENDED);
        textGroup.add(chooseInfo,BorderLayout.SOUTH);
        chooseButton.addActionListener(actionEvent -> {
            File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
            JFileChooser fileChooser = new JFileChooser(homeDirectory);
//            禁止多选
            fileChooser.setMultiSelectionEnabled(false);
//            仅选择文件夹
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(chooseButton);
            if (result == JFileChooser.APPROVE_OPTION){
                File chooseDir = fileChooser.getSelectedFile();
                String chooseDirPath = chooseDir.getPath();
                pathStr.setText(chooseDirPath);
                ManifestDestiny.initPath = chooseDirPath;

                File chooseIsAppDir = new File(chooseDirPath + "/pom.xml");
                boolean pomExist = chooseIsAppDir.exists();
                if (pomExist){
                    ManifestDestiny.dirClassify = ManifestDestiny.DirClassify.POM.name();
                    chooseInfo.setText(DIV_HEADER+ConfigurationTable.IS_APP_DIR_HINT.getInfo()+DIV_ENDED);
                }else {
                    ManifestDestiny.dirClassify = ManifestDestiny.DirClassify.BLANK.name();
                    chooseInfo.setText(DIV_HEADER+ConfigurationTable.NOT_APP_DIR_HINT.getInfo()+DIV_ENDED);
                }
                manifestToolkit.saveConfiguration("currentChoosePath",chooseDirPath);
            }
        });
        return textGroup;
    }

    /**
     * @param info 版权信息
     * @return 底部信息
     */
    public static JPanel copyRightPanel(String info){
        JPanel line = new JPanel();
        JLabel text = new JLabel(info);
        line.add(text);
        return line;
    }

    /**
     * @param connect 连接信息
     *
     */
    private static void systemAbort(Connection connect){
        try {
            connect.close();
        } catch (SQLException ex) {
            logger.error("[数据源]数据库关闭失败.["+ex.getMessage()+"]");
            ex.printStackTrace();
        }finally {
            System.exit(0);
        }
    }

    public static void changeListData(Vector<String> dataList){
        DefaultListModel oldModel = (DefaultListModel) leftList.getModel();
//        TODO 移除旧列表
        DefaultListModel<String> model = new DefaultListModel<>();
        dataList.forEach(model::addElement);
        leftList.setModel(model);
        leftList.updateUI();
    }

}
