/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp;

import cl.rgonzalez.afp.core.services.AfpCoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class Test01 {
    
    @Autowired
    AfpCoreService service;

    @Test
    public void test01() {
        System.out.println("----------------------");
        System.out.println("TEST");
        System.out.println("----------------------");
        service.findAllAfp().forEach(System.out::println);
    }

}
