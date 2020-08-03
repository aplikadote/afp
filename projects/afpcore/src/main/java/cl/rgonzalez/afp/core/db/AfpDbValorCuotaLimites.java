/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core.db;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class AfpDbValorCuotaLimites {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    //
    @ManyToOne
    @JoinColumn(name = "afp_id", referencedColumnName = "id", nullable = false)
    private AfpDbAfp afp;
    //
    @Temporal(TemporalType.DATE)
    @Column(name = "inicio")
    private Date inicio;
    //
    @Temporal(TemporalType.DATE)
    @Column(name = "fin")
    private Date fin;
}
