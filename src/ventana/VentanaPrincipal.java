package ventana;

import controlador.GestionMantenimiento;
import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private GestionMantenimiento gestor;
    
    // componentes de la interfaz
    private JTextField txtId, txtTemp, txtVoltaje, txtExtra;
    private JComboBox<String> comboTipo;
    private JLabel lblExtra;
    private JTextArea areaLogs;
    private JCheckBox chkMouse, chkMonitor, chkTeclado;
    private JPanel panelContenedorExtra;

    public VentanaPrincipal(GestionMantenimiento gestor) {
        this.gestor = gestor;
        setTitle("Sistema de Control de Hardware");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        //PANEL DE ENTRADA
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Registro de Equipo"));

        panelForm.add(new JLabel("ID Inventario:"));
        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Temperatura CPU (°C):"));
        txtTemp = new JTextField();
        panelForm.add(txtTemp);

        panelForm.add(new JLabel("Voltaje Fuente (V):"));
        txtVoltaje = new JTextField();
        panelForm.add(txtVoltaje);

        panelForm.add(new JLabel("Tipo de equipo:"));
        comboTipo = new JComboBox<>(new String[]{"PC Escritorio", "Servidor"});
        panelForm.add(comboTipo);

        lblExtra = new JLabel("Periféricos:");
        panelForm.add(lblExtra);

        //CHECKLIST
        panelContenedorExtra = new JPanel(new CardLayout());
        JPanel panelCheckboxes = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        chkMouse = new JCheckBox("Mouse", true);
        chkMonitor = new JCheckBox("Monitor", true);
        chkTeclado = new JCheckBox("Teclado", true);
        panelCheckboxes.add(chkMouse);
        panelCheckboxes.add(chkMonitor);
        panelCheckboxes.add(chkTeclado);
        txtExtra = new JTextField();

        panelContenedorExtra.add(panelCheckboxes, "VISTA_PC");
        panelContenedorExtra.add(txtExtra, "VISTA_SRV");
        panelForm.add(panelContenedorExtra);
        
        //PANEL DE BOTONES
        JPanel panelBotones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar y diagnosticar");
        JButton btnMostrar = new JButton("Mostrar inventario");
        JButton btnEditar = new JButton("Editar inventario");
        JButton btnGuardar = new JButton("Guardar archivo");
        JButton btnCargar = new JButton("Cargar archivo");
        JButton btnCriticos = new JButton("Equipos defectuosos");
        JButton btnEliminar = new JButton("Eliminar equipo");
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCargar);
        panelBotones.add(btnCriticos);
        panelBotones.add(btnEliminar);

        //PANEL DE SALIDA (LOGS)
        areaLogs = new JTextArea();
        areaLogs.setEditable(false);
        areaLogs.setBackground(new Color(240, 240, 240));
        JScrollPane scroll = new JScrollPane(areaLogs);

        //FRAME
        add(panelForm, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
        scroll.setPreferredSize(new Dimension(580, 150));

        //CONTROLADOR INTERNO
        comboTipo.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelContenedorExtra.getLayout();
            if (comboTipo.getSelectedItem().equals("Servidor")) {
                lblExtra.setText("Num. Ventiladores:");
                cl.show(panelContenedorExtra, "VISTA_SRV");
            } else {
                lblExtra.setText("Periféricos:");
                cl.show(panelContenedorExtra, "VISTA_PC");
            }
        });

        btnRegistrar.addActionListener((ActionEvent e) -> {
            registrarEquipo();
        });
        
        btnMostrar.addActionListener(e -> {
            areaLogs.setText("INVENTARIO ACTUAL\n");
            List<Computadoras> lista = gestor.getInventario();
            if (lista.isEmpty()) {
                areaLogs.append("El inventario está vacío. Registre o cargue un archivo.\n");
            } else {
                for (Computadoras pc : lista) {
                    String mant = pc.isNecesitaMantenimiento() ? "SÍ" : "NO";
                    String detallesExtra = "";
                    String motivo = "";
                    
                    if (pc.isNecesitaMantenimiento()) {
                        try {
                            pc.diagnosticoHardware();
                        } catch (ErrorHardwareException ex) {
                            motivo = " | Motivo: " + ex.getMessage();
                        }
                    }
                    
                    if (pc instanceof PCEscritorio) {
                        detallesExtra = " | Periféricos: " + ((PCEscritorio) pc).getPerifericos();
                    } else if (pc instanceof Servidor) {
                        detallesExtra = " | Ventiladores: " + ((Servidor) pc).getVentiladoresServidor();
                    }
                    
                    areaLogs.append("ID: "+pc.getInventarioID()+" | Tipo: " + pc.getClass().getSimpleName()+" | Temp: " + pc.getTemperaturaCPU()+"°C | Voltaje: " + pc.getVoltajeFuente()+"V | Mantenimiento: "+mant+motivo+detallesExtra+"\n");
                }
            }
        });
        
        btnEditar.addActionListener(e -> {
            editarEquipo();
        });

        btnGuardar.addActionListener(e -> {
            gestor.guardarDatos("inventario_facultad.dat");
            JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
        });

        btnCargar.addActionListener(e -> {
            gestor.cargarDatos("inventario_facultad.dat");
            areaLogs.append("Datos recuperados del archivo.\n");
        });
        
        btnCriticos.addActionListener(e -> {
            mostrarEquiposRevision();
        });
        
        btnEliminar.addActionListener(e -> {
            String idBusqueda = JOptionPane.showInputDialog(this, "ID del equipo:", "Eliminar equipo", JOptionPane.QUESTION_MESSAGE);
            
            // si el usuario no canceló la ventana y escribió algo
            if (idBusqueda != null && !idBusqueda.trim().isEmpty()) {
                boolean eliminado = gestor.eliminarComputadora(idBusqueda.trim());
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "El equipo fue eliminado del sistema.", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                    areaLogs.append("Se eliminó el equipo "+idBusqueda+"\n");
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró ningún equipo con el ID especificado.", "Error al eliminar", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void registrarEquipo() {
        try {
            // captura de datos
            String id = txtId.getText();
            double temp = Double.parseDouble(txtTemp.getText());
            double voltaje = Double.parseDouble(txtVoltaje.getText());
            
            Computadoras nuevaPC;

            if (comboTipo.getSelectedItem().equals("PC Escritorio")) {
                nuevaPC = new PCEscritorio(chkMouse.isSelected(), chkMonitor.isSelected(), chkTeclado.isSelected(), id, temp, voltaje);
            } else {
                int fans = Integer.parseInt(txtExtra.getText());
                nuevaPC = new Servidor(fans, id, temp, voltaje);
            }

            gestor.agregarComputadora(nuevaPC);
            String diagnostico = nuevaPC.diagnosticoHardware();
            
            areaLogs.append("REGISTRO EXITOSO -> ID: " + id + " | " + diagnostico + " | ¿Necesita mantenimiento?: NO\n");
            limpiarCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Ingrese valores numéricos válidos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        } catch (ErrorHardwareException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ALERTA", JOptionPane.WARNING_MESSAGE);
            areaLogs.append("ALERTA -> ID: " + txtId.getText() + " | Motivo: " + e.getMessage() + " | ¿Necesita mantenimiento?: SÍ\n");
            limpiarCampos();
        }
    }
    
    private void editarEquipo() {
        String idBusqueda= JOptionPane.showInputDialog(this, "ID del equipo:", "Editar equipo", JOptionPane.QUESTION_MESSAGE);
        if (idBusqueda != null && !idBusqueda.trim().isEmpty()) {
            Computadoras pcAEditar = null;
            for (Computadoras pc : gestor.getInventario()) {
                if (pc.getInventarioID().equalsIgnoreCase(idBusqueda.trim())) {
                    pcAEditar = pc;
                    break;
                }
            }

            if (pcAEditar != null) {
                JTextField txtEditTemp = new JTextField(String.valueOf(pcAEditar.getTemperaturaCPU()));
                JTextField txtEditVolt = new JTextField(String.valueOf(pcAEditar.getVoltajeFuente()));
                
                // perifericos
                JCheckBox chkEditMouse = new JCheckBox("Mouse");
                JCheckBox chkEditMonitor = new JCheckBox("Monitor");
                JCheckBox chkEditTeclado = new JCheckBox("Teclado");
                JTextField txtEditFans = new JTextField();

                // PANEL
                JPanel panelEdicion = new JPanel(new GridLayout(0, 2, 5, 5));
                panelEdicion.add(new JLabel("Temperatura CPU (°C):"));
                panelEdicion.add(txtEditTemp);
                panelEdicion.add(new JLabel("Voltaje Fuente (V):"));
                panelEdicion.add(txtEditVolt);

                if (pcAEditar instanceof PCEscritorio) {
                    PCEscritorio pc = (PCEscritorio) pcAEditar;
                    chkEditMouse.setSelected(pc.isTieneMouse());
                    chkEditMonitor.setSelected(pc.isTieneMonitor());
                    chkEditTeclado.setSelected(pc.isTieneTeclado());
                    panelEdicion.add(new JLabel("Periféricos:"));
                    JPanel pChecks = new JPanel(); 
                    pChecks.add(chkEditMouse); pChecks.add(chkEditMonitor); pChecks.add(chkEditTeclado);
                    panelEdicion.add(pChecks);
                } else if (pcAEditar instanceof Servidor) {
                    Servidor srv = (Servidor) pcAEditar;
                    txtEditFans.setText(String.valueOf(srv.getVentiladoresServidor()));
                    panelEdicion.add(new JLabel("Num. Ventiladores:"));
                    panelEdicion.add(txtEditFans);
                }

                int opcion = JOptionPane.showConfirmDialog(this, panelEdicion, "Actualizar valores - ID: "+pcAEditar.getInventarioID(), JOptionPane.OK_CANCEL_OPTION);
                
                if (opcion == JOptionPane.OK_OPTION) {
                    try {
                        pcAEditar.setTemperaturaCPU(Double.parseDouble(txtEditTemp.getText()));
                        pcAEditar.setVoltajeFuente(Double.parseDouble(txtEditVolt.getText()));
                        
                        if (pcAEditar instanceof PCEscritorio) {
                            PCEscritorio pc = (PCEscritorio) pcAEditar;
                            pc.setTieneMouse(chkEditMouse.isSelected());
                            pc.setTieneMonitor(chkEditMonitor.isSelected());
                            pc.setTieneTeclado(chkEditTeclado.isSelected());
                        } else if (pcAEditar instanceof Servidor) {
                            Servidor srv = (Servidor) pcAEditar;
                            srv.setVentiladoresServidor(Integer.parseInt(txtEditFans.getText()));
                        }

                        String estadoActualizado = pcAEditar.diagnosticoHardware();
                        JOptionPane.showMessageDialog(this, "Valores actualizados.\n" + estadoActualizado, "Edicion exitosa", JOptionPane.INFORMATION_MESSAGE);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Error: Ingrese un valor válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
                    } catch (ErrorHardwareException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "ALERTA", JOptionPane.WARNING_MESSAGE);
                        areaLogs.append("ALERTA -> ID: " + pcAEditar.getInventarioID() + " | Motivo: " + ex.getMessage() + " | ¿Necesita mantenimiento?: SÍ\n");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún equipo con el ID especificado.", "Equipo no encontrado", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void mostrarEquiposRevision() {
        areaLogs.setText("EQUIPOS QUE REQUIEREN MANTENIMIENTO\n");
        List<Computadoras> lista = gestor.getInventario();
        boolean contados = false;
        for (Computadoras pc : lista)
        {
            if (pc.isNecesitaMantenimiento())
            {
                contados = true;
                String motivo = "";
                String detallesExtra = "";
                try {
                    pc.diagnosticoHardware();
                } catch (ErrorHardwareException ex) {
                    motivo = "\n" + ex.getMessage(); 
                }

                if (pc instanceof PCEscritorio)
                {
                    detallesExtra = " | Periféricos: " + ((PCEscritorio) pc).getPerifericos();
                } else if (pc instanceof Servidor)
                {
                    detallesExtra = " | Ventiladores: " + ((Servidor) pc).getVentiladoresServidor();
                }
                areaLogs.append("ID: "+pc.getInventarioID()+" | Tipo: "+pc.getClass().getSimpleName()+detallesExtra+motivo+"\n");
            }
        }

        if (!contados)
        {
            areaLogs.append("No hay equipos defectuosos.\n");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtTemp.setText("");
        txtVoltaje.setText("");
        txtExtra.setText("");
        chkMouse.setSelected(true);
        chkMonitor.setSelected(true);
        chkTeclado.setSelected(true);
    }
}
