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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
public class AppRest {

    @Autowired
    private Database db;
    //
    private DecimalFormat df = new DecimalFormat("0.00%");
//    private List<Periodo> periodos = new ArrayList<>();
//    private Map<Periodo, Integer> indexPeriodo = new HashMap<>();
    private List<Year> years;
    private List<Month> months;
    private Map<Integer, Year> indexYears;
    private Map<Integer, Month> indexMonths;

    @GetMapping("/api/test")
    public Object test() {
        return Arrays.asList("A", "B", "C");
    }

    @GetMapping("/api/init")
    public Object init() {
        years = new ArrayList<>();
        indexYears = new HashMap<>();
        for (int i = 2020; i >= 2005; i--) {
            Year year = new Year(i);
            years.add(year);
            indexYears.put(i, year);
        }

        months = new ArrayList<>();
        months.add(new Month(1, "Enero"));
        months.add(new Month(2, "Febrero"));
        months.add(new Month(3, "Marzo"));
        months.add(new Month(4, "Abril"));
        months.add(new Month(5, "Mayo"));
        months.add(new Month(6, "Junio"));
        months.add(new Month(7, "Julio"));
        months.add(new Month(8, "Agosto"));
        months.add(new Month(9, "Septiembre"));
        months.add(new Month(10, "Octubre"));
        months.add(new Month(11, "Noviembre"));
        months.add(new Month(12, "Diciembre"));

        indexMonths = new HashMap<>();
        months.stream().forEach(e -> indexMonths.put(e.getId(), e));

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;

        Periodo periodo = new Periodo(year, month);

        Map<String, Object> data = new HashMap<>();
        data.put("years", years);
        data.put("months", months);
        data.put("selectedYear", indexYears.get(year));
        data.put("selectedMonth", indexMonths.get(month));
        data.put("plots", createAllPlots(periodo));
        return data;
    }

    @PostMapping(path = "/api/update", consumes = "application/json", produces = "application/json")
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

    @PostMapping(path = "/api/next", consumes = "application/json", produces = "application/json")
    public Object next(@RequestBody Periodo actual) {
        Periodo next = getNext(actual);
        Map<String, Object> data = new HashMap<>();
        if (next != null) {
            data.put("containsData", true);
            data.put("plots", createAllPlots(next));
            data.put("selectedYear", new Year(next.getYear()));
            data.put("selectedMonth", months.get(next.getMonth() - 1));
        } else {
            data.put("containsData", false);
            data.put("plots", Collections.EMPTY_LIST);
            data.put("selectedYear", Year.EMPTY);
            data.put("selectedMonth", Month.EMPTY);
        }
        return data;
    }

    @PostMapping(path = "/api/previous", consumes = "application/json", produces = "application/json")
    public Object previous(@RequestBody Periodo actual) {
       Periodo next = getPrevious(actual);
        Map<String, Object> data = new HashMap<>();
        if (next != null) {
            data.put("containsData", true);
            data.put("plots", createAllPlots(next));
            data.put("selectedYear", new Year(next.getYear()));
            data.put("selectedMonth", months.get(next.getMonth() - 1));
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
            plots.add(createPlot(Fondo.A, periodo));
            plots.add(createPlot(Fondo.B, periodo));
            plots.add(createPlot(Fondo.C, periodo));
            plots.add(createPlot(Fondo.D, periodo));
            plots.add(createPlot(Fondo.E, periodo));
            return plots;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private Periodo getNext(Periodo actual) {
        int actualYearId = actual.getYear();
        Year actualYear = indexYears.get(actualYearId);
        if (actualYear != null) {
            int month = actual.getMonth();
            if (month == 12) {
                int nextYearId = actual.getYear() + 1;
                Year nextYear = indexYears.get(nextYearId);
                if (nextYear != null) {
                    return new Periodo(nextYearId, 1);
                } else {
                    return null;
                }
            } else {
                return new Periodo(actualYearId, month + 1);
            }
        } else {
            return null;
        }
    }
    
    private Periodo getPrevious(Periodo actual) {
        int actualYearId = actual.getYear();
        Year actualYear = indexYears.get(actualYearId);
        if (actualYear != null) {
            int month = actual.getMonth();
            if (month == 1) {
                int prevYearId = actual.getYear() - 1;
                Year prevYear = indexYears.get(prevYearId);
                if (prevYear != null) {
                    return new Periodo(prevYearId, 12);
                } else {
                    return null;
                }
            } else {
                return new Periodo(actualYearId, month - 1);
            }
        } else {
            return null;
        }
    }

    private Object createPlot(Fondo fondo, Periodo periodo) {
        List<Afp> afps = db.getAfps();
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

        for (Afp afp : afps) {
            Double value = db.get(afp, fondo, periodo, var);
            if (value != null) {
//                System.out.format("  %s %s\n", afp.getName(), value);
                ((List<String>) dataset.get("x")).add(afp.getName());
                ((List<Double>) dataset.get("y")).add(value);
                ((List<String>) dataset.get("text")).add(df.format(value));
            }
        }

        Map<String, Object> margin = new HashMap<>();
        margin.put("l", 40);
        margin.put("r", 30);
        margin.put("b", 60);
        margin.put("t", 40);
        margin.put("pad", 4);

        Map<String, Object> yaxis = new HashMap<>();
        yaxis.put("tickformat", ",.2%");
        
        Map<String, Object> font = new HashMap<>();
        font.put("size", 9);

        Map<String, Object> layout = new HashMap<>();
        layout.put("title", "Fondo " + fondo.name());
        layout.put("showLeyend", false);
        layout.put("margin", margin);
        layout.put("yaxis", yaxis);
        layout.put("font", font);

        Map<String, Object> plotFondoA = new HashMap<>();
        plotFondoA.put("divName", "fondo_" + fondo.name());
        plotFondoA.put("plotName", "Fondo " + fondo.name());
        plotFondoA.put("data", plotArray);
        plotFondoA.put("layout", layout);

        return plotFondoA;
    }
}
