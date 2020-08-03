package cl.rgonzalez.afp;

import cl.rgonzalez.afp.core.db.AfpDbAfpRepo;
import cl.rgonzalez.afp.core.db.AfpDbFondoRepo;
import cl.rgonzalez.afp.core.db.AfpDbValorCuotaLimitesRepo;
import cl.rgonzalez.afp.core.db.AfpDbValorCuotaRepo;
import cl.rgonzalez.afp.core.services.AfpCoreService;
import cl.rgonzalez.afp.core.services.AfpCoreStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Autowired
    AfpCoreStarter starter;
    @Autowired
    AfpCoreService service;
    //
    @Autowired
    AfpDbValorCuotaRepo repoValorCuota;
    @Autowired
    AfpDbValorCuotaLimitesRepo repoValorCuotaLimites;
    @Autowired
    AfpDbAfpRepo repoAfp;
    @Autowired
    AfpDbFondoRepo repoFondo;

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            LOG.info("======================================");
            LOG.info("DBSTART");
            LOG.info("======================================");

//            starter.start(new File("../../data/"));
            starter.start();
            
//            service.findAfpAll().forEach(System.out::println);
//            service.findPeriodosAllSorted().forEach(System.out::println);
            repoValorCuotaLimites.findAll().stream().forEach(System.out::println);
        };
    }

}
