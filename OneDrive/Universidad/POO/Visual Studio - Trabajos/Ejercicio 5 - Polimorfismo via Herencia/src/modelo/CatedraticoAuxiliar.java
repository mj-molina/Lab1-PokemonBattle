package modelo;

public class CatedraticoAuxiliar extends CatedraticoFlexible {

    // Atributos
    private final int horasConsulta;

    // Constructor
    public CatedraticoAuxiliar(int id, String nombre, int costoEnergia, String descripcion,
                               String departamento, int llamadasAtencion, int tiempoAtencion,
                               int prorrogaDias, int horasConsulta) {
        super(id, nombre, costoEnergia, descripcion, departamento, llamadasAtencion, tiempoAtencion, prorrogaDias);
        this.horasConsulta = Math.max(0, horasConsulta);
    }

    // Getters
    public int getHorasConsulta() { return horasConsulta; }

    // Comportamiento
    @Override
    public String getTipo() {
        return "Catedratico Auxiliar";
    }

    @Override
    protected int calcularEnergiaGanada() {
        return super.calcularEnergiaGanada() + horasConsulta / 2;
    }

    // toString
    @Override
    public String toString() {
        return super.toString() + " | Horas de consulta: " + horasConsulta;
    }
}
