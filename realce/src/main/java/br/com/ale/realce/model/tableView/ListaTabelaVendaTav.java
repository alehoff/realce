package br.com.ale.realce.model.tableView;

import java.math.BigDecimal;

import br.com.ale.realce.util.Util;

public class ListaTabelaVendaTav extends ProdutoTav {

	private long idProduto;
	@SuppressWarnings("unused")
	private String nome;
	@SuppressWarnings("unused")
	private String categoria;
	@SuppressWarnings("unused")
	private Integer atacado;
	@SuppressWarnings("unused")
	private Integer volume;
	private BigDecimal valorUnidade;
	private BigDecimal valorAtacado;
	private double margem;
	private BigDecimal valorSugerido;

	/**
	 * @param nome
	 * @param atacado
	 * @param categoria
	 * @param volume
	 * @param idProduto
	 * @param valorUnidade
	 * @param valorAtacado
	 * @param margem
	 * @param valorSugerido
	 */
	public ListaTabelaVendaTav(String nome, Integer atacado, String categoria, Integer volume, long idProduto,
			BigDecimal valorUnidade, BigDecimal valorAtacado, double margem, BigDecimal valorSugerido) {
		super(nome, atacado, categoria, volume);
		this.idProduto = idProduto;
		this.valorUnidade = valorUnidade;
		this.valorAtacado = valorAtacado;
		this.margem = margem;
		this.valorSugerido = valorSugerido;
	}

	public String getTacMargem() {
		return Util.toString(margem);
	}

	public String getTacUnidade() {
		return Util.toString(valorUnidade);
	}

	public String getTacAtacado() {
		return Util.toMoeda(valorAtacado);
	}

	public String getTacUnidadeEmReais() {
		return Util.toMoeda(valorUnidade);
	}

	public String getTacSugerido() {
		return Util.toMoeda(valorSugerido);
	}

	public long getId() {
		return idProduto;
	}

}
