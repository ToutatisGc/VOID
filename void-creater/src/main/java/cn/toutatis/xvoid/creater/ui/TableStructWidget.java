package cn.toutatis.xvoid.creater.ui;

import cn.toutatis.xvoid.creater.ManifestDestiny;
import cn.toutatis.xvoid.creater.tools.CodeGenerator;
import cn.toutatis.xvoid.creater.tools.ConfigurationTable;
import cn.toutatis.xvoid.creater.tools.ManifestToolkit;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static cn.toutatis.xvoid.creater.ui.ManifestDestinyComponent.selectedTable;

/**
 * @author Toutatis_Gc
 * 对于表结构的组件
 */
public class TableStructWidget {

    private static ManifestToolkit manifestToolkit = ManifestToolkit.getInstance();

    public JPanel rootPanel;
    private JButton createTableButton;
    private JLabel entityParamLabel;
    private JLabel chooseTableLabel;
    private JScrollPane chooseScrollPanel;
    private JPanel paramPanel;
    private JLabel createControlLabel;
    private JComboBox<String> useDevLang;
    private JLabel devLangLabel;
    private JTextField selectPackPath;
    private JLabel choosePackageLabel;
    private JTextField tablePrefixStr;
    private JLabel tablePrefixLabel;
    private JCheckBox useSwagger;
    private JCheckBox useLombok;
    private JCheckBox usePersistence;
    private JCheckBox createController;
    private JCheckBox useGenerateJpa;

    public TableStructWidget() {
        /*绑定配置中的包路径和输入事件*/
        selectPackPath.setText(manifestToolkit.getConfigProperties("packagePath"));
        Document packagePathDocument = selectPackPath.getDocument();
        packagePathDocument.addDocumentListener(new DocumentListener() {
            private void updatePackagePath(){
                manifestToolkit.saveConfiguration("packagePath",selectPackPath.getText());
            }
            @Override public void insertUpdate(DocumentEvent e) {
                updatePackagePath();
            }
            @Override public void removeUpdate(DocumentEvent e) {
                updatePackagePath();
            }
            @Override public void changedUpdate(DocumentEvent e) {
                updatePackagePath();
            }
        });

        /*绑定表前缀*/
        tablePrefixStr.setText(manifestToolkit.getConfigProperties("tablePrefix"));
        Document tablePrefixDocument = tablePrefixStr.getDocument();
        tablePrefixDocument.addDocumentListener(new DocumentListener() {
            private void updatePackagePath(){
                manifestToolkit.saveConfiguration("tablePrefix",tablePrefixStr.getText());
            }
            @Override public void insertUpdate(DocumentEvent e) {
                updatePackagePath();
            }
            @Override public void removeUpdate(DocumentEvent e) {
                updatePackagePath();
            }
            @Override public void changedUpdate(DocumentEvent e) {
                updatePackagePath();
            }
        });

        /*滚动栏绑定选择表的JList列表*/
        chooseScrollPanel.setViewportView(selectedTable);
        /*创建表结构的按钮绑定创建表事件*/
        createTableButton.addActionListener(actionEvent -> {
            String selectedTableText = selectedTable.getText();
            String[] tableNameArrays = selectedTableText.replace(" ", "").split(",");
            List<String> tableNames = Arrays.asList(tableNameArrays);
            if (!"".equals(selectedTableText) && tableNames.size()>0){
                CodeGenerator.tableGenerate(tableNameArrays);
            }else {
                JOptionPane.showConfirmDialog(ManifestDestiny.manifest,
                        ConfigurationTable.NULL_SELECT_TABLE_HINT.getInfo()
                        ,ConfigurationTable.NOTIFY_WINDOW_TITLE.getInfo()
                        ,JOptionPane.CLOSED_OPTION);
            }
        });

        /*绑定是否使用Swagger*/
        useSwagger.setSelected(Boolean.parseBoolean(manifestToolkit.getConfigProperties("useSwagger")));
        useSwagger.addChangeListener(changeEvent -> {
            manifestToolkit.saveConfiguration("useSwagger",useSwagger.isSelected()+"");
        });
        /*绑定是否使用Lombok*/
        useLombok.setSelected(Boolean.parseBoolean(manifestToolkit.getConfigProperties("useLombok")));
        useLombok.addChangeListener(changeEvent -> {
            manifestToolkit.saveConfiguration("useLombok",useLombok.isSelected()+"");
        });
        /*绑定是否使用Persistence*/
        usePersistence.setSelected(Boolean.parseBoolean(manifestToolkit.getConfigProperties("usePersistence")));
        usePersistence.addChangeListener(changeEvent -> {
            manifestToolkit.saveConfiguration("usePersistence",usePersistence.isSelected()+"");
        });
        /*绑定是否生成Controller*/
        createController.setSelected(Boolean.parseBoolean(manifestToolkit.getConfigProperties("useGenerateController")));
        createController.addChangeListener(changeEvent -> {
            manifestToolkit.saveConfiguration("useGenerateController",createController.isSelected()+"");
        });
        /*绑定是否使用JPA*/
        useGenerateJpa.setSelected(Boolean.parseBoolean(manifestToolkit.getConfigProperties("useGenerateJpa")));
        useGenerateJpa.addChangeListener(changeEvent -> {
            manifestToolkit.saveConfiguration("useGenerateJpa",useGenerateJpa.isSelected()+"");
        });

        /*绑定开发语言模型*/
        Vector<String> classify =  new Vector<>(2);
        classify.add("Kotlin");
        classify.add("Java");
        String devLangValue = manifestToolkit.getConfigProperties("devLang");
        if ("kotlin".equalsIgnoreCase(devLangValue)) {
            useLombok.setEnabled(false);
            useLombok.setSelected(false);
            manifestToolkit.saveConfiguration("useLombok","false");
        }
        int classifyIndex = 0;
        for (int i = 0; i < classify.size(); i++) {
            if (devLangValue.equalsIgnoreCase(classify.get(i))){
                classifyIndex = i;
                break;
            }
        }
        useDevLang.setSelectedIndex(classifyIndex);
        useDevLang.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                int selectedIndex = useDevLang.getSelectedIndex();
                String select = classify.get(selectedIndex);
                if ("kotlin".equalsIgnoreCase(select)) {
                    useLombok.setEnabled(false);
                    useLombok.setSelected(false);
                    manifestToolkit.saveConfiguration("useLombok","false");
                }else {
                    useLombok.setEnabled(true);
                    useLombok.setSelected(Boolean.parseBoolean(manifestToolkit.getConfigProperties("useLombok")));
                }
                manifestToolkit.saveConfiguration("devLang",select.toLowerCase());
            }
        });

    }


}
