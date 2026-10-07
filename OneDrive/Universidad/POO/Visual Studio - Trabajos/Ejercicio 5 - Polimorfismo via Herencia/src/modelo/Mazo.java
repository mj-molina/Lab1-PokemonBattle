package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Mazo {

    // Atributos
    private final List<Carta> cartas;

    // Constructores
    public Mazo() {
        this.cartas = new ArrayList<>();
    }

    public Mazo(List<Carta> cartas) {
        this.cartas = new ArrayList<>(cartas);
    }

    // Getters
    public List<Carta> getCartas() { return Collections.unmodifiableList(cartas); }

    public int size() { return cartas.size(); }

    public boolean estaVacio() { return cartas.isEmpty(); }

    // Gestion de cartas
    public void agregarCarta(Carta carta) {
        if (carta != null) {
            cartas.add(carta);
        }
    }

    public Carta tomarCarta() {
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.remove(0);
    }

    public void vaciar() {
        cartas.clear();
    }

    // Busqueda
    public Carta buscar(int id) {
        for (Carta carta : cartas) {
            if (carta.getId() == id) {
                return carta;
            }
        }
        return null;
    }

    public Carta buscar(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }
        String criterio = nombre.trim().toLowerCase();
        for (Carta carta : cartas) {
            if (carta.getNombre().toLowerCase().equals(criterio)) {
                return carta;
            }
        }
        for (Carta carta : cartas) {
            if (carta.getNombre().toLowerCase().contains(criterio)) {
                return carta;
            }
        }
        return null;
    }

    // Ordenamiento
    public void ordenarPorEnergia() {
        Collections.sort(cartas);
    }

    public void ordenar(Comparator<Carta> criterio) {
        cartas.sort(criterio);
    }

    // toString
    @Override
    public String toString() {
        return "Mazo con " + cartas.size() + " carta(s)";
    }
}
