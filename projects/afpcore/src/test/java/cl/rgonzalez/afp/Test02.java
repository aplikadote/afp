/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rgonzalez
 */
public class Test02 {

    private List<ValorCuota> valores = new ArrayList<>();
    
    public void test() throws IOException {
        File file = new File("/home/rgonzalez/vcfA2002-2020.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = "";

        String[] header = null;
        List<String[]> yearData = null;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("Valores Provisorios")) {
                processData(header, yearData);
                header = line.split(";");
                yearData = new ArrayList<>();
                break;
            }

            if (line.startsWith("Valores")) {
                continue;
            }

            if (line.startsWith(";")) {
                continue;
            }

            if (line.startsWith("Fecha")) {
                processData(header, yearData);
                header = line.split(";");
                yearData = new ArrayList<>();
            }

            if (line.startsWith("20")) {
                yearData.add(line.split(";"));
            }
        }
        
        
        System.out.println("valores.size(): " + valores.size());
        
        valores.stream().forEach(System.out::println);
    }

    public void processData(String[] header, List<String[]> dataList) {
        if (header == null) {
            return;
        }

        List<String> afps = new ArrayList<>();
        for (int i = 1; i < header.length; i++) {
            String h = header[i];
            if (!h.isEmpty()) {
//                System.out.println(h);
                afps.add(h);
            }
        }

        for (String[] data : dataList) {
            String fechaStr = data[0];

            for (int i = 0; i < afps.size(); i++) {
                String afpStr = afps.get(i);
                String valorStr = data[i * 2 + 1];
                
                ValorCuota cuota = new ValorCuota();
                cuota.setAfp(afpStr);
                cuota.setFecha(fechaStr);
                cuota.setValor(valorStr);
                
                valores.add(cuota);
            }
        }

    }

    public static void main(String[] args) {
        try {
            Test02 test = new Test02();
            test.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
