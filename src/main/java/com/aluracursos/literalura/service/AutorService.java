package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    // Registro de Autores en la DB

    public List<String> listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();

        // ordena y convierte.

        return autores.stream()
                .sorted((p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()))
                .map(Autor::toString)
                .collect(Collectors.toList());
    }

    // Validar tiempode vida del autor en año consultado.

    public List<Autor> getAutoresVivosEnAnio(int anio) {
        return autorRepository.findAll().stream()
                .filter(autor -> autor.getFechaDeNacimiento() != null && autor.getFechaDeNacimiento() <= anio)
                .filter(autor -> autor.getFechadeFallecimiento() == null || (autor.getFechadeFallecimiento() >= anio))
                .collect(Collectors.toList());
    }

    // Busca Autores vivos del año.

    public void listarAutoresVivosEnAnio(int anio) {
        List<Autor> autoresVivos = getAutoresVivosEnAnio(anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("\n/*/ No hay autores Vivos en este año: " + anio + ". /*/");
        } else {
            System.out.println("\nLos Autores vivos en el año ingresado son: " + anio + ":\n*******************************");
            autoresVivos.forEach(System.out::println);
        }
    }

    // Buscar Autores en el año

    public List<Autor> listarAutoresPorNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCase(nombre);
    }

}
