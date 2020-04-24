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
import cl.rgonzalez.afpmvn.core.Storage;
import cl.rgonzalez.afpmvn.core.Tipo;
import cl.rgonzalez.afpmvn.run.Utils;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppRest {

    @GetMapping("/api/test")
    public Object test() {
        return Arrays.asList("A", "B", "C");
    }

    @GetMapping("/api/plots")
    public Object plots() {
        Storage storage = new Storage();
        Database db = storage.restore(Utils.DB);

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
        
        return "[]";
    }
}
