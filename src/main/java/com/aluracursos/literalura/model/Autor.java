package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "Autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechadeFallecimiento;
    @OneToMany(mappedBy = "Autor", cascade = CascadeType.ALL)
    private List<Libro> libros;

    public Autor(){
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechadeFallecimiento = datosAutor.fechaDeFallecimiento();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
    }

    @Override
    public String toString() {
        String vida = (fechaDeNacimiento == null || fechadeFallecimiento == null)
                ? "No hay registro de fechas que mostrar."
                : fechaDeNacimiento + "-" + fechadeFallecimiento;
        return "(" + vida + ") " + nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechadeFallecimiento() {
        return fechadeFallecimiento;
    }

    public void setFechadeFallecimiento(Integer fechadeFallecimiento) {
        this.fechadeFallecimiento = fechadeFallecimiento;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void getFechaDeFallecimiento() {
    }
}
