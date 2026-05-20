/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author recor
 */
public class PCEscritorio extends Computadoras {
    //ATRIBUTOS
    private boolean necesitaMantenimiento;
    
    //CONSTRUCTOR
    public PCEscritorio(boolean necesitaMantenimiento, String inventarioID, double temperaturaCPU, double voltajeFuente) {
        super(inventarioID, temperaturaCPU, voltajeFuente);
        this.necesitaMantenimiento = necesitaMantenimiento;
    }
    
    //METODOS
    @Override
    public String diagnosticoHardware() throws ErrorHardwareException{
        if (!verificarVoltaje())
        {
            throw new ErrorHardwareException("Problemas en la fuente de poder. Se necesita revision.");
        }
        else if (!verificarTemperaturaCPU())
        {
            throw new ErrorHardwareException("Problema de sobrecalentamiento en la CPU. Se necesita revision.");
        }
        return "Equipo en estado normal.";
    }
    
}
