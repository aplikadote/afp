/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.home;

import cl.rgonzalez.afp.core.services.AfpCoreService;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpUiHomePanelModel {

    @Autowired
    AfpCoreService service;
    @Autowired
    AfpUiHomeTableModel tableModel;

    public AfpUiHomePanel init() {
        AfpUiHomePanel panel = new AfpUiHomePanel();

        File dir = new File("../../data/rentabilidad");
        File[] listFiles = dir.listFiles();
        Arrays.sort(listFiles, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        panel.getTable().setModel(tableModel);
        tableModel.setFiles(listFiles);
        tableModel.fireTableDataChanged();

        return panel;
    }

}
