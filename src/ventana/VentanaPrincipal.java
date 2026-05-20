/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ventana;

import controlador.GestionMantenimiento;
import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author recor
 */
public class VentanaPrincipal extends JFrame {
    private GestionMantenimiento gestor;
    
    // Componentes de la interfaz
    private JTextField txtId, txtTemp, txtVoltaje, txtExtra;
    private JComboBox<String> comboTipo;
    private JLabel lblExtra;
    private JTextArea areaLogs;

    public VentanaPrincipal(GestionMantenimiento gestor) {
        this.gestor = gestor;
        setTitle("Sistema de Control de Hardware - FIUNA");
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

        panelForm.add(new JLabel("Tipo de Equipo:"));
        comboTipo = new JComboBox<>(new String[]{"PC Escritorio", "Servidor"});
        panelForm.add(comboTipo);

        lblExtra = new JLabel("¿Mantenimiento? (si/no):");
        panelForm.add(lblExtra);
        txtExtra = new JTextField();
        panelForm.add(txtExtra);

        //PANEL DE BOTONES
        JPanel panelBotones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar y Diagnosticar");
        JButton btnGuardar = new JButton("Guardar Archivo");
        JButton btnCargar = new JButton("Cargar Archivo");
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCargar);

        // PANEL DE SALIDA (LOGS)
        areaLogs = new JTextArea();
        areaLogs.setEditable(false);
        areaLogs.setBackground(new Color(240, 240, 240));
        JScrollPane scroll = new JScrollPane(areaLogs);

        //AGREGAR TODO AL FRAME
        add(panelForm, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
        scroll.setPreferredSize(new Dimension(580, 150));

        //EVENTOS (CONTROLADOR INTERNO)
        
        comboTipo.addActionListener(e -> {
            if (comboTipo.getSelectedItem().equals("Servidor")) {
                lblExtra.setText("Num. Ventiladores:");
            } else {
                lblExtra.setText("¿Mantenimiento? (si/no):");
            }
        });

        btnRegistrar.addActionListener((ActionEvent e) -> {
            registrarEquipo();
        });

        btnGuardar.addActionListener(e -> {
            gestor.guardarDatos("inventario_facultad.dat");
            JOptionPane.showMessageDialog(this, "Datos guardados en binario.");
        });

        btnCargar.addActionListener(e -> {
            gestor.cargarDatos("inventario_facultad.dat");
            areaLogs.append("Sistema: Datos recuperados del archivo.\n");
        });
    }

    private void registrarEquipo() {
        try {
            // Captura de datos
            String id = txtId.getText();
            double temp = Double.parseDouble(txtTemp.getText());
            double voltaje = Double.parseDouble(txtVoltaje.getText());
            
            Computadoras nuevaPC;

            if (comboTipo.getSelectedItem().equals("PC Escritorio")) {
                boolean mant = txtExtra.getText().equalsIgnoreCase("si");
                nuevaPC = new PCEscritorio(mant, id, temp, voltaje);
            } else {
                int fans = Integer.parseInt(txtExtra.getText());
                nuevaPC = new Servidor(fans, id, temp, voltaje);
            }

            // Lógica de ingeniería y respuesta
            gestor.agregarComputadora(nuevaPC);
            String diagnostico = nuevaPC.diagnosticoHardware();
            
            areaLogs.append("ID: " + id + " | " + diagnostico + "\n");
            limpiarCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Ingrese valores numéricos válidos en Temperatura/Voltaje.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
        catch (ErrorHardwareException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "¡Alerta!", JOptionPane.WARNING_MESSAGE);
            areaLogs.append("ID: "+txtId.getText()+" | FALLA CRITICA: "+e.getMessage()+"\n");
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtTemp.setText("");
        txtVoltaje.setText("");
        txtExtra.setText("");
    }
}
