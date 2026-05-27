package modelo;

import java.io.Serializable;

public abstract class Computadoras implements Serializable{
    //ATRIBUTOS
    private String inventarioID;
    private double temperaturaCPU;
    private double voltajeFuente;
    protected boolean necesitaMantenimiento;
    
    //CONSTRUCTOR
    public Computadoras(String inventarioID, double temperaturaCPU, double voltajeFuente) {
        this.inventarioID = inventarioID;
        this.temperaturaCPU = temperaturaCPU;
        this.voltajeFuente = voltajeFuente;
        this.necesitaMantenimiento = false;
    }
    
    //GETTERS Y SETTERS
    public String getInventarioID() {
        return inventarioID;
    }

    public double getTemperaturaCPU() {
        return temperaturaCPU;
    }

    public void setTemperaturaCPU(double temperaturaCPU) throws ErrorHardwareException{
        this.temperaturaCPU = temperaturaCPU;
        if (!verificarTemperaturaCPU())
        {
            this.necesitaMantenimiento = true;
            throw new ErrorHardwareException("Problema de sobrecalentamiento en la CPU.");
        }
    }

    public double getVoltajeFuente() {
        return voltajeFuente;
    }

    public void setVoltajeFuente(double voltajeFuente) throws ErrorHardwareException{
        this.voltajeFuente = voltajeFuente;
        if (!verificarVoltaje())
        {
            this.necesitaMantenimiento = true;
            throw new ErrorHardwareException("Problemas en la fuente de poder.");
        }
    }

    public boolean isNecesitaMantenimiento() {
        return necesitaMantenimiento;
    }

    //METODOS
    public abstract String diagnosticoHardware() throws ErrorHardwareException;
    
    public boolean verificarVoltaje()
    {
        // tolerancia del 5% para 12V [11.4V;12.6V]
        return voltajeFuente>=11.4 && voltajeFuente<=12.6;
    }
    
    public boolean verificarTemperaturaCPU()
    {
        return temperaturaCPU<=85.0;
    }
}
