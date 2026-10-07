package modelo;

public class Curso extends Carta {

    // Atributos
    private final int creditos;
    private int dificultad;

    // Constructor
    public Curso(int id, String nombre, int costoEnergia, String descripcion,
                 int creditos, int dificultad) {
        super(id, nombre, costoEnergia, descripcion);
        this.creditos = Math.max(0, creditos);
        this.dificultad = Math.max(1, dificultad);
    }

    // Getters y setters
    public int getCreditos() { return creditos; }

    public int getDificultad() { return dificultad; }

    public void setDificultad(int dificultad) { this.dificultad = Math.max(1, dificultad); }

    // Comportamiento
    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String jugarCarta(Jugador jugador) {
        jugador.sumarCreditos(creditos);
        String resultado = String.format("%s aprueba %s y suma %d credito(s).", jugador.getNombre(), getNombre(), creditos);
        if (dificultad >= 4) {
            jugador.perderEnergia(1);
            resultado += " Es un curso pesado: pierde 1 de energia extra.";
        }
        return resultado;
    }

    // toString
    @Override
    public String toString() {
        return super.toString() + " | Creditos: " + creditos + " | Dificultad: " + dificultad + "/5";
    }
}