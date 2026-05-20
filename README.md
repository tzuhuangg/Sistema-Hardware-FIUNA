# Sistema de Gestión y Control de Hardware - FIUNA

## Descripción
Este proyecto consiste en monitorear y gestionar los equipos informáticos (Tanto los servidores como las PCs) de las salas de informática de la facultad.

El sistema diagnostica el estado del hardware mediante el análisis de:
* Estado de la temperatura: Alerta sobre sobrecalentamiento en las CPUs.
* Estabilidad eléctrica: Verifica que las fuentes de poder operen dentro del rango de tolerancia del estándar ATX 12V [11.4V;12.6V].
* Refrigeración: Monitorea la redundancia de ventiladores en servidores.

## Tecnologías Aplicadas
* Lenguaje: Java
* Paradigma: Programación Orientada a Objetos (POO)
* Interfaz Gráfica: Java Swing ('JFrame', 'JPanel', etc.)
* Persistencia: Serialización de Objetos ('.dat')
* Arquitectura: Modelo-Vista-Controlador (MVC)

## Estructura y Diseño Orientado a Objetos
El diseño del sistema cumple con los principios fundamentales de la POO:
* Clase Abstracta y Polimorfismo: La clase base 'Computadoras' define el contrato mediante el método abstracto 'diagnosticoHardware()', el cual es sobreescrito ('@Override') por las clases hijas ('PCEscritorio', 'Servidor') para proporcionar diagnósticos específicos.
* Encapsulamiento: Se implementan modificadores de acceso ('private') para proteger la integridad de los datos, accesibles únicamente mediante *getters* y *setters*.
* Excepciones Propias: Se desarrolló la excepción personalizada 'ErrorHardwareException' para manejar y reportar fallas del equipo.

## Instrucciones de Ejecución
1.  Clonar el repositorio en su máquina local.
2.  Abrir el proyecto en su IDE de preferencia (NetBeans, IntelliJ IDEA, Eclipse).
3.  Asegurarse de tener configurado el JDK 8 o superior.
4.  Ejecutar el archivo principal ubicado en 'src/principal/Main.java'.
5.  *Nota sobre persistencia:* Los datos se guardarán automáticamente en la raíz del proyecto bajo el nombre 'inventario_facultad.dat'.

## Capturas de Pantalla - Interfaz Gráfica
<img width="587" height="496" alt="image" src="https://github.com/user-attachments/assets/e2046b74-5b1d-4643-92b5-54e56d9139a9" />

<img width="586" height="491" alt="image" src="https://github.com/user-attachments/assets/dffcf5de-6412-4fd7-b423-f39a2fb17e3f" />

<img width="581" height="491" alt="image" src="https://github.com/user-attachments/assets/86181bb2-b5d9-4512-9abf-dc86ccae1cb2" />


## UML
classDiagram
    class Computadoras {
        <<abstract>>
        -String inventarioID
        -double temperaturaCPU
        -double voltajeFuente
        +diagnosticoHardware() String
        +verificarVoltaje() boolean
        +verificarTemperaturaCPU() boolean
    }

    class PCEscritorio {
        -boolean necesitaMantenimiento
        +diagnosticoHardware() String
    }

    class Servidor {
        -int ventiladoresServidor
        +verificarVentiladores() boolean
        +diagnosticoHardware() String
    }

    %% Relaciones de Herencia
    Computadoras <|-- PCEscritorio
    Computadoras <|-- Servidor

*Proyecto Final - Programación Orientada a Objetos (2026)*
