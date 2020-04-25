/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afpmvn.core;

import java.util.Iterator;
import java.util.NavigableSet;

/**
 *
 * @author usuario
 */
public class Simulador {

    private Database db;

    public Simulador(Database db) {
        this.db = db;
    }

    public double simulate(double cotizacion, String afpName, String fondoName, int initYear, int initMonth) {
        Afp afp = db.findAfp(afpName);
        Fondo fondo = db.findFondo(fondoName);
        double bote = 0;
        Periodo initPeriodo = db.getPeriodo(initYear, initMonth);
        NavigableSet<Periodo> set = db.getPeriodos().tailSet(initPeriodo, true);
        Iterator<Periodo> iterator = set.iterator();
//        iterator.next();
        while (iterator.hasNext()) {
            Periodo periodo = iterator.next();
            Double rate = db.get(afp, fondo, periodo, Variable.RENTAB_MENSUAL);
            bote += cotizacion;
            bote += bote * rate;

            System.out.format("%s %.4f %s\n", periodo, rate, (int) bote);
        }

        return bote;
    }

    public double simulate(double cotizacion, double rate, int year, int month) {
        double bote = 0;
        Periodo date = db.getPeriodo(year, month);
        NavigableSet<Periodo> set = db.getPeriodos().tailSet(date, true);
        Iterator<Periodo> iterator = set.iterator();
//        iterator.next();
        while (iterator.hasNext()) {
            Periodo periodo = iterator.next();
            bote += cotizacion;
            bote += bote * rate;
            System.out.format("%s %.4f %s\n", periodo, rate, (int) bote);
        }
        return bote;
    }
    
    public double simulate(double cotizacion, double rate, int periods) {
        double bote = 0;
        for(int i=0; i<periods; i++) {
            bote += cotizacion;
            bote += bote * rate;
            System.out.format("%s %.4f %s\n", i, rate, (int) bote);
        }
        return bote;

    }

}
