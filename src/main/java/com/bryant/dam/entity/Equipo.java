package com.bryant.dam.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre_equipo", nullable = false, length = 100)
    private String nombreEquipo;

    @Column(name="ciudad", nullable = false, length = 80)
    private String ciudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_liga", nullable = false)
    private Liga liga;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Jugador> jugadores = new ArrayList<>();


    @OneToOne(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Entrenador entrenador;

    public Equipo() {}

    public Equipo(String nombreEquipo, String ciudad) {
        this.nombreEquipo = nombreEquipo;
        this.ciudad = ciudad;
    }

    // Helpers
    public void addJugador(Jugador jugador) {
        jugadores.add(jugador);
        jugador.setEquipo(this);
    }

    public void removeJugador(Jugador jugador) {
        jugadores.remove(jugador);
        jugador.setEquipo(null);
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
        if (entrenador != null) {
            entrenador.setEquipo(this);
        }
    }

    // Getters/Setters
    public Long getId() { return id; }

    public String getNombreEquipo() { return nombreEquipo; }
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public Liga getLiga() { return liga; }
    public void setLiga(Liga liga) { this.liga = liga; }

    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }

    public Entrenador getEntrenador() { return entrenador; }

    @Override
    public String toString() {
        return "Equipo{" +
                "id=" + id +
                ", nombreEquipo='" + nombreEquipo + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", ligaId=" + (liga != null ? liga.getId() : null) +
                '}';
    }
}
