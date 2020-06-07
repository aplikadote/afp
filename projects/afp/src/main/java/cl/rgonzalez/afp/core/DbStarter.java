/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core;

import cl.rgonzalez.afp.db.DbAfp;
import cl.rgonzalez.afp.db.DbAfpRepo;
import cl.rgonzalez.afp.db.DbData;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import cl.rgonzalez.afp.db.DbDataRepo;
import cl.rgonzalez.afp.db.DbFondo;
import cl.rgonzalez.afp.db.DbFondoRepo;
import cl.rgonzalez.afp.db.DbInfo;
import cl.rgonzalez.afp.db.DbInfoRepo;
import cl.rgonzalez.afp.db.DbPeriod;
import cl.rgonzalez.afp.db.DbPeriodRepo;
import org.springframework.data.domain.Example;

@Service
public class DbStarter {

    private static final Logger LOG = LoggerFactory.getLogger(DbStarter.class);

    @Autowired
    DbDataRepo repo;
    @Autowired
    DbAfpRepo repoAfp;
    @Autowired
    DbFondoRepo repoFondo;
    @Autowired
    DbPeriodRepo repoPeriod;
    @Autowired
    DbInfoRepo repoInfo;
    //
    @Value("${afp.data.dir}")
    private String dataDir;
    //
    private DecimalFormat df;

    public DbStarter() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        this.df = new DecimalFormat("0.00%", dfs);
    }

    public void start() {
        try {
            int[] yearLimits = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
            int[] monthLimits = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};

            File dir = new File(dataDir);
            for (File file : dir.listFiles()) {
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

                DbPeriod p = new DbPeriod();
                p.setMonth(month);
                p.setYear(year);
                repoPeriod.save(p);

                List<DbData> list = new ArrayList<>();

                JSONArray data = json.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jfondo = data.getJSONObject(i);
                    String fondo = jfondo.getString("fondo");

                    DbFondo probe = new DbFondo(fondo);
                    DbFondo dbFondo = repoFondo.findOne(Example.of(probe)).orElseGet(() -> {
                        return repoFondo.save(probe);
                    });

                    double aveMonth = 0;
                    int total = 0;

                    JSONArray rows = jfondo.getJSONArray("rows");
                    int m = rows.length();
                    for (int j = 0; j < m; j++) {
                        JSONArray row = rows.getJSONArray(j);

                        String afp = row.getString(0);
                        DbAfp probe2 = new DbAfp(afp);
                        DbAfp dbAfp = repoAfp.findOne(Example.of(probe2)).orElseGet(() -> {
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

                        DbData afpData = new DbData();
                        afpData.setPeriodo(p);
                        afpData.setAfp(dbAfp);
                        afpData.setFondo(dbFondo);
                        afpData.setRateMonth(rateMonth);
                        afpData.setRateAcumThisYear(rateAcumThisYear);
                        afpData.setRateAcumFullYear(rateAcumFullYear);
                        afpData.setRateAverageTotal(rateAverageTotal);

                        list.add(afpData);
                    }

                    DbAfp probe2 = new DbAfp("PROMEDIO");
                    DbAfp dbAfp = repoAfp.findOne(Example.of(probe2)).orElseGet(() -> {
                        return repoAfp.save(probe2);
                    });

                    DbData afpData = new DbData();
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
            
            DbInfo info;
            info = new DbInfo();
            info.setKey("MIN_YEAR");
            info.setType("Integer");
            info.setValue(Integer.toString(yearLimits[0]));
            repoInfo.save(info);
            
            info = new DbInfo();
            info.setKey("MAX_YEAR");
            info.setType("Integer");
            info.setValue(Integer.toString(yearLimits[1]));
            repoInfo.save(info);
            
            info = new DbInfo();
            info.setKey("MIN_MONTH");
            info.setType("Integer");
            info.setValue(Integer.toString(monthLimits[0]));
            repoInfo.save(info);
            
            info = new DbInfo();
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
