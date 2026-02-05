package com.bryant.dam.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Ligas")
public class Liga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_liga", nullable = false, length = 100)
    private String nombreLiga;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @OneToMany(mappedBy = "liga", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipo> equipos = new ArrayList<>();

    public Liga() {}

    public Liga(String nombreLiga, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombreLiga = nombreLiga;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Helpers
    public void addEquipo(Equipo equipo) {
        equipos.add(equipo);
        equipo.setLiga(this);
    }

    public void removeEquipo(Equipo equipo) {
        equipos.remove(equipo);
        equipo.setLiga(null);
    }

    // Getters/Setters
    public Long getId() { return id; }

    public String getNombreLiga() { return nombreLiga; }
    public void setNombreLiga(String nombreLiga) { this.nombreLiga = nombreLiga; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public List<Equipo> getEquipos() { return equipos; }
    public void setEquipos(List<Equipo> equipos) { this.equipos = equipos; }

    @Override
    public String toString() {
        return "Liga{" +
                "id=" + id +
                ", nombreLiga='" + nombreLiga + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
