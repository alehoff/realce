package br.com.ale.realce.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import br.com.ale.realce.model.entidade.Estoque;
import br.com.ale.realce.model.enuns.Status;
import br.com.ale.realce.model.tableView.ListaEstoqueTav;
import br.com.ale.realce.util.ObjectFactory;

public class EstoqueDao extends Dao<Estoque> {

    public List<ListaEstoqueTav> getAll(String filtro) {

        hql = new StringBuilder();
        hql.append("select new br.com.ale.realce.model.tableView.ListaEstoqueTav(")
                .append(" p.nome, p.atacado, p.categoria.nome, p.volume.volume, ")
                .append(" p.estoque.id, p.estoque.quantidade, p.estoque.minimo, p.estoque.comprar)")
                .append(" from Produto p")
                .append(" where p.status = :status");
        if (!filtro.isEmpty()) {
            hql.append(" and (lower(p.nome) like :filtro or lower(p.categoria.nome) like :filtro)");
        }
        hql.append(" order by p.categoria.nome, p.volume,p.nome");

        try {
            TypedQuery<ListaEstoqueTav> tp = banco.getManager().createQuery(hql.toString(),ListaEstoqueTav.class);
            tp.setParameter("status",Status.ATIVO);
            if(!filtro.isEmpty()){
                tp.setParameter("filtro","%"+filtro.toLowerCase()+"%");
            }
            return tp.getResultList();

        } catch (Exception e) {
            return new ArrayList<ListaEstoqueTav>();
        }
    }

    public Long getTotalRegistros(String filtro) {

        boolean filtrar = !filtro.isEmpty();
        hql = new StringBuilder();

        hql.append("select count(e) from Estoque e");
        hql.append(" where e.produto.status = :status");
        if (filtrar) {
            hql.append(" and upper(e.produto.nome) like :filtro");
            hql.append(" or upper(e.produto.categoria.nome) like :filtro");
        }

        try {
            TypedQuery<Long> qr = banco.getManager().createQuery(hql.toString(), Long.class);
            qr.setParameter("status", Status.ATIVO);
            if (filtrar) {
                qr.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
            }
            return qr.getSingleResult();
        } catch (Exception e) {
            return 0L;
        }
    }

    public List<Estoque> getAll(String filtro, int maximoRegistros, int indice) {

        boolean filtrar = !filtro.isEmpty();
        hql = new StringBuilder();

        hql.append("from Estoque e");
        hql.append(" left join fetch e.produto p");
        hql.append(" left join fetch p.categoria c");
        hql.append(" left join fetch p.volume v");
        hql.append(" left join fetch p.tabelaVenda tv");
        hql.append(" left join fetch p.tabelaCusto tc");
        hql.append(" where p.status = :status");
        if (filtrar) {
            hql.append(" and upper(p.nome) like :filtro");
            hql.append(" or upper(c.nome) like :filtro");
        }

        try {
            TypedQuery<Estoque> qr = banco.getManager().createQuery(hql.toString(), Estoque.class);
            qr.setParameter("status", Status.ATIVO);
            if (filtrar) {
                qr.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
            }
            qr.setFirstResult(indice);
            qr.setMaxResults(maximoRegistros);

            return qr.getResultList();

        } catch (Exception e) {
            return new ArrayList<Estoque>();
        }
    }

    public Integer getMaiorVendaProduto(long idProduto) {

        hql = ObjectFactory.getStringBuilder();

        hql.append("select max(ip.quantidade) from ItemPedidoVenda ip");
        hql.append(" where ip.produto.id = :idProduto");

        try {
            return banco.getManager().createQuery(hql.toString(), Integer.class).setParameter("idProduto", idProduto)
                    .getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    public Integer getMenorVendaProduto(long idProduto) {

        hql = ObjectFactory.getStringBuilder();

        hql.append("select min(ip.quantidade) from ItemPedidoVenda ip");
        hql.append(" where ip.produto.id = :idProduto");

        try {
            return banco.getManager().createQuery(hql.toString(), Integer.class).setParameter("idProduto", idProduto)
                    .getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    public Double getMediarVendaProduto(long idProduto) {

        hql = ObjectFactory.getStringBuilder();

        hql.append("select avg(ip.quantidade) from ItemPedidoVenda ip");
        hql.append(" where ip.produto.id = :idProduto");

        try {
            return banco.getManager().createQuery(hql.toString(), Double.class).setParameter("idProduto", idProduto)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0D;
        }
    }

}
