/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ale.realce.util;

import javax.persistence.EntityManager;

/**
 * @author mbs-6
 */
public class Banco {

    private static Banco banco = null;
    private static Conn conn = null;
    private static EntityManager em;

    private Banco() {
        Banco.conn = Conn.getInstance();
    }

    public static Banco getInstance() {
        if (banco == null) {
            banco = new Banco();
        }
        return banco;
    }

    public void abreConn() {
        em = conn.getEM();
    }

    public void fechaConn() {
        try {
            em.close();
        } finally {
            em = null;
        }
    }

    public void abreTransacao() {
        em = conn.getEM();
        em.getTransaction().begin();
    }

    public void fechaTransacao(boolean ok) {

        try {
            if (ok) {
                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            em = null;
        }
    }

    public EntityManager getManager(){
        return Banco.em;
    }

}
