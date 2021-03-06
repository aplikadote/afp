/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.rentabilidad;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import org.springframework.stereotype.Service;

@Service
public class AfpUiRentabilidadPanel extends javax.swing.JPanel {

    public AfpUiRentabilidadPanel() {
        initComponents();
    }

    public JComboBox getComboAfp() {
        return comboAfp;
    }

    public JComboBox getComboFondo() {
        return comboFondo;
    }

    public JComboBox getComboPeriodoFin() {
        return comboPeriodoFin;
    }

    public JComboBox getComboPeriodoInicio() {
        return comboPeriodoInicio;
    }

    public JPanel getPanelPlot() {
        return panelPlot;
    }

    public JTable getTable() {
        return table;
    }

    public JButton getButtonPlot() {
        return buttonPlot;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTitle = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        panelMain = new javax.swing.JPanel();
        panelButtons = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        comboAfp = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        comboFondo = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        comboPeriodoInicio = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        comboPeriodoFin = new javax.swing.JComboBox();
        buttonPlot = new javax.swing.JButton();
        split = new javax.swing.JSplitPane();
        panelPlot = new javax.swing.JPanel();
        panelTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel7.setText("Rentabilidad");
        panelTitle.add(jLabel7);

        add(panelTitle, java.awt.BorderLayout.NORTH);

        panelMain.setLayout(new java.awt.BorderLayout());

        panelButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setText("AFP");
        panelButtons.add(jLabel2);

        comboAfp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelButtons.add(comboAfp);

        jLabel4.setText("Fondo");
        panelButtons.add(jLabel4);

        comboFondo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelButtons.add(comboFondo);

        jLabel1.setText("Periodo");
        panelButtons.add(jLabel1);

        comboPeriodoInicio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelButtons.add(comboPeriodoInicio);

        jLabel3.setText("-");
        panelButtons.add(jLabel3);

        comboPeriodoFin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelButtons.add(comboPeriodoFin);

        buttonPlot.setText("Graficar"); // NOI18N
        panelButtons.add(buttonPlot);

        panelMain.add(panelButtons, java.awt.BorderLayout.PAGE_START);

        split.setDividerLocation(300);
        split.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        panelPlot.setLayout(new javax.swing.BoxLayout(panelPlot, javax.swing.BoxLayout.LINE_AXIS));
        split.setLeftComponent(panelPlot);

        panelTable.setLayout(new javax.swing.BoxLayout(panelTable, javax.swing.BoxLayout.LINE_AXIS));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table);

        panelTable.add(jScrollPane1);

        split.setRightComponent(panelTable);

        panelMain.add(split, java.awt.BorderLayout.CENTER);

        add(panelMain, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonPlot;
    private javax.swing.JComboBox comboAfp;
    private javax.swing.JComboBox comboFondo;
    private javax.swing.JComboBox comboPeriodoFin;
    private javax.swing.JComboBox comboPeriodoInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelMain;
    private javax.swing.JPanel panelPlot;
    private javax.swing.JPanel panelTable;
    private javax.swing.JPanel panelTitle;
    private javax.swing.JSplitPane split;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
