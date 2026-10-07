package modelo;

public abstract class Carta implements Comparable<Carta> {

    // Atributos
    private final int id;
    private final String nombre;
    private int costoEnergia;
    private final String descripcion;

    // Constructor
    public Carta(int id, String nombre, int costoEnergia, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.costoEnergia = Math.max(0, costoEnergia);
        this.descripcion = descripcion;
    }

    // Getters y setters
    public int getId() { return id; }

    public String getNombre() { return nombre; }

    public int getCostoEnergia() { return costoEnergia; }

    public void setCostoEnergia(int costoEnergia) { this.costoEnergia = Math.max(0, costoEnergia); }

    public String getDescripcion() { return descripcion; }

    // Comportamiento
    public abstract String getTipo();

    public abstract String jugarCarta(Jugador jugador);

    public String jugarCarta(Jugador jugador, Tablero tablero) {
        return jugarCarta(jugador);
    }

    // Comparable
    @Override
    public int compareTo(Carta otra) {
        return Integer.compare(costoEnergia, otra.costoEnergia);
    }

    // equals, hashCode y toString
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Carta)) {
            return false;
        }
        return id == ((Carta) obj).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | Tipo: %s | Energia: %d | %s", id, nombre, getTipo(), costoEnergia, descripcion);
    }
}
