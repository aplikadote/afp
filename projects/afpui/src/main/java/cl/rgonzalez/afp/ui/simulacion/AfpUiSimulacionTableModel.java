/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.simulacion;

import cl.rgonzalez.afp.core.services.AfpCoreService;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpUiSimulacionTableModel extends AbstractTableModel {

    @Autowired
    AfpCoreService service;
    //
    List<String> columnNames = Arrays.asList("Periodo", "Tasa Afp", "Total Afp", "Sin Tasa", "Total Sin Tasa", "Tasa Fija", "Total Tasa Fija");
    List<AfpUiSimulacionRow> rows;

    public List<AfpUiSimulacionRow> getRows() {
        return rows;
    }

    public void setRows(List<AfpUiSimulacionRow> rows) {
        this.rows = rows;
    }

    @Override
    public int getRowCount() {
        return rows != null ? rows.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return AfpUiSimulacionRow.class;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AfpUiSimulacionRow r = rows.get(rowIndex);
        return r;
    }

}
