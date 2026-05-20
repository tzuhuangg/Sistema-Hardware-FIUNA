/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author recor
 */
public class ErrorHardwareException extends Exception{

    public ErrorHardwareException() {
        super("Error en el hardware, se necesita revision.");
    }

    public ErrorHardwareException(String message) {
        super(message);
    }
    
}
