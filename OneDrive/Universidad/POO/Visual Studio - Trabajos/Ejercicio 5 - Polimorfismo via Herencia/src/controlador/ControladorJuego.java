package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.Carta;
import modelo.FabricaCartas;
import modelo.Jugador;
import modelo.Mazo;
import modelo.Tablero;
import vista.VistaConsola;

public class ControladorJuego {

    // Constantes
    private static final int ENERGIA_INICIAL = 5;
    private static final int ENERGIA_POR_TURNO = 2;
    private static final int META_CREDITOS = 12;
    private static final int CARTAS_INICIALES = 3;
    private static final int NUMERO_JUGADORES = 2;
    private static final int COSTO_TOMAR_CARTA = 1;

    // Atributos
    private final Mazo mazo;
    private final List<Jugador> jugadores;
    private Tablero tablero;
    private final VistaConsola vista;
    private int indiceJugadorActual;

    // Constructor
    public ControladorJuego(Mazo mazo, VistaConsola vista) {
        this.mazo = mazo;
        this.vista = vista;
        this.jugadores = new ArrayList<>();
        this.tablero = new Tablero();
        this.indiceJugadorActual = 0;
    }

    // Menu principal
    public void iniciar() {
        cargarCatalogo();
        boolean salir = false;
        while (!salir) {
            vista.mostrarMenu();
            int opcion = vista.leerEntero("Opcion: ");
            switch (opcion) {
                case 1:
                    listarCartas();
                    break;
                case 2:
                    buscarCarta(vista.leerEntero("ID de la carta: "));
                    break;
                case 3:
                    buscarCarta(vista.leerTexto("Nombre de la carta: "));
                    break;
                case 4:
                    ordenarPorEnergia();
                    break;
                case 5:
                    jugarPartida();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    vista.mostrarMensaje("Opcion invalida.");
                    break;
            }
        }
        vista.mostrarMensaje("Hasta pronto!");
    }

    // Carga inicial
    private void cargarCatalogo() {
        mazo.vaciar();
        for (Carta carta : new FabricaCartas().generarCatalogo()) {
            mazo.agregarCarta(carta);
        }
    }

    // Consultas del mazo
    public void listarCartas() {
        vista.mostrarCartas(mazo.getCartas());
    }

    public void buscarCarta(int id) {
        Carta carta = mazo.buscar(id);
        if (carta == null) {
            vista.mostrarMensaje("No se encontro ninguna carta con el ID " + id + ".");
        } else {
            vista.mostrarCarta(carta);
        }
    }

    public void buscarCarta(String nombre) {
        Carta carta = mazo.buscar(nombre);
        if (carta == null) {
            vista.mostrarMensaje("No se encontro ninguna carta con el nombre \"" + nombre + "\".");
        } else {
            vista.mostrarCarta(carta);
        }
    }

    public void ordenarPorEnergia() {
        mazo.ordenarPorEnergia();
        vista.mostrarMensaje("Mazo ordenado por costo de energia.");
        vista.mostrarCartas(mazo.getCartas());
    }

    // Partida
    private void jugarPartida() {
        prepararPartida();
        boolean abandonada = false;
        while (!juegoTerminado()) {
            if (!jugarTurno()) {
                abandonada = true;
                break;
            }
        }
        if (abandonada) {
            vista.mostrarMensaje("Partida abandonada.");
        } else {
            mostrarResultado();
        }
        cargarCatalogo();
    }

    private void prepararPartida() {
        jugadores.clear();
        tablero = new Tablero();
        indiceJugadorActual = 0;
        for (int i = 1; i <= NUMERO_JUGADORES; i++) {
            String nombre = vista.leerTexto("Nombre del jugador " + i + ": ");
            if (nombre.isEmpty()) {
                nombre = "Jugador " + i;
            }
            Jugador jugador = new Jugador(nombre, ENERGIA_INICIAL);
            for (int j = 0; j < CARTAS_INICIALES && !mazo.estaVacio(); j++) {
                jugador.agregarCartaMano(mazo.tomarCarta());
            }
            jugadores.add(jugador);
        }
    }

    // Turnos
    private boolean jugarTurno() {
        Jugador jugador = getJugadorActual();
        int llamadas = jugador.getLlamadasAtencion();
        int regeneracion = Math.max(0, ENERGIA_POR_TURNO - llamadas);
        int energiaAntes = jugador.getEnergia();
        jugador.ganarEnergia(regeneracion);
        int energiaGanada = jugador.getEnergia() - energiaAntes;
        jugador.reiniciarLlamadasAtencion();
        String detalle = (llamadas > 0) ? ", " + llamadas + " llamada(s) de atencion aplicada(s)" : "";
        vista.mostrarMensaje("\n=== Turno de " + jugador.getNombre() + " (+" + energiaGanada + " de energia" + detalle + ") ===");
        while (true) {
            vista.mostrarMensaje(tablero.toString());
            vista.mostrarEstadoJugador(jugador, mazo.size());
            vista.mostrarMenuTurno();
            int opcion = vista.leerEntero("Opcion: ");
            switch (opcion) {
                case 1:
                    usarCarta();
                    if (juegoTerminado()) {
                        return true;
                    }
                    break;
                case 2:
                    tomarCarta();
                    break;
                case 3:
                    pasarTurno();
                    return true;
                case 0:
                    return false;
                default:
                    vista.mostrarMensaje("Opcion invalida.");
                    break;
            }
        }
    }

    private void usarCarta() {
        Jugador jugador = getJugadorActual();
        if (jugador.getMano().isEmpty()) {
            vista.mostrarMensaje("No tienes cartas en la mano.");
            return;
        }
        int posicion = vista.leerEntero("Numero de la carta en tu mano: ");
        if (posicion < 1 || posicion > jugador.getMano().size()) {
            vista.mostrarMensaje("Numero de carta invalido.");
            return;
        }
        Carta carta = jugador.getMano().get(posicion - 1);
        vista.mostrarMensaje(jugador.usarCarta(carta, tablero));
    }

    private boolean tomarCarta() {
        if (mazo.estaVacio()) {
            vista.mostrarMensaje("El mazo esta vacio.");
            return false;
        }
        Jugador jugador = getJugadorActual();
        if (!jugador.gastarEnergia(COSTO_TOMAR_CARTA)) {
            vista.mostrarMensaje("Necesitas " + COSTO_TOMAR_CARTA + " de energia para tomar una carta.");
            return false;
        }
        Carta carta = mazo.tomarCarta();
        jugador.agregarCartaMano(carta);
        vista.mostrarMensaje(jugador.getNombre() + " toma: " + carta
                + " (costo " + COSTO_TOMAR_CARTA + " de energia)");
        return true;
    }

    private void pasarTurno() {
        tablero.avanzarTurno();
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
    }

    public Jugador getJugadorActual() {
        return jugadores.get(indiceJugadorActual);
    }

    // Fin de partida
    public boolean juegoTerminado() {
        for (Jugador jugador : jugadores) {
            if (jugador.getCreditos() >= META_CREDITOS) {
                return true;
            }
        }
        if (!mazo.estaVacio()) {
            return false;
        }
        for (Jugador jugador : jugadores) {
            if (!jugador.getMano().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private Jugador determinarGanador() {
        Jugador ganador = null;
        boolean empate = false;
        for (Jugador jugador : jugadores) {
            if (ganador == null || jugador.getCreditos() > ganador.getCreditos()) {
                ganador = jugador;
                empate = false;
            } else if (jugador.getCreditos() == ganador.getCreditos()) {
                empate = true;
            }
        }
        return empate ? null : ganador;
    }

    private void mostrarResultado() {
        vista.mostrarMensaje("\n=== FIN DE LA PARTIDA ===");
        for (Jugador jugador : jugadores) {
            vista.mostrarMensaje(jugador.toString());
        }
        Jugador ganador = determinarGanador();
        if (ganador == null) {
            vista.mostrarMensaje("La partida termina en empate.");
        } else {
            vista.mostrarMensaje("Ganador: " + ganador.getNombre() + " con " + ganador.getCreditos() + " credito(s).");
        }
    }
}