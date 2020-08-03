/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.rentabilidad;

import cl.rgonzalez.afp.core.db.AfpDbAfp;
import cl.rgonzalez.afp.core.db.AfpDbFondo;
import cl.rgonzalez.afp.core.db.AfpDbPeriodo;
import cl.rgonzalez.afp.core.db.AfpDbRentabilidad;
import cl.rgonzalez.afp.core.services.AfpCoreService;
import cl.rgonzalez.afp.core.services.AfpCoreServiceException;
import cl.rgonzalez.afp.ui.AfpUiComboAfpRenderer;
import cl.rgonzalez.afp.ui.AfpUiComboFondoRenderer;
import cl.rgonzalez.afp.ui.AfpUiComboPeriodoRenderer;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
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
public class AfpUiRentabilidadPanelModel {

    @Autowired
    AfpCoreService service;
    @Autowired
    AfpUiRentabilidadTableModel tableModel;
    @Autowired
    AfpUiRentabilidadTableRenderer tableRenderer;
    @Autowired
    AfpUiComboAfpRenderer comboRendererAfp;
    @Autowired
    AfpUiComboFondoRenderer comboRendererFondo;
    @Autowired
    AfpUiComboPeriodoRenderer comboRendererPeriodo;
    //
    private AfpUiRentabilidadPanel panel;
    private JComboBox comboAfp;
    private JComboBox comboFondo;
    private JComboBox comboPeriodoFin;
    private JComboBox comboPeriodoInicio;
    private JPanel panelPlot;
    private JTable table;
    private JButton buttonPlot;
    private ChartPanel panelChart;
    private JFreeChart chart;
    private CategoryPlot plot;
    private DefaultCategoryDataset dataset;
    private CategoryAxis domainAxis;
    private NumberAxis rangeAxis;
    private LineAndShapeRenderer renderer;

    public AfpUiRentabilidadPanel init() {
        this.panel = new AfpUiRentabilidadPanel();

        this.comboAfp = panel.getComboAfp();
        this.comboFondo = panel.getComboFondo();
        this.comboPeriodoFin = panel.getComboPeriodoFin();
        this.comboPeriodoInicio = panel.getComboPeriodoInicio();
        this.panelPlot = panel.getPanelPlot();
        this.table = panel.getTable();
        this.buttonPlot = panel.getButtonPlot();

        this.dataset = new DefaultCategoryDataset();

        this.domainAxis = new CategoryAxis();
        this.rangeAxis = new NumberAxis();
        this.renderer = new LineAndShapeRenderer();

        this.domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        this.rangeAxis.setNumberFormatOverride(new DecimalFormat("#.00%"));

        this.plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer);
        this.plot.setBackgroundPaint(Color.WHITE);
        this.chart = new JFreeChart(plot);
        this.chart.setBackgroundPaint(Color.WHITE);
        this.panelChart = new ChartPanel(chart);
        this.panelChart.setBackground(Color.WHITE);
        this.panelChart.setMaximumDrawWidth(1920);
        this.panelChart.setMaximumDrawHeight(1080);
        this.panelPlot.add(panelChart);

        comboAfp.removeAllItems();
        comboFondo.removeAllItems();
        comboPeriodoInicio.removeAllItems();
        comboPeriodoFin.removeAllItems();

        comboAfp.setMaximumRowCount(12);
        comboFondo.setMaximumRowCount(5);
        comboPeriodoInicio.setMaximumRowCount(12);
        comboPeriodoFin.setMaximumRowCount(12);

        comboAfp.setRenderer(comboRendererAfp);
        comboFondo.setRenderer(comboRendererFondo);
        comboPeriodoInicio.setRenderer(comboRendererPeriodo);
        comboPeriodoFin.setRenderer(comboRendererPeriodo);

        service.findAfpAll().forEach(comboAfp::addItem);
        service.findFondoAll().forEach(comboFondo::addItem);
        service.findPeriodosAllSorted().forEach(comboPeriodoInicio::addItem);
        service.findPeriodosAllSorted().forEach(comboPeriodoFin::addItem);

        this.table.setModel(tableModel);
        this.table.setDefaultRenderer(AfpUiRentabilidadRow.class, tableRenderer);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        this.buttonPlot.addActionListener(e -> plot());

        return panel;
    }

    private void plot() {
        AfpDbAfp afp = (AfpDbAfp) comboAfp.getSelectedItem();
        AfpDbFondo fondo = (AfpDbFondo) comboFondo.getSelectedItem();
        AfpDbPeriodo periodoInicio = (AfpDbPeriodo) comboPeriodoInicio.getSelectedItem();
        AfpDbPeriodo periodoFin = (AfpDbPeriodo) comboPeriodoFin.getSelectedItem();

        List<AfpDbRentabilidad> list = null;
        try {
            list = service.findRentabilidadBy(afp, fondo, periodoInicio, periodoFin);
        } catch (AfpCoreServiceException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sin datos", "Informacion", 1);
            return;
        }

        Collections.sort(list, new Comparator<AfpDbRentabilidad>() {
            @Override
            public int compare(AfpDbRentabilidad o1, AfpDbRentabilidad o2) {
                AfpDbPeriodo p1 = o1.getPeriodo();
                AfpDbPeriodo p2 = o2.getPeriodo();
                int diff = Integer.compare(p1.getYear(), p2.getYear());
                return diff == 0 ? Integer.compare(p1.getMonth(), p2.getMonth()) : diff;
            }
        });

        this.dataset.clear();
        Map<String, List<AfpDbRentabilidad>> map = new HashMap<>();
        List<AfpDbPeriodo> periodos = new ArrayList<>();
        for (AfpDbRentabilidad r : list) {
            dataset.addValue(r.getRateMonth(), "Rentabilidad", service.formatPeriodo(r.getPeriodo()));

            AfpDbAfp x1 = r.getAfp();
            AfpDbFondo x2 = r.getFondo();
            AfpDbPeriodo p = r.getPeriodo();

            String key = x1.getName() + "-" + x2.getName();
            List<AfpDbRentabilidad> aux = map.get(key);
            if (aux == null) {
                aux = new ArrayList<>();
                map.put(key, aux);
            }

            aux.add(r);
            periodos.add(p);
        }

        List<AfpUiRentabilidadRow> rows = new ArrayList<>();
        Set<Map.Entry<String, List<AfpDbRentabilidad>>> entrySet = map.entrySet();
        for (Map.Entry<String, List<AfpDbRentabilidad>> e : entrySet) {
            List<AfpDbRentabilidad> aux = e.getValue();
            AfpDbAfp x1 = aux.get(0).getAfp();
            AfpDbFondo x2 = aux.get(0).getFondo();

            AfpUiRentabilidadRow row = new AfpUiRentabilidadRow();
            row.setAfp(x1);
            row.setFondo(x2);
            row.setPeriodos(aux);

            rows.add(row);
        }

        this.tableModel.setRows(rows);
        this.tableModel.setPeriodos(periodos);
        this.tableModel.restart();
        this.tableModel.fireTableStructureChanged();
        for (int i = 0; i < this.table.getColumnCount(); i++) {
            this.table.getColumnModel().getColumn(i).setPreferredWidth(100);
        }
    }

}
