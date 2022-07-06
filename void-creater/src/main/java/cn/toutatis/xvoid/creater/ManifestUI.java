package cn.toutatis.xvoid.creater;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;

public class ManifestUI {
    private JPanel rootPanel;
    private JList list1;
    private JTabbedPane tabbedPane1;
    private JButton button1;
    private JTextField textField1;
    private JButton button2;
    private JTextArea textArea1;
    private JButton button3;
    private JButton button4;

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        JFrame frame = new JFrame("ManifestUI");
        frame.setContentPane(new ManifestUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
