/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core;

import cl.rgonzalez.afp.db.DbAfp;
import cl.rgonzalez.afp.db.DbFondo;
import cl.rgonzalez.afp.db.DbPeriod;
import java.util.Map;
import lombok.Data;

@Data
public class Inversion {
       
    private DbAfp afp;
    private DbPeriod periodo;
    private Map<DbFondo, Double> porcentajePorFondo; // 2 fondos max.
}
