/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui;

import cl.rgonzalez.afpmvn.core.Database;
import cl.rgonzalez.afpmvn.core.Storage;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    @Value("${db.path}")
    private String path;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

//    @EventListener({ApplicationReadyEvent.class})
//    private void applicationReadyEvent() {
//        new BrowserRunner().run();
//    }
    @Bean
    public Database bean001() {
        Storage st = new Storage();
        return st.restore(new File(path));
    }
}
