package br.com.ale.realce.relatorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.ale.realce.model.entidade.ItemPedido;
import br.com.ale.realce.model.entidade.ItemPedidoVenda;
import br.com.ale.realce.model.entidade.PedidoVenda;
import br.com.ale.realce.util.Datas;
import br.com.ale.realce.util.Util;

public class RelatorioPedidoVenda extends Relatorio {

	public void relatorioPedidoVenda(PedidoVenda pedido) {

		String url = "/JasperReport/pedidoVenda.jrxml";

		HashMap<String, Object> map = new HashMap<>();
		map.put("dataPedido", Datas.formata(pedido.getData()));
		map.put("numeroPedido", Util.formataNumeroPedido(pedido.getNumero()));
		map.put("usuario", pedido.getUsuario().getUsuario());
		map.put("valorTotalPedido", Util.toMoeda(pedido.getValorTotalPedido()));

		List<RelatorioItensPedidoVenda> itens = new ArrayList<RelatorioPedidoVenda.RelatorioItensPedidoVenda>();
		ItemPedidoVenda ipv;

		for (ItemPedido ip : pedido.getItensPedido()) {
			ipv = (ItemPedidoVenda) ip;
			itens.add(new RelatorioItensPedidoVenda(
					ipv.getProduto().getDescricaoProduto(), 
					Util.toCaixaUnidade(ipv.getQuantidade(), ipv.getProduto().getAtacado()),
					Util.toMoeda(ipv.getValorUnidade()), 
					Util.toMoeda(ipv.getValorTotalItem())));
		}

		try {
			gerarRelatorio(map, itens, url);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public class RelatorioItensPedidoVenda {
		private String produto;
		private String quantidade;
		private String valorUnidade;
		private String valorTotal;

		/**
		 * @param produto
		 * @param quantidade
		 * @param valorUnidade
		 * @param valorTotal
		 */
		public RelatorioItensPedidoVenda(String produto, String quantidade, String valorUnidade, String valorTotal) {
			super();
			this.produto = produto;
			this.quantidade = quantidade;
			this.valorUnidade = valorUnidade;
			this.valorTotal = valorTotal;
		}

		public String getProduto() {
			return produto;
		}

		public String getQuantidade() {
			return quantidade;
		}

		public String getValorUnidade() {
			return valorUnidade;
		}

		public String getValorTotal() {
			return valorTotal;
		}

	}
}
