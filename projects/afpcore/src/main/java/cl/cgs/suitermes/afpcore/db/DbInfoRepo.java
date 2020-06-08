/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.cgs.suitermes.afpcore.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author aplik
 */
public interface DbInfoRepo extends CrudRepository<DbInfo, Integer>, JpaRepository<DbInfo, Integer> {

}
