import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Copa implements Iterable<Programable> {
    private ArrayList<Equipo> equipos;
    private ArrayList<Programable> eventos;

    // Unico criterio cronologico para cualquier tipo de evento.
    private static final Comparator<Programable> ORDEN_CALENDARIO =
            Comparator.comparing(Programable::getFecha)
                    .thenComparingInt(Programable::getHora)
                    .thenComparing(Programable::getSede, String.CASE_INSENSITIVE_ORDER);

    public Copa() {
        equipos = new ArrayList<>();
        eventos = new ArrayList<>();
    }
    public void registrarEquipo(Equipo equipo) {
        if (equipo == null || buscarEquipo(equipo.getCodigo()) != null)
            throw new IllegalArgumentException("Equipo nulo o codigo repetido.");
        equipos.add(equipo);
    }
    public Equipo buscarEquipo(String codigo) {
        if (codigo == null) return null;
        for (Equipo equipo : equipos)
            if (equipo.getCodigo().equalsIgnoreCase(codigo.trim())) return equipo;
        return null;
    }
    public void agregarEvento(Programable evento) {
        if (evento == null || eventos.contains(evento))
            throw new IllegalArgumentException("Evento nulo o ya registrado.");
        eventos.add(evento);
    }
    public void programarEvento(Programable evento, LocalDate fecha, int hora, String sede) {
        if (evento == null || !eventos.contains(evento))
            throw new IllegalArgumentException("El evento no esta registrado.");
        if (fecha == null || hora < 0 || hora > 23 || sede == null || sede.trim().isEmpty())
            throw new IllegalArgumentException("Fecha, hora (0-23) y sede validas son obligatorias.");
        if (evento instanceof Puntuable && ((Puntuable) evento).estaFinalizado())
            throw new IllegalStateException("Un encuentro finalizado no puede reprogramarse.");
        for (Programable otro : eventos) {
            if (otro == evento || !otro.estaProgramado()) continue;
            if (otro.getFecha().equals(fecha) && otro.getHora() == hora) {
                if (otro.getSede().equalsIgnoreCase(sede.trim()))
                    throw new IllegalArgumentException("La sede ya esta ocupada en ese horario.");
                if (evento instanceof Puntuable && otro instanceof Puntuable) {
                    Puntuable actual = (Puntuable) evento;
                    Puntuable existente = (Puntuable) otro;
                    Equipo a = actual.getEquipoLocal(), b = actual.getEquipoVisitante();
                    Equipo c = existente.getEquipoLocal(), d = existente.getEquipoVisitante();
                    if (a == c || a == d || b == c || b == d)
                        throw new IllegalArgumentException("Un equipo ya tiene encuentro en ese horario.");
                }
            }
        }
        evento.programar(fecha, hora, sede);
    }
    public void registrarResultado(Puntuable encuentro, int marcadorLocal, int marcadorVisitante) {
        if (encuentro == null || !eventos.contains(encuentro))
            throw new IllegalArgumentException("El encuentro no esta registrado.");
        encuentro.registrarResultado(marcadorLocal, marcadorVisitante);
    }
    public ArrayList<Equipo> obtenerTablaPosiciones() {
        ArrayList<Equipo> tabla = new ArrayList<>(equipos);
        Collections.sort(tabla);
        return tabla;
    }
    @Override public Iterator<Programable> iterator() {
        ArrayList<Programable> calendario = new ArrayList<>();
        for (Programable evento : eventos) if (evento.estaProgramado()) calendario.add(evento);
        calendario.sort(ORDEN_CALENDARIO);
        return calendario.iterator();
    }
    public void cargarDatosIniciales() {
        Equipo faro = new Equipo("E1", "Faro", "Empresa A");
        Equipo luna = new Equipo("E2", "Luna", "Empresa B");
        Equipo sol = new Equipo("E3", "Sol", "Empresa A");
        Equipo rio = new Equipo("E4", "Rio", "Empresa B");
        registrarEquipo(faro); registrarEquipo(luna); registrarEquipo(sol); registrarEquipo(rio);
        Encuentro f1 = new FutbolSala(faro, sol);
        Encuentro b1 = new Baloncesto(luna, rio);
        Encuentro f2 = new FutbolSala(sol, luna);
        Encuentro b2 = new Baloncesto(faro, rio);
        agregarEvento(f1); agregarEvento(b1); agregarEvento(f2); agregarEvento(b2);
        programarEvento(f1, LocalDate.of(2026, 11, 12), 9, "Cancha A");
        programarEvento(b1, LocalDate.of(2026, 11, 12), 11, "Gimnasio");
        programarEvento(f2, LocalDate.of(2026, 11, 13), 9, "Cancha A");
        programarEvento(b2, LocalDate.of(2026, 11, 13), 11, "Gimnasio");
        registrarResultado(f1, 2, 2); // Empate futbol: Faro y Sol +1.
        registrarResultado(b1, 80, 75); // Luna +2, Rio +1.
        Exhibicion exhibicion = new Exhibicion("Clinica de baloncesto");
        agregarEvento(exhibicion);
        programarEvento(exhibicion, LocalDate.of(2026, 11, 12), 10, "Auditorio");
        // Faro, Sol y Rio quedan empatados a 1 punto: orden por nombre.
    }
}
