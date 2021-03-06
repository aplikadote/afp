/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class AfpDbRentabilidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    //
    @ManyToOne
    private AfpDbPeriodo periodo;
    //
    @ManyToOne
    private AfpDbAfp afp;
    //
    @ManyToOne
    private AfpDbFondo fondo;
    //
    @Column(name = "rateMonth")
    private Double rateMonth;
    //
    @Column(name = "rateAcumThisYear")
    private Double rateAcumThisYear;
    //
    @Column(name = "rateAcumFullYear")
    private Double rateAcumFullYear;
    //
    @Column(name = "rateAverageTotal")
    private Double rateAverageTotal;

}
