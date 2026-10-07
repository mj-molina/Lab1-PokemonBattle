package modelo;

public abstract class Catedratico extends Carta {

    // Atributos
    private final String departamento;
    private int llamadasAtencion;
    private int tiempoAtencion;

    // Constructor
    public Catedratico(int id, String nombre, int costoEnergia, String descripcion,
                       String departamento, int llamadasAtencion, int tiempoAtencion) {
        super(id, nombre, costoEnergia, descripcion);
        this.departamento = departamento;
        this.llamadasAtencion = Math.max(0, llamadasAtencion);
        this.tiempoAtencion = Math.max(0, tiempoAtencion);
    }

    // Getters y setters
    public String getDepartamento() { return departamento; }

    public int getLlamadasAtencion() { return llamadasAtencion; }

    public void setLlamadasAtencion(int llamadasAtencion) { this.llamadasAtencion = Math.max(0, llamadasAtencion); }

    public int getTiempoAtencion() { return tiempoAtencion; }

    public void setTiempoAtencion(int tiempoAtencion) { this.tiempoAtencion = Math.max(0, tiempoAtencion); }

    // Comportamiento
    @Override
    public String getTipo() {
        return "Catedratico";
    }

    @Override
    public String jugarCarta(Jugador jugador) {
        int energiaGanada = calcularEnergiaGanada();
        int llamadas = calcularLlamadasGeneradas();
        jugador.ganarEnergia(energiaGanada);
        jugador.recibirLlamadaAtencion(llamadas);
        return String.format("%s dicta clase de %s: %s gana %d de energia y recibe %d llamada(s) de atencion.",
                getNombre(), departamento, jugador.getNombre(), energiaGanada, llamadas);
    }

    protected abstract int calcularEnergiaGanada();

    protected abstract int calcularLlamadasGeneradas();

    // toString
    @Override
    public String toString() {
        return super.toString() + " | Departamento: " + departamento
                + " | Llamadas de atencion: " + llamadasAtencion
                + " | Tiempo de atencion: " + tiempoAtencion;
    }
}
