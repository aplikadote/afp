/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp;

import cl.rgonzalez.afp.core.services.AfpCoreService;
import cl.rgonzalez.afp.core.services.AfpCoreStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"cl.rgonzalez.afp.core", "cl.rgonzalez.afp.web"})
@EntityScan(basePackages = {"cl.rgonzalez.afp.core.db"})
@EnableJpaRepositories(basePackages = "cl.rgonzalez.afp.core.db")
@SpringBootApplication
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    AfpCoreStarter starter;
    @Autowired
    AfpCoreService service;

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            LOG.info("======================================");
            LOG.info("DBSTART");
            LOG.info("======================================");
            starter.start(new File("../../data/"));
        };
    }
}
