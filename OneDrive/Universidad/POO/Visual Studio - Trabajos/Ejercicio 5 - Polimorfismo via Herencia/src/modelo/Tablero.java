package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Tablero {

    // Atributos
    private final List<EventoCampus> eventosActivos;
    private int turnoActual;

    // Constructor
    public Tablero() {
        this.eventosActivos = new ArrayList<>();
        this.turnoActual = 1;
    }

    // Getters
    public List<EventoCampus> getEventosActivos() { return Collections.unmodifiableList(eventosActivos); }

    public int getTurnoActual() { return turnoActual; }

    // Eventos
    public void agregarEvento(EventoCampus evento) {
        if (evento != null && evento.getDuracionTurnos() > 0) {
            eventosActivos.add(evento);
        }
    }

    public void avanzarTurno() {
        turnoActual++;
        Iterator<EventoCampus> iterador = eventosActivos.iterator();
        while (iterador.hasNext()) {
            if (iterador.next().avanzarTurno()) {
                iterador.remove();
            }
        }
    }

    public int getModificadorEnergiaTotal() {
        int total = 0;
        for (EventoCampus evento : eventosActivos) {
            total += evento.getModificadorEnergia();
        }
        return total;
    }

    // toString
    @Override
    public String toString() {
        if (eventosActivos.isEmpty()) {
            return "Tablero (turno " + turnoActual + "): sin eventos activos.";
        }
        StringBuilder sb = new StringBuilder("Tablero (turno " + turnoActual + "): ");
        for (int i = 0; i < eventosActivos.size(); i++) {
            EventoCampus evento = eventosActivos.get(i);
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(evento.getNombre())
              .append(" [")
              .append(evento.getTurnosRestantes())
              .append(" turno(s), costo ")
              .append(String.format("%+d", evento.getModificadorEnergia()))
              .append("]");
        }
        return sb.toString();
    }
}
