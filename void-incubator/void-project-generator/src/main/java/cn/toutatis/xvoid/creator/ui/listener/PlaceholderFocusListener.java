package cn.toutatis.xvoid.creator.ui.listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * 占位符 listener
 * @author Toutatis_Gc
 */
public class PlaceholderFocusListener implements FocusListener {

    public PlaceholderFocusListener(JTextField jTextField, String placeholder) {
        this.jTextField = jTextField;
        this.placeholder = placeholder;
    }

    private final JTextField jTextField;

    private final String placeholder;

    @Override
    public void focusGained(FocusEvent e) {
        if (placeholder.equals(jTextField.getText())) {
            jTextField.setText("");
            jTextField.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().isEmpty()) {
            jTextField.setText(placeholder);
            jTextField.setForeground(Color.GRAY);
        }
    }

    private String getText(){
        return placeholder.equals(jTextField.getText()) ? "" : jTextField.getText();
    }

}
