public class Equipo implements Comparable<Equipo> {
    private String codigo;
    private String nombre;
    private String empresa;
    private int puntosTabla;

    public Equipo(String codigo, String nombre, String empresa) {
        if (codigo == null || codigo.trim().isEmpty() || nombre == null || nombre.trim().isEmpty()
                || empresa == null || empresa.trim().isEmpty()) {
            throw new IllegalArgumentException("Codigo, nombre y empresa son obligatorios.");
        }
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.empresa = empresa.trim();
        this.puntosTabla = 0;
    }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getEmpresa() { return empresa; }
    public int getPuntosTabla() { return puntosTabla; }
    public void sumarPuntos(int puntos) {
        if (puntos < 0) throw new IllegalArgumentException("Los puntos no pueden ser negativos.");
        puntosTabla += puntos;
    }
    @Override public int compareTo(Equipo otro) {
        int orden = Integer.compare(otro.puntosTabla, puntosTabla);
        return orden != 0 ? orden : nombre.compareToIgnoreCase(otro.nombre);
    }
    @Override public String toString() {
        return nombre + " (" + empresa + ", " + codigo + ") - " + puntosTabla + " puntos";
    }
}
