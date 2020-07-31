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
import cl.rgonzalez.afp.core.services.AfpCoreServiceException;
import cl.rgonzalez.afp.core.services.AfpCoreSimulacionService;
import cl.rgonzalez.afp.core.services.AfpCoreSimulacionServiceData;
import cl.rgonzalez.afp.core.services.AfpCoreSimulacionServiceDataInterval;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AfpApi {

    @Autowired
    AfpCoreService service;
    @Autowired
    AfpCoreSimulacionService simulation;

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

    @GetMapping("/api/data/db")
    public Object getDb() {
        Map<String, Object> map = new HashMap<>();
        map.put("afps", service.findAfpAll());
        map.put("fondos", service.findFondoAll());
        map.put("periodos", service.findPeriodosSortedAll());
        map.put("rentabilidad", service.findRentabilidadAll());
        return map;
    }

    @GetMapping("/api/simulate")
    public Object simular(AfpApiSimulateInput input) throws AfpCoreServiceException {
        AfpDbAfp afp = service.findAfpById(input.getAfp());
        AfpDbFondo fondo = service.findFondoById(input.getFondo());
        AfpDbPeriodo periodoInicio = service.findPeriodoById(input.getInicio());
        AfpDbPeriodo periodoFin = service.findPeriodoById(input.getFin());

//        System.out.println("afp: " + afp);
//        System.out.println("fondo: " + fondo);
//        System.out.println("inicio: " + periodoInicio);
//        System.out.println("fin: " + periodoFin);
//        System.out.println("cotizacion: " + data.getCotizacion());
//        System.out.println("comision: " + data.getComision());
//        System.out.println("tasa: " + data.getTasaFija());
        simulation.setAfp(afp);
        simulation.setFondo(fondo);
        simulation.setPeriodoInicio(periodoInicio);
        simulation.setPeriodoFin(periodoFin);
        simulation.setCotizacion(input.getCotizacion());
        simulation.setComision(input.getComision());
        simulation.setTasaFija(input.getTasaFija());

        AfpCoreSimulacionServiceData data = simulation.simulate();
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        return map;
    }
}
