/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core;

import cl.rgonzalez.afp.db.DbInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbService {
    
    @Autowired
    DbInfoRepo repoInfo;
    
    public void view() {
        System.out.println("======================");
        System.out.println("VIEW");
        System.out.println("======================");
        repoInfo.findAll().forEach(e -> {
            System.out.println(String.format("%s (%s) = %s", e.getKey(), e.getType(), e.getValue()));
        });
    }
}
