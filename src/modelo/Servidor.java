package modelo;

public class Servidor extends Computadoras {
    //ATRIBUTOS
    private int ventiladoresServidor;

    //CONSTRUCTOR
    public Servidor(int ventiladoresServidor, String inventarioID, double temperaturaCPU, double voltajeFuente) {
        super(inventarioID, temperaturaCPU, voltajeFuente);
        this.ventiladoresServidor = ventiladoresServidor;
    }
    
    //GETTERS Y SETTERS
    public int getVentiladoresServidor() {
        return ventiladoresServidor;
    }

    public void setVentiladoresServidor(int ventiladoresServidor) {
        this.ventiladoresServidor = ventiladoresServidor;
    }
    
    //METODOS
    public boolean verificarVentiladores()
    {
        return ventiladoresServidor>=2;
    }
    
    @Override
    public String diagnosticoHardware() throws ErrorHardwareException{
        String error="";
        if (!verificarVentiladores())
        {
            this.necesitaMantenimiento = true;
            error+= "Sistema de enfriamiento ineficiente.\n";
        }
        if (!verificarVoltaje())
        {
            this.necesitaMantenimiento = true;
            error+= "Problemas en la fuente de poder del servidor.\n";
        }
        if (!verificarTemperaturaCPU())
        {
            this.necesitaMantenimiento = true;
            error+= "Problema de sobrecalentamiento en el servidor.\n";
        }
        if (!error.isEmpty())
        {
            throw new ErrorHardwareException(error);
        }
        
        this.necesitaMantenimiento = false;
        return "Servidor funcionando correctamente.";
    }
    
}
