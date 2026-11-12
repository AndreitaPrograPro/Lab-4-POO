import java.time.LocalDate;

public abstract class Encuentro implements Programable, Puntuable {
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private int marcadorLocal;
    private int marcadorVisitante;
    private boolean finalizado;
    private LocalDate fecha;
    private int hora;
    private String sede;

    protected Encuentro(Equipo equipoLocal, Equipo equipoVisitante) {
        if (equipoLocal == null || equipoVisitante == null || equipoLocal == equipoVisitante
                || equipoLocal.getCodigo().equals(equipoVisitante.getCodigo())) {
            throw new IllegalArgumentException("Los equipos deben existir y ser diferentes.");
        }
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
    }
    @Override public void programar(LocalDate fecha, int hora, String sede) {
        if (finalizado) throw new IllegalStateException("Un encuentro finalizado no puede reprogramarse.");
        if (fecha == null || hora < 0 || hora > 23 || sede == null || sede.trim().isEmpty()) {
            throw new IllegalArgumentException("Fecha, hora (0-23) y sede validas son obligatorias.");
        }
        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
    }
    @Override public void registrarResultado(int marcadorLocal, int marcadorVisitante) {
        if (!estaProgramado()) throw new IllegalStateException("Primero debe programar el encuentro.");
        if (finalizado) throw new IllegalStateException("El encuentro ya tiene resultado.");
        validarMarcador(marcadorLocal, marcadorVisitante);
        int puntosLocal = calcularPuntosLocal(marcadorLocal, marcadorVisitante);
        int puntosVisitante = calcularPuntosVisitante(marcadorLocal, marcadorVisitante);
        if (puntosLocal < 0 || puntosVisitante < 0) throw new IllegalArgumentException("Puntos invalidos.");
        this.marcadorLocal = marcadorLocal;
        this.marcadorVisitante = marcadorVisitante;
        equipoLocal.sumarPuntos(puntosLocal);
        equipoVisitante.sumarPuntos(puntosVisitante);
        finalizado = true;
    }
    protected abstract void validarMarcador(int marcadorLocal, int marcadorVisitante);
    protected abstract int calcularPuntosLocal(int marcadorLocal, int marcadorVisitante);
    protected abstract int calcularPuntosVisitante(int marcadorLocal, int marcadorVisitante);
    @Override public Equipo getEquipoLocal() { return equipoLocal; }
    @Override public Equipo getEquipoVisitante() { return equipoVisitante; }
    @Override public LocalDate getFecha() { return fecha; }
    @Override public int getHora() { return hora; }
    @Override public String getSede() { return sede; }
    @Override public boolean estaProgramado() { return fecha != null; }
    @Override public boolean estaFinalizado() { return finalizado; }
    @Override public String getDescripcion() {
        String resultado = finalizado ? " | " + marcadorLocal + "-" + marcadorVisitante : " | pendiente";
        return getClass().getSimpleName() + ": " + equipoLocal.getNombre() + " vs "
                + equipoVisitante.getNombre() + resultado;
    }
    @Override public String toString() { return getDescripcion(); }
}
