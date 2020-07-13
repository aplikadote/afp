/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core.services;

import cl.rgonzalez.afp.core.db.AfpDbAfp;
import cl.rgonzalez.afp.core.db.AfpDbRentabilidad;
import cl.rgonzalez.afp.core.db.AfpDbFondo;
import cl.rgonzalez.afp.core.db.AfpDbInfo;
import cl.rgonzalez.afp.core.db.AfpDbPeriodo;
import java.io.File;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import cl.rgonzalez.afp.core.db.AfpDbAfpRepo;
import cl.rgonzalez.afp.core.db.AfpDbFondoRepo;
import cl.rgonzalez.afp.core.db.AfpDbInfoRepo;
import cl.rgonzalez.afp.core.db.AfpDbPeriodoRepo;
import cl.rgonzalez.afp.core.db.AfpDbRentabilidadRepo;

@Service
public class AfpCoreRentabilidadReader {

    private static final Logger LOG = LoggerFactory.getLogger(AfpCoreStarter.class);

    @Autowired
    AfpDbRentabilidadRepo repo;
    @Autowired
    AfpDbAfpRepo repoAfp;
    @Autowired
    AfpDbFondoRepo repoFondo;
    @Autowired
    AfpDbPeriodoRepo repoPeriod;
    @Autowired
    AfpDbInfoRepo repoInfo;
    //
    private DecimalFormat df;

    public AfpCoreRentabilidadReader() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        this.df = new DecimalFormat("0.00%", dfs);
    }

    public void readAndSave(File rentabilidadDir) {
        System.out.println("=====================================");
        System.out.println(rentabilidadDir);
        System.out.println("=====================================");

        try {
            int[] yearLimits = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
            int[] monthLimits = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};

            for (File file : rentabilidadDir.listFiles()) {
                LOG.info("  archivo: " + file.getName());

                String str = new String(Files.readAllBytes(file.toPath()));
                JSONObject json = new JSONObject(str);

                String yearStr = json.getString("year");
                String monthStr = json.getString("month");

                int year = Integer.parseInt(yearStr);
                int month = Integer.parseInt(monthStr);
                yearLimits[0] = Math.min(yearLimits[0], year);
                yearLimits[1] = Math.max(yearLimits[1], year);
                monthLimits[0] = Math.min(monthLimits[0], month);
                monthLimits[1] = Math.max(monthLimits[1], month);

                AfpDbPeriodo p = new AfpDbPeriodo();
                p.setMonth(month);
                p.setYear(year);
                repoPeriod.save(p);

                List<AfpDbRentabilidad> list = new ArrayList<>();

                JSONArray data = json.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jfondo = data.getJSONObject(i);
                    String fondo = jfondo.getString("fondo");

                    AfpDbFondo probe = new AfpDbFondo(fondo);
                    AfpDbFondo dbFondo = repoFondo.findOne(Example.of(probe)).orElseGet(() -> {
                        return repoFondo.save(probe);
                    });

                    double aveMonth = 0;
                    int total = 0;

                    JSONArray rows = jfondo.getJSONArray("rows");
                    int m = rows.length();
                    for (int j = 0; j < m; j++) {
                        JSONArray row = rows.getJSONArray(j);

                        String afp = row.getString(0);
                        AfpDbAfp probe2 = new AfpDbAfp(afp);
                        AfpDbAfp dbAfp = repoAfp.findOne(Example.of(probe2)).orElseGet(() -> {
                            return repoAfp.save(probe2);
                        });

                        String rateMonthRateStr = row.getString(1);
                        String rateAcumThisYearStr = row.getString(2);
                        String rateAcumFullYearStr = row.getString(3);
                        String rateAverageTotalStr = row.getString(4);

                        Double rateMonth = parse(rateMonthRateStr);
                        Double rateAcumThisYear = parse(rateAcumThisYearStr);
                        Double rateAcumFullYear = parse(rateAcumFullYearStr);
                        Double rateAverageTotal = parse(rateAverageTotalStr);

                        if (rateMonth != null) {
                            aveMonth += rateMonth;
                            total++;
                        }

                        AfpDbRentabilidad afpData = new AfpDbRentabilidad();
                        afpData.setPeriodo(p);
                        afpData.setAfp(dbAfp);
                        afpData.setFondo(dbFondo);
                        afpData.setRateMonth(rateMonth);
                        afpData.setRateAcumThisYear(rateAcumThisYear);
                        afpData.setRateAcumFullYear(rateAcumFullYear);
                        afpData.setRateAverageTotal(rateAverageTotal);

                        list.add(afpData);
                    }

                    AfpDbAfp probe2 = new AfpDbAfp("PROMEDIO");
                    AfpDbAfp dbAfp = repoAfp.findOne(Example.of(probe2)).orElseGet(() -> {
                        return repoAfp.save(probe2);
                    });

                    AfpDbRentabilidad afpData = new AfpDbRentabilidad();
                    afpData.setPeriodo(p);
                    afpData.setAfp(dbAfp);
                    afpData.setFondo(dbFondo);
                    afpData.setRateMonth(aveMonth / (double) total);
                    afpData.setRateAcumThisYear(0.0);
                    afpData.setRateAcumFullYear(0.0);
                    afpData.setRateAverageTotal(0.0);

                    list.add(afpData);
                }

                repo.saveAll(list);
            }

            AfpDbInfo info;
            info = new AfpDbInfo();
            info.setKey("MIN_YEAR");
            info.setType("Integer");
            info.setValue(Integer.toString(yearLimits[0]));
            repoInfo.save(info);

            info = new AfpDbInfo();
            info.setKey("MAX_YEAR");
            info.setType("Integer");
            info.setValue(Integer.toString(yearLimits[1]));
            repoInfo.save(info);

            info = new AfpDbInfo();
            info.setKey("MIN_MONTH");
            info.setType("Integer");
            info.setValue(Integer.toString(monthLimits[0]));
            repoInfo.save(info);

            info = new AfpDbInfo();
            info.setKey("MAX_MONTH");
            info.setType("Integer");
            info.setValue(Integer.toString(monthLimits[1]));
            repoInfo.save(info);

        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    private Double parse(String str) {
        try {
            return df.parse(str).doubleValue();
        } catch (ParseException ex) {
            return null;
        }
    }
}
