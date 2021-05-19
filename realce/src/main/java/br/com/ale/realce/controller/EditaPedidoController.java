package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.interfaces.ModalObject;
import br.com.ale.realce.model.dao.ProdutoDao;
import br.com.ale.realce.model.entidade.ItemPedido;
import br.com.ale.realce.model.entidade.ItemPedidoCompra;
import br.com.ale.realce.model.entidade.PedidoCompra;
import br.com.ale.realce.model.entidade.Produto;
import br.com.ale.realce.model.tableView.ListaItemPedidoTav;
import br.com.ale.realce.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class EditaPedidoController implements Initializable, ModalObject {

    @FXML
    private TextField tef_codigo;

    @FXML
    private TextField tef_nomeProduto;

    @FXML
    private TextField tef_caixa;

    @FXML
    private TextField tef_unidade;

    @FXML
    private TextField tef_total;

    @FXML
    private Button but_adicionar;

    @FXML
    private Button but_excluir;

    @FXML
    private Button but_fechar;

    @FXML
    private Label lbl_pedido;

    @FXML
    private Label lbl_valorTotal;

    @FXML
    private Label lbl_totalItens;

    @FXML
    private Label lbl_data;

    @FXML
    private TableView<ListaItemPedidoTav> tavItemPedidoCompra;

    @FXML
    private TableColumn<ListaItemPedidoTav, String> tacProduto;

    @FXML
    private TableColumn<ListaItemPedidoTav, String> tacQuantidade;

    @FXML
    private TableColumn<ListaItemPedidoTav, String> tacValorUnidade;

    @FXML
    private TableColumn<ListaItemPedidoTav, String> tacValorTotal;

    @Override
    public void setStage(Stage stage) {
        this.myStage = stage;
    }

    @Override
    public boolean isOk() {
        return ok;
    }

    @Override
    public void setObject(Object object) {
        this.pedidoCompra = (PedidoCompra) object;
        this.itemPedidoCompra = ObjectFactory.getItemPedidoCompra(pedidoCompra, null);
        atualizaDadosPedidoCompraTelaUsuario();
        atualizaTableView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //CONFIGURAÇÃO INICIAL TELA
        inicializa();

        //AÇÕES DO USUÁRIO
        // localiza o produto pelo nome
        tef_nomeProduto.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                localizaProdutoPeloNome();
            }
        });

        but_adicionar.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                addItemPedidoCompra();
            }
        });
        but_adicionar.setOnAction(e -> {
            addItemPedidoCompra();
        });

        but_fechar.setOnAction(e -> {
            fecharPedido();
        });
        but_fechar.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                fecharPedido();
            }
        });

        but_excluir.setOnAction(e -> {
            editaItemPedido();
        });
        but_excluir.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                editaItemPedido();
            }
        });

        tef_codigo.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                localizaProdutoPelocodigo();
            }
        });

    }

    private final ProdutoDao produtoDao;
    private PedidoCompra pedidoCompra;
    private ItemPedidoCompra itemPedidoCompra;
    private final Rota rota;
    private final Mensagem mensagem;
    private ValidaUtil<ItemPedido> validaItemPedido;
    private ValidaUtil<PedidoCompra> validaPedidoCompra;
    private boolean ok = false;
    private Integer indiceItemPedido;
    private Stage myStage;

    public EditaPedidoController() {
        super();
        this.produtoDao = new ProdutoDao();
        this.rota = new Rota();
        this.validaItemPedido = new ValidaUtil<ItemPedido>();
        this.validaPedidoCompra = new ValidaUtil<PedidoCompra>();
        this.mensagem = new Mensagem();
        this.indiceItemPedido = null;
    }

    private void inicializa() {

        MascarasFX.mascaraNumeroInteiro(tef_caixa);
        MascarasFX.mascaraNumeroInteiro(tef_unidade);
        MascarasFX.mascaraNumero(tef_total);
        inicializaTableView();
        atualizaTableView();
    }

    private void inicializaTableView() {
        tacProduto.setCellValueFactory(new PropertyValueFactory<>("tacProduto"));
        tacQuantidade.setCellValueFactory(new PropertyValueFactory<>("tacQuantidade"));
        tacValorTotal.setCellValueFactory(new PropertyValueFactory<>("tacValorTotal"));
        tacValorUnidade.setCellValueFactory(new PropertyValueFactory<>("tacValorUnidade"));

    }

    private void atualizaTableView() {
        tavItemPedidoCompra.getItems().clear();
        ItemPedidoCompra itemPedidoCompra1 = null;
        if (pedidoCompra != null) {
            if (pedidoCompra.getItensPedido().size() > 0) {
                for (int i = 0; i < pedidoCompra.getItensPedido().size(); i++) {
                    itemPedidoCompra1 = (ItemPedidoCompra) pedidoCompra.getItensPedido().get(i);
                    tavItemPedidoCompra.getItems().add(new ListaItemPedidoTav(
                            i,
                            itemPedidoCompra1.getProduto().getNome(),
                            itemPedidoCompra1.getProduto().getAtacado(),
                            itemPedidoCompra1.getProduto().getCategoria().getNome(),
                            itemPedidoCompra1.getProduto().getVolume().getVolume(),
                            itemPedidoCompra1.getQuantidade(),
                            itemPedidoCompra1.getValorUnidade(),
                            itemPedidoCompra1.getValorTotalItem()));
                    itemPedidoCompra1 = null;
                }
            }
        }
    }

    private void atualizaDadosPedidoCompraTelaUsuario() {
        lbl_data.setText(Datas.formata(pedidoCompra.getData()));
        lbl_pedido.setText(Util.formataNumeroPedido(pedidoCompra.getNumero()));
        lbl_totalItens.setText(Util.toString(pedidoCompra.getItensPedido().size()));
        lbl_valorTotal.setText(Util.toMoeda(pedidoCompra.getValorTotalPedido()));
    }

    private void localizaProdutoPeloNome() {
        itemPedidoCompra.setProduto((Produto) rota.openLocalizaProduto(tef_nomeProduto.getText()));
        atualizaAtributosTela();
        tef_caixa.requestFocus();

    }

    private void localizaProdutoPelocodigo() {
        itemPedidoCompra.setProduto(produtoDao.getProdutoPeloCodigo(tef_codigo.getText()));
        atualizaAtributosTela();
        tef_caixa.requestFocus();
    }

    private void atualizaAtributosTela() {
        tef_codigo.clear();
        tef_nomeProduto.clear();
        tef_caixa.clear();
        tef_total.clear();
        tef_unidade.clear();
        if(itemPedidoCompra.getProduto()!=null){
            tef_codigo.setText(itemPedidoCompra.getProduto().getCodigoLeitura());
            tef_nomeProduto.setText(itemPedidoCompra.getProduto().getDescricaoProduto());
        }

    }

    private void addItemPedidoCompra() {


        try {
            itemPedidoCompra.setPedido(pedidoCompra);
            itemPedidoCompra.setQuantidade(Util.toInteger(tef_caixa.getText()), Util.toInteger(tef_unidade.getText()));
            itemPedidoCompra.setValorTotalItem(Util.toBigDecimal(tef_total.getText()));

            validaItemPedido.valida(itemPedidoCompra);

            pedidoCompra.addItemPedido(itemPedidoCompra, indiceItemPedido);

            validaPedidoCompra.valida(pedidoCompra);

            itemPedidoCompra = ObjectFactory.getItemPedidoCompra(null, null);
            indiceItemPedido = null;

            atualizaDadosPedidoCompraTelaUsuario();
            atualizaAtributosTela();
            atualizaTableView();

            tef_nomeProduto.requestFocus();

        } catch (Exception e) {
            e.printStackTrace();
            mensagem.publica(e.getMessage());
        }
    }

    private void editaItemPedido() {

        try {
            // deve existir um item selecionado
            Util.validaTableView(tavItemPedidoCompra);

            // recebe o item selecionado
            indiceItemPedido = tavItemPedidoCompra.getSelectionModel().getSelectedItem().getIndex();
            itemPedidoCompra = pedidoCompra.getItemPedidoCompra(indiceItemPedido);

            // atualiza atributos para usuário editar
            atualizaAtributosTela();

            tef_caixa.requestFocus();

        } catch (Exception e) {
            mensagem.publica(e.getMessage());
        }
    }

    private void fecharPedido() {
        this.ok = true;
        myStage.close();
    }

}
