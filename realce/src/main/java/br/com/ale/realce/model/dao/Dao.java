package br.com.ale.realce.model.dao;

import br.com.ale.realce.util.Banco;

public abstract class Dao<T> {

    protected final Banco banco = Banco.getInstance();
    protected StringBuilder hql;

    public void salvar(T t) {
        banco.getManager().persist(t);
    }

    public void editar(T t) {
        banco.getManager().merge(t);
    }

    public void delete(T t) {
        banco.getManager().remove(t);
    }

    public T getById(Long id,Class<T> classe){
        return banco.getManager().find(classe,id);
    }

}
