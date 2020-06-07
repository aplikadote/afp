/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core;

import cl.rgonzalez.afp.db.DbPeriod;
import lombok.Data;

@Data
public class Cotizacion {

    private DbPeriod periodo;
    private double monto;
}
