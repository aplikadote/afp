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
public class Afp implements Serializable {

    private String name;
    private Map<Fondo, FondoData> map;

    public Afp(String name) {
        this.name = name;
        this.map = new HashMap<Fondo, FondoData>();
    }

    public String getName() {
        return name;
    }

    public void put(Fondo fondo, Periodo periodo, Tipo tipo, Double rate) {
        FondoData fondoData = getFondoData(fondo);
        fondoData.put(periodo, tipo, rate);
    }

    private FondoData getFondoData(Fondo fondo) {
        FondoData fondoData = map.get(fondo);
        if (fondoData == null) {
            fondoData = new FondoData();
            map.put(fondo, fondoData);
        }
        return fondoData;
    }

    public Double get(Fondo fondo, Periodo periodo, Tipo tipo) {
        FondoData data = map.get(fondo);
        return data.get(periodo, tipo);
    }

    @Override
    public String toString() {
        return "Afp{" + "name=" + name + '}';
    }
}
