/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author recor
 */
public class Servidor extends Computadoras {
    //ATRIBUTOS
    private int ventiladoresServidor;

    //CONSTRUCTOR
    public Servidor(int ventiladoresServidor, String inventarioID, double temperaturaCPU, double voltajeFuente) {
        super(inventarioID, temperaturaCPU, voltajeFuente);
        this.ventiladoresServidor = ventiladoresServidor;
    }
    
    //METODOS
    public boolean verificarVentiladores()
    {
        return ventiladoresServidor>=2;
    }
    
    @Override
    public String diagnosticoHardware() throws ErrorHardwareException{
        if (!verificarVentiladores())
        {
            throw new ErrorHardwareException("Problema en el servidor: Sistema de enfriamiento ineficiente.");
        }
        else if (!verificarVoltaje())
        {
            throw new ErrorHardwareException("Problemas en la fuente de poder del servidor. Se necesita revision.");
        }
        return "Servidor funcionando correctamente.";
    }
    
}
