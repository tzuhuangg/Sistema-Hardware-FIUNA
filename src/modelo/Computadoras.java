/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author recor
 */
public abstract class Computadoras implements Serializable{
    //ATRIBUTOS
    private String inventarioID;
    private double temperaturaCPU;
    private double voltajeFuente;
    
    //CONSTRUCTOR
    public Computadoras(String inventarioID, double temperaturaCPU, double voltajeFuente) {
        this.inventarioID = inventarioID;
        this.temperaturaCPU = temperaturaCPU;
        this.voltajeFuente = voltajeFuente;
    }
    
    //GETTERS Y SETTERS
    public String getInventarioID() {
        return inventarioID;
    }

    public void setInventarioID(String inventarioID) {
        this.inventarioID = inventarioID;
    }

    public double getTemperaturaCPU() {
        return temperaturaCPU;
    }

    public void setTemperaturaCPU(double temperaturaCPU) {
        this.temperaturaCPU = temperaturaCPU;
    }

    public double getVoltajeFuente() {
        return voltajeFuente;
    }

    public void setVoltajeFuente(double voltajeFuente) {
        this.voltajeFuente = voltajeFuente;
    }
    
    //METODOS
    public abstract String diagnosticoHardware() throws ErrorHardwareException;
    
    public boolean verificarVoltaje()
    {
        // Tolerancia del 5% para 12V [11.4V;12.6V]
        return voltajeFuente>=11.4 && voltajeFuente<=12.6;
    }
    
    public boolean verificarTemperaturaCPU()
    {
        return temperaturaCPU<=85.0;
    }
}
