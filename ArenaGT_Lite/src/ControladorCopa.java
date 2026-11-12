import java.time.LocalDate;
import java.util.ArrayList;

public class ControladorCopa {
    private Copa copa;
    public ControladorCopa(Copa copa) {
        if (copa == null) throw new IllegalArgumentException("La copa es obligatoria.");
        this.copa = copa;
        for (Programable evento : copa) eventosRegistrados.add(evento);
    }
    public void registrarEquipo(String codigo, String nombre, String empresa) {
        copa.registrarEquipo(new Equipo(codigo, nombre, empresa));
    }
    public void crearEncuentro(String deporte, String codigoLocal, String codigoVisitante) {
        Equipo local = copa.buscarEquipo(codigoLocal);
        Equipo visitante = copa.buscarEquipo(codigoVisitante);
        if (local == null || visitante == null) throw new IllegalArgumentException("Equipo no encontrado.");
        Encuentro encuentro;
        if (deporte.equalsIgnoreCase("futbol") || deporte.equalsIgnoreCase("futbol sala"))
            encuentro = new FutbolSala(local, visitante);
        else if (deporte.equalsIgnoreCase("baloncesto"))
            encuentro = new Baloncesto(local, visitante);
        else throw new IllegalArgumentException("Deporte desconocido. Use futbol o baloncesto.");
        copa.agregarEvento(encuentro);
        eventosRegistrados.add(encuentro);
    }
    public void crearExhibicion(String titulo) {
        Exhibicion exhibicion = new Exhibicion(titulo);
        copa.agregarEvento(exhibicion);
        eventosRegistrados.add(exhibicion);
    }
    public void programarEvento(int indice, LocalDate fecha, int hora, String sede) {
        ArrayList<Programable> lista = obtenerEventos();
        if (indice < 0 || indice >= lista.size()) throw new IllegalArgumentException("Indice fuera de rango.");
        copa.programarEvento(lista.get(indice), fecha, hora, sede);
    }
    public void registrarResultado(int indice, int marcadorLocal, int marcadorVisitante) {
        ArrayList<Programable> lista = obtenerEventos();
        if (indice < 0 || indice >= lista.size()) throw new IllegalArgumentException("Indice fuera de rango.");
        Programable evento = lista.get(indice);
        if (!(evento instanceof Puntuable)) throw new IllegalArgumentException("Una exhibicion no tiene resultado.");
        copa.registrarResultado((Puntuable) evento, marcadorLocal, marcadorVisitante);
    }
    public ArrayList<Equipo> obtenerTabla() { return copa.obtenerTablaPosiciones(); }
    public ArrayList<Programable> obtenerCalendario() {
        ArrayList<Programable> calendario = new ArrayList<>();
        for (Programable evento : copa) calendario.add(evento);
        return calendario;
    }
    public ArrayList<Programable> obtenerEventos() {
        // Incluye programados y sin programar; se seleccionan por indice.
        // Copa solo expone eventos mediante Iterable, asi que mantenemos el
        // mismo catalogo en un orden de insercion desde el controlador.
        return new ArrayList<>(eventosRegistrados);
    }
    private ArrayList<Programable> eventosRegistrados = new ArrayList<>();
}
