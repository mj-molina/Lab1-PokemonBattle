package modelo;

public class EventoCampus extends Carta {

    // Atributos
    private final int duracionTurnos;
    private int turnosRestantes;
    private final int modificadorEnergia;

    // Constructor
    public EventoCampus(int id, String nombre, int costoEnergia, String descripcion,
                        int duracionTurnos, int modificadorEnergia) {
        super(id, nombre, costoEnergia, descripcion);
        this.duracionTurnos = Math.max(0, duracionTurnos);
        this.turnosRestantes = this.duracionTurnos;
        this.modificadorEnergia = modificadorEnergia;
    }

    // Getters
    public int getDuracionTurnos() { return duracionTurnos; }

    public int getTurnosRestantes() { return turnosRestantes; }

    public int getModificadorEnergia() { return modificadorEnergia; }

    // Comportamiento
    @Override
    public String getTipo() {
        return "Evento Campus";
    }

    public boolean avanzarTurno() {
        if (turnosRestantes > 0) {
            turnosRestantes--;
        }
        return turnosRestantes == 0;
    }

    @Override
    public String jugarCarta(Jugador jugador) {
        return aplicarEfecto(jugador, null);
    }

    @Override
    public String jugarCarta(Jugador jugador, Tablero tablero) {
        String resultado = aplicarEfecto(jugador, tablero);
        if (tablero != null) {
            tablero.agregarEvento(this);
        }
        return resultado;
    }

    protected String aplicarEfecto(Jugador jugador, Tablero tablero) {
        return String.format("%s activa %s: el costo de las cartas cambia %+d durante %d turno(s).",
                jugador.getNombre(), getNombre(), modificadorEnergia, duracionTurnos);
    }

    // toString
    @Override
    public String toString() {
        String detalle = duracionTurnos > 0
                ? " | Duracion: " + duracionTurnos + " turno(s) | Modificador de costo: " + String.format("%+d", modificadorEnergia)
                : " | Efecto inmediato";
        return super.toString() + detalle;
    }
}