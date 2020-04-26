/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui;

import cl.rgonzalez.afpmvn.core.Afp;
import cl.rgonzalez.afpmvn.core.Database;
import cl.rgonzalez.afpmvn.core.Fondo;
import cl.rgonzalez.afpmvn.core.Periodo;
import cl.rgonzalez.afpmvn.core.Variable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppRestAfp {

    @Autowired
    private Database db;
    @Autowired
    private AppServices services;

    @GetMapping("/api/afp/init")
    public Object init() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;

        Periodo periodo = new Periodo(year, month);

        Map<String, Object> data = new HashMap<>();
        data.put("years", services.getYears());
        data.put("months", services.getMonths());
        data.put("selectedYear", services.getYear(year));
        data.put("selectedMonth", services.getMonth(month));
        data.put("plots", createAllPlots(periodo));
        return data;
    }

    @PostMapping(path = "/api/afp/update", consumes = "application/json", produces = "application/json")
    public Object update(@RequestBody Periodo periodo) {
        Map<String, Object> data = new HashMap<>();
        if (db.getPeriodos().contains(periodo)) {
            data.put("containsData", true);
            data.put("plots", createAllPlots(periodo));
        } else {
            data.put("containsData", false);
            data.put("plots", Collections.EMPTY_LIST);
        }
        return data;
    }

    @PostMapping(path = "/api/afp/next", consumes = "application/json", produces = "application/json")
    public Object next(@RequestBody Periodo actual) {
        Periodo next = services.getNext(actual);
        Map<String, Object> data = new HashMap<>();
        if (next != null) {
            data.put("containsData", true);
            data.put("plots", createAllPlots(next));
            data.put("selectedYear", new Year(next.getYear()));
            data.put("selectedMonth", services.getMonth(next.getMonth()));
        } else {
            data.put("containsData", false);
            data.put("plots", Collections.EMPTY_LIST);
            data.put("selectedYear", Year.EMPTY);
            data.put("selectedMonth", Month.EMPTY);
        }
        return data;
    }

    @PostMapping(path = "/api/afp/previous", consumes = "application/json", produces = "application/json")
    public Object previous(@RequestBody Periodo actual) {
        Periodo next = services.getPrevious(actual);
        Map<String, Object> data = new HashMap<>();
        if (next != null) {
            data.put("containsData", true);
            data.put("plots", createAllPlots(next));
            data.put("selectedYear", new Year(next.getYear()));
            data.put("selectedMonth", services.getMonth(next.getMonth()));
        } else {
            data.put("containsData", false);
            data.put("plots", Collections.EMPTY_LIST);
            data.put("selectedYear", Year.EMPTY);
            data.put("selectedMonth", Month.EMPTY);
        }
        return data;
    }

    private List<Object> createAllPlots(Periodo periodo) {
        if (db.getPeriodos().contains(periodo)) {
            List<Object> plots = new ArrayList<>();
            db.getAfps().forEach(e -> {
                Double value = db.get(e, Fondo.A, periodo, Variable.RENTAB_MENSUAL);
                if (value != null) {
                    plots.add(createPlot(e, periodo));
                }
            });
            return plots;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private Object createPlot(Afp afp, Periodo periodo) {
        Variable var = Variable.RENTAB_MENSUAL;

        List plotArray = new ArrayList();
        Map<String, Object> dataset = new HashMap<>();
        plotArray.add(dataset);

        dataset.put("x", new ArrayList<String>());
        dataset.put("y", new ArrayList<Double>());
        dataset.put("type", "bar");
        dataset.put("text", new ArrayList<String>());
        dataset.put("textposition", "auto");
        dataset.put("hoverinfo", "none");

        for (Fondo fondo : Fondo.values()) {
            Double value = db.get(afp, fondo, periodo, var);
            if (value != null) {
//                System.out.format("  %s %s\n", afp.getName(), value);
                ((List<String>) dataset.get("x")).add(fondo.name());
                ((List<Double>) dataset.get("y")).add(value);
                ((List<String>) dataset.get("text")).add(services.getDecimalFormat().format(value));
            }
        }

        Map<String, Object> margin = new HashMap<>();
        margin.put("l", 50);
        margin.put("r", 0);
        margin.put("b", 20);
        margin.put("t", 40);
        margin.put("pad", 4);

        Map<String, Object> yaxis = new HashMap<>();
        yaxis.put("tickformat", ",.2%");

        Map<String, Object> font = new HashMap<>();
        font.put("size", 9);

        Map<String, Object> layout = new HashMap<>();
        layout.put("title", afp.getName());
        layout.put("showLeyend", false);
        layout.put("margin", margin);
        layout.put("yaxis", yaxis);
        layout.put("font", font);

        Map<String, Object> options = new HashMap<>();
        options.put("staticPlot", true);
        
        Map<String, Object> plotFondoA = new HashMap<>();
        plotFondoA.put("divName", "fondo_" + afp.getName());
        plotFondoA.put("plotName", "Fondo " + afp.getName());
        plotFondoA.put("data", plotArray);
        plotFondoA.put("layout", layout);
        plotFondoA.put("options", options);

        return plotFondoA;
    }
}
