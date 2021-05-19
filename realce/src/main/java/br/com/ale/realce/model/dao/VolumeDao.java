package br.com.ale.realce.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ale.realce.model.entidade.Volume;

public class VolumeDao extends Dao<Volume> {

	public List<Volume> getAll() {

		hql = new StringBuilder();

		hql.append("from Volume v order by v.volume");

		try {
			return banco.getManager()
					.createQuery(hql.toString(), Volume.class)
					.getResultList();

		} catch (Exception e) {
			return new ArrayList<Volume>();
		}
	}

	public Integer getProximoCodigo() {

		hql = new StringBuilder();
		hql.append("select max(v.codigo) from Volume v");

		try {

			int proximo = banco.getManager()
					.createQuery(hql.toString(), Integer.class)
					.getSingleResult();

			return ++proximo;
		} catch (Exception e) {
			return 1;
		}
	}

	public boolean ehCadastrado(Volume volume) {

		hql = new StringBuilder();

		hql.append("from Volume v");
		hql.append(" where v.volume = ").append(volume.getVolume());
		if (volume.getId() != null) {
			hql.append(" and v.id != ").append(volume.getId());
		}

		try {
			return banco.getManager()
					.createQuery(hql.toString(), Volume.class)
					.getSingleResult() != null;

		} catch (Exception e) {
			return false;
		}
	}

}
