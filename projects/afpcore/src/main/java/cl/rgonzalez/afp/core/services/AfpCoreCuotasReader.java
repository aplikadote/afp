/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core.services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

/**
 *
 * @author rgonzalez
 */
public class AfpCoreCuotasReader {
    
    public void read() throws IOException {
//        File file = new File("/home/rgonzalez/trabajo/github/aplikadote/afp/data/cuota/vcfA2002-2002.csv");
        File file = new File("/home/rgonzalez/trabajo/github/aplikadote/afp/data/cuota/vcfA2020-2020.csv");
        List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
        lines.stream().skip(2).forEach(this::processLine);
    }
    
    private void processLine(String s){
        int i = 0;
        String[] fields = s.split(";");
        String fecha = fields[i++]; // yyyy-MM-dd
        
        String cuotaCuprum = fields[i++];
        String patrimonioCuprum = fields[i++];
        
        String cuotaHabitat = fields[i++];
        String patrimonioHabitat = fields[i++];
        
        String cuotaMagister = fields[i++];
        String patrimonioMagister = fields[i++];
        
        String cuotaPlanVital = fields[i++];
        String patrimonioPlanVital = fields[i++];
        
        String cuotaProvida = fields[i++];
        String patrimonioProvida = fields[i++];
        
        String cuotaSantaMaria = fields[i++];
        String patrimonioSantaMaria = fields[i++];
        
        String cuotaBansander = fields[i++];
        String patrimonioBansander = fields[i++];
        
        System.out.println(cuotaBansander);
    }

    public static void main(String[] args) {
        try {
            AfpCoreCuotasReader reader = new AfpCoreCuotasReader();
            reader.read();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
