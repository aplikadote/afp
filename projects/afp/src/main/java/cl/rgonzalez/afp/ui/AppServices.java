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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppServices {

    private final DecimalFormat decimalFormat = new DecimalFormat("0.00%");
    private final List<AppYear> years;
    private final List<AppMonth> months;
    private final List<AppAfp> afps;
    private final List<AppFondo> fondos;
    private final Map<Integer, AppYear> indexYears;
    private final Map<Integer, AppMonth> indexMonths;
    private final Map<Integer, Afp> indexAfps;
    private final Map<Integer, AppAfp> indexAppAfps;
    private final Map<Integer, Fondo> indexFondos;
    private final Map<Integer, AppFondo> indexAppFondos;

    @Autowired
    public AppServices(Database db) {
        int index = 0;
        this.afps = new ArrayList<>();
        this.indexAfps = new HashMap<>();
        this.indexAppAfps = new HashMap<>();

        for (Afp afp : db.getAfps()) {
            AppAfp obj = new AppAfp(index, afp.getName());
            afps.add(obj);
            indexAfps.put(index, afp);
            indexAppAfps.put(index, obj);
            index++;
        }

        index = 0;
        this.fondos = new ArrayList<>();
        this.indexFondos = new HashMap<>();
        this.indexAppFondos = new HashMap<>();
        for (Fondo fondo : Fondo.values()) {
            AppFondo obj = new AppFondo(index, fondo.name());
            fondos.add(obj);
            indexFondos.put(index, fondo);
            indexAppFondos.put(index, obj);
            index++;
        }

        years = new ArrayList<>();
        indexYears = new HashMap<>();
        for (int i = 2020; i >= 2005; i--) {
            AppYear year = new AppYear(i);
            years.add(year);
            indexYears.put(i, year);
        }

        months = new ArrayList<>();
        months.add(new AppMonth(1, "Enero"));
        months.add(new AppMonth(2, "Febrero"));
        months.add(new AppMonth(3, "Marzo"));
        months.add(new AppMonth(4, "Abril"));
        months.add(new AppMonth(5, "Mayo"));
        months.add(new AppMonth(6, "Junio"));
        months.add(new AppMonth(7, "Julio"));
        months.add(new AppMonth(8, "Agosto"));
        months.add(new AppMonth(9, "Septiembre"));
        months.add(new AppMonth(10, "Octubre"));
        months.add(new AppMonth(11, "Noviembre"));
        months.add(new AppMonth(12, "Diciembre"));

        indexMonths = new HashMap<>();
        months.stream().forEach(e -> indexMonths.put(e.getId(), e));
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public List<AppYear> getAppYears() {
        return years;
    }

    public List<AppMonth> getAppMonths() {
        return months;
    }

    public Map<Integer, AppYear> getIndexYears() {
        return indexYears;
    }

    public Map<Integer, AppMonth> getIndexMonths() {
        return indexMonths;
    }

    public AppYear getYear(int year) {
        return indexYears.get(year);
    }

    public AppMonth getMonth(int month) {
        return indexMonths.get(month);
    }

    public List<AppAfp> getAppAfps() {
        return afps;
    }

    public List<AppFondo> getAppFondos() {
        return fondos;
    }

    public Afp getAfp(int id) {
        return indexAfps.get(id);
    }

    public Fondo getFondo(int id) {
        return indexFondos.get(id);
    }
    
    public AppAfp getAppAfp(int id) {
        return indexAppAfps.get(id);
    }

    public AppFondo getAppFondo(int id) {
        return indexAppFondos.get(id);
    }

    public Periodo getNextPeriodo(Periodo actual) {
        int actualYearId = actual.getYear();
        AppYear actualYear = getYear(actualYearId);
        if (actualYear != null) {
            int month = actual.getMonth();
            if (month == 12) {
                int nextYearId = actual.getYear() + 1;
                AppYear nextYear = getYear(nextYearId);
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

    public Periodo getPreviousPeriodo(Periodo actual) {
        int actualYearId = actual.getYear();
        AppYear actualYear = getYear(actualYearId);
        if (actualYear != null) {
            int month = actual.getMonth();
            if (month == 1) {
                int prevYearId = actual.getYear() - 1;
                AppYear prevYear = getYear(prevYearId);
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

    public AppYear getNextYear(AppYear actual) {
        return getYear(actual.getId() + 1);
    }

    public AppYear getPreviousYear(AppYear actual) {
        return getYear(actual.getId() - 1);
    }

}
