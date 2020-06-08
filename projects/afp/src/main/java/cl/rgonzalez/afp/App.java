/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp;

import cl.cgs.suitermes.afpcore.core.DbService;
import cl.cgs.suitermes.afpcore.core.DbStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner demo(DbStarter starter, DbService service) {
        return (args) -> {
            LOG.info("======================================");
            LOG.info("DBSTART");
            LOG.info("======================================");
            starter.start(new File("D:/trabajo/github/aplikadote/afp/data/"));
            service.view();
        };
    }
}
