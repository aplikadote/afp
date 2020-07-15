/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.web.old;

import cl.rgonzalez.afp.core.db.AfpDbAfp;
import cl.rgonzalez.afp.core.db.AfpDbPeriodo;
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
@RequestMapping("/api/mensual/afp")
public class AppRestMensualAfp {

//    @Autowired
//    private AppServices services;
//
//    @GetMapping("init")
//    public Object init() {
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.MONTH, -1);
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH) + 1;
//
////        Periodo periodo = new Periodo(year, month);
//        Map<String, Object> data = new HashMap<>();
//        data.put("years", services.getAppYears());
//        data.put("months", services.getAppMonths());
//        data.put("selectedYear", services.getYear(year));
//        data.put("selectedMonth", services.getMonth(month));
////        data.put("plots", createAllPlots(periodo));
//        return data;
//    }
//
//    @PostMapping(path = "update", consumes = "application/json", produces = "application/json")
////    public Object update(@RequestBody Periodo periodo) {
//    public Object update(@RequestBody AfpDbPeriodo periodo) {
////        Map<String, Object> data = new HashMap<>();
////        if (db.getPeriodos().contains(periodo)) {
////            data.put("containsData", true);
////            data.put("plots", createAllPlots(periodo));
////        } else {
////            data.put("containsData", false);
////            data.put("plots", Collections.EMPTY_LIST);
////        }
////        return data;
//        return null;
//    }
//
//    @PostMapping(path = "next", consumes = "application/json", produces = "application/json")
//    public Object next(@RequestBody AfpDbPeriodo actual) {
////        Periodo next = services.getNextPeriodo(actual);
//        Map<String, Object> data = new HashMap<>();
////        if (next != null) {
////            data.put("containsData", true);
////            data.put("plots", createAllPlots(next));
////            data.put("selectedYear", new AppYear(next.getYear()));
////            data.put("selectedMonth", services.getMonth(next.getMonth()));
////        } else {
////            data.put("containsData", false);
////            data.put("plots", Collections.EMPTY_LIST);
////            data.put("selectedYear", AppYear.EMPTY);
////            data.put("selectedMonth", AppMonth.EMPTY);
////        }
//        return data;
//    }
//
//    @PostMapping(path = "previous", consumes = "application/json", produces = "application/json")
//    public Object previous(@RequestBody AfpDbPeriodo actual) {
////        Periodo next = services.getPreviousPeriodo(actual);
////        Map<String, Object> data = new HashMap<>();
////        if (next != null) {
////            data.put("containsData", true);
////            data.put("plots", createAllPlots(next));
////            data.put("selectedYear", new AppYear(next.getYear()));
////            data.put("selectedMonth", services.getMonth(next.getMonth()));
////        } else {
////            data.put("containsData", false);
////            data.put("plots", Collections.EMPTY_LIST);
////            data.put("selectedYear", AppYear.EMPTY);
////            data.put("selectedMonth", AppMonth.EMPTY);
////        }
////        return data;
//        return null;
//    }
//
//    private List<Object> createAllPlots(AfpDbPeriodo periodo) {
////        if (db.getPeriodos().contains(periodo)) {
////            List<Object> plots = new ArrayList<>();
////            db.getAfps().forEach(e -> {
////                Double value = db.get(e, Fondo.A, periodo, Variable.RENTAB_MENSUAL);
////                if (value != null) {
////                    plots.add(createPlot(e, periodo));
////                }
////            });
////            return plots;
////        } else {
//        return Collections.EMPTY_LIST;
////        }
//    }
//
////    private Object createPlot(Afp afp, Periodo periodo) {
//    private Object createPlot(AfpDbAfp afp) {
////        Variable var = Variable.RENTAB_MENSUAL;
////
////        List plotArray = new ArrayList();
////        Map<String, Object> dataset = new HashMap<>();
////        plotArray.add(dataset);
////
////        dataset.put("x", new ArrayList<String>());
////        dataset.put("y", new ArrayList<Double>());
////        dataset.put("type", "bar");
////        dataset.put("text", new ArrayList<String>());
////        dataset.put("textposition", "auto");
////        dataset.put("hoverinfo", "none");
////
////        for (Fondo fondo : Fondo.values()) {
////            Double value = db.get(afp, fondo, periodo, var);
////            if (value != null) {
//////                System.out.format("  %s %s\n", afp.getName(), value);
////                ((List<String>) dataset.get("x")).add(fondo.name());
////                ((List<Double>) dataset.get("y")).add(value);
////                ((List<String>) dataset.get("text")).add(services.getDecimalFormat().format(value));
////            }
////        }
////
////        Map<String, Object> margin = new HashMap<>();
////        margin.put("l", 50);
////        margin.put("r", 0);
////        margin.put("b", 20);
////        margin.put("t", 40);
////        margin.put("pad", 4);
////
////        Map<String, Object> yaxis = new HashMap<>();
////        yaxis.put("tickformat", ",.2%");
////
////        Map<String, Object> font = new HashMap<>();
////        font.put("size", 9);
////
////        Map<String, Object> layout = new HashMap<>();
////        layout.put("title", afp.getName());
////        layout.put("showLeyend", false);
////        layout.put("margin", margin);
////        layout.put("yaxis", yaxis);
////        layout.put("font", font);
////
////        Map<String, Object> options = new HashMap<>();
////        options.put("staticPlot", true);
//
//        Map<String, Object> plotFondoA = new HashMap<>();
////        plotFondoA.put("divName", "fondo_" + afp.getName());
////        plotFondoA.put("plotName", "Fondo " + afp.getName());
////        plotFondoA.put("data", plotArray);
////        plotFondoA.put("layout", layout);
////        plotFondoA.put("options", options);
//
//        return plotFondoA;
//    }
}
