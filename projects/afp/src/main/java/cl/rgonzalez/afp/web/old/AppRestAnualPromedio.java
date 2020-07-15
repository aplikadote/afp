/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.web.old;

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
@RequestMapping("/api/anual/promedio")
public class AppRestAnualPromedio {

//    @Autowired
//    private AppServices services;
//
//    @GetMapping("init")
//    public Object init() {
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.MONTH, -1);
//        int year = c.get(Calendar.YEAR);
//
//        AppYear selecterYear = services.getYear(year);
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("years", services.getAppYears());
//        data.put("selectedYear", selecterYear);
//        data.put("plots", createAllPlots(selecterYear));
//        return data;
//
//    }
//
//    @PostMapping(path = "update", consumes = "application/json", produces = "application/json")
//    public Object update(@RequestBody Request request) {
//        AppYear year = services.getYear(request.getYear());
//
//        Map<String, Object> data = new HashMap<>();
////        if (db.getPeriodos().contains(periodo)) {
//        data.put("containsData", true);
//        data.put("plots", createAllPlots(year));
////        } else {
////            data.put("containsData", false);
////            data.put("plots", Collections.EMPTY_LIST);
////        }
//        return data;
//    }
//
//    @PostMapping(path = "next", consumes = "application/json", produces = "application/json")
//    public Object next(@RequestBody Request request) {
//        AppYear year = services.getYear(request.getYear());
//
//        AppYear next = services.getNextYear(year);
//        Map<String, Object> data = new HashMap<>();
//        if (next != null) {
//            data.put("containsData", true);
//            data.put("plots", createAllPlots(next));
//            data.put("selectedYear", next);
//        } else {
//            data.put("containsData", false);
//            data.put("plots", Collections.EMPTY_LIST);
//            data.put("selectedYear", AppYear.EMPTY);
//        }
//        return data;
//    }
//
//    @PostMapping(path = "previous", consumes = "application/json", produces = "application/json")
//    public Object previous(@RequestBody Request request) {
//        AppYear year = services.getYear(request.getYear());
//
//        AppYear previous = services.getPreviousYear(year);
//        Map<String, Object> data = new HashMap<>();
//        if (previous != null) {
//            data.put("containsData", true);
//            data.put("plots", createAllPlots(previous));
//            data.put("selectedYear", previous);
//        } else {
//            data.put("containsData", false);
//            data.put("plots", Collections.EMPTY_LIST);
//            data.put("selectedYear", AppYear.EMPTY);
//        }
//        return data;
//    }
//
//    private List<Object> createAllPlots(AppYear year) {
//        List<Object> plots = new ArrayList<>();
////        for (Fondo fondo : Fondo.values()) {
////            Object plot = createPlot(year, fondo);
////            plots.add(plot);
////        }
//        return plots;
//    }
//
////    private Object createPlot(AppYear year, Fondo fondo) {
//    private Object createPlot(AppYear year) {
////        Variable var = Variable.RENTAB_MENSUAL;
//
////        List plotArray = new ArrayList();
////        Map<String, Object> dataset = new HashMap<>();
////        plotArray.add(dataset);
////
////        dataset.put("x", new ArrayList<String>());
////        dataset.put("y", new ArrayList<Double>());
////        dataset.put("type", "scatter");
////        dataset.put("mode", "lines+markers+text");
////        dataset.put("text", new ArrayList<String>());
////        dataset.put("textposition", "top");
////        dataset.put("hoverinfo", "none");
////
////        for (int i = 1; i <= 12; i++) {
////            Periodo periodo = new Periodo(year.getId(), i);
////            Double value = services.getAfpPromedio().get(fondo, periodo, Variable.RENTAB_MENSUAL);
////            value = value == null ? 0 : value;
////            ((List<String>) dataset.get("x")).add(services.getMonth(i).getName());
////            ((List<Double>) dataset.get("y")).add(value);
////            ((List<String>) dataset.get("text")).add(services.getDecimalFormat().format(value));
////        }
////
////        Map<String, Object> margin = new HashMap<>();
////        margin.put("l", 50);
////        margin.put("r", 0);
////        margin.put("b", 20);
////        margin.put("t", 50);
////        margin.put("pad", 4);
////
////        Map<String, Object> yaxis = new HashMap<>();
////        yaxis.put("tickformat", ",.2%");
////
////        Map<String, Object> font = new HashMap<>();
////        font.put("size", 9);
////
////        Map<String, Object> layout = new HashMap<>();
////        layout.put("title", year.getName() + " - " + "PROMEDIO" + " - Fondo " + fondo.name());
////        layout.put("showLeyend", false);
////        layout.put("margin", margin);
////        layout.put("yaxis", yaxis);
////        layout.put("font", font);
////
////        Map<String, Object> options = new HashMap<>();
////        options.put("staticPlot", true);
//
//        Map<String, Object> plotFondoA = new HashMap<>();
////        plotFondoA.put("divName", "fondo_" + fondo.name());
////        plotFondoA.put("plotName", "Fondo " + fondo.name());
////        plotFondoA.put("data", plotArray);
////        plotFondoA.put("layout", layout);
////        plotFondoA.put("options", options);
//
//        return plotFondoA;
//    }
//
//    public static class Request {
//
//        private int year;
//
//        public Request() {
//        }
//
//        public int getYear() {
//            return year;
//        }
//
//        public void setYear(int year) {
//            this.year = year;
//        }
//
//    }
}
