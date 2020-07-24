/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.simulacion;

import cl.rgonzalez.afp.core.services.AfpCoreSimulacionServiceData;
import cl.rgonzalez.afp.core.db.AfpDbPeriodo;
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
    List<AfpCoreSimulacionServiceData> rows;

    public List<AfpCoreSimulacionServiceData> getRows() {
        return rows;
    }

    public void setRows(List<AfpCoreSimulacionServiceData> rows) {
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
        switch (columnIndex) {
            case 0:
                return AfpDbPeriodo.class;
            case 1:
                return Double.class;
            case 2:
                return Double.class;
            case 3:
                return Double.class;
            case 4:
                return Double.class;
            case 5:
                return Double.class;
            case 6:
                return Double.class;
            default:
                return String.class;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AfpCoreSimulacionServiceData r = rows.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return r.getPeriodo();
            case 1:
                return r.getTasaAfp();
            case 2:
                return r.getTotalAfp();
            case 3:
                return r.getTasaNone();
            case 4:
                return r.getTotalNone();
            case 5:
                return r.getTasaFija();
            case 6:
                return r.getTotalFija();
            default:
                return "";
        }
    }

}
