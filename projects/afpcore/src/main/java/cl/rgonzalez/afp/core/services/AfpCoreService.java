/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core.services;

import cl.rgonzalez.afp.core.db.AfpDbAfp;
import cl.rgonzalez.afp.core.db.AfpDbFondo;
import cl.rgonzalez.afp.core.db.AfpDbPeriodo;
import cl.rgonzalez.afp.core.db.AfpDbRentabilidad;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import cl.rgonzalez.afp.core.db.AfpDbAfpRepo;
import cl.rgonzalez.afp.core.db.AfpDbFondoRepo;
import cl.rgonzalez.afp.core.db.AfpDbInfoRepo;
import cl.rgonzalez.afp.core.db.AfpDbPeriodoRepo;
import cl.rgonzalez.afp.core.db.AfpDbRentabilidadRepo;
import cl.rgonzalez.afp.core.db.AfpDbValorCuota;
import cl.rgonzalez.afp.core.db.AfpDbValorCuotaRepo;
import java.util.ArrayList;
import javax.transaction.Transactional;

@Service
@Transactional
public class AfpCoreService {

    @Autowired
    AfpDbInfoRepo repoInfo;
    @Autowired
    AfpDbRentabilidadRepo repoRentabilidad;
    @Autowired
    AfpDbPeriodoRepo repoPeriodo;
    @Autowired
    AfpDbAfpRepo repoAfp;
    @Autowired
    AfpDbFondoRepo repoFondo;
    @Autowired
    AfpDbValorCuotaRepo repoValorCuota;

    public File[] getAllRentabilidadFiles() {
        return null;
    }

    public List<AfpDbPeriodo> findPeriodosAll() {
        return repoPeriodo.findAll();
    }

    public List<AfpDbPeriodo> findPeriodosAllSorted() {
        List<AfpDbPeriodo> periodos = repoPeriodo.findAll();
        Collections.sort(periodos, new Comparator<AfpDbPeriodo>() {
            @Override
            public int compare(AfpDbPeriodo o1, AfpDbPeriodo o2) {
                int diff = Integer.compare(o1.getYear(), o2.getYear());
                return diff == 0 ? Integer.compare(o1.getMonth(), o2.getMonth()) : diff;
            }
        });
        return periodos;
    }

    public List<AfpDbAfp> findAfpAll() {
        return repoAfp.findAll();
    }

    public List<AfpDbFondo> findFondoAll() {
        return repoFondo.findAll();
    }

    public List<AfpDbRentabilidad> findRentabilidadAll() {
        return repoRentabilidad.findAll();
    }

    public List<AfpDbRentabilidad> findRentabilidadBy(AfpDbPeriodo periodo) {
        AfpDbRentabilidad probe = new AfpDbRentabilidad();
        probe.setPeriodo(periodo);
        return repoRentabilidad.findAll(Example.of(probe));
    }

    public List<AfpDbRentabilidad> findRentabilidadBy(AfpDbAfp afp, AfpDbPeriodo periodo) {
        AfpDbRentabilidad probe = new AfpDbRentabilidad();
        probe.setPeriodo(periodo);
        probe.setAfp(afp);
        return repoRentabilidad.findAll(Example.of(probe));
    }

    public List<AfpDbRentabilidad> findRentabilidadBy(AfpDbAfp afp, AfpDbFondo fondo, AfpDbPeriodo inicio, AfpDbPeriodo fin) throws AfpCoreServiceException {
        checkRange(inicio, fin);

        AfpDbRentabilidad probe = new AfpDbRentabilidad();
        probe.setAfp(afp);
        probe.setFondo(fondo);

        List<AfpDbRentabilidad> list = repoRentabilidad.findAll(Example.of(probe));
        return list.stream().filter(p -> inRange(p.getPeriodo(), inicio, fin)).collect(Collectors.toList());
    }

    public void checkRange(AfpDbPeriodo inicio, AfpDbPeriodo fin) throws AfpCoreServiceException {
        int c1 = formatPeriodoInt(inicio);
        int c2 = formatPeriodoInt(fin);
        if (c1 > c2) {
            throw new AfpCoreServiceException("Rango periodos erroneo: " + formatPeriodo(inicio) + " > " + formatPeriodo(fin));
        }
    }

    public boolean inRange(AfpDbPeriodo periodo, AfpDbPeriodo inicio, AfpDbPeriodo fin) {
        int c1 = formatPeriodoInt(inicio);
        int c2 = formatPeriodoInt(fin);
        int c = formatPeriodoInt(periodo);
        return c1 <= c && c <= c2;
    }

    public String formatDbl(Double value, int decs) {
        DecimalFormat df = new DecimalFormat("#,###.#");
        df.setMaximumFractionDigits(decs);
        return df.format(value);
    }

    public String formatDblPer(Double value, int decs) {
        DecimalFormat df = new DecimalFormat("#.#%");
        df.setMaximumFractionDigits(decs);
        return df.format(value);
    }

    public String formatPeriodo(AfpDbPeriodo periodo) {
        String m = periodo.getMonth() < 10 ? "0" + periodo.getMonth() : Integer.toString(periodo.getMonth());
        return periodo.getYear() + "/" + m;
    }

    public int formatPeriodoInt(AfpDbPeriodo periodo) {
        return periodo.getYear() * 100000 + periodo.getMonth();
    }

    public double parseDbl(String strCotizacion) throws ParseException {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.parse(strCotizacion).doubleValue();
    }

    public AfpDbPeriodo findPeriodoById(int id) {
        return repoPeriodo.findById(id).orElse(null);
    }

    public AfpDbAfp findAfpById(int id) {
        return repoAfp.findById(id).orElse(null);
    }

    public AfpDbFondo findFondoById(int id) {
        return repoFondo.findById(id).orElse(null);
    }

    public List<AfpDbValorCuota> findValorCuotaAll(AfpDbFondo fondo, AfpDbPeriodo inicio, AfpDbPeriodo fin) throws AfpCoreServiceException {
        checkRange(inicio, fin);

        AfpDbValorCuota probe = new AfpDbValorCuota();
        probe.setAfp(repoAfp.findByName("PROMEDIO"));
        probe.setFondo(fondo);

        List<AfpDbValorCuota> list = repoValorCuota.findAll(Example.of(probe));

        List<AfpDbPeriodo> periodos = findPeriodosAllSorted();
        List<AfpDbPeriodo> filtered = new ArrayList<>();
        int from = periodos.indexOf(inicio);
        int to = periodos.indexOf(fin);
        for (int i = from; i < to; i++) {
            filtered.add(periodos.get(i));
        }
        
        return null;
    }

}
