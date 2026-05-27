# Sistema de Gestión y Control de Hardware - FIUNA

## Descripción del problema
La administración de hardware en los laboratorios y salas de servidores de la facultad carece de un sistema de información centralizado para el monitoreo preventivo. La administración actual es manual y arcaica, lo que imposibilita la detección temprana de anomalidades antes de que deriven en fallas críticas o pérdida total del equipo.
Tanto la ausencia de validación de umbrales operativos (sobrecalentamiento en la CPU) como las fluctuaciones de energía en la fuente de poder (con una tolerancia de 11.4V a 12.6V), representan un problema para el correcto funcionamiento de las máquinas. Así también, el escaso número de ventiladores en cada servidor y la ausencia de periféricos en cada computadora.

## Solución propuesta
Con este proyecto se busca, tanto optimizar como organizar, la gestión y mantenimiento de las máquinas y servidores de las salas de informática, a través del control de los componentes.
El programa actual resuelve estas deficiencias mediante la implementación de un sistema de gestión de arquitectura MVC (Modelo-Vista-Controlador) desarrollado en Java. Con este sistema implementamos un motor de diagnóstico con diseño polimórfico que evalúa el estado del hardware basándonos en parámetros específicos según el tipo de nodo (PC de Escritorio o Servidor). Mediante el registro de errores y el uso de excepciones personalizadas, el software automatiza el cambio de estado de los equipos, garantizando la persistencia de los datos en un archivo binario y generando reportes sobre las anomalías, convirtiendo el mantenimiento correctivo en mantenimiento preventivo.

## Instrucciones de ejecución
1.  Copiar el repositorio en su PC.
2.  Abrir el proyecto en su IDE de preferencia (NetBeans, IntelliJ IDEA, Eclipse).
3.  Asegurarse de tener configurado el JDK 8 o superior.
4.  Ejecutar el archivo principal ubicado en "src/principal/Main.java".
*Nota:* Los datos se guardan en la carpeta del proyecto con el nombre "inventario_facultad.dat".

## Capturas de Pantalla - Interfaz Gráfica
<img width="586" height="493" alt="image" src="https://github.com/user-attachments/assets/5a65c51a-ee94-418e-914a-1604535331b8" />
<img width="763" height="490" alt="image" src="https://github.com/user-attachments/assets/493354d4-c0f3-4727-881a-e7082690b2ed" />
<img width="763" height="494" alt="image" src="https://github.com/user-attachments/assets/146af886-d016-4575-9ed6-20f193787316" />
<img width="763" height="494" alt="image" src="https://github.com/user-attachments/assets/032261c3-263e-4697-9a5d-de1db2bd4d2e" />
<img width="763" height="492" alt="image" src="https://github.com/user-attachments/assets/53d0a861-a32a-4c91-8bdd-d31d870c48af" />
<img width="763" height="492" alt="image" src="https://github.com/user-attachments/assets/7e9f0052-fe38-4ce3-bc84-e4118e143621" />
<img width="760" height="491" alt="image" src="https://github.com/user-attachments/assets/15ef607c-d71e-4e35-a59c-0d4e1ab131ed" />
<img width="763" height="492" alt="image" src="https://github.com/user-attachments/assets/80a8ae7c-75ee-4ac0-92ed-e915430c5be2" />

## UML
[UML Gestion Computadoras.pdf](https://github.com/user-attachments/files/28324886/UML.Gestion.Computadoras.pdf)

*Proyecto Final - Programación Orientada a Objetos (2026)*
