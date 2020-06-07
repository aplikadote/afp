/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp;

import cl.rgonzalez.afp.core.DbService;
import cl.rgonzalez.afp.core.DbStarter;
import cl.rgonzalez.afpmvn.core.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public Database bean001() {
//        Storage st = new Storage();
//        return st.restore(new File(path));
        return new Database();
    }

    @Bean
    public CommandLineRunner demo(DbStarter starter, DbService service) {
        return (args) -> {
            LOG.info("======================================");
            LOG.info("DBSTART");
            LOG.info("======================================");
            starter.start();
            service.view();
        };
    }
}
