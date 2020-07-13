/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.rentabilidad;

import cl.rgonzalez.afp.core.db.AfpDbAfp;
import cl.rgonzalez.afp.core.db.AfpDbFondo;
import cl.rgonzalez.afp.core.db.AfpDbRentabilidad;
import java.util.List;

/**
 *
 * @author rgonzalez
 */
public class AfpUiRentabilidadRow {

    AfpDbAfp afp;
    AfpDbFondo fondo;
    List<AfpDbRentabilidad> periodos;

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

    public List<AfpDbRentabilidad> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<AfpDbRentabilidad> periodos) {
        this.periodos = periodos;
    }
    
}
