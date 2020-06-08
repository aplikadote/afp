/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.cgs.suitermes.afpcore.db.DbDataRepo;

@RestController()
@RequestMapping("/api/data")
public class AppRestData {

    @Autowired
    private DbDataRepo repo;

    @GetMapping("all")
    public Object init() {
        return repo.findAll();
    }
}
