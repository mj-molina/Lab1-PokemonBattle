package modelo;

public class Cafecito extends CartaHumor {

    // Atributos
    private final int energiaRecuperada;

    // Constructor
    public Cafecito(int id, String nombre, int costoEnergia, String descripcion,
                    String frase, int energiaRecuperada) {
        super(id, nombre, costoEnergia, descripcion, 0, frase);
        this.energiaRecuperada = Math.max(0, energiaRecuperada);
    }

    // Getters
    public int getEnergiaRecuperada() { return energiaRecuperada; }

    // Comportamiento
    @Override
    protected String aplicarEfecto(Jugador jugador, Tablero tablero) {
        jugador.ganarEnergia(energiaRecuperada);
        return String.format("%s se toma un cafecito y recupera %d de energia. \"%s\"",
                jugador.getNombre(), energiaRecuperada, getFrase());
    }

    // toString
    @Override
    public String toString() {
        return super.toString() + " | Energia recuperada: " + energiaRecuperada;
    }
}
