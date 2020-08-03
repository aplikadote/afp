/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core.services;

import cl.rgonzalez.afp.core.db.AfpDbAfp;
import cl.rgonzalez.afp.core.db.AfpDbAfpRepo;
import cl.rgonzalez.afp.core.db.AfpDbFondo;
import cl.rgonzalez.afp.core.db.AfpDbFondoRepo;
import cl.rgonzalez.afp.core.db.AfpDbInfoRepo;
import cl.rgonzalez.afp.core.db.AfpDbPeriodo;
import cl.rgonzalez.afp.core.db.AfpDbPeriodoRepo;
import cl.rgonzalez.afp.core.db.AfpDbValorCuota;
import cl.rgonzalez.afp.core.db.AfpDbValorCuotaLimites;
import cl.rgonzalez.afp.core.db.AfpDbValorCuotaLimitesRepo;
import cl.rgonzalez.afp.core.db.AfpDbValorCuotaRepo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AfpCoreValorCuotaReader {

    private static final Logger LOG = LoggerFactory.getLogger(AfpCoreValorCuotaReader.class);

    @Autowired
    AfpDbValorCuotaRepo repoValorCuota;
    @Autowired
    AfpDbValorCuotaLimitesRepo repoValorCuotaLimites;
    @Autowired
    AfpDbFondoRepo repoFondo;
    @Autowired
    AfpDbAfpRepo repoAfp;
    @Autowired
    AfpDbPeriodoRepo repoPeriodo;
    @Autowired
    AfpDbInfoRepo repoInfo;
    @Autowired
    ResourceLoader resourceLoader;
    //
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(new Locale("es-CL")));

    public void readAndSave() throws AfpCoreValorCuotaReaderException, IOException {
        Calendar c = Calendar.getInstance();
        c.set(2002, Calendar.AUGUST, 1);
        Date initialDate = c.getTime();

        // FONDOS
        List<AfpDbFondo> fondos = Arrays.asList(new AfpDbFondo("A"), new AfpDbFondo("B"), new AfpDbFondo("C"), new AfpDbFondo("D"), new AfpDbFondo("E"));
        repoFondo.saveAll(fondos);

        for (AfpDbFondo f : fondos) {
            processFondo(f, initialDate);
        }

        finish();
    }

    private void processFondo(AfpDbFondo fondo, Date initialDate) throws AfpCoreValorCuotaReaderException, IOException {
        Resource resource = resourceLoader.getResource("classpath:/valorcuota/vcf" + fondo.getName() + ".csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        String line = "";

        String[] header = null;
        List<String[]> yearData = null;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("Valores Provisorios")) {
                processChunk(fondo, header, yearData);
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
                processChunk(fondo, header, yearData);
                header = line.split(";");
                yearData = new ArrayList<>();
            }

            if (line.startsWith("20")) {
                try {
                    String dateStr = line.substring(0, 10);
//                    System.out.println("date: " + dateStr);
                    Date date = sdf.parse(dateStr);
                    if (date.after(initialDate) || date.equals(initialDate)) {
                        yearData.add(line.split(";"));
                    }
                } catch (ParseException ex) {
                    throw new RuntimeException("Error", ex);
                }
            }
        }
    }

    private void processChunk(AfpDbFondo fondo, String[] header, List<String[]> dataList) throws AfpCoreValorCuotaReaderException {
        if (header == null) {
            return;
        }

        List<String> afpnameList = new ArrayList<>();
        Map<String, AfpDbAfp> afpIndex = new HashMap<>();

        AfpDbAfp afpPromedio = repoAfp.findByName("PROMEDIO");
        afpPromedio = afpPromedio == null ? repoAfp.save(new AfpDbAfp("PROMEDIO")) : afpPromedio;
        afpIndex.put("PROMEDIO", afpPromedio);

        for (int i = 1; i < header.length; i++) {
            String afpName = header[i];
            if (!afpName.isEmpty()) {
                AfpDbAfp afp = repoAfp.findByName(afpName);
                afp = afp == null ? repoAfp.save(new AfpDbAfp(afpName)) : afp;
                afpnameList.add(afpName);
                afpIndex.put(afpName, afp);
            }
        }

        Set<AfpDbPeriodo> periodos = new HashSet<>();
        try {
            List<AfpDbValorCuota> valores = new ArrayList<>();
            for (String[] data : dataList) {
                String fechaStr = data[0];
                Date fecha = sdf.parse(fechaStr);
                AfpDbPeriodo p = parsePeriodo(fechaStr);
                periodos.add(p);

                double promedio = 0;
                for (int i = 0; i < afpnameList.size(); i++) {
                    String afpStr = afpnameList.get(i);
                    AfpDbAfp afp = afpIndex.get(afpStr);

                    String valorStr = data[i * 2 + 1];
                    Double valor = df.parse(valorStr).doubleValue();

                    AfpDbValorCuota cuota = new AfpDbValorCuota();
                    cuota.setAfp(afp);
                    cuota.setFondo(fondo);
                    cuota.setFecha(fecha);
                    cuota.setValor(valor);
                    valores.add(cuota);
                    promedio += valor;
                }
                promedio = (double) promedio / (double) afpnameList.size();

                AfpDbValorCuota cuota = new AfpDbValorCuota();
                cuota.setAfp(afpIndex.get("PROMEDIO"));
                cuota.setFondo(fondo);
                cuota.setFecha(fecha);
                cuota.setValor(promedio);
                valores.add(cuota);
            }
            repoValorCuota.saveAll(valores);
            repoPeriodo.saveAll(periodos);
        } catch (ParseException ex) {
            throw new AfpCoreValorCuotaReaderException("Error de parseo", ex);
        }

    }

    private void finish() {
        List<AfpDbAfp> afps = repoAfp.findAll();
        AfpDbFondo fondoA = repoFondo.findByName("A");
        for (AfpDbAfp afp : afps) {
            AfpDbValorCuota probe = new AfpDbValorCuota();
            probe.setAfp(afp);
            probe.setFondo(fondoA);

            List<AfpDbValorCuota> data = repoValorCuota.findAll(Example.of(probe), Sort.by("fecha"));
            if (data.isEmpty()) {
                repoAfp.delete(afp);
            } else {
                AfpDbValorCuotaLimites limites = new AfpDbValorCuotaLimites();
                limites.setAfp(afp);
                limites.setInicio(data.get(0).getFecha());
                limites.setFin(data.get(data.size() - 1).getFecha());
                
                repoValorCuotaLimites.save(limites);
            }

        }

    }

    private AfpDbPeriodo parsePeriodo(String yyyyMMdd) {
        String[] split = yyyyMMdd.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        AfpDbPeriodo p = new AfpDbPeriodo();
        p.setYear(year);
        p.setMonth(month);
        return p;
    }

}
