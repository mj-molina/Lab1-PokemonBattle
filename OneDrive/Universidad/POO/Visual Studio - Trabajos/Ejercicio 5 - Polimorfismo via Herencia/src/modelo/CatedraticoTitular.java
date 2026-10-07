package modelo;

public class CatedraticoTitular extends CatedraticoEstricto {

    // Atributos
    private final int aniosExperiencia;

    // Constructor
    public CatedraticoTitular(int id, String nombre, int costoEnergia, String descripcion,
                              String departamento, int llamadasAtencion, int tiempoAtencion,
                              int nivelExigencia, int aniosExperiencia) {
        super(id, nombre, costoEnergia, descripcion, departamento, llamadasAtencion, tiempoAtencion, nivelExigencia);
        this.aniosExperiencia = Math.max(0, aniosExperiencia);
    }

    // Getters
    public int getAniosExperiencia() { return aniosExperiencia; }

    // Comportamiento
    @Override
    public String getTipo() {
        return "Catedratico Titular";
    }

    @Override
    protected int calcularEnergiaGanada() {
        return super.calcularEnergiaGanada() + aniosExperiencia / 5;
    }

    // toString
    @Override
    public String toString() {
        return super.toString() + " | Anios de experiencia: " + aniosExperiencia;
    }
}
