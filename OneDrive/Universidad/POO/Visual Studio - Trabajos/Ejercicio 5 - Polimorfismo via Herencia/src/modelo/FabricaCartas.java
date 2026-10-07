package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FabricaCartas {

    // Constantes
    private static final int MINIMO_CARTAS = 10;
    private static final int CANTIDAD_POR_DEFECTO = 20;

    // Datos para la generacion
    private static final String[] NOMBRES_CATEDRATICOS = {
        "Dr. Recursivo", "Licda. Polimorfa", "Ing. Compilador", "Dr. Bucle Infinito",
        "Mtra. Heredera", "Ing. Segfault", "Dr. Algoritmo", "Licda. Stack Overflow",
        "Ing. Null Pointer", "Mtro. Binario"
    };

    private static final String[] DEPARTAMENTOS = {
        "Computacion", "Matematica", "Fisica", "Ingenieria", "Humanidades"
    };

    private static final String[] NOMBRES_CURSOS = {
        "Programacion Orientada a Objetos", "Calculo 1", "Calculo 2", "Algebra Lineal",
        "Fisica 1", "Estructuras de Datos", "Bases de Datos", "Quimica General",
        "Estadistica 1", "Redes de Computadoras"
    };

    private static final String[] NOMBRES_EVENTOS = {
        "Semana de Parciales", "Feria de Clubes", "Paro de Buses",
        "Dia de Puertas Abiertas", "Semana de Entregas"
    };

    private static final String[] DESCRIPCIONES_EVENTOS = {
        "Todos estudian y todo cuesta mas.",
        "Los clubes reparten energia a todos.",
        "Llegar al campus cuesta un poco mas.",
        "El campus se llena de buen ambiente.",
        "Las entregas se acumulan y el estres sube."
    };

    private static final int[] DURACIONES_EVENTOS = {2, 2, 2, 2, 2};

    private static final int[] MODIFICADORES_EVENTOS = {1, -1, 1, -1, 2};

    // Atributos
    private final Random random;
    private final Set<String> nombresUsados;

    // Constructores
    public FabricaCartas() {
        this.random = new Random();
        this.nombresUsados = new HashSet<>();
    }

    public FabricaCartas(long semilla) {
        this.random = new Random(semilla);
        this.nombresUsados = new HashSet<>();
    }

    // Generacion del catalogo
    public List<Carta> generarCatalogo() {
        return generarCatalogo(CANTIDAD_POR_DEFECTO);
    }

    public List<Carta> generarCatalogo(int cantidad) {
        int total = Math.max(MINIMO_CARTAS, cantidad);
        nombresUsados.clear();
        List<Carta> catalogo = new ArrayList<>();
        for (int id = 1; id <= total; id++) {
            int tipo = (id <= 4) ? id - 1 : random.nextInt(4);
            switch (tipo) {
                case 0:
                    catalogo.add(crearCatedratico(id));
                    break;
                case 1:
                    catalogo.add(crearCurso(id));
                    break;
                case 2:
                    catalogo.add(crearEvento(id));
                    break;
                default:
                    catalogo.add(crearCartaHumor(id));
                    break;
            }
        }
        Collections.shuffle(catalogo, random);
        return catalogo;
    }

    // Creacion por tipo
    private Catedratico crearCatedratico(int id) {
        String nombre = nombreUnico(NOMBRES_CATEDRATICOS[random.nextInt(NOMBRES_CATEDRATICOS.length)], id);
        String departamento = DEPARTAMENTOS[random.nextInt(DEPARTAMENTOS.length)];
        int tiempoAtencion = 1 + random.nextInt(2);
        switch (random.nextInt(4)) {
            case 0:
                return new CatedraticoEstricto(id, nombre, 2, "Exige mucho, pero su clase rinde.",
                        departamento, 1, tiempoAtencion, 1 + random.nextInt(3));
            case 1:
                return new CatedraticoTitular(id, nombre, 3, "Catedratico veterano con mano dura.",
                        departamento, 1, tiempoAtencion, 1 + random.nextInt(3), 5 + random.nextInt(16));
            case 2:
                return new CatedraticoFlexible(id, nombre, 2, "Siempre ofrece una prorroga.",
                        departamento, 0, tiempoAtencion, random.nextInt(2));
            default:
                return new CatedraticoAuxiliar(id, nombre, 1, "Auxiliar con horas de consulta extra.",
                        departamento, 0, tiempoAtencion, random.nextInt(2), 2 + random.nextInt(7));
        }
    }

    private Curso crearCurso(int id) {
        String nombre = nombreUnico(NOMBRES_CURSOS[random.nextInt(NOMBRES_CURSOS.length)], id);
        int creditos = 2 + random.nextInt(3);
        int dificultad = 1 + random.nextInt(5);
        return new Curso(id, nombre, creditos, "Curso de " + creditos + " creditos.", creditos, dificultad);
    }

    private EventoCampus crearEvento(int id) {
        int indice = random.nextInt(NOMBRES_EVENTOS.length);
        return new EventoCampus(id, nombreUnico(NOMBRES_EVENTOS[indice], id), 1,
                DESCRIPCIONES_EVENTOS[indice], DURACIONES_EVENTOS[indice], MODIFICADORES_EVENTOS[indice]);
    }

    private CartaHumor crearCartaHumor(int id) {
        switch (random.nextInt(3)) {
            case 0:
                return new Cafecito(id, nombreUnico("Cafecito de la Cafeteria", id), 1,
                        "Una taza que salva la tarde.",
                        "El codigo no compila, pero el cafe si.", 2 + random.nextInt(2));
            case 1:
                return new EntregaUltimoMinuto(id, nombreUnico("Entrega a las 11:59 pm", id), 0,
                        "Subes la tarea justo a tiempo.",
                        "La subi con tres segundos de sobra.", 1 + random.nextInt(2));
            default:
                return new LabNoCompila(id, nombreUnico("Lab que no compila", id), 1,
                        "Pierdes energia, pero aprendes depurando.",
                        "En mi maquina si funciona.", 1 + random.nextInt(2));
        }
    }

    // Nombres unicos
    private String nombreUnico(String base, int id) {
        if (nombresUsados.add(base)) {
            return base;
        }
        String alternativo = base + " #" + id;
        nombresUsados.add(alternativo);
        return alternativo;
    }
}
