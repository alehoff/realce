package br.com.ale.realce.model.tableView;

import java.math.BigDecimal;
import java.util.Date;

import br.com.ale.realce.util.Datas;
import br.com.ale.realce.util.Util;

public class ListaCaixaPorPeriodoTav {

	private Long id;
	private Date data;
	private String usuario;
	private BigDecimal saldo;
	private Long totalPedidos;

	/**
	 * @param id
	 * @param data
	 * @param usuario
	 * @param saldo
	 * @param totalPedidos
	 */
	public ListaCaixaPorPeriodoTav(Long id, Date data, String usuario, BigDecimal saldo, Long totalPedidos) {
		super();
		this.id = id;
		this.data = data;
		this.usuario = usuario;
		this.saldo = saldo;
		this.totalPedidos = totalPedidos;
	}

	public Long getId() {
		return id;
	}

	public String getData() {
		return Datas.formata(data);
	}

	public String getUsuario() {
		return usuario;
	}

	public String getSaldo() {
		return Util.toMoeda(saldo);
	}

	public Long getTotalPedidos() {
		return totalPedidos;
	}

}
