package com.example.gestorjuegos.domain.juego;

import com.example.gestorjuegos.domain.DAO;
import com.example.gestorjuegos.domain.HibernateUtil;
import com.example.gestorjuegos.domain.usuario.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static com.example.gestorjuegos.domain.juego.Game.merge;

public class GameDAO implements DAO<Game> {

    public static final HashMap<String, String> QUERY_ATTR;  //Hacemos un mapa para hacer la consulta a los atributos

    static {
        QUERY_ATTR = new HashMap<>();
        QUERY_ATTR.put("studio", "select distinct(g.studio) from Game g");
        QUERY_ATTR.put("enterprise", "select distinct(g.enterprise) from Game g");
        QUERY_ATTR.put("plataform", "select distinct(g.plataform) from Game g");
        QUERY_ATTR.put("gameStatus", "select distinct(g.gameStatus) from Game g");
        QUERY_ATTR.put("category", "select distinct(g.category) from Game g");
        QUERY_ATTR.put("format", "select distinct(g.format) from Game g");


    }

    @Override
    public ArrayList<Game> getAll() {
        return null;
    }

   /* public ArrayList<Game> getAllFromUser(User u) {
        ArrayList<Game> results = new ArrayList<>(0);

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Query<Game> q = s.createQuery("from Game where usuarioId=:userId",Game.class);
            q.setParameter("userId",u.getId());
            results = (ArrayList<Game>) q.getResultList();
        }
        return results;
    }*/

    @Override
    public Game get(Long id) {
        return null;
    }

    @Override
    public Game save(Game data) {

        Game salida = null;

        try (org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {

            Transaction t = s.beginTransaction(); // Inicio transaccion

            s.persist(data);//Persist hace que un juegoe ste sincroniado con la base de datos



            t.commit();

            salida = data;
        } catch (Exception ex) {

            System.out.println("Error al guardar el juego");
        }
        return data;
    }

    @Override
    public void update(Game data) {

        try (org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {

            Transaction t = s.beginTransaction(); // Inicio transaccion

            Game g = s.get(Game.class, data.getId());

            Game.merge(data, g); //Hemos refactorizado en merge en la clase GAME


            t.commit();

        }
    }


    @Override
    public void delete(Game data) {

        HibernateUtil.getSessionFactory().inTransaction((session) -> { //Accedemos a la base de datos

            Game g = session.get(Game.class, data.getId());
            session.remove(g);

        });

    }

    public List<String> getCategories() { //Sacamos todas las categorias de los juegos
        ArrayList<String> results = new ArrayList<>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> q = s.createQuery("select distinct(g.category) from Game g", String.class);//Hacemos con Game g una referencia para hacer una consulta
            results = (ArrayList<String>) q.getResultList();
        }
        return results;

    }

    public List<String> getDistinctAttribute(String attr) {

        ArrayList<String> results = new ArrayList<>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> q = s.createQuery(QUERY_ATTR.get(attr), String.class);
            results = (ArrayList<String>) q.getResultList();
        }
        return results;

    }

    public List<String> getStudio() {
        ArrayList<String> results = new ArrayList<>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> q = s.createQuery("select distinct(g.studio) from Game g", String.class);//Hacemos con Game g una referencia para hacer una consulta
            results = (ArrayList<String>) q.getResultList();
        }
        return results;

    }

    public List<String> getEnterprise() {
        ArrayList<String> results = new ArrayList<>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> q = s.createQuery("select distinct(g.enterprise) from Game g", String.class);
            results = (ArrayList<String>) q.getResultList();
        }
        return results;

    }

    public List<String> getFormat() {
        ArrayList<String> results = new ArrayList<>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> q = s.createQuery("select distinct(g.format) from Game g", String.class);
            results = (ArrayList<String>) q.getResultList();
        }
        return results;

    }

    public List<String> getPlatform() {
        ArrayList<String> results = new ArrayList<>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> q = s.createQuery("select distinct(g.plataform) from Game g", String.class);
            results = (ArrayList<String>) q.getResultList();
        }
        return results;

    }

    public List<String> getGameStatus() {
        ArrayList<String> results = new ArrayList<>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> q = s.createQuery("select distinct(g.gameStatus) from Game g", String.class);
            results = (ArrayList<String>) q.getResultList();
        }
        return results;

    }

    public List<String> getBoxStatus() {
        ArrayList<String> results = new ArrayList<>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> q = s.createQuery("select distinct(g.boxStatus) from Game g", String.class);
            results = (ArrayList<String>) q.getResultList();
        }
        return results;

    }
}
