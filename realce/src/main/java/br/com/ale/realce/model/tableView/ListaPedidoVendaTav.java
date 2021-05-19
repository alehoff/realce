package br.com.ale.realce.model.tableView;

import br.com.ale.realce.util.Util;

import java.math.BigDecimal;

public class ListaPedidoVendaTav {
	
	private long idPedido;
	private Long numero;
	private String descricao;
	private BigDecimal valorTotalPedido;

	public ListaPedidoVendaTav(long idPedido, Long numero, String descricao, BigDecimal valorTotalPedido) {
		this.idPedido = idPedido;
		this.numero = numero;
		this.descricao = descricao;
		this.valorTotalPedido = valorTotalPedido;
	}

	public long getIdPedido() {
		return idPedido;
	}

	public String getTacValorPedido(){
		return Util.toMoeda(valorTotalPedido);
	}

	public String getTacDescricao(){
		return descricao;
	}

	public String getTacNumeroPedido(){
		return Util.formataNumeroPedido(numero);
	}
}
