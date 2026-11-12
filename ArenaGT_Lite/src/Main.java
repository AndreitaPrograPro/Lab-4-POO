public class Main {
    public static void main(String[] args) {
        Copa copa = new Copa();
        copa.cargarDatosIniciales();
        ControladorCopa controlador = new ControladorCopa(copa);
        VistaCopa vista = new VistaCopa(controlador);
        vista.iniciar();
    }
}
