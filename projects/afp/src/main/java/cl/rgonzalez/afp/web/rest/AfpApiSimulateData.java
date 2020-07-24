/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.web.rest;

/**
 *
 * @author rgonzalez
 */
public class AfpApiSimulateData {

    private int afp;
    private int fondo;
    private int inicio;
    private int fin;
    private double cotizacion;
    private double comision;
    private double tasaFija;

    public AfpApiSimulateData() {
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public int getAfp() {
        return afp;
    }

    public void setAfp(int afp) {
        this.afp = afp;
    }

    public int getFondo() {
        return fondo;
    }

    public void setFondo(int fondo) {
        this.fondo = fondo;
    }

    public double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public double getTasaFija() {
        return tasaFija;
    }

    public void setTasaFija(double tasaFija) {
        this.tasaFija = tasaFija;
    }

    @Override
    public String toString() {
        return "AfpApiSimulateData{" + "afp=" + afp + ", fondo=" + fondo + ", inicio=" + inicio + ", fin=" + fin + ", cotizacion=" + cotizacion + ", comision=" + comision + ", tasaFija=" + tasaFija + '}';
    }

}
