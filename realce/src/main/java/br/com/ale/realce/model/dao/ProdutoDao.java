package br.com.ale.realce.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import br.com.ale.realce.model.entidade.Produto;
import br.com.ale.realce.model.enuns.Status;
import br.com.ale.realce.model.tableView.ListaProdutoTav;
import br.com.ale.realce.model.tableView.ListaTabelaVendaTav;
import br.com.ale.realce.util.ObjectFactory;

public class ProdutoDao extends Dao<Produto> {

	public List<ListaProdutoTav> getListaProdutoTav(Status status, String filtro) {

		hql = new StringBuilder();

		hql.append("select new br.com.ale.realce.model.tableView.ListaProdutoTav(");
		hql.append("p.id, p.nome, p.codigoLeitura, p.atacado, p.categoria.nome, p.volume.volume");
		hql.append(") from Produto p");
		hql.append(" where p.status = :status");
		if (filtro != null) {
			hql.append(" and (upper(p.nome) like :filtro or upper(p.categoria.nome) like :filtro)");
		}
		hql.append(" order by p.categoria.nome, p.volume, p.nome");

		try {
			TypedQuery<ListaProdutoTav> query = banco.getManager().createQuery(hql.toString(), ListaProdutoTav.class);

			query.setParameter("status", status);
			if (filtro != null) {
				query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
			}

			return query.getResultList();

		} catch (Exception e) {
			return null;
		}
	}

	public List<ListaTabelaVendaTav> getListaTabelaVenda(String filtro) {
		hql = ObjectFactory.getStringBuilder();

		hql.append("select new br.com.ale.realce.model.tableView.ListaTabelaVendaTav(");
		hql.append("p.nome, p.atacado, p.categoria.nome, p.volume.volume, p.id, p.tabelaVenda.valorUnidade,");
		hql.append(" p.tabelaVenda.valorAtacado, p.margemLucro, p.tabelaCusto.valorSugerido)");
		hql.append(" from Produto p");
		hql.append(" where p.status = :status");
		if (filtro != null) {
			hql.append(" and (upper(p.nome) like :filtro or (p.categoria.nome) like :filtro)");
		}
		hql.append(" order by p.categoria.nome, p.volume.volume, p.nome");
		try {
			TypedQuery<ListaTabelaVendaTav> query = banco.getManager().createQuery(hql.toString(), ListaTabelaVendaTav.class);
			query.setParameter("status", Status.ATIVO);
			if (filtro != null) {
				query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
			}
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public Produto getProdutoPeloId(long id) {
		return banco.getManager().find(Produto.class, id);
	}

	public Integer getProximoCodigo(long idCategoria, long idVolume) {

		hql = new StringBuilder();

		hql.append("select max(p.codigo) from Produto p");
		hql.append(" where p.categoria.id = :idCategoria");
		hql.append(" and p.volume.id = :idVolume");

		try {
			int proximo = banco.getManager()
					.createQuery(hql.toString(), Integer.class)
					.setParameter("idCategoria", idCategoria)
					.setParameter("idVolume", idVolume)
					.getSingleResult();

			return ++proximo;

		} catch (Exception e) {
			return 1;
		}
	}

	public boolean ehCadastrado(Produto produto) {

		hql = new StringBuilder();

		hql.append("from Produto p");
		hql.append(" where upper(p.nome) = :nome");
		if (produto.getId() != null) {
			hql.append(" and p.id != :id");
		}

		try {

			TypedQuery<Produto> query =banco.getManager().createQuery(hql.toString(), Produto.class);

			query.setParameter("nome", produto.getNome().toUpperCase());
			if (produto.getId() != null) {
				query.setParameter("id", produto.getId());
			}

			return query.getSingleResult() != null;

		} catch (Exception e) {
			return false;
		}
	}

	public List<Produto> getAll(String filtro) {

		hql = new StringBuilder();

		hql.append("from Produto p");
		hql.append(" left join fetch p.categoria c");
		hql.append(" left join fetch p.volume v");
		hql.append(" left join fetch p.estoque e");
		hql.append(" left join fetch p.tabelaVenda tv");
		hql.append(" left join fetch p.tabelaCusto tc");
		hql.append(" where (upper(p.nome) like :filtro or upper(c.nome) like :filtro)");
		hql.append(" and p.status = :status ");
		hql.append(" order by c.nome,v.volume,p.nome");

		try {
			TypedQuery<Produto> query = banco.getManager().createQuery(hql.toString(), Produto.class);

			query.setParameter("status", Status.ATIVO);
			query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
			query.getResultList();

			return query.getResultList();

		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public Produto getProdutoPeloCodigo(String codigo) {

		hql = ObjectFactory.getStringBuilder();

		hql.append("from Produto p");
		hql.append(" left join fetch p.estoque et");
		hql.append(" left join fetch p.volume vl");
		hql.append(" left join fetch p.categoria ct");
		hql.append(" left join fetch p.tabelaVenda tv");
		hql.append(" left join fetch p.tabelaCusto tc");
		hql.append(" where p.codigoLeitura = :codigoLeitura");
		hql.append(" and p.status = :status");

		try {

			return banco.getManager().createQuery(hql.toString(), Produto.class)
					.setParameter("codigoLeitura", codigo.replace(",", ".")).setParameter("status", Status.ATIVO)
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

}
