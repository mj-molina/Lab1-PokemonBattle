package modelo;

public class LabNoCompila extends CartaHumor {

    // Atributos
    private final int energiaPerdida;

    // Constructor
    public LabNoCompila(int id, String nombre, int costoEnergia, String descripcion,
                        String frase, int energiaPerdida) {
        super(id, nombre, costoEnergia, descripcion, 0, frase);
        this.energiaPerdida = Math.max(0, energiaPerdida);
    }

    // Getters
    public int getEnergiaPerdida() { return energiaPerdida; }

    // Comportamiento
    @Override
    protected String aplicarEfecto(Jugador jugador, Tablero tablero) {
        jugador.perderEnergia(energiaPerdida);
        jugador.sumarCreditos(1);
        return String.format("%s: el lab no compila. Pierde %d de energia, pero gana 1 credito depurando. \"%s\"",
                jugador.getNombre(), energiaPerdida, getFrase());
    }

    // toString
    @Override
    public String toString() {
        return super.toString() + " | Energia perdida: " + energiaPerdida;
    }
}
