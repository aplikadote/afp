/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.simulacion;

import cl.rgonzalez.afp.core.services.AfpCoreSimulacionServiceDataInterval;
import cl.rgonzalez.afp.core.db.AfpDbAfp;
import cl.rgonzalez.afp.core.db.AfpDbFondo;
import cl.rgonzalez.afp.core.db.AfpDbPeriodo;
import cl.rgonzalez.afp.core.services.AfpCoreService;
import cl.rgonzalez.afp.core.services.AfpCoreServiceException;
import cl.rgonzalez.afp.core.services.AfpCoreSimulacionService;
import cl.rgonzalez.afp.core.services.AfpCoreSimulacionServiceData;
import cl.rgonzalez.afp.ui.AfpUiComboAfpRenderer;
import cl.rgonzalez.afp.ui.AfpUiComboFondoRenderer;
import cl.rgonzalez.afp.ui.AfpUiComboPeriodoRenderer;
import cl.rgonzalez.afp.ui.AfpUiTableRendererAfpDbPeriodo;
import cl.rgonzalez.afp.ui.AfpUiTableRendererDouble;
import java.awt.Color;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpUiSimulacionPanelModel {

    @Autowired
    AfpCoreService service;
    @Autowired
    AfpUiComboAfpRenderer comboRendererAfp;
    @Autowired
    AfpUiComboFondoRenderer comboRendererFondo;
    @Autowired
    AfpUiComboPeriodoRenderer comboRendererPeriodo;
    @Autowired
    AfpUiSimulacionTableModel tableModel;
    @Autowired
    AfpUiTableRendererAfpDbPeriodo tableRendererPeriodo;
    @Autowired
    AfpUiTableRendererDouble tableRendererDouble;
    @Autowired
    AfpCoreSimulacionService simulation;
    //
    private JComboBox comboAfp;
    private JComboBox comboFondo;
    private JComboBox comboPeriodoInicio;
    private JComboBox comboPeriodoFin;
    private JComboBox comboPagination;
    private JTable table;
    private JButton buttonCalcular;
    private JTextField textCotizacion;
    private JTextField textTasaFija;
    private JTextField textComision;
    private JPanel panelPlot;
    private DefaultCategoryDataset dataset;
    private List<AfpCoreSimulacionServiceDataInterval> rows;
    private int elements;
    private int pages;
    private int pagination = 20;

    public AfpUiSimulacionPanel init() {
        AfpUiSimulacionPanel panel = new AfpUiSimulacionPanel();
        this.comboAfp = panel.getComboAfp();
        this.comboFondo = panel.getComboFondo();
        this.comboPeriodoInicio = panel.getComboPeriodoInicio();
        this.comboPeriodoFin = panel.getComboPeriodoFin();
        this.comboPagination = panel.getComboPagination();
        this.table = panel.getTable();
        this.buttonCalcular = panel.getButtonCalcular();
        this.textCotizacion = panel.getTextCotizacion();
        this.textTasaFija = panel.getTextTasaFija();
        this.panelPlot = panel.getPanelPlot();
        this.textComision = panel.getTextComision();

        comboAfp.removeAllItems();
        comboFondo.removeAllItems();
        comboPeriodoInicio.removeAllItems();
        comboPeriodoFin.removeAllItems();

        comboAfp.setMaximumRowCount(20);
        comboFondo.setMaximumRowCount(5);
        comboPeriodoInicio.setMaximumRowCount(20);
        comboPeriodoFin.setMaximumRowCount(20);

        comboAfp.setRenderer(comboRendererAfp);
        comboFondo.setRenderer(comboRendererFondo);
        comboPeriodoInicio.setRenderer(comboRendererPeriodo);
        comboPeriodoFin.setRenderer(comboRendererPeriodo);

        service.findAfpAll().forEach(comboAfp::addItem);
        service.findFondoAll().forEach(comboFondo::addItem);
        service.findPeriodosSortedAll().forEach(e -> {
            comboPeriodoInicio.addItem(e);
            comboPeriodoFin.addItem(e);
        });

        table.setModel(tableModel);
        table.setDefaultRenderer(AfpDbPeriodo.class, tableRendererPeriodo);
        table.setDefaultRenderer(Double.class, tableRendererDouble);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int n = table.getColumnCount();
        for (int i = 0; i < n; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(100);
        }

        buttonCalcular.addActionListener(e -> calcular());

        this.dataset = new DefaultCategoryDataset();

        CategoryAxis domainAxis = new CategoryAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        NumberAxis rangeAxis = new NumberAxis();
//        rangeAxis.setNumberFormatOverride(new DecimalFormat("#"));

        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

        CategoryPlot plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer);
        plot.setBackgroundPaint(Color.WHITE);
        JFreeChart chart = new JFreeChart(plot);
        chart.setBackgroundPaint(Color.WHITE);
        ChartPanel panelChart = new ChartPanel(chart);
        panelChart.setBackground(Color.WHITE);
        panelChart.setMaximumDrawWidth(1920);
        panelChart.setMaximumDrawHeight(1080);
        this.panelPlot.add(panelChart);

        panel.getComboPagination().removeAllItems();
        panel.getComboPagination().addActionListener(e -> {
            Integer value = (Integer) panel.getComboPagination().getSelectedItem();
            if (value != null) {
                updateChart(value - 1, pagination);
            }
        });

        panel.getButtonPaginationNext().addActionListener(e -> {
            Integer value = (Integer) panel.getComboPagination().getSelectedItem();
            if (value != null) {
                value = value + 1;
                if (value <= panel.getComboPagination().getItemCount()) {
                    panel.getComboPagination().setSelectedItem(value);
                }

            }
        });

        panel.getButtonPaginationPrev().addActionListener(e -> {
            Integer value = (Integer) panel.getComboPagination().getSelectedItem();
            if (value != null) {
                value = value - 1;
                if (value > 0) {
                    panel.getComboPagination().setSelectedItem(value);
                }
            }
        });

//        panel.getPanelTableButtons().add(new);
        return panel;
    }

    private void calcular() {
        dataset.clear();
        tableModel.setRows(Collections.EMPTY_LIST);
        tableModel.fireTableDataChanged();

        AfpDbAfp afp = (AfpDbAfp) comboAfp.getSelectedItem();
        AfpDbFondo fondo = (AfpDbFondo) comboFondo.getSelectedItem();
        AfpDbPeriodo periodoInicio = (AfpDbPeriodo) comboPeriodoInicio.getSelectedItem();
        AfpDbPeriodo periodoFin = (AfpDbPeriodo) comboPeriodoFin.getSelectedItem();

        String strCotizacion = textCotizacion.getText();
        if (strCotizacion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese cotizacion", "Informacion", 1);
            return;
        }

        String strComision = textComision.getText();
        if (strComision.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese comision", "Informacion", 1);
            return;
        }

        String strTasaFija = textTasaFija.getText();
        if (strTasaFija.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Tasa Fija", "Informacion", 1);
            return;
        }

        double cotizacion = 0;
        try {
            cotizacion = service.parseDbl(strCotizacion);
            if (cotizacion <= 0) {
                JOptionPane.showMessageDialog(null, "Ingrese cotizacion mayor que cero", "Error", 0);
                return;
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error de parseo en cotizacion", "Error", 0);
            return;
        }

        double comision = 0;
        try {
            comision = service.parseDbl(strComision);
            if (cotizacion <= 0) {
                JOptionPane.showMessageDialog(null, "Ingrese comision mayor que cero", "Error", 0);
                return;
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error de parseo en comision", "Error", 0);
            return;
        }

        double tasaFija = 0;
        try {
            tasaFija = service.parseDbl(strTasaFija);
            if (tasaFija <= 0) {
                JOptionPane.showMessageDialog(null, "Ingrese Tasa Fija mayor que cero", "Error", 0);
                return;
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error de parseo en cotizacion", "Error", 0);
            return;
        }

        try {
            simulation.setAfp(afp);
            simulation.setFondo(fondo);
            simulation.setPeriodoInicio(periodoInicio);
            simulation.setPeriodoFin(periodoFin);
            simulation.setCotizacion(cotizacion);
            simulation.setComision(comision);
            simulation.setTasaFija(tasaFija);

            AfpCoreSimulacionServiceData data = simulation.simulate();
            rows = data.getIntervals();
            tableModel.setRows(rows);
            tableModel.fireTableDataChanged();

            updateChart(0, 20);

            elements = rows.size();
            pages = (int) Math.ceil(((double) elements / (double) pagination));
//            System.out.println("pagination: " + pagination);
//            System.out.println("elements: " + elements);
//            System.out.println("pages: " + pages);

            comboPagination.removeAllItems();
            for (int i = 0; i < pages; i++) {
                comboPagination.addItem(i + 1);
            }

            if (rows.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Sin datos", "Informacion", 1);
            }

        } catch (AfpCoreServiceException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }
    }

    private void updateChart(int page, int pagination) {
        dataset.clear();

        int init = page * pagination;
        for (int i = init; i < init + pagination; i++) {
            if (i < elements) {
                AfpCoreSimulacionServiceDataInterval row = rows.get(i);
                dataset.addValue(row.getTotalAfp(), "AFP", service.formatPeriodo(row.getPeriodo()));
                dataset.addValue(row.getTotalNone(), "NN", service.formatPeriodo(row.getPeriodo()));
                dataset.addValue(row.getTotalFija(), "FIJA", service.formatPeriodo(row.getPeriodo()));
            }
        }
    }
}
