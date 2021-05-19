package br.com.ale.realce.model.tableView;

import br.com.ale.realce.util.Util;

public abstract class ProdutoTav {

	private String nome;
	private Integer atacado;
	private String categoria;
	private Integer volume;

	/**
	 * @param nome
	 * @param atacado
	 * @param categoria
	 * @param volume
	 */
	public ProdutoTav(String nome, Integer atacado, String categoria, Integer volume) {
		super();
		this.nome = nome;
		this.atacado = atacado;
		this.categoria = categoria;
		this.volume = volume;
	}

	public String getTacProduto() {
		return categoria + " " + nome + " " + Util.toString(atacado) + "/" + Util.toString(volume);
	}

	public Integer getAtacado()
	{
		return atacado;
	}

}
