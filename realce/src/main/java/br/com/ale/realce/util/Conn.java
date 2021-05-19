package br.com.ale.realce.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conn {

    private static EntityManagerFactory emf = null;
    private static Conn singleton = null;

    private Conn() {
        try {
            if (Conn.emf == null) {
                Conn.emf = Persistence.createEntityManagerFactory("realcePU");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Conn getInstance() {
        if (Conn.singleton == null) {
            Conn.singleton = new Conn();
        }
        return Conn.singleton;
    }

    public EntityManager getEM() {
        return Conn.emf.createEntityManager();
    }
}
