    package com.aluracursos.literalura.service;

    import com.aluracursos.literalura.model.*;
    import com.aluracursos.literalura.repository.LibrosRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.text.Normalizer;
    import java.util.ArrayList;
    import java.util.Comparator;
    import java.util.List;
    import java.util.Optional;
    import java.util.regex.Pattern;

    @Service
    public class LibroService {

        @Autowired
        private LibrosRepository librosRepository;

        @Autowired
        private ConvierteDatos conversor;

        // Buscar libro con metodo
        public void buscarLibroPorTitulo(String tituloLibro, String json) {
            try {
                Datos datos = conversor.obtenerDatos(json, Datos.class);
                Optional<DatosLibros> libroBuscado = datos.resultados().stream()
                        .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                        .findFirst();
                if (libroBuscado.isPresent()) {
                    DatosLibros datosLibros = libroBuscado.get();
                    Libro libro = new Libro(datosLibros);
                    System.out.println("\nInformación del libro:\n───────────────\n" + libro.toString());
                    librosRepository.save(libro);
                    System.out.println("\n /*/ Se guardó la busqueda en la DB /*/");
                } else {
                    System.out.println("\n /*/ El libro no Existe en nuestra base de datos, intente nuevamente /*/");
                }
            } catch (Exception e) {
                System.err.println("¡OCURRIÓ UN ERROR!: " + e.getMessage());
            }
        }

        // Quitar acentosen Idioma
        public static String eliminarTildes(String input) {
            String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(normalized).replaceAll("");
        }

        // Buscar libros por idioma :)

        public List<Libro> listarLibrosPorIdioma(Idiomas idioma) {
            List<Libro> libros = librosRepository.findByIdioma(idioma);
            if (libros.isEmpty()) {
                System.out.println("/*/ No se encontró libros en ese idioma, intente con otro " + idioma);
            } else {
                System.out.println("\nLos libros en el idioma buscados son: " + idioma + ":\n──────────────────────────────");
                libros.forEach(System.out::println);
            }
            return libros;
        }

        //Top 5

        public List<Libro> obtenerTop5LibrosMasDescargados(List<Libro> libros) {

            libros.sort(Comparator.comparingInt(Libro::getNumeroDescargas).reversed());
            return libros.subList(0, Math.min(libros.size(), 5));
        }


    }