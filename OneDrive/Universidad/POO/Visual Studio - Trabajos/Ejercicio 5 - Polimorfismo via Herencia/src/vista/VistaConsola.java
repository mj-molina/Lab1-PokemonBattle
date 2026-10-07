package vista;

import java.util.List;
import java.util.Scanner;
import modelo.Carta;
import modelo.Jugador;

public class VistaConsola {

    // Atributos
    private final Scanner scanner;

    // Constructor
    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    // Menu
    public void mostrarMenu() {
        System.out.println();
        System.out.println("=== UVG CARD BATTLE ===");
        System.out.println("1. Listar cartas del mazo");
        System.out.println("2. Buscar carta por ID");
        System.out.println("3. Buscar carta por nombre");
        System.out.println("4. Ordenar mazo por costo de energia");
        System.out.println("5. Jugar partida");
        System.out.println("0. Salir");
    }

    public void mostrarMenuTurno() {
        System.out.println("1. Usar carta");
        System.out.println("2. Tomar carta");
        System.out.println("3. Pasar turno");
        System.out.println("0. Abandonar partida");
    }

    // Entrada
    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingresa un numero entero.");
            }
        }
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    // Salida
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarCarta(Carta carta) {
        System.out.println(carta);
    }

    public void mostrarCartas(List<Carta> cartas) {
        if (cartas.isEmpty()) {
            System.out.println("No hay cartas en el mazo.");
            return;
        }
        System.out.println("--- Cartas en el mazo (" + cartas.size() + ") ---");
        for (Carta carta : cartas) {
            System.out.println(carta);
        }
    }

            public void mostrarEstadoJugador(Jugador jugador) {
        System.out.println("--- " + jugador + " ---");
        mostrarMano(jugador);
    }

    public void mostrarEstadoJugador(Jugador jugador, int cartasEnMazo) {
        System.out.println("--- " + jugador.getNombre()
                + " | Energia: " + jugador.getEnergia() + "/" + Jugador.ENERGIA_MAXIMA
                + " | Creditos: " + jugador.getCreditos()
                + " | Llamadas de atencion: " + jugador.getLlamadasAtencion()
                + " | Cartas en mazo: " + cartasEnMazo
                + " | Cartas en mano: " + jugador.getMano().size() + " ---");
        mostrarMano(jugador);
    }

    private void mostrarMano(Jugador jugador) {
        List<Carta> mano = jugador.getMano();
        if (mano.isEmpty()) {
            System.out.println("Mano vacia.");
            return;
        }
        System.out.println("Mano:");
        for (int i = 0; i < mano.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + mano.get(i));
        }
    }
}