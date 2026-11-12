import java.time.LocalDate;

public class Exhibicion implements Programable {
    private String titulo;
    private LocalDate fecha;
    private int hora;
    private String sede;
    public Exhibicion(String titulo) {
        if (titulo == null || titulo.trim().isEmpty())
            throw new IllegalArgumentException("El titulo es obligatorio.");
        this.titulo = titulo.trim();
    }
    @Override public void programar(LocalDate fecha, int hora, String sede) {
        if (fecha == null || hora < 0 || hora > 23 || sede == null || sede.trim().isEmpty())
            throw new IllegalArgumentException("Fecha, hora (0-23) y sede validas son obligatorias.");
        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
    }
    public String getTitulo() { return titulo; }
    @Override public LocalDate getFecha() { return fecha; }
    @Override public int getHora() { return hora; }
    @Override public String getSede() { return sede; }
    @Override public boolean estaProgramado() { return fecha != null; }
    @Override public String getDescripcion() { return "Exhibicion: " + titulo; }
    @Override public String toString() { return getDescripcion(); }
}
