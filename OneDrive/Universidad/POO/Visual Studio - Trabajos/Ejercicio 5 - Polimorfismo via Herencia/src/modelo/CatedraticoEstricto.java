package modelo;

public class CatedraticoEstricto extends Catedratico {

    // Atributos
    private int nivelExigencia;

    // Constructor
    public CatedraticoEstricto(int id, String nombre, int costoEnergia, String descripcion,
                               String departamento, int llamadasAtencion, int tiempoAtencion,
                               int nivelExigencia) {
        super(id, nombre, costoEnergia, descripcion, departamento, llamadasAtencion, tiempoAtencion);
        this.nivelExigencia = Math.max(0, nivelExigencia);
    }

    // Getters y setters
    public int getNivelExigencia() { return nivelExigencia; }

    public void setNivelExigencia(int nivelExigencia) { this.nivelExigencia = Math.max(0, nivelExigencia); }

    // Comportamiento
    @Override
    public String getTipo() {
        return "Catedratico Estricto";
    }

    @Override
    protected int calcularEnergiaGanada() {
        return getTiempoAtencion() + nivelExigencia;
    }

    @Override
    protected int calcularLlamadasGeneradas() {
        return Math.max(1, getLlamadasAtencion());
    }

    // toString
    @Override
    public String toString() {
        return super.toString() + " | Exigencia: " + nivelExigencia;
    }
}