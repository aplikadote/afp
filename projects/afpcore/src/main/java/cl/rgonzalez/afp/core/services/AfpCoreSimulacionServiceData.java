/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core.services;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author rgonzalez
 */
public class AfpCoreSimulacionServiceData {

    private List<AfpCoreSimulacionServiceDataInterval> intervals = Collections.EMPTY_LIST;
    private double totalAfp;
    private double totalBase;
    private double totalFijo;
    private double rentabilidadAfp;
    private double rentabilidadFija;

    public AfpCoreSimulacionServiceData() {
    }

    public AfpCoreSimulacionServiceData(List<AfpCoreSimulacionServiceDataInterval> intervals) {
        this.intervals = intervals;
        
        AfpCoreSimulacionServiceDataInterval last = intervals.get(intervals.size() - 1);
        this.totalAfp = last.getTotalAfp();
        this.totalBase = last.getTotalNone();
        this.totalFijo = last.getTotalFija();
        this.rentabilidadAfp = (totalAfp - totalBase) / totalBase;
        this.rentabilidadFija = (totalFijo - totalBase) / totalBase;
    }

    public List<AfpCoreSimulacionServiceDataInterval> getIntervals() {
        return intervals;
    }

    public double getTotalAfp() {
        return totalAfp;
    }

    public double getTotalBase() {
        return totalBase;
    }

    public double getTotalFijo() {
        return totalFijo;
    }

    public double getRentabilidadAfp() {
        return rentabilidadAfp;
    }

    public double getRentabilidadFija() {
        return rentabilidadFija;
    }

}
