/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core.services;

import cl.rgonzalez.afp.core.db.AfpDbPeriodo;

/**
 *
 * @author rgonzalez
 */
public class AfpCoreSimulacionServiceData {

    private AfpDbPeriodo periodo;
    private double tasaAfp;
    private double totalAfp;
    private double tasaNone;
    private double totalNone;
    private double tasaFija;
    private double totalFija;

    public AfpDbPeriodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(AfpDbPeriodo periodo) {
        this.periodo = periodo;
    }

    public double getTasaAfp() {
        return tasaAfp;
    }

    public void setTasaAfp(double tasaAfp) {
        this.tasaAfp = tasaAfp;
    }

    public double getTotalAfp() {
        return totalAfp;
    }

    public void setTotalAfp(double totalAfp) {
        this.totalAfp = totalAfp;
    }

    public double getTasaNone() {
        return tasaNone;
    }

    public void setTasaNone(double tasaNone) {
        this.tasaNone = tasaNone;
    }

    public double getTotalNone() {
        return totalNone;
    }

    public void setTotalNone(double totalNone) {
        this.totalNone = totalNone;
    }

    public double getTasaFija() {
        return tasaFija;
    }

    public void setTasaFija(double tasaFija) {
        this.tasaFija = tasaFija;
    }

    public double getTotalFija() {
        return totalFija;
    }

    public void setTotalFija(double totalFija) {
        this.totalFija = totalFija;
    }
    
    

}
