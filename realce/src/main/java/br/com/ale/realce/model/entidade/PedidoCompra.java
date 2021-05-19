package br.com.ale.realce.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;

/**
 * Entity implementation class for Entity: PedidoCompra
 *
 */
@Entity
public class PedidoCompra extends Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	public PedidoCompra() {
		super();
	}
	
	public void addItemPedido(ItemPedidoCompra itemPedidoCompra, Integer indice) throws Exception {

		if (indice != null) {
			itensPedido.set(indice, itemPedidoCompra);
		} else {
			if (itensPedido.contains(itemPedidoCompra)) {
				throw new Exception("Item já cadastrado");
			}
			itensPedido.add(itemPedidoCompra);
		}
		valorTotalPedido = valorTotalPedido.add(itemPedidoCompra.getValorTotalItem());

	}

	public ItemPedidoCompra getItemPedidoCompra(Integer index){

		ItemPedidoCompra ipc = (ItemPedidoCompra) itensPedido.get(index);
		//devolve ao estoque a quantidade
		ipc.getProduto().getEstoque().adiciona(ipc.getQuantidade());
		//atualiza valor total pedido
		valorTotalPedido = valorTotalPedido.subtract(ipc.getValorTotalItem());

		return ipc;
	}


	public Integer getIndiceItemPedidoCompra(ItemPedidoCompra itemPedidoCompra) {

		if (itensPedido.size() < 1) {
			return null;
		}
		return itensPedido.indexOf(itemPedidoCompra);
	}
	
	public void extornaDoValorTotalPedido(BigDecimal valor) {
		this.valorTotalPedido = this.valorTotalPedido.subtract(valor);
	}
}
