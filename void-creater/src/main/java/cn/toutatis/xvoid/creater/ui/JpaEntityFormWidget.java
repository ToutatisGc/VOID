package cn.toutatis.xvoid.creater.ui;

import cn.toutatis.xvoid.creater.ManifestDestiny;
import cn.toutatis.xvoid.toolkit.objects.ObjectToolkit;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static cn.toutatis.xvoid.creater.ui.ManifestDestinyComponent.manifestToolkit;

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
                addFieldButton.setText("????????????");
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
            addFieldButton.setText("????????????");
        });
        fieldName.getDocument().addDocumentListener(new DocumentListener() {
            private void checkExist(){
                if (!fieldMap.containsKey(fieldName.getText())){ addFieldButton.setText("????????????"); }
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
                if (ObjectToolkit.getInstance().strNotBlank(aDefault)){
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
                        "rInt int null comment '???????????????',\n" +
                        "rStr varchar(255) null comment '???????????????',\n" +
                        "createTime datetime not null comment '????????????',\n" +
                        "createBy varchar(32)  default 'SYSTEM' null comment '???????????????',\n" +
                        "lastUpdateTime datetime not null comment '??????????????????',\n" +
                        "updateBy varchar(32) default 'SYSTEM' null comment '???????????????',\n" +
                        "version int default 0 not null comment '?????????',\n" +
                        "status tinyint not null comment '????????????',\n" +
                        "logicDeleted tinyint default 0 not null comment '0??????:1??????',\n" +
                        "remark varchar(255) null comment '??????',\n" +
                        "belongTo varchar(255) default 'SYSTEM' null comment '??????',"
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
                JOptionPane.showMessageDialog(null, "?????????["+tableNameText.getText()+"]??????");
                ManifestDestiny.tableList.add(tableNameText.getText());
                ManifestDestinyComponent.changeListData(ManifestDestiny.tableList);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null, throwables.getMessage(),"????????????", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}
