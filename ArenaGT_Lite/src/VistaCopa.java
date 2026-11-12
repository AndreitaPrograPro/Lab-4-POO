import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class VistaCopa {
    private ControladorCopa controlador;
    private Scanner scanner;
    public VistaCopa(ControladorCopa controlador) {
        this.controlador = controlador;
        this.scanner = new Scanner(System.in);
    }
    public void mostrarMenu() {
        System.out.println("\n=== ARENAGT LITE ===");
        System.out.println("1. Registrar equipo");
        System.out.println("2. Crear encuentro");
        System.out.println("3. Crear exhibicion");
        System.out.println("4. Programar / reprogramar evento");
        System.out.println("5. Registrar resultado");
        System.out.println("6. Tabla de posiciones");
        System.out.println("7. Calendario");
        System.out.println("8. Ver todos los eventos");
        System.out.println("0. Salir");
    }
    public int solicitarOpcion() { return solicitarEntero("Opcion: "); }
    public String solicitarTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
    public int solicitarEntero(String mensaje) {
        return Integer.parseInt(solicitarTexto(mensaje));
    }
    public LocalDate solicitarFecha(String mensaje) {
        return LocalDate.parse(solicitarTexto(mensaje + " (AAAA-MM-DD): "));
    }
    public void mostrarEquipos(ArrayList<Equipo> equipos) {
        System.out.println("\n--- TABLA DE POSICIONES ---");
        for (Equipo equipo : equipos) System.out.println(equipo);
    }
    public void mostrarEventos(ArrayList<Programable> eventos) {
        System.out.println("\n--- EVENTOS (indice para seleccionar) ---");
        for (int i = 0; i < eventos.size(); i++) {
            Programable e = eventos.get(i);
            System.out.println(i + ". " + e.getDescripcion() + " | "
                    + (e.estaProgramado() ? e.getFecha() + " " + e.getHora() + ":00 @ " + e.getSede() : "Sin programar"));
        }
    }
    public void mostrarCalendario(Iterable<Programable> eventos) {
        System.out.println("\n--- CALENDARIO ---");
        for (Programable e : eventos)
            System.out.println(e.getFecha() + " " + String.format("%02d:00", e.getHora())
                    + " | " + e.getSede() + " | " + e.getDescripcion());
    }
    public void mostrarMensaje(String mensaje) { System.out.println(mensaje); }
    public void iniciar() {
        int opcion = -1;
        do {
            try {
                mostrarMenu();
                opcion = solicitarOpcion();
                switch (opcion) {
                    case 1:
                        controlador.registrarEquipo(solicitarTexto("Codigo: "), solicitarTexto("Nombre: "), solicitarTexto("Empresa: "));
                        mostrarMensaje("Equipo registrado.");
                        break;
                    case 2:
                        String deporte = solicitarTexto("Deporte (futbol/baloncesto): ");
                        String local = solicitarTexto("Codigo local: ");
                        String visitante = solicitarTexto("Codigo visitante: ");
                        controlador.crearEncuentro(deporte, local, visitante);
                        mostrarMensaje("Encuentro creado sin programar.");
                        break;
                    case 3:
                        controlador.crearExhibicion(solicitarTexto("Titulo: "));
                        mostrarMensaje("Exhibicion creada sin programar.");
                        break;
                    case 4:
                        mostrarEventos(controlador.obtenerEventos());
                        int indice = solicitarEntero("Indice del evento: ");
                        LocalDate fecha = solicitarFecha("Fecha");
                        int hora = solicitarEntero("Hora (0-23): ");
                        String sede = solicitarTexto("Sede: ");
                        controlador.programarEvento(indice, fecha, hora, sede);
                        mostrarMensaje("Evento programado.");
                        break;
                    case 5:
                        mostrarEventos(controlador.obtenerEventos());
                        int indiceResultado = solicitarEntero("Indice del encuentro: ");
                        int golesLocal = solicitarEntero("Marcador local: ");
                        int golesVisitante = solicitarEntero("Marcador visitante: ");
                        controlador.registrarResultado(indiceResultado, golesLocal, golesVisitante);
                        mostrarMensaje("Resultado registrado.");
                        break;
                    case 6:
                        mostrarEquipos(controlador.obtenerTabla());
                        break;
                    case 7:
                        mostrarCalendario(controlador.obtenerCalendario());
                        break;
                    case 8:
                        mostrarEventos(controlador.obtenerEventos());
                        break;
                    case 0:
                        mostrarMensaje("Hasta luego.");
                        break;
                    default:
                        mostrarMensaje("Opcion invalida.");
                }
            } catch (NumberFormatException e) {
                mostrarMensaje("Error: ingrese un numero entero valido.");
            } catch (DateTimeParseException e) {
                mostrarMensaje("Error: fecha invalida. Use AAAA-MM-DD.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                mostrarMensaje("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }
}
