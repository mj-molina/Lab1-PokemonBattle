import controlador.ControladorJuego;
import modelo.Mazo;
import vista.VistaConsola;

public class Main {

    // Driver program
    public static void main(String[] args) {
        Mazo mazo = new Mazo();
        VistaConsola vista = new VistaConsola();
        ControladorJuego controlador = new ControladorJuego(mazo, vista);
        controlador.iniciar();
    }
}

