/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afpmvn.run;

import cl.rgonzalez.afpmvn.core.Afp;
import cl.rgonzalez.afpmvn.core.Database;
import cl.rgonzalez.afpmvn.core.Fondo;
import cl.rgonzalez.afpmvn.core.Periodo;
import cl.rgonzalez.afpmvn.core.Tipo;
import cl.rgonzalez.afpmvn.core.Storage;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author usuario
 */
public class ReadDatabase {

    public void read() {
        Storage storage = new Storage();
        Database db = storage.restore();

        List<Afp> afps = db.getAfps();
        TreeSet<Periodo> periodos = db.getPeriodos();
        Tipo[] tipos = Tipo.values();

        Fondo fondo = Fondo.A;
        for (Periodo periodo : periodos) {
            System.out.format("%s %s\n", periodo, fondo);
            for (Afp afp : afps) {
                Double value = db.get(afp, fondo, periodo, Tipo.RENTAB_MENSUAL);
                System.out.format("  %s %s\n", afp.getName(), value);
            }
        }
    }

    public static void main(String[] args) {
        ReadDatabase reader = new ReadDatabase();
        reader.read();
    }
}
