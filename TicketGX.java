import java.util.Scanner;
import java.util.Arrays;

public class TicketGX {

    // Constante que define cuántos datos tiene cada evento.
    // 0: ID | 1: Nombre | 2: Precio | 3: Total | 4: Disponibles | 5: Fecha
    private static final int COLUMNAS = 6;

    public static void main(String[] args) {
        // Matriz donde se guardaran los eventos. Empieza vacia.
        String[][] eventos = new String[0][COLUMNAS];
        Scanner scanner = new Scanner(System.in);
        int siguienteID = 100; // inicilizado el ID en 100 para que sea mas realista el sistema
        int opcion = 0;

//------------------------------------------------------------------------------------------------------------
        

        System.out.println(" _______  ___   _______  ___   _  _______  _______  _______  __   __ ");
        System.out.println("|       ||   | |       ||   | | ||       ||       ||       ||  |_|  |");
        System.out.println("|_     _||   | |       ||   |_| ||    ___||_     _||    ___||       |");
        System.out.println("  |   |  |   | |       ||      _||   |___   |   |  |   | __ |       |");
        System.out.println("  |   |  |   | |      _||     |_ |    ___|  |   |  |   ||  | |     | ");
        System.out.println("  |   |  |   | |     |_ |    _  ||   |___   |   |  |   |_| ||   _   |");
        System.out.println("  |___|  |___| |_______||___| |_||_______|  |___|  |_______||__| |__|");

        //Se utiliza para crear un delay en la aparicion de texto
        try {
           //Ponemos a "Dormir" el programa durante los ms que queremos
           Thread.sleep(3000);
        } catch (Exception e) {
           System.out.println(e);
        }
        
        System.out.print("\033[H\033[2J"); //esto y el flush se usa para limppiar la pantalla
        System.out.flush();
        System.out.println("### Bienvenido a TicketGX! ###");
        System.out.println("");   
        System.out.println("***** Iniciando el sistema! *****");
        try {
            //Ponemos a "Dormir" el programa durante los ms que queremos
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e);
        }
        
//------------------------------------------------------------------------------------------------------------
        
        do {
            mostrarMenu();
            // Manejo básico de errores de entrada para la opción
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
            } catch (java.util.InputMismatchException e) {
                System.out.println("***Entrada inválida. Ingrese un número entero.***");
                scanner.nextLine(); // Limpiar el buffer
                opcion = 0; // Para que el bucle continúe
                continue;
            }

            switch (opcion) {
                case 1:
                    // Crear Evento
                    eventos = crearEvento(eventos, scanner, siguienteID);
                    siguienteID++;
                    break;
                case 2:
                    // Comprar Entradas
                    comprarEntradas(eventos, scanner);
                    break;
                case 3:
                    // Ver Eventos
                    verEventosDisponibles(eventos);
                    break;
                case 4:
                    // Editar Evento 
                    eventos = editarEvento(eventos, scanner);
                    break;
                case 5:
                    // Eliminar Evento 
                    eventos = eliminarEvento(eventos, scanner);
                    break;
                case 6:
                    // Ordenar Eventos por Precio
                    eventos = ordenarEventosBubbleSort(eventos, scanner);
                    break;
                case 7:
                    System.out.println("\n Saliendo. Gracias por utilizar nuestros sevicios!!!");
                    break;
                default:
                    System.out.println("");
                    System.out.println("***Opción no válida. Ingresa el numero entero de la opción que desea.***");
            }
        } while (opcion != 7); // El bucle ahora termina con la opción 7 (Salir)

        scanner.close();
    }

    // --- FUNCIONES ---

    public static void mostrarMenu() {
        System.out.println("\n----- MENU -----");
        System.out.println("1. Crear Evento (Admin)");
        System.out.println("2. Comprar Entradas");
        System.out.println("3. Ver Eventos");
        System.out.println("4. Editar Evento (Admin)");
        System.out.println("5. Eliminar Evento (Admin)");
        System.out.println("6. Ordenar Eventos por Precio (Admin)");
        System.out.println("7. Salir");
        System.out.print("Ingrese el numero de la opcion: ");
    }

    /**
     * Busca la fila de un evento por su ID.
     */
    public static int buscarFilaEvento(String[][] eventos, String idBuscada) {
        for (int i = 0; i < eventos.length; i++) {
            if (eventos[i][0].equals(idBuscada)) {
                return i;
            }
        }
        return -1; // No se encontró
    }

    // --- 1. CREAR EVENTO ---
    
    public static String[][] crearEvento(String[][] eventosActuales, Scanner scanner, int id) {
        System.out.print("\033[H\033[2J"); //esto y el flush se usa para limppiar la pantalla
        System.out.flush();
        System.out.println("\n---  Crear Nuevo Evento ---");
        
        System.out.print("Nombre del evento: ");
        String nombre = scanner.nextLine();
        
        String precio;
        double precioNum = 0.0;
        while (true) {
            System.out.print("Precio de la entrada (ej: 15.50): ");
            precio = scanner.nextLine(); 
            try {
                precioNum = Double.parseDouble(precio);
                if (precioNum < 0) throw new Exception();
                break;
            } catch (Exception e) {
                System.out.println("Precio inválido. Ingrese un número positivo.");
            }
        }
        
        String cantidad;
        int cantidadNum = 0;
        while (true) {
            System.out.print("Cantidad TOTAL de entradas: ");
            cantidad = scanner.nextLine(); 
            try {
                cantidadNum = Integer.parseInt(cantidad);
                if (cantidadNum <= 0) throw new Exception();
                break;
            } catch (Exception e) {
                System.out.println("Cantidad total inválida. Ingrese un número entero positivo.");
            }
        }
        
        System.out.print("Fecha del evento (ej: 01/01/2026): ");
        String fecha = scanner.nextLine();

        // Creamos la nueva fila de datos
        String[] nuevoEvento = {
            String.valueOf(id), // Columna 0: ID
            nombre,            // Columna 1: Nombre
            precio,            // Columna 2: Precio
            cantidad,          // Columna 3: Total
            cantidad,          // Columna 4: Disponibles (igual al total)
            fecha              // Columna 5: Fecha
        };

        // Se redimenciona la matriz (copia y agrega el nuevo evento)
        int nuevaLongitud = eventosActuales.length + 1;
        String[][] nuevaMatriz = Arrays.copyOf(eventosActuales, nuevaLongitud);
        nuevaMatriz[nuevaLongitud - 1] = nuevoEvento;

        System.out.println("\n Evento '" + nombre + "' CREADO con ID: " + id);
        
        return nuevaMatriz;
    }

    // --- 2. COMPRAR ENTRADAS ---

    public static void comprarEntradas(String[][] eventos, Scanner scanner) {
        
        System.out.println("\n---  Comprar Entradas ---");
        if (eventos.length == 0) {
            System.out.println(" No hay eventos disponibles para comprar.");
            return;
        }

        verEventosDisponibles(eventos);
        System.out.print("Ingrese ID del evento a comprar: ");
        String idEvento = scanner.nextLine();
        
        int fila = buscarFilaEvento(eventos, idEvento);

        if (fila == -1) {
            System.out.println("ID no encontrada.");
            return;
        }
        
        // Convertimos a los tipos correctos para operar
        String nombreEvento = eventos[fila][1];
        double precio = Double.parseDouble(eventos[fila][2]);
        int disponibles = Integer.parseInt(eventos[fila][4]);

        System.out.println("\nEvento: " + nombreEvento + ". Disponibles: " + disponibles);
        System.out.print("¿Cuantas entradas desea comprar?: ");
        
        int cantidadAComprar = 0;
        try {
            cantidadAComprar = scanner.nextInt();
            scanner.nextLine();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Entrada inválida. Debe ser un número entero.");
            scanner.nextLine();
            return;
        }


        if (cantidadAComprar <= 0 || cantidadAComprar > disponibles) {
            System.out.println(" Cantidad inválida o insuficiente stock.");
            return;
        }

        // Mostrar total
        double total = cantidadAComprar * precio;
        System.out.printf("Total a pagar por %d entradas: $%.2f%n", cantidadAComprar, total);

        System.out.print("Confirmar compra (S/N): ");
        String confirmacion = scanner.nextLine().trim().toUpperCase();

        if (confirmacion.equals("S")) {
            // Actualizar la matriz de forma simple
            int nuevoDisponible = disponibles - cantidadAComprar;
            eventos[fila][4] = String.valueOf(nuevoDisponible);
            
            System.out.println(" ¡Compra EXITOSA! Le quedan " + nuevoDisponible + " tickets disponibles en el evento.");
        } else {
            System.out.println("Transacción cancelada.");
        }
    }

    // --- 3. VER EVENTOS DISPONIBLES ---
    
    public static void verEventosDisponibles(String[][] eventos) {
        System.out.println("\n-----  LISTADO DE EVENTOS -----");
        
        if (eventos.length == 0) {
            System.out.println("No hay eventos registrados.");
            return;
        }

        // Formato simple para la consola
        System.out.println("ID   | Nombre                 | Precio   | Disp/Total | Fecha");
        System.out.println("-----------------------------------------------------------------------");

        for (String[] evento : eventos) {
            String id = evento[0];
            String nombre = evento[1];
            String precio = evento[2];
            String total = evento[3];
            String disponible = evento[4];
            String fecha = evento[5];

            // Formato para alinear columnas
            System.out.printf("%-4s | %-20s | $%-6s | %-4s/%-4s | %s%n", 
                              id, nombre, precio, disponible, total, fecha);
        }
    }

    // --- 4. EDITAR EVENTO (ADMIN) ---
    
    public static String[][] editarEvento(String[][] eventos, Scanner scanner) {
        System.out.println("\n---  Editar Evento Existente ---");
        if (eventos.length == 0) {
            System.out.println(" No hay eventos para editar.");
            return eventos;
        }

        verEventosDisponibles(eventos);
        System.out.print("Ingrese el ID del evento a editar: ");
        String idEvento = scanner.nextLine();
        
        int fila = buscarFilaEvento(eventos, idEvento);

        if (fila == -1) {
            System.out.println(" ID no encontrada. Volviendo al menú.");
            return eventos;
        }

        String[] eventoAEditar = eventos[fila];
        String nombreActual = eventoAEditar[1];

        System.out.println("\n--- Editando Evento ID: " + idEvento + " (" + nombreActual + ") ---");
        System.out.println("1. Nombre: " + eventoAEditar[1]);
        System.out.println("2. Precio: $" + eventoAEditar[2]);
        System.out.println("3. Cantidad Total de Entradas: " + eventoAEditar[3] + " (Disponibles: " + eventoAEditar[4] + ")");
        System.out.println("4. Fecha: " + eventoAEditar[5]);
        System.out.println("0. Cancelar y Volver");
        System.out.print("Seleccione el número del campo a editar: ");

        int campo = 0;
        try {
            campo = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea
        } catch (java.util.InputMismatchException e) {
            System.out.println("Entrada inválida. Volviendo al menú.");
            scanner.nextLine();
            return eventos;
        }

        switch (campo) {
            case 1:
                System.out.print("Nuevo Nombre (Actual: " + eventoAEditar[1] + "): ");
                eventoAEditar[1] = scanner.nextLine();
                System.out.println("Nombre actualizado.");
                break;
            case 2:
                System.out.print("Nuevo Precio (Actual: $" + eventoAEditar[2] + "): ");
                String nuevoPrecio = scanner.nextLine();
                try {
                    Double.parseDouble(nuevoPrecio); // Validar que sea un número
                    eventoAEditar[2] = nuevoPrecio;
                    System.out.println("Precio actualizado.");
                } catch (NumberFormatException e) {
                    System.out.println("Valor de precio inválido. Edición cancelada.");
                }
                break;
            case 3:
                System.out.print("Nueva Cantidad TOTAL de Entradas (Actual: " + eventoAEditar[3] + "): ");
                String nuevaCantidadTotalStr = scanner.nextLine();
                try {
                    int nuevaCantidadTotal = Integer.parseInt(nuevaCantidadTotalStr);
                    int disponiblesActuales = Integer.parseInt(eventoAEditar[4]);
                    int vendidos = Integer.parseInt(eventoAEditar[3]) - disponiblesActuales;

                    if (nuevaCantidadTotal < vendidos) {
                        System.out.println("ERROR: La nueva cantidad total (" + nuevaCantidadTotal + ") es menor a las entradas ya vendidas (" + vendidos + ").");
                        System.out.println("Edición cancelada.");
                    } else {
                        // Actualizamos el Total y recalculamos los Disponibles
                        int nuevosDisponibles = nuevaCantidadTotal - vendidos;
                        eventoAEditar[3] = nuevaCantidadTotalStr; // Columna 3: Total
                        eventoAEditar[4] = String.valueOf(nuevosDisponibles); // Columna 4: Disponibles
                        System.out.println("Cantidad total y disponibilidad actualizadas. Nueva disponibilidad: " + nuevosDisponibles);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Valor de cantidad inválido. Edición cancelada.");
                }
                break;
            case 4:
                System.out.print("Nueva Fecha (Actual: " + eventoAEditar[5] + "): ");
                eventoAEditar[5] = scanner.nextLine();
                System.out.println("Fecha actualizada.");
                break;
            case 0:
                System.out.println("Edición cancelada.");
                break;
            default:
                System.out.println("Opción no válida. Volviendo al menú.");
        }

        return eventos;
    }

    // --- 5. ELIMINAR EVENTO (ADMIN) ---
    
    public static String[][] eliminarEvento(String[][] eventosActuales, Scanner scanner) {
        System.out.println("\n---  Eliminar Evento ---");
        if (eventosActuales.length == 0) {
            System.out.println(" No hay eventos para eliminar.");
            return eventosActuales;
        }

        verEventosDisponibles(eventosActuales);
        System.out.print("Ingrese el ID del evento a ELIMINAR: ");
        String idEvento = scanner.nextLine();
        
        int filaAEliminar = buscarFilaEvento(eventosActuales, idEvento);

        if (filaAEliminar == -1) {
            System.out.println(" ID no encontrada. Volviendo al menú.");
            return eventosActuales;
        }

        String nombreEvento = eventosActuales[filaAEliminar][1];

        System.out.print("¿Seguro que desea eliminar el evento '" + nombreEvento + "' (ID: " + idEvento + ")? (S/N): ");
        String confirmacion = scanner.nextLine().trim().toUpperCase();

        if (!confirmacion.equals("S")) {
            System.out.println(" Eliminación cancelada.");
            return eventosActuales;
        }

        // Crear una nueva matriz con una longitud menor
        int nuevaLongitud = eventosActuales.length - 1;
        String[][] nuevaMatriz = new String[nuevaLongitud][COLUMNAS];
        int nuevoIndice = 0;

        // Copiar todas las filas excepto la que se quiere eliminar
        for (int i = 0; i < eventosActuales.length; i++) {
            if (i != filaAEliminar) {
                // Copiar el array interno (la fila) al nuevo array
                nuevaMatriz[nuevoIndice] = eventosActuales[i];
                nuevoIndice++;
            }
        }

        System.out.println(" *** Evento '" + nombreEvento + "' eliminado exitosamente. ***");
        return nuevaMatriz;
    }
    
    // --- 6. ORDENAR EVENTOS POR PRECIO CON BUBBLE SORT  ---
    /**
     * Ordena la matriz de eventos utilizando el algoritmo Bubble Sort
     * basado en la columna de Precio.
     * El usuario elige entre orden ascendente o descendente.
     */
    public static String[][] ordenarEventosBubbleSort(String[][] eventos, Scanner scanner) {
        System.out.println("\n---  Ordenar Eventos por Precio (Bubble Sort) ---");
        
        if (eventos.length <= 1) {
            System.out.println(" No hay suficientes eventos para ordenar.");
            return eventos;
        }

        System.out.println("Seleccione el orden:");
        System.out.println("1. Ascendente (Menor a Mayor Precio)");
        System.out.println("2. Descendente (Mayor a Menor Precio)");
        System.out.print("Opcion: ");

        int orden = 0;
        try {
            orden = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea
        } catch (java.util.InputMismatchException e) {
            System.out.println("Entrada invalida. Operacion cancelada.");
            scanner.nextLine();
            return eventos;
        }

        if (orden != 1 && orden != 2) {
            System.out.println("Opcion de ordenamiento no valida. Operacion cancelada.");
            return eventos;
        }

        int n = eventos.length;
        boolean swapped;
        
        // BUCLE PRINCIPAL DE BUBBLE SORT
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                
                // Conversión de precios de String a double para la comparación
                try {
                    double precio1 = Double.parseDouble(eventos[j][2]);     // Precio del evento actual
                    double precio2 = Double.parseDouble(eventos[j + 1][2]); // Precio del siguiente evento
                    
                    boolean shouldSwap = false;

                    if (orden == 1) { // Ascendente: si el primero es MAYOR que el segundo, swapear
                        if (precio1 > precio2) {
                            shouldSwap = true;
                        }
                    } else { // Descendente: si el primero es MENOR que el segundo, swapear
                        if (precio1 < precio2) {
                            shouldSwap = true;
                        }
                    }

                    if (shouldSwap) {
                        // SWAP: Intercambiar la fila completa
                        String[] temp = eventos[j];
                        eventos[j] = eventos[j + 1];
                        eventos[j + 1] = temp;
                        swapped = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error de formato de precio en el evento ID " + eventos[j][0] + " o " + eventos[j + 1][0] + ". El ordenamiento se detiene.");
                    return eventos;
                }
            }
            // Si no hubo swaps implica q esta ordenada
            if (!swapped) break;
        }

        System.out.println(" *** Eventos ordenados exitosamente por precio. ***");
        verEventosDisponibles(eventos); // Muestra la lista ordenada para confirmar
        return eventos;
    }
}
