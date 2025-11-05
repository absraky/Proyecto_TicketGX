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

        System.out.println(" Sistema BÁSICO de Tickets Activo ");

        do {
            mostrarMenu();
            // Asumimos que el usuario ingresará un número correctamente (¡simple!)
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    // El administrador crea un evento. La matriz se actualiza.
                    eventos = crearEvento(eventos, scanner, siguienteID);
                    siguienteID++;
                    break;
                case 2:
                    // El usuario compra tickets.
                    comprarEntradas(eventos, scanner);
                    break;
                case 3:
                    // Se muestran los eventos.
                    verEventosDisponibles(eventos);
                    break;
                case 4:
                    System.out.println("\n Saliendo. ¡Vuelve para tu próximo evento!");
                    break;
                default:
                    System.out.println(" Opción no válida. Intenta de nuevo.");
            }
        } while (opcion != 4);

        scanner.close();
    }

    // --- FUNCIONES (MÉTODOS) ---

    public static void mostrarMenu() {
        System.out.println("\n--- Menú Simple ---");
        System.out.println("1. Crear Evento (Admin)");
        System.out.println("2. Comprar Entradas");
        System.out.println("3. Ver Eventos");
        System.out.println("4. Salir");
        System.out.print("Selecciona: ");
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
        System.out.println("\n---  Crear Nuevo Evento ---");
        
        System.out.print("Nombre del evento: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Precio de la entrada (ej: 15.50): ");
        // Leemos como String y confiamos en que luego se parseará bien.
        String precio = scanner.nextLine(); 
        
        System.out.print("Cantidad TOTAL de entradas: ");
        String cantidad = scanner.nextLine(); // Leemos como String
        
        System.out.print("Fecha del evento (ej: 01/01/2026): ");
        String fecha = scanner.nextLine();

        // Creamos la nueva fila de datos
        String[] nuevoEvento = {
            String.valueOf(id), // Columna 0: ID
            nombre,             // Columna 1: Nombre
            precio,             // Columna 2: Precio
            cantidad,           // Columna 3: Total
            cantidad,           // Columna 4: Disponibles (igual al total)
            fecha               // Columna 5: Fecha
        };

        // Redimensionamiento básico de matriz (el truco "novato")
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
            System.out.println(" No hay eventos.");
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
        
        // Convertimos a los tipos correctos para operar (ASUMIMOS que son válidos)
        String nombreEvento = eventos[fila][1];
        double precio = Double.parseDouble(eventos[fila][2]);
        int disponibles = Integer.parseInt(eventos[fila][4]);

        System.out.println("\nEvento: " + nombreEvento + ". Disponibles: " + disponibles);
        System.out.print("¿Cuántas entradas desea comprar?: ");
        int cantidadAComprar = scanner.nextInt();
        scanner.nextLine();

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
            
            System.out.println("🎉 ¡Compra EXITOSA! Su saldo final de tickets es: " + nuevoDisponible);
        } else {
            System.out.println("Transacción cancelada.");
        }
    }

    // --- 3. VER EVENTOS DISPONIBLES ---
    
    public static void verEventosDisponibles(String[][] eventos) {
        System.out.println("\n---  LISTADO DE EVENTOS ---");
        
        if (eventos.length == 0) {
            System.out.println("Sin eventos registrados.");
            return;
        }

        // Formato simple para la consola
        System.out.println("ID | Nombre | Precio | Disponibilidad | Fecha");
        System.out.println("----------------------------------------------");

        for (String[] evento : eventos) {
            String id = evento[0];
            String nombre = evento[1];
            String precio = evento[2];
            String total = evento[3];
            String disponible = evento[4];
            String fecha = evento[5];

            // Muestra: ID | Nombre | Precio | Disponibles de Total | Fecha
            System.out.println(id + " | " + nombre + " | $" + precio + 
                               " | " + disponible + " de " + total + 
                               " | " + fecha);
        }
    }
}
