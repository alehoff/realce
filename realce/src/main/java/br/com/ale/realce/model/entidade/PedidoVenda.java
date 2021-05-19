package br.com.ale.realce.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

import br.com.ale.realce.util.Util;

/**
 * Entity implementation class for Entity: PedidoVenda
 *
 */
@Entity
public class PedidoVenda extends Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(length = 180)
	private String descricao;

	@Min(value = 0)
	private BigDecimal saldo;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Pagamento> pagamentos = new ArrayList<Pagamento>();

	public PedidoVenda() {
		super();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public void addItemPedidoVenda(ItemPedidoVenda itemPedidoVenda) throws Exception {

		itemPedidoVenda.setPedido(this);

		if (itensPedido.contains(itemPedidoVenda)) {
			throw new Exception("Produto já cadastrado para este pedido");
		}

		itemPedidoVenda.getProduto().getEstoque().subtrair(itemPedidoVenda.getQuantidade());

		valorTotalPedido = valorTotalPedido.add(itemPedidoVenda.getValorTotalItem());

		saldo = saldo.add(itemPedidoVenda.getValorTotalItem());

		itensPedido.add(itemPedidoVenda);
	}

	public void removeItemPedidoVenda(int index) throws Exception {

		//localiza item
		ItemPedido itemPedido = itensPedido.get(index);

		//atualiza estoque
		itemPedido.getProduto().getEstoque().adiciona(itemPedido.getQuantidade());

		// REMOVE DO SALDO A SER PAGO O VALOR DO ITEM
		saldo = saldo.subtract(itemPedido.getValorTotalItem());

		// ATUALIZA O VALOR DO PEDIDO
		valorTotalPedido = valorTotalPedido.subtract(itemPedido.getValorTotalItem());

		// REMOVE O ITEM DO PEDIDO
		itensPedido.remove(index);
	}

	public ItemPedidoVenda getItemPedidoVenda(int index) throws Exception {

		//localiza item
		ItemPedido itemPedido = itensPedido.get(index);
		itensPedido.remove(index);

		//atualiza estoque
		itemPedido.getProduto().getEstoque().adiciona(itemPedido.getQuantidade());

		// REMOVE DO SALDO A SER PAGO O VALOR DO ITEM
		saldo = saldo.subtract(itemPedido.getValorTotalItem());

		// ATUALIZA O VALOR DO PEDIDO
		valorTotalPedido = valorTotalPedido.subtract(itemPedido.getValorTotalItem());

		// retorna O ITEM DO PEDIDO
		return (ItemPedidoVenda) itemPedido;

	}

	public void addPagamento(Pagamento pagamento) throws Exception {

		if (pagamento.getValor().compareTo(saldo) > 0) {
			throw new Exception("Pagamento superior ao saldo existente neste pedido");
		}

		pagamento.setPedidoVenda(this);

		if (pagamentos.contains(pagamento)) {
			throw new Exception("Forma de pagamento já realizada");
		}

		saldo = saldo.subtract(pagamento.getValor());

		pagamentos.add(pagamento);
	}
	
	public void removePagamento(Pagamento pagamento) {
		
		saldo = saldo.add(pagamento.getValor());
		
		pagamentos.remove(pagamento);
		
	}

	public boolean pedidoSemSaldoDevedor() {
		return saldo.compareTo(Util.stringParaBigDecimal("0.0")) == 0;
	}

}
