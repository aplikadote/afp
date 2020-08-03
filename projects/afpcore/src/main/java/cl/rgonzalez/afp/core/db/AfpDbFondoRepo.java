/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core.db;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author aplik
 */
public interface AfpDbFondoRepo extends CrudRepository<AfpDbFondo, Integer>, JpaRepository<AfpDbFondo, Integer> {

    default AfpDbFondo findByName(String name) {
        AfpDbFondo probe = new AfpDbFondo();
        probe.setName(name);
        return findOne(Example.of(probe)).orElse(null);
    }
}
