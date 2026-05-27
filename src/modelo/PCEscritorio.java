package modelo;

public class PCEscritorio extends Computadoras {
    //ATRIBUTOS
    private boolean tieneMouse;
    private boolean tieneMonitor;
    private boolean tieneTeclado;
    
    //CONSTRUCTOR
    public PCEscritorio(boolean tieneMouse, boolean tieneMonitor, boolean tieneTeclado, String inventarioID, double temperaturaCPU, double voltajeFuente) {
        super(inventarioID, temperaturaCPU, voltajeFuente);
        this.tieneMouse = tieneMouse;
        this.tieneMonitor = tieneMonitor;
        this.tieneTeclado = tieneTeclado;
    }
    
    //GETTERS Y SETTERS
    public boolean isTieneMouse() {
        return tieneMouse;
    }

    public void setTieneMouse(boolean tieneMouse) {
        this.tieneMouse = tieneMouse;
    }

    public boolean isTieneMonitor() {
        return tieneMonitor;
    }

    public void setTieneMonitor(boolean tieneMonitor) {
        this.tieneMonitor = tieneMonitor;
    }

    public boolean isTieneTeclado() {
        return tieneTeclado;
    }

    public void setTieneTeclado(boolean tieneTeclado) {
        this.tieneTeclado = tieneTeclado;
    }
    
    //METODOS
    @Override
    public String diagnosticoHardware() throws ErrorHardwareException{
        String error="";
        if (!tieneMouse || !tieneMonitor || !tieneTeclado)
        {
            this.necesitaMantenimiento = true;
            error+= "Faltan periféricos esenciales.\n";
        }
        if (!verificarVoltaje())
        {
            this.necesitaMantenimiento = true;
            error+= "Problemas en la fuente de poder.\n";
        }
        if (!verificarTemperaturaCPU())
        {
            this.necesitaMantenimiento = true;
            error+= "Problema de sobrecalentamiento en la CPU.\n";
        }
        
        if (!error.isEmpty())
        {
            throw new ErrorHardwareException(error);
        }
        
        this.necesitaMantenimiento = false;
        return "Equipo en estado normal.";
    }
    
    public String getPerifericos()
    {
        String p="";
        if (tieneMouse) p+= "[Mouse] ";
        if (tieneMonitor) p+= "[Monitor] ";
        if (tieneTeclado) p+= "[Teclado] ";
        return p.isEmpty()?"Ninguno": p;
    }

}
