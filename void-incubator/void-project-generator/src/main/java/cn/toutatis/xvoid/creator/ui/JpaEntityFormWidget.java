package cn.toutatis.xvoid.creator.ui;

import cn.toutatis.xvoid.creator.ManifestDestiny;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSONObject;

import jakarta.swing.*;
import jakarta.swing.event.DocumentEvent;
import jakarta.swing.event.DocumentListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static cn.toutatis.xvoid.creator.ui.ManifestDestinyComponent.manifestToolkit;

public class JpaEntityFormWidget {
    public JPanel rootPanel;
    private JList fieldList;
    private JTextField fieldName;
    private JTextField tableNameText;
    private JCheckBox isPrimKey;
    private JComboBox<String> fieldType;
    private JSpinner fieldLength;
    private JButton addFieldButton;
    private JButton removeFieldButton;
    private JCheckBox fieldUnique;
    private JTextField comment;
    private JCheckBox fieldNullable;
    private JButton generateButton;
    private JLabel tableNameLabel;
    private JPanel tablePanel;
    private JPanel fieldPanel;
    private JLabel fieldNameLabel;
    private JLabel keyLabel;
    private JLabel fieldTypeLabel;
    private JLabel opLabel;
    private JLabel commentLabel;
    private JLabel lengthLabel;
    private JCheckBox generateStructSameTime;
    private JTextArea tableComment;
    private JLabel tableCommentLabel;
    private JTextField defaultValue;
    private JLabel defaultLabel;
    private JCheckBox extendsBaseProperties;
    private JCheckBox autoIncrement;

    private final LinkedHashMap<String, JSONObject> fieldMap = new LinkedHashMap<>();

    public JpaEntityFormWidget() {

        ArrayList<String> strings = new ArrayList<>();
        addFieldButton.addActionListener(actionEvent -> {
            if ("".equals(fieldName.getText())){ return; }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type",fieldType.getSelectedItem());
            jsonObject.put("length",fieldLength.getValue());
            jsonObject.put("isPrim",isPrimKey.isSelected());
            jsonObject.put("nullable",fieldNullable.isSelected());
            jsonObject.put("unique",fieldUnique.isSelected());
            jsonObject.put("autoIncrement",autoIncrement.isSelected());
            jsonObject.put("default",defaultValue.getText());
            jsonObject.put("comment",comment.getText());
            fieldMap.put(fieldName.getText(),jsonObject);
            fieldList.setListData(fieldMap.keySet().toArray());
        });
        fieldList.addListSelectionListener(listSelectionEvent -> {
            if (listSelectionEvent.getValueIsAdjusting()){
                int selectedIndex = fieldList.getSelectedIndex();
                String select = (String) fieldMap.keySet().toArray()[selectedIndex];
                JSONObject jsonObject = fieldMap.get(select);
                for (int i = 0; i < fieldType.getItemCount(); i++) {
                    String itemAt = fieldType.getItemAt(i);
                    if (itemAt.equals(jsonObject.getString("type"))){
                        fieldType.setSelectedIndex(i);
                        break;
                    }
                }
                fieldName.setText(select);
                fieldLength.setValue(jsonObject.get("length"));
                isPrimKey.setSelected(jsonObject.getBoolean("isPrim"));
                fieldNullable.setSelected(jsonObject.getBoolean("nullable"));
                fieldUnique.setSelected(jsonObject.getBoolean("unique"));
                autoIncrement.setSelected(jsonObject.getBoolean("autoIncrement"));
                defaultValue.setText(jsonObject.getString("default"));
                comment.setText(jsonObject.getString("comment"));
                addFieldButton.setText("覆盖保存");
            }
        });
        removeFieldButton.addActionListener(actionEvent -> {
            int selectedIndex = fieldList.getSelectedIndex();
            String select = (String) fieldMap.keySet().toArray()[selectedIndex];
            fieldMap.remove(select);
            fieldName.setText("");
            fieldLength.setValue(0);
            isPrimKey.setSelected(false);
            fieldNullable.setSelected(false);
            fieldUnique.setSelected(false);
            autoIncrement.setSelected(false);
            comment.setText("");
            defaultValue.setText("");
            fieldList.setListData(fieldMap.keySet().toArray());
            addFieldButton.setText("添加字段");
        });
        fieldName.getDocument().addDocumentListener(new DocumentListener() {
            private void checkExist(){
                if (!fieldMap.containsKey(fieldName.getText())){ addFieldButton.setText("添加字段"); }
            }
            @Override
            public void insertUpdate(DocumentEvent e) {checkExist();}
            @Override
            public void removeUpdate(DocumentEvent e) {checkExist();}
            @Override
            public void changedUpdate(DocumentEvent e) {checkExist();}
        });
        generateStructSameTime.setSelected(Boolean.parseBoolean(manifestToolkit.getConfigProperties("generateStructSameTime")));
        generateStructSameTime.addActionListener(e ->
                manifestToolkit.saveConfiguration("generateStructSameTime",generateStructSameTime.isSelected()+"")
        );
        extendsBaseProperties.setSelected(Boolean.parseBoolean(manifestToolkit.getConfigProperties("extendsBaseProperties")));
        extendsBaseProperties.addActionListener(e ->
                manifestToolkit.saveConfiguration("extendsBaseProperties",extendsBaseProperties.isSelected()+"")
        );

        generateButton.addActionListener(actionEvent -> {
            StringBuilder ddl = new StringBuilder("CREATE TABLE ");
            ddl.append(tableNameText.getText()).append(" (");
            Object[] keys = fieldMap.keySet().toArray();
            ArrayList<String> primKeyList = new ArrayList<>();
            ArrayList<String> uniqueList = new ArrayList<>();
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i].toString();
                JSONObject value = fieldMap.get(key);
                ddl.append("`").append(key).append("` ");
                String type = value.getString("type");
                switch (type){
                    case "INT":
                        ddl.append(type).append(" ");
                        break;
                    case "VARCHAR":
                    default:
                        ddl.append(type).append("(").append(value.getIntValue("length")).append(") ");
                        break;
                }
                if (value.getBoolean("nullable")){ ddl.append("NOT NULL "); }
                if (value.getBoolean("autoIncrement")){ ddl.append("AUTO_INCREMENT "); }
                String aDefault = value.getString("default");
                if (Validator.strNotBlank(aDefault)){
                    ddl.append("DEFAULT ");
                    if ("VARCHAR".equals(type)){
                        ddl.append("'").append(aDefault).append("'");
                    }else{
                        ddl.append(aDefault);
                    }
                }
                ddl.append(",\n");
                if(value.getBoolean("isPrim")){
                    primKeyList.add(key);
                }
                if(value.getBoolean("unique")){
                    uniqueList.add(key);
                }
            }
            if(Boolean.parseBoolean(manifestToolkit.getConfigProperties("extendsBaseProperties"))){
                ddl.append(
                        "rInt int null comment '预留整形值',\n" +
                        "rStr varchar(255) null comment '预留字符串',\n" +
                        "createTime datetime default current_timestamp() not null comment '创建日期',\n" +
                        "createBy varchar(32)  default 'SYSTEM' null comment '创建操作人',\n" +
                        "lastUpdateTime datetime default current_timestamp() not null comment '最后更新日期',\n" +
                        "updateBy varchar(32) default 'SYSTEM' null comment '更新操作人',\n" +
                        "version int default 0 not null comment '版本号',\n" +
                        "status tinyint default 0 not null comment '数据状态',\n" +
                        "logicDeleted tinyint default 0 not null comment '0正常:1删除',\n" +
                        "remark varchar(255) null comment '备注',\n" +
                        "belongTo varchar(255) default 'SYSTEM' null comment '归属',"
                );
            }
            if (primKeyList.size() > 0){
                ddl.append("PRIMARY KEY (");
                for (int i = 0; i < primKeyList.size(); i++) {
                    String key = primKeyList.get(i);
                    ddl.append("`").append(key).append("`");
                    if (i != primKeyList.size() - 1){
                        ddl.append(",");
                    }
                }
                ddl.append(")");
                if (uniqueList.size() > 0){
                    ddl.append(",");
                }
            }
            if (uniqueList.size() > 0){
                ddl.append("UNIQUE INDEX (");
                for (int i = 0; i < uniqueList.size(); i++) {
                    String key = uniqueList.get(i);
                    ddl.append("`").append(key).append("`");
                    if (i != uniqueList.size() -1){
                        ddl.append(",");
                    }
                }
                ddl.append(")");
            }
            ddl.append(") COMMENT = '").append(tableComment.getText()).append("';");
            System.err.println(ddl.toString());
            try (Connection connect = manifestToolkit.getConnect();
                 PreparedStatement preparedStatement = connect.prepareStatement(ddl.toString());
            ) {
                preparedStatement.execute();
                JOptionPane.showMessageDialog(null, "生成表["+tableNameText.getText()+"]成功");
                ManifestDestiny.tableList.add(tableNameText.getText());
                ManifestDestinyComponent.changeListData(ManifestDestiny.tableList);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null, throwables.getMessage(),"生成失败", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}
