package modelo;

public class EntregaUltimoMinuto extends CartaHumor {

    // Atributos
    private final int cartasDescartadas;

    // Constructor
    public EntregaUltimoMinuto(int id, String nombre, int costoEnergia, String descripcion,
                               String frase, int cartasDescartadas) {
        super(id, nombre, costoEnergia, descripcion, 0, frase);
        this.cartasDescartadas = Math.max(0, cartasDescartadas);
    }

    // Getters
    public int getCartasDescartadas() { return cartasDescartadas; }

    // Comportamiento
    @Override
    protected String aplicarEfecto(Jugador jugador, Tablero tablero) {
        int descartadas = jugador.descartarCartas(cartasDescartadas);
        jugador.ganarEnergia(descartadas);
        return String.format("%s entrega a ultima hora: descarta %d carta(s) y gana %d de energia. \"%s\"",
                jugador.getNombre(), descartadas, descartadas, getFrase());
    }

    // toString
    @Override
    public String toString() {
        return super.toString() + " | Cartas a descartar: " + cartasDescartadas;
    }
}
