public class Baloncesto extends Encuentro {
    public Baloncesto(Equipo equipoLocal, Equipo equipoVisitante) { super(equipoLocal, equipoVisitante); }
    @Override protected void validarMarcador(int marcadorLocal, int marcadorVisitante) {
        if (marcadorLocal < 0 || marcadorVisitante < 0 || marcadorLocal == marcadorVisitante)
            throw new IllegalArgumentException("Baloncesto exige marcadores no negativos y sin empate.");
    }
    @Override protected int calcularPuntosLocal(int marcadorLocal, int marcadorVisitante) {
        return marcadorLocal > marcadorVisitante ? 2 : 1;
    }
    @Override protected int calcularPuntosVisitante(int marcadorLocal, int marcadorVisitante) {
        return marcadorVisitante > marcadorLocal ? 2 : 1;
    }
}
