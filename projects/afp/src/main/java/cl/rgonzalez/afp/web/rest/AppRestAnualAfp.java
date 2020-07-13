/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.web.rest;

import cl.rgonzalez.afp.web.AppAfp;
import cl.rgonzalez.afp.web.AppFondo;
import cl.rgonzalez.afp.web.AppServices;
import cl.rgonzalez.afp.web.AppYear;
import cl.rgonzalez.afpmvn.core.Database;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/anual/afp")
public class AppRestAnualAfp {

    @Autowired
    private Database db;
    @Autowired
    private AppServices services;

    @GetMapping("init")
    public Object init() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        int year = c.get(Calendar.YEAR);

        AppYear selecterYear = services.getYear(year);
        AppFondo selectedFondo = services.getAppFondos().get(0);

        Map<String, Object> data = new HashMap<>();
        data.put("years", services.getAppYears());
        data.put("fondos", services.getAppFondos());
        data.put("selectedYear", selecterYear);
        data.put("selectedFondo", selectedFondo);
        data.put("plots", createAllPlots(selecterYear, selectedFondo));
        return data;

    }

    @PostMapping(path = "update", consumes = "application/json", produces = "application/json")
    public Object update(@RequestBody Request request) {
        AppYear year = services.getYear(request.getYear());
        AppFondo fondo = services.getAppFondo(request.getFondo());

        Map<String, Object> data = new HashMap<>();
//        if (db.getPeriodos().contains(periodo)) {
        data.put("containsData", true);
        data.put("plots", createAllPlots(year, fondo));
//        } else {
//            data.put("containsData", false);
//            data.put("plots", Collections.EMPTY_LIST);
//        }
        return data;
    }

    @PostMapping(path = "next", consumes = "application/json", produces = "application/json")
    public Object next(@RequestBody Request request) {
        AppYear year = services.getYear(request.getYear());
        AppFondo fondo = services.getAppFondo(request.getFondo());

        AppYear next = services.getNextYear(year);
        Map<String, Object> data = new HashMap<>();
        if (next != null) {
            data.put("containsData", true);
            data.put("plots", createAllPlots(next, fondo));
            data.put("selectedYear", next);
        } else {
            data.put("containsData", false);
            data.put("plots", Collections.EMPTY_LIST);
            data.put("selectedYear", AppYear.EMPTY);
        }
        return data;
    }

    @PostMapping(path = "previous", consumes = "application/json", produces = "application/json")
    public Object previous(@RequestBody Request request) {
        AppYear year = services.getYear(request.getYear());
        AppFondo fondo = services.getAppFondo(request.getFondo());

        AppYear previous = services.getPreviousYear(year);
        Map<String, Object> data = new HashMap<>();
        if (previous != null) {
            data.put("containsData", true);
            data.put("plots", createAllPlots(previous, fondo));
            data.put("selectedYear", previous);
        } else {
            data.put("containsData", false);
            data.put("plots", Collections.EMPTY_LIST);
            data.put("selectedYear", AppYear.EMPTY);
        }
        return data;
    }

    private List<Object> createAllPlots(AppYear year, AppFondo fondo) {
        List<Object> plots = new ArrayList<>();
        List<AppAfp> afps = services.getAppAfps();
        for (AppAfp afp : afps) {
            Object plot = createPlot(year, afp, fondo);
            if (plot != null) {
                plots.add(plot);
            }
        }
        return plots;
    }

    private Object createPlot(AppYear year, AppAfp afp, AppFondo fondo) {
        Variable var = Variable.RENTAB_MENSUAL;

        List plotArray = new ArrayList();
        Map<String, Object> dataset = new HashMap<>();
        plotArray.add(dataset);

        dataset.put("x", new ArrayList<String>());
        dataset.put("y", new ArrayList<Double>());
        dataset.put("type", "scatter");
        dataset.put("mode", "lines+markers+text");
        dataset.put("text", new ArrayList<String>());
        dataset.put("textposition", "top");
        dataset.put("hoverinfo", "none");

        boolean allNull = true;
        for (int i = 1; i <= 12; i++) {
            Periodo periodo = new Periodo(year.getId(), i);
            Double value = db.get(services.getAfp(afp.getId()), services.getFondo(fondo.getId()), periodo, var);
            allNull = allNull && value == null;
            value = value != null ? value : 0;
            ((List<String>) dataset.get("x")).add(services.getMonth(i).getName());
            ((List<Double>) dataset.get("y")).add(value);
            ((List<String>) dataset.get("text")).add(services.getDecimalFormat().format(value));
        }

        if (allNull) {
            return null;
        }

        Map<String, Object> margin = new HashMap<>();
        margin.put("l", 50);
        margin.put("r", 0);
        margin.put("b", 20);
        margin.put("t", 50);
        margin.put("pad", 4);

        Map<String, Object> yaxis = new HashMap<>();
        yaxis.put("tickformat", ",.2%");

        Map<String, Object> font = new HashMap<>();
        font.put("size", 9);

        Map<String, Object> layout = new HashMap<>();
        layout.put("title", year.getName() + " - " + afp.getName() + " - Fondo " + fondo.getName());
        layout.put("showLeyend", false);
        layout.put("margin", margin);
        layout.put("yaxis", yaxis);
        layout.put("font", font);

        Map<String, Object> options = new HashMap<>();
        options.put("staticPlot", true);

        Map<String, Object> plotFondoA = new HashMap<>();
        plotFondoA.put("divName", "afp_" + afp.getName());
        plotFondoA.put("plotName", "Afp " + afp.getName());
        plotFondoA.put("data", plotArray);
        plotFondoA.put("layout", layout);
        plotFondoA.put("options", options);

        return plotFondoA;
    }

    public static class Request {

        private int year;
        private int fondo;

        public Request() {
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getFondo() {
            return fondo;
        }

        public void setFondo(int fondo) {
            this.fondo = fondo;
        }

    }
}
