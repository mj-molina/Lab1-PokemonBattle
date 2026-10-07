package modelo;

public class CatedraticoFlexible extends Catedratico {

    // Atributos
    private int prorrogaDias;

    // Constructor
    public CatedraticoFlexible(int id, String nombre, int costoEnergia, String descripcion,
                               String departamento, int llamadasAtencion, int tiempoAtencion,
                               int prorrogaDias) {
        super(id, nombre, costoEnergia, descripcion, departamento, llamadasAtencion, tiempoAtencion);
        this.prorrogaDias = Math.max(0, prorrogaDias);
    }

    // Getters y setters
    public int getProrrogaDias() { return prorrogaDias; }

    public void setProrrogaDias(int prorrogaDias) { this.prorrogaDias = Math.max(0, prorrogaDias); }

    // Comportamiento
    @Override
    public String getTipo() {
        return "Catedratico Flexible";
    }

    @Override
    protected int calcularEnergiaGanada() {
        return getTiempoAtencion() + prorrogaDias;
    }

    @Override
    protected int calcularLlamadasGeneradas() {
        return 0;
    }

    // toString
    @Override
    public String toString() {
        return super.toString() + " | Prorroga (dias): " + prorrogaDias;
    }
}
