package br.com.ale.realce.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.ale.realce.interfaces.ModalObject;
import br.com.ale.realce.model.dao.ProdutoDao;
import br.com.ale.realce.model.entidade.ItemPedido;
import br.com.ale.realce.model.entidade.ItemPedidoVenda;
import br.com.ale.realce.model.entidade.Pagamento;
import br.com.ale.realce.model.entidade.PedidoVenda;
import br.com.ale.realce.model.entidade.Produto;
import br.com.ale.realce.model.enuns.FormaPagamento;
import br.com.ale.realce.model.tableView.ListaItemPedidoTav;
import br.com.ale.realce.util.MascarasFX;
import br.com.ale.realce.util.Mensagem;
import br.com.ale.realce.util.ObjectFactory;
import br.com.ale.realce.util.Rota;
import br.com.ale.realce.util.Util;
import br.com.ale.realce.util.ValidaUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class PedidoVendacontroller implements Initializable, ModalObject {

	@FXML
	private TextField tefCodigo;

	@FXML
	private TextField tefProduto;

	@FXML
	private TextField tefCaixa;

	@FXML
	private TextField tefUnidade;

	@FXML
	private Button butSalvarItemPedido;

	@FXML
	private Button butEditarItemPedido;

	@FXML
	private Button butExcluirItemPedido;

	@FXML
	private TableView<ListaItemPedidoTav> tavItemPedidoVenda;

	@FXML
	private TableColumn<ListaItemPedidoTav, String> tacNomeProduto;

	@FXML
	private TableColumn<ListaItemPedidoTav, String> tacQuantidadeItemPedidoVenda;

	@FXML
	private TableColumn<ListaItemPedidoTav, String> tacValorUnidadeItemPedido;

	@FXML
	private TableColumn<ListaItemPedidoTav, String> tacValorTotalItemPedido;

	@FXML
	private TextField tefDescricaoPedido;

	@FXML
	private Label lblEstoque;

	@FXML
	private Label lblUnidadeProduto;

	@FXML
	private Label lblCaixaProduto;

	@FXML
	private Label lblTotalItem;

	@FXML
	private Label lblTotalPedido;

	@FXML
	private Label lblSaldoPedido;

	@FXML
	private ComboBox<FormaPagamento> cobFormaPagamento;

	@FXML
	private TextField tefValorPagamento;

	@FXML
	private Button butSalvarPagamento;

	@FXML
	private Button butExcluirPagamento;

	@FXML
	private TableView<Pagamento> tavPagamento;

	@FXML
	private TableColumn<Pagamento, String> tacDescricaoPagamento;

	@FXML
	private TableColumn<Pagamento, String> tacValorPagamento;

	@FXML
	private Button butFecharPedido;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		inicializa();
		actions();
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.centerOnScreen();
	}

	@Override
	public boolean isOk() {
		return ok;
	}

	@Override
	public void setObject(Object object) {
		pedidoVenda = (PedidoVenda) object;
	}

	private ItemPedidoVenda itemPedidoVenda;
	private PedidoVenda pedidoVenda;
	private final ProdutoDao produtoDao;
	private final Rota rota;
	private final Mensagem mensagem;
	private final ValidaUtil<ItemPedidoVenda> validaItemPedidoVenda;
	private final ValidaUtil<Pagamento> validaPagamento;
	private Stage stage;
	private boolean ok = false;

	public PedidoVendacontroller() {
		this.pedidoVenda = null;
		this.itemPedidoVenda = ObjectFactory.getItemPedidoVenda(null, null);
		this.produtoDao = new ProdutoDao();
		this.rota = new Rota();
		this.mensagem = new Mensagem();
		this.validaItemPedidoVenda = new ValidaUtil<ItemPedidoVenda>();
		this.validaPagamento = new ValidaUtil<Pagamento>();

	}

	private void actions() {

		// LOCALIZA PRODUTO PELO NOME OU CÓDIGO DO MESMO
		tefCodigo.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				localizaProdutoPeloCodigo();
			}
		});
		tefProduto.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				localizaProdutoPeloNome();
			}
		});

		// CALCULAR VALOR DO ITEM DO PEDIDO
		tefCaixa.setOnKeyReleased(event -> adicionaQuantidade());
		tefUnidade.setOnKeyReleased(event -> adicionaQuantidade());

		// ADICIONAR ITEM PEDIDO VENDA AO PEDIDO VENDA
		butSalvarItemPedido.setOnAction(e -> addItemPedidoVenda());
		butSalvarItemPedido.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				addItemPedidoVenda();
			}
		});

		// REMOVE ITEM PEDIDO DO PEDIDO VENDA
		butExcluirItemPedido.setOnAction(e -> removeItemPedidoVenda());
		butExcluirItemPedido.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				removeItemPedidoVenda();
			}
		});

		// EDITA ITEM PEDIDO PARA PEDIDO VENDA
		butEditarItemPedido.setOnAction(e -> retornaItemPedidoParaEdicao());
		butEditarItemPedido.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				retornaItemPedidoParaEdicao();
			}
		});

		// SALVA PAGAMENTOS REALIZADOS PARA PEDIDO DE VENDA
		butSalvarPagamento.setOnAction(e -> realizarPagamentoPedidoVenda());
		butSalvarPagamento.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				realizarPagamentoPedidoVenda();
			}
		});
		butExcluirPagamento.setOnAction(e -> removePagamento());

		// FECHA O CAIXA
		butFecharPedido.setOnAction(e -> fecharPedido());
		butFecharPedido.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				fecharPedido();
			}
		});

	}

	private void fecharPedido() {

		if (pedidoVenda.pedidoSemSaldoDevedor()) {
			pedidoVenda.setDescricao(tefDescricaoPedido.getText());
			ok = true;
			stage.close();
		} else {
			mensagem.publica("Saldo deve ser zerado");
		}

	}

	private void inicializa() {
		inicializaComboPagamento();
		inicializaTableColunsItensPedidoVenda();
		inicializaTableColunsPagamento();
		MascarasFX.mascaraNumeroInteiro(tefCaixa);
		MascarasFX.mascaraNumeroInteiro(tefUnidade);
	}

	private void realizarPagamentoPedidoVenda() {

		Pagamento pagamento = ObjectFactory.getPagamento(pedidoVenda);

		pagamento.setFormaPagamento(cobFormaPagamento.getSelectionModel().getSelectedItem());
		pagamento.setValor(Util.stringParaBigDecimal(tefValorPagamento.getText()));

		try {
			validaPagamento.valida(pagamento);

			pedidoVenda.addPagamento(pagamento);

			atualizaAtributosPedidoParaUsuario();

			atualizaTableViewPAgamentos();

			tefValorPagamento.clear();

		} catch (Exception e) {
			mensagem.publica(e.getMessage());
		}
	}

	private void atualizaTableViewPAgamentos() {
		tavPagamento.getItems().clear();
		tavPagamento.getItems().addAll(pedidoVenda.getPagamentos());
	}

	private void removePagamento() {
		try {
			Util.validaTableView(tavPagamento);

			pedidoVenda.removePagamento(tavPagamento.getSelectionModel().getSelectedItem());

			atualizaTableViewPAgamentos();

			atualizaAtributosPedidoParaUsuario();

		} catch (Exception e) {
			mensagem.publica(e.getMessage());
		}
	}

	private void retornaItemPedidoParaEdicao() {
		try {
			Util.validaTableView(tavItemPedidoVenda);

			int index = tavItemPedidoVenda.getSelectionModel().getSelectedItem().getIndex();

			itemPedidoVenda = pedidoVenda.getItemPedidoVenda(index);

			atualizaAtributosItemPedidoVendaTelaParaUsuario();

			atualizaAtributosPedidoParaUsuario();

			atualizaTableViewItensPedidoVenda();

		} catch (Exception e) {
			mensagem.publica(e.getMessage());
		}
	}

	private void removeItemPedidoVenda() {

		try {
			// verifica se table view possui item selecionado
			Util.validaTableView(tavItemPedidoVenda);

			// remove o item do pedido
			pedidoVenda.removeItemPedidoVenda(tavItemPedidoVenda.getSelectionModel().getSelectedItem().getIndex());

			// atualiza table view
			atualizaTableViewItensPedidoVenda();

			// atualiza labels com informações ao usuário sobre pedido
			atualizaAtributosPedidoParaUsuario();

		} catch (Exception e) {
			mensagem.publica(e.getMessage());
		}
	}

	private void addItemPedidoVenda() {

		try {
			// valida o itemPedidoVenda
			validaItemPedidoVenda.valida(itemPedidoVenda);

			// adiciona o item ao pedido
			pedidoVenda.addItemPedidoVenda(itemPedidoVenda);

			// atualiza tableView itempedido
			atualizaTableViewItensPedidoVenda();

			// novo objeto itemPedidoVenda
			itemPedidoVenda = ObjectFactory.getItemPedidoVenda(null, null);

			// atualiza atributos ItemPedidoVenda na tela
			tefCaixa.clear();
			tefUnidade.clear();

			//
			atualizaAtributosItemPedidoVendaTelaParaUsuario();

			// atualiza atributos do PedidoVenda na tela
			atualizaAtributosPedidoParaUsuario();

			// foca no campo código produto para novo item
			tefCodigo.requestFocus();

		} catch (Exception e) {
			mensagem.publica(e.getMessage());
		}
	}

	private void atualizaAtributosPedidoParaUsuario() {

		lblSaldoPedido.setText(Util.toMoeda(pedidoVenda.getSaldo()));
		lblTotalPedido.setText(Util.toMoeda(pedidoVenda.getValorTotalPedido()));

	}

	private void atualizaTableViewItensPedidoVenda() {

		tavItemPedidoVenda.getItems().clear();

		List<ItemPedido> itens = pedidoVenda.getItensPedido();
		for (int index = 0; index < itens.size(); index++) {
			tavItemPedidoVenda.getItems().add(new ListaItemPedidoTav(index, itens.get(index).getProduto().getNome(),
					itens.get(index).getProduto().getAtacado(), itens.get(index).getProduto().getCategoria().getNome(),
					itens.get(index).getProduto().getVolume().getVolume(), itens.get(index).getQuantidade(),
					itens.get(index).getValorUnidade(), itens.get(index).getValorTotalItem()));
		}
	}

	private void adicionaQuantidade() {
		try {
			itemPedidoVenda.setQuantidade(Util.toInteger(tefCaixa.getText()), Util.toInteger(tefUnidade.getText()));
			atualizaAtributosItemPedidoVendaTelaParaUsuario();
		} catch (Exception e) {
			mensagem.publica(e.getMessage());
			tefProduto.requestFocus();
		}
	}

	private void localizaProdutoPeloNome() {
		itemPedidoVenda.setProduto((Produto) rota.openLocalizaProduto(tefProduto.getText()));
		atualizaAtributosItemPedidoVendaTelaParaUsuario();
	}

	private void localizaProdutoPeloCodigo() {
		itemPedidoVenda.setProduto(produtoDao.getProdutoPeloCodigo(tefCodigo.getText()));
		atualizaAtributosItemPedidoVendaTelaParaUsuario();
	}

	private void atualizaAtributosItemPedidoVendaTelaParaUsuario() {
		if (itemPedidoVenda.getProduto() != null) {
			tefProduto.setText(itemPedidoVenda.getProduto().getDescricaoProduto());
			tefCodigo.setText(itemPedidoVenda.getProduto().getCodigoLeitura());
			lblCaixaProduto.setText(Util.toMoeda(itemPedidoVenda.getProduto().getTabelaVenda().getValorAtacado()));
			lblEstoque.setText(Util.toCaixaUnidade(itemPedidoVenda.getProduto().getEstoque().getQuantidade(),
					itemPedidoVenda.getProduto().getAtacado()));
			lblUnidadeProduto.setText(Util.toMoeda(itemPedidoVenda.getProduto().getTabelaVenda().getValorUnidade()));
			lblTotalItem.setText(Util.toMoeda(itemPedidoVenda.getValorTotalItem()));
		} else {
			tefProduto.clear();
			tefCodigo.clear();
			lblCaixaProduto.setText("");
			lblEstoque.setText("");
			lblUnidadeProduto.setText("");
			lblTotalItem.setText("");
		}
	}

	private void inicializaTableColunsItensPedidoVenda() {
		tacNomeProduto.setCellValueFactory(new PropertyValueFactory<>("tacProduto"));
		tacQuantidadeItemPedidoVenda.setCellValueFactory(new PropertyValueFactory<>("tacQuantidade"));
		tacValorTotalItemPedido.setCellValueFactory(new PropertyValueFactory<>("tacValorTotal"));
		tacValorUnidadeItemPedido.setCellValueFactory(new PropertyValueFactory<>("tacValorUnidade"));
	}

	private void inicializaTableColunsPagamento() {
		tacDescricaoPagamento.setCellValueFactory(new PropertyValueFactory<>("tacDescricaoPagamento"));
		tacValorPagamento.setCellValueFactory(new PropertyValueFactory<>("tacValorPagamento"));
	}

	private void inicializaComboPagamento() {
		cobFormaPagamento.getItems().clear();
		cobFormaPagamento.getItems().addAll(FormaPagamento.values());
		cobFormaPagamento.getSelectionModel().selectFirst();
	}

}
