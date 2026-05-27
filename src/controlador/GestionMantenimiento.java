package controlador;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import modelo.Computadoras;

public class GestionMantenimiento {
    private List<Computadoras> inventario;

    public GestionMantenimiento() {
        inventario = new ArrayList<>();
    }
    
    public void agregarComputadora(Computadoras pc)
    {
        inventario.add(pc);
    }
    
    //SERIALIZACION
    public void guardarDatos(String rutaArchivo) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            salida.writeObject(inventario);
            System.out.println("Datos guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar. "+e.getMessage());
        }
    }
    
    //CARGAR DESDE EL ARCHIVO
    public void cargarDatos(String rutaArchivo) {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            inventario=(ArrayList<Computadoras>) entrada.readObject();
            System.out.println("Datos cargados exitosamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar datos. "+e.getMessage());
        }
    }
    
    public List<Computadoras> getInventario() {
        return inventario;
    }
    
    //eliminar una computadora o servidor
    public boolean eliminarComputadora(String id) {
        return inventario.removeIf(pc -> pc.getInventarioID().equalsIgnoreCase(id));
    }
}
