package com.bryant.dam;

import com.bryant.dam.entity.*;
import com.bryant.dam.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Long ligaId;
        Long equipoAId;
        Long equipoBId;

        // 1) Crea 1 liga
        // 2) Crea 3 equipos y asócialos a la liga
        // 3) Crea 6 jugadores y asócialos a los equipos
        // 4) Crea 3 entrenadores y asócialos a un equipo
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Liga liga = new Liga("Liga DAM", LocalDate.of(2026, 2, 1), LocalDate.of(2026, 12, 31));
            session.persist(liga);

            Equipo eq1 = new Equipo("Tigres", "Madrid");
            Equipo eq2 = new Equipo("Halcones", "Valencia");
            Equipo eq3 = new Equipo("Lobos", "Sevilla");

            eq1.setLiga(liga);
            eq2.setLiga(liga);
            eq3.setLiga(liga);

            session.persist(eq1);
            session.persist(eq2);
            session.persist(eq3);

            // 6 jugadores
            Jugador j1 = new Jugador("Juan", "DC", new BigDecimal("1200000.00"), 10, "ES");
            Jugador j2 = new Jugador("Pedro", "MC", new BigDecimal("900000.00"), 4, "ES");
            Jugador j3 = new Jugador("Lucas", "DF", new BigDecimal("700000.00"), 1, "AR");
            Jugador j4 = new Jugador("Marco", "DC", new BigDecimal("1500000.00"), 12, "IT");
            Jugador j5 = new Jugador("Andre", "PT", new BigDecimal("800000.00"), 0, "PT");
            Jugador j6 = new Jugador("Khalid", "EI", new BigDecimal("1100000.00"), 6, "MA");

            // Asignación a equipos (3 + 2 + 1)
            j1.setEquipo(eq1);
            j2.setEquipo(eq1);
            j3.setEquipo(eq1);

            j4.setEquipo(eq2);
            j5.setEquipo(eq2);

            j6.setEquipo(eq3);

            session.persist(j1);
            session.persist(j2);
            session.persist(j3);
            session.persist(j4);
            session.persist(j5);
            session.persist(j6);

            // 3 entrenadores asociados a equipos
            Entrenador e1 = new Entrenador("Mister A", 8, 2);
            Entrenador e2 = new Entrenador("Mister B", 7, 1);
            Entrenador e3 = new Entrenador("Mister C", 9, 4);

            // Asociación 1 entrenador por equipo
            e1.setEquipo(eq1);
            e2.setEquipo(eq2);
            e3.setEquipo(eq3);

            session.persist(e1);
            session.persist(e2);
            session.persist(e3);

            tx.commit();

            ligaId = liga.getId();
            equipoAId = eq1.getId();
            equipoBId = eq2.getId();

            System.out.println("\n✅ Datos iniciales creados");
        }

        // 5) Ficha un par de jugadores para otro equipo (mover 2 del eq1 al eq2)
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            List<Jugador> dosJugadores = session.createQuery(
                            "select j from Jugador j where j.equipo.id = :idEq", Jugador.class)
                    .setParameter("idEq", equipoAId)
                    .setMaxResults(2)
                    .list();

            Equipo equipoDestino = session.get(Equipo.class, equipoBId);

            for (Jugador j : dosJugadores) {
                j.setEquipo(equipoDestino);
                session.merge(j);
            }

            tx.commit();
            System.out.println("\n✅ Fichajes hechos: 2 jugadores movidos al equipo " + equipoDestino.getNombreEquipo());
        }

        // 6) Muestra datos de todos los equipos
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Equipo> equipos = session.createQuery("from Equipo", Equipo.class).list();
            System.out.println("\n📌 Todos los equipos:");
            equipos.forEach(System.out::println);
        }

        // 7) Muestra los jugadores de un equipo (equipoB)
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Jugador> jugadores = session.createQuery(
                            "select j from Jugador j where j.equipo.id = :idEq", Jugador.class)
                    .setParameter("idEq", equipoBId)
                    .list();

            System.out.println("\n📌 Jugadores del equipo id=" + equipoBId + ":");
            jugadores.forEach(System.out::println);
        }

        // 8) Muestra los equipos de una liga
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Equipo> equiposLiga = session.createQuery(
                            "select e from Equipo e where e.liga.id = :idLiga", Equipo.class)
                    .setParameter("idLiga", ligaId)
                    .list();

            System.out.println("\n📌 Equipos de la liga id=" + ligaId + ":");
            equiposLiga.forEach(System.out::println);
        }

        // 9) Muestra todos los entrenadores de los equipos de una liga
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Entrenador> entrenadores = session.createQuery(
                            "select en from Entrenador en where en.equipo.liga.id = :idLiga", Entrenador.class)
                    .setParameter("idLiga", ligaId)
                    .list();

            System.out.println("\n📌 Entrenadores de la liga id=" + ligaId + ":");
            entrenadores.forEach(System.out::println);
        }

        System.out.println("\n✅ FIN (cumple el enunciado).");
    }
}
