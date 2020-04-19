/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afpmvn.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author administrador
 */
public class FondoData implements Serializable {

    private Map<Periodo, Double> mapMonth;
    private Map<Periodo, Double> mapThisYear;
    private Map<Periodo, Double> mapFullYear;
    private Map<Periodo, Double> mapAverageTotal;

    public FondoData() {
        this.mapMonth = new HashMap<Periodo, Double>();
        this.mapThisYear = new HashMap<Periodo, Double>();
        this.mapFullYear = new HashMap<Periodo, Double>();
        this.mapAverageTotal = new HashMap<Periodo, Double>();
    }

    public void put(Periodo periodo, Tipo tipo, Double value) {
        switch (tipo) {
            case RENTAB_MENSUAL:
                this.mapMonth.put(periodo, value);
                break;
            case RENTAB_DESDE_ENERO:
                this.mapThisYear.put(periodo, value);
                break;
            case RENTAB_ULTIMOS_12_MESES:
                this.mapFullYear.put(periodo, value);
                break;
            case PROMEDIO_ANUAL_DESDE_20020927:
                this.mapAverageTotal.put(periodo, value);
                break;
        }
    }

    public Double get(Periodo periodo, Tipo tipo) {
        switch (tipo) {
            case RENTAB_MENSUAL:
                return this.mapMonth.get(periodo);
            case RENTAB_DESDE_ENERO:
                return this.mapThisYear.get(periodo);
            case RENTAB_ULTIMOS_12_MESES:
                return this.mapFullYear.get(periodo);
            case PROMEDIO_ANUAL_DESDE_20020927:
                return this.mapAverageTotal.get(periodo);
            default:
                return null;
        }
    }

}
