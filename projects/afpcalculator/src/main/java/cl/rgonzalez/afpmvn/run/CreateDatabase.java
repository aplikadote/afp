/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afpmvn.run;

import cl.rgonzalez.afpmvn.core.Periodo;
import cl.rgonzalez.afpmvn.core.Database;
import cl.rgonzalez.afpmvn.core.Variable;
import cl.rgonzalez.afpmvn.core.Storage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author administrador
 */
public class CreateDatabase {

    private Database db;
    private DecimalFormat df;

    public CreateDatabase() {
        this.db = new Database();

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        this.df = new DecimalFormat("0.00%", dfs);
    }

    public void start(File dirData, File fileDb) throws IOException, ParseException {
        System.out.println(dirData.getAbsolutePath());

        for (File file : dirData.listFiles()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            JSONObject json = new JSONObject(sb.toString());
            processJson(json);

            Storage storage = new Storage();
            storage.persist(fileDb, db);
        }

    }

    public void processJson(JSONObject json) throws IOException, ParseException {
        String yearStr = json.getString("year");
        String monthStr = json.getString("month");
        JSONArray data = json.getJSONArray("data");

        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);

        Periodo periodo = new Periodo(year, month);

        int n = data.length();

        for (int i = 0; i < n; i++) {
            JSONObject jfondo = data.getJSONObject(i);
            String fondo = jfondo.getString("fondo");
//            System.out.println("fondoName : " + fondoName);

            JSONArray rows = jfondo.getJSONArray("rows");
            int m = rows.length();
            for (int j = 0; j < m; j++) {
                JSONArray row = rows.getJSONArray(j);

                String afp = row.getString(0);
                String rateMonthRateStr = row.getString(1);
                String rateAcumThisYearStr = row.getString(2);
                String rateAcumFullYearStr = row.getString(3);
                String rateAverageTotalStr = row.getString(4);

                Double rateMonth = parse(rateMonthRateStr);
                Double rateAcumThisYear = parse(rateAcumThisYearStr);
                Double rateAcumFullYear = parse(rateAcumFullYearStr);
                Double rateAverageTotal = parse(rateAverageTotalStr);

                db.put(afp, fondo, periodo, Variable.RENTAB_MENSUAL, rateMonth);
                db.put(afp, fondo, periodo, Variable.RENTAB_DESDE_ENERO, rateAcumThisYear);
                db.put(afp, fondo, periodo, Variable.RENTAB_ULTIMOS_12_MESES, rateAcumFullYear);
                db.put(afp, fondo, periodo, Variable.PROMEDIO_ANUAL_DESDE_20020927, rateAverageTotal);
            }
        }
    }

    private Double parse(String str) {
        try {
            return df.parse(str).doubleValue();
        } catch (ParseException ex) {
            return null;
        }
    }

    public Database getRoot() {
        return db;
    }

    public static void main(String[] args) throws ParseException {
        try {
            CreateDatabase process = new CreateDatabase();
            process.start(Utils.DATA, Utils.DB);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
