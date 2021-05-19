package br.com.ale.realce.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ale.realce.model.entidade.Categoria;

public class CategoriaDao extends Dao<Categoria> {

	public List<Categoria> getAll() {
		hql = new StringBuilder();
		hql.append("from Categoria c order by c.nome");
	
		try {
			return banco.getManager()
					.createQuery(hql.toString(), Categoria.class)
					.getResultList();
			
		} catch (Exception e) {
			return new ArrayList<Categoria>();
		} 
	}

	public int getProximoCodigo() {

		hql = new StringBuilder();

		hql.append("select max(c.codigo) from Categoria c");
	
		try {
			int codigo = banco.getManager()
					.createQuery(hql.toString(), Integer.class)
					.getSingleResult();
			
			return ++codigo;
		} catch (Exception e) {
			return 1;
		}
	}

	public boolean ehCadastrado(Categoria categoria) {
		
		hql = new StringBuilder();
		hql.append("from Categoria c");
		hql.append(" where upper(c.nome) = '");
		hql.append(categoria.getNome().toUpperCase()).append("'");
		if(categoria.getId()!=null) {
			hql.append(" and c.id !=").append(categoria.getId());
		}

		try {
			return banco.getManager()
					.createQuery(hql.toString(),Categoria.class)
					.getSingleResult()!=null;
			
		} catch (Exception e) {
			return false;
		}
	}

}
