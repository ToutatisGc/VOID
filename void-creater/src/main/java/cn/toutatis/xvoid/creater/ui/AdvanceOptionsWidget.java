package cn.toutatis.xvoid.creater.ui;

import cn.toutatis.xvoid.creater.tools.ManifestToolkit;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author TouTatis_Gc
 * 高级配置面板
 */
public class AdvanceOptionsWidget {

    private static ManifestToolkit manifestToolkit = ManifestToolkit.getInstance();

    public JPanel rootPanel;
    private JPanel createrOptionsPanel;
    private JPanel tableOptionsPanel;
    private JCheckBox useKotlinUseJavaModel;
    private JTextField authorInfoText;
    private JLabel authorLabel;

    public AdvanceOptionsWidget() {
        authorInfoText.setText(manifestToolkit.getConfigProperties("author"));
        Document tablePrefixDocument = authorInfoText.getDocument();
        tablePrefixDocument.addDocumentListener(new DocumentListener() {
            private void updatePackagePath(){
                manifestToolkit.saveConfiguration("author",authorInfoText.getText());
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
    }
}
