package com.bryant.dam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Entrenador")
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name="calificacion", nullable = false)
    private Integer calificacion;

    @Column(name="titulos", nullable = false)
    private Integer titulos;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_equipo", unique = true)
    private Equipo equipo;

    public Entrenador() {}

    public Entrenador(String nombre, Integer calificacion, Integer titulos) {
        this.nombre = nombre;
        this.calificacion = calificacion;
        this.titulos = titulos;
    }

    // Getters/Setters
    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getCalificacion() { return calificacion; }
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }

    public Integer getTitulos() { return titulos; }
    public void setTitulos(Integer titulos) { this.titulos = titulos; }

    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }

    @Override
    public String toString() {
        return "Entrenador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", calificacion=" + calificacion +
                ", titulos=" + titulos +
                ", equipoId=" + (equipo != null ? equipo.getId() : null) +
                '}';
    }
}
