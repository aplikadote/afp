/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.simulacion;

import cl.rgonzalez.afp.core.services.AfpCoreService;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpUiSimulacionTableRenderer extends DefaultTableCellRenderer {

    @Autowired
    AfpCoreService service;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.

        AfpUiSimulacionRow s = (AfpUiSimulacionRow) value;
        String text = "";
        switch (column) {
            case 0:
                text = service.formatPeriodo(s.getPeriodo());
                break;
            case 1:
                text = service.formatDblPer(s.getTasaAfp(), 2);
                break;
            case 2:
                text = service.formatDbl(s.getTotalAfp(), 2);
                break;
            case 3:
                text = service.formatDblPer(s.getTasaNone(), 2);
                break;
            case 4:
                text = service.formatDbl(s.getTotalNone(), 2);
                break;
            case 5:
                text = service.formatDblPer(s.getTasaFija(), 2);
                break;
            case 6:
                text = service.formatDbl(s.getTotalFija(), 2);
                break;
            default:
                text = "";
                break;
        }

        label.setText(text);
        return label;
    }

}
