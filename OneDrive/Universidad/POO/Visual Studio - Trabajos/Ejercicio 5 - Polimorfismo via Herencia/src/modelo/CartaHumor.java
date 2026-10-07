package modelo;

public abstract class CartaHumor extends EventoCampus {

    // Atributos
    private final String frase;

    // Constructor
    public CartaHumor(int id, String nombre, int costoEnergia, String descripcion,
                      int duracionTurnos, String frase) {
        super(id, nombre, costoEnergia, descripcion, duracionTurnos, 0);
        this.frase = frase;
    }

    // Getters
    public String getFrase() { return frase; }

    // Comportamiento
    @Override
    public String getTipo() {
        return "Carta de Humor";
    }

    @Override
    protected abstract String aplicarEfecto(Jugador jugador, Tablero tablero);

    // toString
    @Override
    public String toString() {
        return super.toString() + " | Frase: \"" + frase + "\"";
    }
}
