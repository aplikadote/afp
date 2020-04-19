/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afpmvn.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 *
 * @author administrador
 */
public class Database implements Serializable {

    private List<Afp> afps;
    private TreeSet<Periodo> periodos;
    private Map<String, Afp> nameToAfp;

    public Database() {
        this.afps = new ArrayList<Afp>();
        this.periodos = new TreeSet<Periodo>();
        this.nameToAfp = new HashMap<String, Afp>();
    }
    
    public void put(String afpName, String fondoName, Periodo periodo, Tipo tipo, Double rateMonth) {
        Afp afp = putAfp(afpName);
        Fondo fondo = getFondo(fondoName);
        this.periodos.add(periodo);
        afp.put(fondo, periodo, tipo, rateMonth);
    }
    
    public Double get(Afp afp, Fondo fondo, Periodo periodo, Tipo tipo) {
        return afp.get(fondo, periodo,  tipo);
    }

    private Afp putAfp(String afpName) {
        Afp afp = this.nameToAfp.get(afpName);
        if (afp == null) {
            afp = new Afp(afpName);
            this.afps.add(afp);
            this.nameToAfp.put(afpName, afp);
        }
        return afp;
    }

//    public Afp getAfp(String afpName) {
//        return this.nameToAfp.get(afpName);
//    }

    public Periodo getPeriodo(int year, int month) {
        Periodo date = new Periodo(year, month);
        periodos.add(date);
        return date;
    }

    private Fondo getFondo(String fondoName) {
        return Fondo.valueOf(fondoName);
    }

    public List<Afp> getAfps() {
        return afps;
    }

    public TreeSet<Periodo> getPeriodos() {
        return periodos;
    }

    public Afp findAfp(String afpName) {
        return this.nameToAfp.get(afpName);
    }
    
    public Fondo findFondo(String fondoName) {
        return Fondo.valueOf(fondoName);
    }
}
