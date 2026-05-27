package modelo;

public class ErrorHardwareException extends Exception{

    public ErrorHardwareException() {
        super("Error en el hardware, se necesita revision.");
    }

    public ErrorHardwareException(String message) {
        super(message);
    }
    
}
