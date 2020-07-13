/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.rentabilidad;

import cl.rgonzalez.afp.core.db.AfpDbPeriodo;
import cl.rgonzalez.afp.core.db.AfpDbRentabilidad;
import cl.rgonzalez.afp.core.services.AfpCoreService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpUiRentabilidadTableModel extends AbstractTableModel {

    @Autowired
    AfpCoreService service;
    //
    List<String> columnNames = Arrays.asList("AFP", "Fondo", "Rentabilidad");
    List<AfpUiRentabilidadRow> rows;
    List<AfpDbPeriodo> periodos;

    public List<AfpUiRentabilidadRow> getRows() {
        return rows;
    }

    public void setRows(List<AfpUiRentabilidadRow> rows) {
        this.rows = rows;
    }

    public List<AfpDbPeriodo> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<AfpDbPeriodo> periodos) {
        this.periodos = periodos;
    }

    public void restart() {
        columnNames = new ArrayList<>();
        columnNames.add("Afp");
        columnNames.add("Fondo");

        for (AfpDbPeriodo p : periodos) {
            columnNames.add(service.formatPeriodo(p));
        }
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
        return AfpUiRentabilidadRow.class;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AfpUiRentabilidadRow r = rows.get(rowIndex);
        return r;
    }

    

}
