package br.com.ale.realce.util;

import java.util.Date;

import br.com.ale.realce.model.dao.CaixaDao;
import br.com.ale.realce.model.dao.TabelaVendaDao;
import br.com.ale.realce.model.entidade.Caixa;
import br.com.ale.realce.model.entidade.Estoque;
import br.com.ale.realce.model.entidade.HistoricoCaixa;
import br.com.ale.realce.model.entidade.ItemPedidoCompra;
import br.com.ale.realce.model.entidade.ItemPedidoVenda;
import br.com.ale.realce.model.entidade.Pagamento;
import br.com.ale.realce.model.entidade.Pedido;
import br.com.ale.realce.model.entidade.PedidoCompra;
import br.com.ale.realce.model.entidade.PedidoVenda;
import br.com.ale.realce.model.entidade.Produto;
import br.com.ale.realce.model.entidade.TabelaCusto;
import br.com.ale.realce.model.entidade.TabelaVenda;
import br.com.ale.realce.model.entidade.Usuario;
import br.com.ale.realce.model.enuns.FormaPagamento;
import br.com.ale.realce.model.enuns.Operacao;
import br.com.ale.realce.model.enuns.Privilegio;

public class ObjectFactory {

	public static StringBuilder getStringBuilder() {
		return new StringBuilder("");
	}

	public static Conn getConn() {
		return Conn.getInstance();
	}

	public static TabelaVendaDao getTabelaVendaDao() {
		return new TabelaVendaDao();
	}

	public static Pagamento getPagamento(PedidoVenda pedidoVenda) {

		Pagamento pagamento = new Pagamento();

		pagamento.setFormaPagamento(FormaPagamento.DINHEIRO);
		pagamento.setId(null);
		pagamento.setPedidoVenda(pedidoVenda);
		pagamento.setValor(Util.stringParaBigDecimal("0.0"));

		return pagamento;
	}

	public static PedidoCompra getPedidoCompra(Usuario usuario) {

		PedidoCompra pedidoCompra = new PedidoCompra();

		pedidoCompra.setData(new Date());
		pedidoCompra.setUsuario(usuario);
		pedidoCompra.setId(null);

		return pedidoCompra;
	}

	public static Produto getProduto() {

		Produto produto = new Produto();
		Estoque estoque = new Estoque();
		TabelaVenda tabelaVenda = new TabelaVenda();
		TabelaCusto tabelaCusto = new TabelaCusto();

		produto.setEstoque(estoque);
		produto.setTabelaVenda(tabelaVenda);
		produto.setTabelaCusto(tabelaCusto);
		estoque.setProduto(produto);
		tabelaVenda.setProduto(produto);
		tabelaCusto.setProduto(produto);

		return produto;
	}

	public static ItemPedidoCompra getItemPedidoCompra(Pedido pedido, Produto produto) {

		ItemPedidoCompra item = new ItemPedidoCompra();

		item.setId(null);
		item.setPedido(pedido);
		item.setProduto(produto);
		//item.setQuantidade("0", "0");
		//item.setValorTotalItem("0.0");
		item.setValorUnidade(Util.stringParaBigDecimal("0.0"));

		return item;

	}

	public static ItemPedidoVenda getItemPedidoVenda(Produto produto, Pedido pedido) {

		ItemPedidoVenda item = new ItemPedidoVenda();

		item.setId(null);
		item.setPedido(pedido);
		item.setProduto(produto);
		return item;
	}

	public static Usuario getUsuario() {

		Usuario usuario = new Usuario();

		usuario.setPrivilegio(Privilegio.A_DEFINIR);
		usuario.setSenha("");
		usuario.setUsuario("");

		return usuario;

	}

	public static Caixa getCaixa(Usuario usuario) {

		Caixa caixa = new Caixa();

		caixa.setSaldo(Util.stringParaBigDecimal("0.0"));
		caixa.setUsuario(usuario);
		caixa.setData(Datas.getDataSistema());

		return caixa;

	}

	public static PedidoVenda getPedidoVenda(Usuario usuario) {

		PedidoVenda pedidoVenda = new PedidoVenda();

		pedidoVenda.setData(Datas.getDataSistema());
		pedidoVenda.setDescricao("");
		pedidoVenda.setId(null);
		pedidoVenda.setSaldo(Util.stringParaBigDecimal("0.0"));
		pedidoVenda.setUsuario(usuario);

		return pedidoVenda;
	}

	public static HistoricoCaixa getHistoricoCaixa(Caixa caixa) {

		HistoricoCaixa historicoCaixa = new HistoricoCaixa();

		historicoCaixa.setCaixa(caixa);
		historicoCaixa.setData(Datas.getDataSistema());
		historicoCaixa.setDescricao("");
		historicoCaixa.setOperacao(Operacao.CREDITO);
		historicoCaixa.setSaldo(Util.stringParaBigDecimal("0.0"));
		historicoCaixa.setValor(Util.stringParaBigDecimal("0.0"));

		return historicoCaixa;
	}

	public static PaginacaoUtil getPaginacaoUtil() {
		return new PaginacaoUtil();
	}

	public static Mensagem getMensagem() {
		return new Mensagem();
	}

	public static CaixaDao getCaixaDao() {
		return new CaixaDao();
	}

	public static Rota getRota() {
		return new Rota();
	}

}
