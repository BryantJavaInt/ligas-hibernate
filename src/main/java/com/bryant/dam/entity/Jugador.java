package com.bryant.dam.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Jugador")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name="posicion", nullable = false, length = 20)
    private String posicion;

    @Column(name="valor_mercado", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorMercado;

    @Column(name="goles", nullable = false)
    private Integer goles;

    @Column(name="nacionalidad", nullable = false, length = 5)
    private String nacionalidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_equipo", nullable = false)
    private Equipo equipo;

    public Jugador() {}

    public Jugador(String nombre, String posicion, BigDecimal valorMercado, Integer goles, String nacionalidad) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.valorMercado = valorMercado;
        this.goles = goles;
        this.nacionalidad = nacionalidad;
    }

    // Getters/Setters
    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }

    public BigDecimal getValorMercado() { return valorMercado; }
    public void setValorMercado(BigDecimal valorMercado) { this.valorMercado = valorMercado; }

    public Integer getGoles() { return goles; }
    public void setGoles(Integer goles) { this.goles = goles; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", posicion='" + posicion + '\'' +
                ", valorMercado=" + valorMercado +
                ", goles=" + goles +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", equipoId=" + (equipo != null ? equipo.getId() : null) +
                '}';
    }
}
