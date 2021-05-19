package br.com.ale.realce.model.dao;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import br.com.ale.realce.model.entidade.PedidoCompra;
import br.com.ale.realce.model.tableView.ListaPedidoCompraTav;
import br.com.ale.realce.util.ObjectFactory;

public class PedidoCompraDao extends Dao<PedidoCompra> {

    public PedidoCompraDao() {
        super();
    }


    public PedidoCompra getById(long id) {
        return banco.getManager().find(PedidoCompra.class, id);
    }

    public List<ListaPedidoCompraTav> getAll(Date inicio, Date termino) {

        hql = new StringBuilder();
        hql
                .append("select new br.com.ale.realce.model.tableView.ListaPedidoCompraTav (")
                .append(" pc.id, pc.data, pc.numero, pc.valorTotalPedido, ")
                .append(" (select count(ip.id) from ItemPedido ip where ip.pedido.id = pc.id)")
                .append(") from PedidoCompra pc")
                .append(" where pc.data between :inicio and :termino")
                //.append(" group by pc.id,pc.data,pc.valorTotalPedido")
                .append(" order by pc.data");
        try {
            TypedQuery<ListaPedidoCompraTav> query = banco.getManager().createQuery(hql.toString(), ListaPedidoCompraTav.class);

            query
                    .setParameter("inicio", inicio)
                    .setParameter("termino", termino);

            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public Long getProximoNumeroPedido() {
        hql = new StringBuilder();
        hql
                .append("select max(pc.numero) from PedidoCompra pc");
        try {
            TypedQuery<Long> query = banco.getManager().createQuery(hql.toString(), Long.class);
            Long numero = query.getSingleResult();
            if (numero == null) {
                return 1L;
            }
            return ++numero;
        } catch (Exception e) {
            return 1L;
        }
    }

    public boolean pedidoPossuiItensCadastrados() {

        hql = ObjectFactory.getStringBuilder();
        TypedQuery<Long> tq;

        hql.append("select count(p) from PedidoCompra p");
        hql.append(" where p.valorTotalPedido = :valor");

        try {
            tq = banco.getManager().createQuery(hql.toString(), Long.class);
            tq.setParameter("valor", new BigDecimal("0.0"));

            return tq.getSingleResult() > 0;

        } catch (Exception e) {
            return true;
        }
    }


}
