package principal;

import controlador.GestionMantenimiento;
import javax.swing.UIManager;
import ventana.VentanaPrincipal;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Intentar poner el diseño del sistema operativo (más bonito)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 1. Inicializar el controlador (Lógica y Datos)
        GestionMantenimiento gestor = new GestionMantenimiento();

        // 2. Inicializar la vista (GUI) y pasarle el gestor
        VentanaPrincipal ventana = new VentanaPrincipal(gestor);

        // 3. Hacerla visible
        ventana.setVisible(true);
    }
    
}
