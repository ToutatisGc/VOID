package cn.toutatis.xvoid.creator.ui;

import cn.toutatis.xvoid.creator.tools.ManifestToolkit;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

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
    public JComboBox<String> serviceTypeComboBox;
    private JLabel serviceLabel;

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
        serviceTypeComboBox.addActionListener(e -> {
            manifestToolkit.saveConfiguration("serviceType", (String) serviceTypeComboBox.getSelectedItem());
        });
    }
}
