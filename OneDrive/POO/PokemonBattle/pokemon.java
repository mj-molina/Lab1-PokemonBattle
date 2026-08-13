import java.util.List;
import java.util.ArrayList;


// Organizacion Orientada a Objetos 
// Seccion 10
// Maria Jose Molina - 26785


// Clase principal
public class Main {
    public static void main(String[] args) {
        Controlador controlador = new Controlador("Sistema de Combate Pokemon");
        controlador.registrarEntrenador("Ash");
        controlador.registrarEntrenador("Misty");
        controlador.iniciarCombate();
    }
}

// Clase Pokemon
class Pokemon {
    private String nombre;
    private Tipo tipo;
    private int ataque;
    private int defensa;
    private Habilidad habilidad;

    public Pokemon(String nombre, Tipo tipo, int ataque, int defensa, Habilidad habilidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ataque = ataque;
        this.defensa = defensa;
        this.habilidad = habilidad;
    }

    public int calcularAtaque(Pokemon rival) {
        int total = ataque;
        total += tipo.compararVentaja(rival.tipo);
        if (habilidad.activar()) {
            total += habilidad.getEfecto();
        }
        return total;
    }

    public String getNombre() { return nombre; }
}

// Clase Tipo
class Tipo {
    private String nombre;

    public Tipo(String nombre) {
        this.nombre = nombre;
    }

    public int compararVentaja(Tipo rival) {
        if (nombre.equals("Fuego") && rival.nombre.equals("Planta")) return 20;
        if (nombre.equals("Planta") && rival.nombre.equals("Agua")) return 20;
        if (nombre.equals("Agua") && rival.nombre.equals("Fuego")) return 20;
        if (nombre.equals("Electrico") && rival.nombre.equals("Agua")) return 20;

        if (nombre.equals("Fuego") && rival.nombre.equals("Agua")) return -10;
        if (nombre.equals("Planta") && rival.nombre.equals("Fuego")) return -10;
        if (nombre.equals("Agua") && rival.nombre.equals("Planta")) return -10;
        if (nombre.equals("Agua") && rival.nombre.equals("Electrico")) return -10;

        return 0; 
    }
}

// Clase Habilidad
class Habilidad {
    private String nombre;
    private int efecto;
    private int probabilidad; 

    public Habilidad(String nombre, int efecto, int probabilidad) {
        this.nombre = nombre;
        this.efecto = efecto;
        this.probabilidad = probabilidad;
    }

    public boolean activar() {
        int numero = (int)(Math.random() * 100);
        return numero < probabilidad;
    }

    public int getEfecto() { return efecto; }
}

// Clase Entrenador
class Entrenador {
    private String nombre;
    private Pokemon[] pokemons = new Pokemon[4];
    private int contador = 0;

    public Entrenador(String nombre) {
        this.nombre = nombre;
    }

    public void agregarPokemon(Pokemon p) {
        if (contador < 4) {
            pokemons[contador] = p;
            contador++;
        }
    }

    public Pokemon elegirPokemon(int ronda) {
        return pokemons[ronda];
    }

    public String getNombre() { return nombre; }
}

// Clase PokemonDB
class PokemonDB {
    private java.util.List<Pokemon> pokemons = new java.util.ArrayList<>();

    public void agregarPokemon(Pokemon p) {
        pokemons.add(p);
    }

    public java.util.List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void mostrarDatos() {
        for (Pokemon p : pokemons) {
            System.out.println("Pokemon: " + p.getNombre());
        }
    }
}

// Clase ControladorCombate
class ControladorCombate {
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private int ronda = 0;

    public ControladorCombate(Entrenador e1, Entrenador e2) {
        this.entrenador1 = e1;
        this.entrenador2 = e2;
    }

    public void loopCombate() {
        int victorias1 = 0, victorias2 = 0;

        for (ronda = 0; ronda < 4; ronda++) {
            Pokemon p1 = entrenador1.elegirPokemon(ronda);
            Pokemon p2 = entrenador2.elegirPokemon(ronda);

            int ataque1 = p1.calcularAtaque(p2);
            int ataque2 = p2.calcularAtaque(p1);

            System.out.println("Ronda " + (ronda+1) + ": " + p1.getNombre() + " vs " + p2.getNombre());

            if (ataque1 > ataque2) {
                victorias1++;
                System.out.println(entrenador1.getNombre() + " gana la ronda.");
            } else if (ataque2 > ataque1) {
                victorias2++;
                System.out.println(entrenador2.getNombre() + " gana la ronda.");
            } else {
                System.out.println("Empate en la ronda.");
            }
        }

        if (victorias1 > victorias2) {
            System.out.println("Ganador final: " + entrenador1.getNombre());
        } else if (victorias2 > victorias1) {
            System.out.println("Ganador final: " + entrenador2.getNombre());
        } else {
            System.out.println("El combate termina en empate.");
        }
    }
}

// Clase Controlador
class Controlador {
    private String nombre;
    private Entrenador entrenador1;
    private Entrenador entrenador2;

    public Controlador(String nombre) {
        this.nombre = nombre;
    }

    public void registrarEntrenador(String nombre) {
        if (entrenador1 == null) {
            entrenador1 = new Entrenador(nombre);
            // ejemplo: agregar pokemons
            entrenador1.agregarPokemon(new Pokemon("Charmander", new Tipo("Fuego"), 50, 30, new Habilidad("Llamarada", 15, 30)));
            entrenador1.agregarPokemon(new Pokemon("Bulbasaur", new Tipo("Planta"), 40, 40, new Habilidad("Latigazo", 10, 25)));
            entrenador1.agregarPokemon(new Pokemon("Squirtle", new Tipo("Agua"), 45, 35, new Habilidad("Burbuja", 12, 20)));
            entrenador1.agregarPokemon(new Pokemon("Pikachu", new Tipo("Electrico"), 55, 25, new Habilidad("Impactrueno", 20, 35)));
        } else {
            entrenador2 = new Entrenador(nombre);
            entrenador2.agregarPokemon(new Pokemon("Charmander", new Tipo("Fuego"), 50, 30, new Habilidad("Llamarada", 15, 30)));
            entrenador2.agregarPokemon(new Pokemon("Bulbasaur", new Tipo("Planta"), 40, 40, new Habilidad("Latigazo", 10, 25)));
            entrenador2.agregarPokemon(new Pokemon("Squirtle", new Tipo("Agua"), 45, 35, new Habilidad("Burbuja", 12, 20)));
            entrenador2.agregarPokemon(new Pokemon("Pikachu", new Tipo("Electrico"), 55, 25, new Habilidad("Impactrueno", 20, 35)));
        }
    }

    public void iniciarCombate() {
        if (entrenador1 != null && entrenador2 != null) {
            ControladorCombate combate = new ControladorCombate(entrenador1, entrenador2);
            combate.loopCombate();
        } else {
            System.out.println("No hay suficientes entrenadores registrados.");
        }
    }
}
