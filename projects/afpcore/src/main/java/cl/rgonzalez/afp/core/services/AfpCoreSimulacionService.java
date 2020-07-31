/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core.services;

import cl.rgonzalez.afp.core.db.AfpDbAfp;
import cl.rgonzalez.afp.core.db.AfpDbFondo;
import cl.rgonzalez.afp.core.db.AfpDbPeriodo;
import cl.rgonzalez.afp.core.db.AfpDbRentabilidad;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpCoreSimulacionService {

    @Autowired
    AfpCoreService service;
    //
    private AfpDbAfp afp;
    private AfpDbFondo fondo;
    private AfpDbPeriodo periodoInicio;
    private AfpDbPeriodo periodoFin;
    private double cotizacion;
    private double comision;
    private double tasaFija;

    public AfpCoreSimulacionServiceData simulate() throws AfpCoreServiceException {
        List<AfpCoreSimulacionServiceDataInterval> list = new ArrayList<>();

        List<AfpDbRentabilidad> rentabilidadList = service.findRentabilidadBy(afp, fondo, periodoInicio, periodoFin);
        if (rentabilidadList.isEmpty()) {
            return new AfpCoreSimulacionServiceData();
        }

        rentabilidadList.sort(new Comparator<AfpDbRentabilidad>() {
            @Override
            public int compare(AfpDbRentabilidad o1, AfpDbRentabilidad o2) {
                int p1 = service.formatPeriodoInt(o1.getPeriodo());
                int p2 = service.formatPeriodoInt(o2.getPeriodo());
                return Integer.compare(p1, p2);
            }
        });

        double accumAfp = 0;
        double accumNone = 0;
        double accumTasaFija = 0;
        for (AfpDbRentabilidad obj : rentabilidadList) {
            Double tasaVariable = obj.getRateMonth();
            double pozo = accumAfp + cotizacion;
            double ganancia = pozo * tasaVariable;
            accumAfp = pozo + ganancia;

            accumNone = accumNone + cotizacion + comision;

            pozo = accumTasaFija + cotizacion + comision;
            ganancia = pozo * tasaFija;
            accumTasaFija = pozo + ganancia;

            AfpCoreSimulacionServiceDataInterval row = new AfpCoreSimulacionServiceDataInterval();
            row.setPeriodo(obj.getPeriodo());
            row.setTasaAfp(tasaVariable);
            row.setTotalAfp(accumAfp);
            row.setTasaNone(0);
            row.setTotalNone(accumNone);
            row.setTasaFija(tasaFija);
            row.setTotalFija(accumTasaFija);

            list.add(row);
        }

        return new AfpCoreSimulacionServiceData(list);
    }

    public AfpDbAfp getAfp() {
        return afp;
    }

    public void setAfp(AfpDbAfp afp) {
        this.afp = afp;
    }

    public AfpDbFondo getFondo() {
        return fondo;
    }

    public void setFondo(AfpDbFondo fondo) {
        this.fondo = fondo;
    }

    public AfpDbPeriodo getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(AfpDbPeriodo periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    public AfpDbPeriodo getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(AfpDbPeriodo periodoFin) {
        this.periodoFin = periodoFin;
    }

    public double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public double getTasaFija() {
        return tasaFija;
    }

    public void setTasaFija(double tasaFija) {
        this.tasaFija = tasaFija;
    }
}
