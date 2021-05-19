package br.com.ale.realce.model.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import br.com.ale.realce.model.entidade.PedidoVenda;
import br.com.ale.realce.model.entidade.Usuario;
import br.com.ale.realce.model.tableView.ListaPedidoVendaTav;

public class PedidoVendaDao extends Dao<PedidoVenda> {

    public PedidoVendaDao() {
        super();
    }

    public List<ListaPedidoVendaTav> getAll(Date data, Usuario usuario) {

        hql = new StringBuilder();

        hql.append("select new br.com.ale.realce.model.tableView.ListaPedidoVendaTav(")
                .append(" pv.id, pv.numero, pv.descricao, pv.valorTotalPedido)")
                .append(" from PedidoVenda pv")
                .append(" where pv.data = :data")
                .append(" and pv.usuario.id = :idUsuario")
                .append(" order by pv.data desc");

        try {
            TypedQuery<ListaPedidoVendaTav> query = banco.getManager().createQuery(hql.toString(), ListaPedidoVendaTav.class);

            query.setParameter("data", data)
                    .setParameter("idUsuario", usuario.getId());

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }


    public Long getProximoNumeroPedido() {

        hql = new StringBuilder();
        hql.append("select max(pv.numero) from PedidoVenda pv");
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

	public PedidoVenda getById(long idPedido) {

		try {
			return banco.getManager().find(PedidoVenda.class, idPedido);
		} catch (Exception e) {
			return null;
		}
	}
}
