package br.com.ale.realce.model.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.TypedQuery;

import br.com.ale.realce.model.comboBox.UsuarioCB;
import br.com.ale.realce.model.entidade.Usuario;
import br.com.ale.realce.model.enuns.Privilegio;
import br.com.ale.realce.util.ObjectFactory;

public class UsuarioDao extends Dao<Usuario> {

	public boolean ehCadastrado(Usuario usuario) throws Exception {

		boolean ehCadastrado = usuario.getId() != null;
		boolean negarCadastro = false;

		hql = new StringBuilder();
		hql.append("from Usuario u where upper(u.usuario) = :usuario");
		if (ehCadastrado) {
			hql.append(" and u.id != :id");
		}

		try {
			TypedQuery<Usuario> query = banco.getManager().createQuery(hql.toString(), Usuario.class);

			query.setParameter("usuario", usuario.getUsuario().toUpperCase());
			if (ehCadastrado) {
				query.setParameter("id", usuario.getId());
			}

			if (query.getSingleResult() != null) {
				negarCadastro = true;
			}

		} catch (Exception e) {
			negarCadastro = false;
		}

		return negarCadastro;
	}

	public Usuario getByUsuario(String usuario) {

		try {
			return banco.getManager().createQuery("from Usuario u where u.usuario = :usuario", Usuario.class)
					.setParameter("usuario", usuario).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Usuario> getAll(String filtro) {

		hql = new StringBuilder();

		hql.append("from Usuario u");
		if (!filtro.isEmpty()) {
			hql.append(" where upper(u.usuario) like :filtro");
		}
		hql.append(" order by u.usuario");

		try {
			TypedQuery<Usuario> query = banco.getManager().createQuery(hql.toString(), Usuario.class);

			if (!filtro.isEmpty()) {
				query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
			}

			return query.getResultList();

		} catch (Exception e) {
			return new ArrayList<Usuario>();
		}
	}

	/**
	 * Lista todos usuários ativos no sistema
	 * 
	 * @return
	 */
	public List<UsuarioCB> getAll() {

		hql = ObjectFactory.getStringBuilder();

		hql.append("select new br.com.ale.realce.model.comboBox.UsuarioCB(");
		hql.append(" u.id, u.usuario");
		hql.append(") from Usuario u");
		hql.append(" where u.privilegio in (:privilegios)");

		try {
			TypedQuery<UsuarioCB> query = banco.getManager().createQuery(hql.toString(), UsuarioCB.class);

			query.setParameter("privilegios", Arrays.asList(Privilegio.ADMIN, Privilegio.USUARIO));

			return query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
