/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afpmvn.run;

import cl.rgonzalez.afpmvn.core.Database;
import cl.rgonzalez.afpmvn.core.Simulador;
import cl.rgonzalez.afpmvn.core.Storage;

/**
 * BANSANDER ============================= first: [2005 7] last : [2008 2]
 *
 * SANTA MARIA ============================= first: [2005 7] last : [2008 2]
 *
 * CAPITAL ============================= first: [2008 3] last : [2015 11]
 * =============================
 *
 * MODELO ============================= first: [2010 8] last : [2015 11]
 *
 */
public class RunSimulation {

    public void run() {
        try {
            Storage storage = new Storage();
            Database db = storage.restore();
            Simulador sim = new Simulador(db);

            int year = 2007;
            int month = 1;
            double cotizacion = 30000;

//            db.getAfps().forEach(System.out::println);
//            double bote = sim.simulate(cotizacion, "CUPRUM", "A", year, month);
//            System.out.println((int) bote);

            System.out.println(" ------------------------- ");
            
            double bote2 = sim.simulate(cotizacion, 0.005, 120);
            System.out.println((int) bote2);

//            double bote1 = sim.simulate(mensual, 0.000, year, month);
//            System.out.println("bote1: " + bote1);
//
//            double bote2 = db.simulate(mensual, 0.0035, year, month);
//            double renta2 = (bote2 - bote1) / bote1;
//            System.out.println("bote2: " + bote2 + ", rentabilidad :" + (renta2 * 100));
//     
//            List<Afp> afps = new ArrayList<Afp>();
//            afps.add(db.getAfp("CUPRUM"));
//            afps.add(db.getAfp("HABITAT"));
//            afps.add(db.getAfp("PLANVITAL"));
//            afps.add(db.getAfp("PROVIDA"));
//            afps.add(db.getAfp("SISTEMA"));
//            
//            for (Afp afp: afps) {
//                String name = afp.getName();
//                System.out.println("=============================");
//                System.out.println(name);
//                System.out.println("=============================");
//                
//                for (int i = 0; i < Fondo.values().length; i++) {
//                    Fondo cluster = Fondo.values()[i];
//                    double bote = db.simulate(mensual, name, cluster.name(), year, month);
//                    double rentabilidad = (bote - bote1) / bote1;
////                    System.out.println("bote '" + cluster.name() + "': " + bote + ", rentabilidad '" + cluster.name() + "': " + (rentabilidad * 100));
//                    System.out.println((rentabilidad * 100));
//                }
//                System.out.println();
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RunSimulation main = new RunSimulation();
        main.run();
    }

}
