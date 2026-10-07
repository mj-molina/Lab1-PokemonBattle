package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jugador {

    // Constantes
    public static final int ENERGIA_MAXIMA = 10;

    // Atributos
    private final String nombre;
    private int energia;
    private int creditos;
    private int llamadasAtencion;
    private final List<Carta> mano;

    // Constructores
    public Jugador(String nombre, int energiaInicial) {
        this.nombre = nombre;
        this.energia = Math.min(ENERGIA_MAXIMA, Math.max(0, energiaInicial));
        this.creditos = 0;
        this.llamadasAtencion = 0;
        this.mano = new ArrayList<>();
    }

    public Jugador(String nombre) {
        this(nombre, 5);
    }

    // Getters
    public String getNombre() { return nombre; }

    public int getEnergia() { return energia; }

    public int getCreditos() { return creditos; }

    public int getLlamadasAtencion() { return llamadasAtencion; }

    public List<Carta> getMano() { return Collections.unmodifiableList(mano); }

    // Energia
    public void ganarEnergia(int cantidad) {
        if (cantidad > 0) {
            energia = Math.min(ENERGIA_MAXIMA, energia + cantidad);
        }
    }

    public boolean gastarEnergia(int cantidad) {
        if (cantidad <= energia) {
            energia -= cantidad;
            return true;
        }
        return false;
    }

    public void perderEnergia(int cantidad) {
        energia = Math.max(0, energia - Math.max(0, cantidad));
    }

    public boolean puedeJugar(Carta carta) {
        return carta != null && energia >= carta.getCostoEnergia();
    }

    // Creditos y llamadas de atencion
    public void sumarCreditos(int cantidad) {
        if (cantidad > 0) {
            creditos += cantidad;
        }
    }

    public void recibirLlamadaAtencion(int cantidad) {
        if (cantidad > 0) {
            llamadasAtencion += cantidad;
        }
    }

    public void reiniciarLlamadasAtencion() {
        llamadasAtencion = 0;
    }

    // Mano
    public void agregarCartaMano(Carta carta) {
        if (carta != null) {
            mano.add(carta);
        }
    }

    public boolean quitarCartaMano(Carta carta) {
        return mano.remove(carta);
    }

    public int descartarCartas(int cantidad) {
        int descartadas = 0;
        while (descartadas < cantidad && !mano.isEmpty()) {
            mano.remove(mano.size() - 1);
            descartadas++;
        }
        return descartadas;
    }

    // Usar cartas (polimorfismo)
    public String usarCarta(Carta carta) {
        return usarCarta(carta, null);
    }

    public String usarCarta(Carta carta, Tablero tablero) {
        if (carta == null || !mano.contains(carta)) {
            return "Esa carta no esta en la mano de " + nombre + ".";
        }
        int costo = costoEfectivo(carta, tablero);
        if (!gastarEnergia(costo)) {
            return String.format("Energia insuficiente para jugar %s (cuesta %d, tienes %d).",
                    carta.getNombre(), costo, energia);
        }
        mano.remove(carta);
        String efecto = (tablero == null) ? carta.jugarCarta(this) : carta.jugarCarta(this, tablero);
        return String.format("%s juega %s (costo %d).%n%s", nombre, carta.getNombre(), costo, efecto);
    }

    private int costoEfectivo(Carta carta, Tablero tablero) {
        int modificador = (tablero == null) ? 0 : tablero.getModificadorEnergiaTotal();
        return Math.max(0, carta.getCostoEnergia() + modificador);
    }

    // equals, hashCode y toString
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Jugador)) {
            return false;
        }
        return nombre.equals(((Jugador) obj).nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s | Energia: %d/%d | Creditos: %d | Llamadas de atencion: %d | Cartas en mano: %d",
                nombre, energia, ENERGIA_MAXIMA, creditos, llamadasAtencion, mano.size());
    }
}