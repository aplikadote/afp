/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.rentabilidad;

import cl.rgonzalez.afp.core.db.AfpDbRentabilidad;
import cl.rgonzalez.afp.core.services.AfpCoreService;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpUiRentabilidadTableRenderer extends DefaultTableCellRenderer {

    @Autowired
    AfpCoreService service;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.

        AfpUiRentabilidadRow r = (AfpUiRentabilidadRow) value;
        String text = "";
        switch (column) {
            case 0:
                text = r.getAfp().getName();
                break;
            case 1:
                text = r.getFondo().getName();
                break;
            default:
                text = service.formatDblPer(r.getPeriodos().get(column - 2).getRateMonth(), 2);
                break;
        }

        label.setText(text);
        return label;
    }

}
