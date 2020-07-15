/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.web.rest;

import cl.rgonzalez.afp.core.db.AfpDbAfp;
import cl.rgonzalez.afp.core.db.AfpDbFondo;
import cl.rgonzalez.afp.core.db.AfpDbPeriodo;
import cl.rgonzalez.afp.core.db.AfpDbRentabilidad;
import cl.rgonzalez.afp.core.services.AfpCoreService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AfpApi {

    @Autowired
    AfpCoreService service;

    @GetMapping("/api/data/afps")
    public List<AfpDbAfp> getAfps() {
        return service.findAfpAll();
    }

    @GetMapping("/api/data/fondos")
    public List<AfpDbFondo> getFondos() {
        return service.findFondoAll();
    }
    
    @GetMapping("/api/data/periodos")
    public List<AfpDbPeriodo> getPeriodos() {
        return service.findPeriodosSortedAll();
    }

    @GetMapping("/api/data/rentabilidad")
    public List<AfpDbRentabilidad> getRentabilidad() {
        return service.findRentabilidadAll();
    }
}
