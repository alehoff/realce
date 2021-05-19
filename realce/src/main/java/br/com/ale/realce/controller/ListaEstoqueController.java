package br.com.ale.realce.controller;

import br.com.ale.realce.model.dao.EstoqueDao;
import br.com.ale.realce.model.entidade.Estoque;
import br.com.ale.realce.model.tableView.ListaEstoqueTav;
import br.com.ale.realce.util.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaEstoqueController implements Initializable {

    @FXML
    private TableView<ListaEstoqueTav> tavEstoque;

    @FXML
    private TableColumn<ListaEstoqueTav, String> tacProduto;

    @FXML
    private TableColumn<ListaEstoqueTav, String> tacQuantidade;

    @FXML
    private TableColumn<ListaEstoqueTav, String> tacMinimo;

    @FXML
    private TableColumn<ListaEstoqueTav, String> tacComprar;

    @FXML
    private TextField tef_filtro;

    @FXML
    private MenuItem meiValorMinimo;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //configurações iniciais
        tef_filtro.clear();
        inicializaTableColumns();
        atualizaTableView();

        //ações do usuário
        tef_filtro.setOnKeyReleased(event -> atualizaTableView());
        meiValorMinimo.setOnAction(event -> editaValorMinimo());
    }

    private final EstoqueDao estoqueDao;
    private final Mensagem mensagem;
    private final Rota rota;
    private final Banco banco;

    public ListaEstoqueController() {
        super();
        this.banco = Banco.getInstance();
        this.estoqueDao = new EstoqueDao();
        this.mensagem = new Mensagem();
        this.rota = new Rota();
    }


    private void inicializaTableColumns() {
        tacComprar.setCellValueFactory(new PropertyValueFactory<>("tacComprar"));
        tacMinimo.setCellValueFactory(new PropertyValueFactory<>("tacMinimo"));
        tacProduto.setCellValueFactory(new PropertyValueFactory<>("tacProduto"));
        tacQuantidade.setCellValueFactory(new PropertyValueFactory<>("tacQuantidade"));
    }

    private void atualizaRegistrosTableView() {
        tavEstoque.getItems().clear();
        tavEstoque.getItems().addAll(estoqueDao.getAll(tef_filtro.getText()));
        if (tavEstoque.getItems().size() > 0) {
            tavEstoque.getSelectionModel().selectFirst();
        }
    }

    private void atualizaTableView() {
        banco.abreConn();
        try {
            atualizaRegistrosTableView();
        } finally {
            banco.fechaConn();
        }
    }

    /**
     * Abre modal para edição do valor mínimo em estoque para determinado
     * produto
     */
    private void editaValorMinimo() {
        boolean ok = false;
        banco.abreTransacao();
        try {
            // verifica se existem itens selecionados para edição
            Util.validaTableView(tavEstoque);
            // abre modal para edição do valor mínimo em estoque
            Estoque estoque = estoqueDao.getById(tavEstoque.getSelectionModel().getSelectedItem().getIdEstoque(),Estoque.class);
            ok = rota.openEditaValorMinimo("Valor Mínimo Produto", estoque);
            if (ok) {
                atualizaRegistrosTableView();
            }
        } catch (Exception e) {
            mensagem.publica(e.getMessage());
        } finally {
            banco.fechaTransacao(ok);
        }
    }

}
