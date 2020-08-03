/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpCoreStarter {

    private static final Logger LOG = LoggerFactory.getLogger(AfpCoreStarter.class);

//    @Autowired
//    AfpCoreRentabilidadReader readerRentabilidad;
    @Autowired
    AfpCoreValorCuotaReader readerValorCuota;

    public AfpCoreStarter() {
    }

//    public void start(File rootDir) {
    public void start() {
        try {
            //        System.out.println("=====================================");
//        System.out.println(rootDir);
//        System.out.println("=====================================");

//        readerRentabilidad.readAndSave(new File(rootDir, "rentabilidad"));
//        readerValorCuota.readAndSave();
            readerValorCuota.readAndSave();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
