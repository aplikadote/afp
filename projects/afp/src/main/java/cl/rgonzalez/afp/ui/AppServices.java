/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui;

import cl.rgonzalez.afpmvn.core.Periodo;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AppServices {

    private final DecimalFormat decimalFormat = new DecimalFormat("0.00%");
    private final List<Year> years;
    private final List<Month> months;
    private final Map<Integer, Year> indexYears;
    private final Map<Integer, Month> indexMonths;

    public AppServices() {
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
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public List<Year> getYears() {
        return years;
    }

    public List<Month> getMonths() {
        return months;
    }

    public Map<Integer, Year> getIndexYears() {
        return indexYears;
    }

    public Map<Integer, Month> getIndexMonths() {
        return indexMonths;
    }

    public Year getYear(int year) {
        return indexYears.get(year);
    }

    public Month getMonth(int month) {
        return indexMonths.get(month);
    }
    
    public Periodo getNext(Periodo actual) {
        int actualYearId = actual.getYear();
        Year actualYear = getYear(actualYearId);
        if (actualYear != null) {
            int month = actual.getMonth();
            if (month == 12) {
                int nextYearId = actual.getYear() + 1;
                Year nextYear = getYear(nextYearId);
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
    
    public Periodo getPrevious(Periodo actual) {
        int actualYearId = actual.getYear();
        Year actualYear = getYear(actualYearId);
        if (actualYear != null) {
            int month = actual.getMonth();
            if (month == 1) {
                int prevYearId = actual.getYear() - 1;
                Year prevYear = getYear(prevYearId);
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
    
}
