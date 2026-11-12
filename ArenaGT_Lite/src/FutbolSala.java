public class FutbolSala extends Encuentro {
    public FutbolSala(Equipo equipoLocal, Equipo equipoVisitante) { super(equipoLocal, equipoVisitante); }
    @Override protected void validarMarcador(int marcadorLocal, int marcadorVisitante) {
        if (marcadorLocal < 0 || marcadorVisitante < 0)
            throw new IllegalArgumentException("No se admiten marcadores negativos.");
    }
    @Override protected int calcularPuntosLocal(int marcadorLocal, int marcadorVisitante) {
        return marcadorLocal > marcadorVisitante ? 3 : marcadorLocal == marcadorVisitante ? 1 : 0;
    }
    @Override protected int calcularPuntosVisitante(int marcadorLocal, int marcadorVisitante) {
        return marcadorVisitante > marcadorLocal ? 3 : marcadorLocal == marcadorVisitante ? 1 : 0;
    }
}
