public interface Puntuable {
    void registrarResultado(int marcadorLocal, int marcadorVisitante);
    Equipo getEquipoLocal();
    Equipo getEquipoVisitante();
    boolean estaFinalizado();
}
