package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.*;
import com.aluracursos.literalura.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {
    private Scanner teclado;
    private ConsumoAPI consumoAPI;
    private ConvierteDatos conversor;
    private static final String URL_BASE = "https://gutendex.com/books/";
    private List<Libro> libro;

    //inyección de dependencias

    @Autowired
    private LibrosRepository librosRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private EstadisticasService estadisticasService;



    @PostConstruct
    public void init() {
        teclado = new Scanner(System.in);
        consumoAPI = new ConsumoAPI();
        conversor = new ConvierteDatos();
    }

    // Creando Menú.

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \nSeleccione una opcion de la lista:\n
                    1 - Busca el libro por título.
                    2 - Lista los libros registrados.
                    3 - Lista los autores registrados.
                    4 - Lista los autores vivos en un determinado año.
                    5 - Lista los libros por idioma.
                    6 - Lista los libros por título.
                    7 - Lista los autores por nombre.
                    8 - Busca los 5 libros más descargados.
                    9 - Muestra las estadisticas de la base de datos.

                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnDeterminadoAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    listarLibrosPorTitulo();
                    break;
                case 7:
                    listarAutoresPorNombre();
                    break;
                case 8:
                    buscarTop5LibrosDescargados();
                    break;
                case 9:
                    mostrarEstadisticas();
                    break;
                case 0:
                    System.out.println("\n\nCerrando la APP.\n\n");
                    break;
                default:
                    System.out.println("La opción seleccionada es incorrecta.");
            }
        }
    }

    // Buscar por este metodo un libro.

    public void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar: ");
        String tituloLibro = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+").toLowerCase());

        libroService.buscarLibroPorTitulo(tituloLibro, json);
    }

    //lista de libros
    private void listarLibrosRegistrados() {

        // Traer los libros y almacenarlos en la DB.

        List<Libro> libros = librosRepository.findAll();
        libros.forEach(System.out::println);
    }

    //Poner autores de la DB.

    private void listarAutoresRegistrados() {

        // Trae y ordena la lista de la DB.

        List<String> sortedAutores = autorService.listarAutoresRegistrados();

        System.out.println("\nLista de Autores Registrados :\n──────────────────────────────");
        sortedAutores.forEach(System.out::println);
    }

    //lista de Autores Vivos DB.

    private void listarAutoresVivosEnDeterminadoAnio() {
        System.out.println("Aqui encontrará los Autores vivos en el año de consulta." +
                "\n¿Año a buscar con Autores Vivos?");

        int anio = teclado.nextInt();

        // Lista los autores vivos solicitados.

        autorService.listarAutoresVivosEnAnio(anio);
    }

    //Libros por Idioma en lista.

    private void listarLibrosPorIdioma() {
        System.out.println("En esta opción podrá buscar libros escritos en un determinado idioma. \n" +
                "¿En qué idioma desea buscar?");
        String idiomaStr = teclado.nextLine().toLowerCase(); // Convertir a minúsculas

        // Quita los acentos para evitar errores de escritura

        idiomaStr = LibroService.eliminarTildes(idiomaStr);

        // Español= CASTELLANO.

        if ("español".equalsIgnoreCase(idiomaStr)) {
            idiomaStr = "CASTELLANO";
        }

        try {
            Idiomas idioma = Idiomas.valueOf(idiomaStr.toUpperCase());
            libroService.listarLibrosPorIdioma(idioma);
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma no existe, verifique idioma seleccionado.");
        }
    }

    //Lista por título.

    private void listarLibrosPorTitulo() {
        System.out.println("Aquí podrá buscar su libro por Título. \n" +
                "¿Cual es el título del libro en su busqueda?");
        String titulo = teclado.nextLine();
        List<Libro> libro = librosRepository.findByTituloContainingIgnoreCase(titulo);

        if (libro.isEmpty()) {
            System.out.println("Lo siento, no encontré el libro que busca, intente con otro: " + titulo);
        } else {
            System.out.println("\n¡Eureka!, aquí está el libro que busca:\n────────────────");
            libro.forEach(System.out::println);
        }
    }

    //Autores por nombre.

    private void listarAutoresPorNombre() {
        System.out.println("Aquí encontrará los Autores por su nombre. \n" +
                "Escriba el nombre o apellido del Autor");

        String nombreAutor = teclado.nextLine();

        List<Autor> autores = autorService.listarAutoresPorNombre(nombreAutor);

        // lista vacia .

        if (autores.isEmpty()) {
            System.out.println("Lo siento, no encontré el autor, Intente con el seudónimo : " + nombreAutor);
        } else {
            System.out.println("\n¡EureKa:! Aquí está su Autor\n────────────────");
            autores.forEach(System.out::println);
        }
    }

    //Top 5

    private void buscarTop5LibrosDescargados() {

        // llamar la lista.

        List<Libro> libros = librosRepository.findAll();

        // Obtengo el top 5.

        List<Libro> top5Libros = libroService.obtenerTop5LibrosMasDescargados(libros);

        System.out.println("\n" +
                "Los cinco libros más descargados .\n" +
                "CANTIDAD     TITULOS\n" +
                "VECES        MAS DESCARGADOS\n" +
                "────────     ───────────");

        // Mostrar la lista

        top5Libros.forEach(libro -> System.out.println(libro.getNumeroDescargas() + "        " + libro.getTitulo().toUpperCase()));
    }

    //Estadisticas

    private void mostrarEstadisticas() {

        // Llama al servicio de estadísticas para mostrar las estadísticas de la base de datos

        estadisticasService.mostrarEstadisticas();
    }
}


