/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop_management_system;

import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author User
 */
public class TextAreaEditor extends DefaultCellEditor {
    public TextAreaEditor() {
        super(new JTextField());
        final JTextArea textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        editorComponent = scrollPane;
        
        delegate = new DefaultCellEditor.EditorDelegate() {
            public void setValue(Object value) {
                textArea.setText((value != null) ? value.toString() : "");
            }
            public Object getCellEditorValue() {
                return textArea.getText();
            }
        };
    }
}
