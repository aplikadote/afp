/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp;

import cl.rgonzalez.afp.ui.AfpUiFrameMain;
import cl.rgonzalez.afp.core.services.AfpCoreService;
import cl.rgonzalez.afp.core.services.AfpCoreStarter;
import com.alee.laf.WebLookAndFeel;
import java.awt.EventQueue;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"cl.rgonzalez.afp.core", "cl.rgonzalez.afp.ui"})
@EntityScan(basePackages = {"cl.rgonzalez.afp.core.db"})
@EnableJpaRepositories(basePackages = "cl.rgonzalez.afp.core.db")
@SpringBootApplication
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Autowired
    AfpUiFrameMain frame;

    @Autowired
    AfpCoreStarter starter;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.headless(false);
        builder.run(args);
    }

    @Bean
    public CommandLineRunner demo(AfpCoreStarter starter, AfpCoreService service) {
        return (args) -> {
            WebLookAndFeel.install();
            
            starter.start(new File("../../data"));
            
            EventQueue.invokeLater(() -> {
                frame.init();
                frame.showMe();
            });
        };
    }

}
