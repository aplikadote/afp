/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui;

import cl.rgonzalez.afp.core.db.AfpDbPeriodo;
import cl.rgonzalez.afp.core.services.AfpCoreService;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpUiTableRendererAfpDbPeriodo extends DefaultTableCellRenderer {

    @Autowired
    AfpCoreService service;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.

        AfpDbPeriodo p = (AfpDbPeriodo) value;
        String text = service.formatPeriodo(p);
        label.setText(text);
        return label;
    }

}
