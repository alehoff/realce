package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.model.dao.ProdutoDao;
import br.com.ale.realce.model.entidade.Produto;
import br.com.ale.realce.model.tableView.ListaTabelaVendaTav;
import br.com.ale.realce.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class ListaTabelaVendaController implements Initializable {

    @FXML
    private TableView<ListaTabelaVendaTav> tavTabelaVenda;

    @FXML
    private TableColumn<ListaTabelaVendaTav, String> tacProduto;

    @FXML
    private TableColumn<ListaTabelaVendaTav, String> tacMargem;

    @FXML
    private TableColumn<ListaTabelaVendaTav, String> tacUnidade;

    @FXML
    private TableColumn<ListaTabelaVendaTav, String> tacAtacado;

    @FXML
    private TableColumn<ListaTabelaVendaTav, String> tacSugerido;

    @FXML
    private TextField tef_filtro;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // configurações iniciais da tela
        inicializa();

        // ações usuario
        tacUnidade.setCellFactory(TextFieldTableCell.forTableColumn());
        tacMargem.setCellFactory(TextFieldTableCell.forTableColumn());

        tacUnidade.setOnEditCommit(e -> {
            atualizaTabelaVenda(e.getNewValue(), tavTabelaVenda.getSelectionModel().getSelectedItem().getId());
        });

        tacMargem.setOnEditCommit(e -> {
            atualizaMargemLucro(tavTabelaVenda.getSelectionModel().getSelectedItem().getId(), e.getNewValue());
        });

        tef_filtro.setOnKeyReleased(e -> {
            banco.abreConn();
            try {
                atualizaTableView();
            } finally {
                banco.fechaConn();
            }
        });
    }

    private final ProdutoDao produtoDao;
    private final Mensagem mensagem;
    private final Banco banco;
    private final ValidaUtil<Produto> validaProduto;

    public ListaTabelaVendaController() {
        super();
        this.produtoDao = new ProdutoDao();
        this.mensagem = ObjectFactory.getMensagem();
        this.banco = Banco.getInstance();
        this.validaProduto = new ValidaUtil<Produto>();
    }

    private void inicializa() {

        tavTabelaVenda.setEditable(true);

        inicializaTableColumns();

        banco.abreConn();
        try {
            atualizaTableView();
        } finally {
            banco.fechaConn();
        }
    }

    private void inicializaTableColumns() {

        tacAtacado.setCellValueFactory(new PropertyValueFactory<>("tacAtacado"));
        tacMargem.setCellValueFactory(new PropertyValueFactory<>("tacMargem"));
        tacProduto.setCellValueFactory(new PropertyValueFactory<>("tacProduto"));
        tacSugerido.setCellValueFactory(new PropertyValueFactory<>("tacSugerido"));
        tacUnidade.setCellValueFactory(new PropertyValueFactory<>("tacUnidade"));
    }

    /**
     * Atualiza objeto TabelaVenda com valores informados pelo usuário
     *
     * @param valor
     */
    private void atualizaTabelaVenda(String valor, long idProduto) {
        boolean salvar = false;
        banco.abreTransacao();
        try {
            Produto produto = produtoDao.getProdutoPeloId(idProduto);
            produto.getTabelaVenda().setValorUnidade(Util.stringParaBigDecimal(valor));
            validaProduto.valida(produto);
            atualizaTableView();
            salvar = true;
        } catch (Exception e) {
            mensagem.publica(e.getMessage());
        } finally {
           banco.fechaTransacao(salvar);
        }
    }

    private void atualizaMargemLucro(long idProduto, String margem) {
        boolean editar = false;
       banco.abreTransacao();
        try {
            Produto p = produtoDao.getProdutoPeloId(idProduto);
            p.setMargemLucro(Util.toDouble(margem));
            validaProduto.valida(p);
            p.getTabelaCusto().atualizaValorSugerido();
            atualizaTableView();
            editar = true;
        } catch (Exception e) {
            mensagem.publica(e.getMessage());
        } finally {
            banco.fechaTransacao(editar);
        }
    }

    private void atualizaTableView() {
        String filtro = tef_filtro.getText();
        tavTabelaVenda.getItems().clear();
        tavTabelaVenda.getItems().addAll(produtoDao.getListaTabelaVenda(filtro));
    }

}
