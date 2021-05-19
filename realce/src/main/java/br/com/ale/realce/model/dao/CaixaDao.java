package br.com.ale.realce.model.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import br.com.ale.realce.model.entidade.Caixa;
import br.com.ale.realce.model.entidade.Usuario;
import br.com.ale.realce.model.tableView.ListaCaixaPorPeriodoTav;
import br.com.ale.realce.util.ObjectFactory;

public class CaixaDao extends Dao<Caixa> {

	public Caixa getCaixaUsuario(Usuario usuario, Date data) {

		hql = ObjectFactory.getStringBuilder();

		hql.append("from Caixa c");
		hql.append(" left join fetch c.usuario u");
		hql.append(" left join fetch c.historicosCaixa hc");
		hql.append(" where u.id = :idUsuario");
		hql.append(" and c.data = :data");

		try {
			TypedQuery<Caixa> query = banco.getManager().createQuery(hql.toString(), Caixa.class);

			query.setParameter("idUsuario", usuario.getId());
			query.setParameter("data", data);

			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	public List<ListaCaixaPorPeriodoTav> getCaixaPorPeriodo(Date inicio, Date termino) {

		hql = ObjectFactory.getStringBuilder();

		hql.append("select new  br.com.ale.realce.model.tableView.ListaCaixaPorPeriodoTav(");
		hql.append("cx.id, cx.data, cx.usuario.usuario, cx.saldo, (");
		hql.append("select count(p.id) from PedidoVenda p where p.data = cx.data and p.usuario = cx.usuario)");
		hql.append(")");
		hql.append(" from Caixa cx");
		hql.append(" where cx.data between :inicio and :termino");
		hql.append(" order by cx.data");
		try {
			TypedQuery<ListaCaixaPorPeriodoTav> query = banco.getManager().createQuery(hql.toString(),
					ListaCaixaPorPeriodoTav.class);

			query.setParameter("inicio", inicio);
			query.setParameter("termino", termino);

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Caixa getCaixa(Long id) {

		hql = ObjectFactory.getStringBuilder();
		hql.append("from Caixa c where c.id = :idCaixa");

		try {
			return banco.getManager().createQuery(hql.toString(), Caixa.class).setParameter("idCaixa", id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
