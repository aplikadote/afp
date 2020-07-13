/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.home;

import java.io.File;
import javax.swing.table.AbstractTableModel;
import org.springframework.stereotype.Service;

@Service
public class AfpUiHomeTableModel extends AbstractTableModel {

    private File[] files;

    public AfpUiHomeTableModel() {
    }

    void setFiles(File[] files) {
        this.files = files;
    }

    @Override
    public int getRowCount() {
        return files.length;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public String getColumnName(int column) {
        return "Archivos Cargados en la Base de Datos";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return files[rowIndex].getName();
    }

}
