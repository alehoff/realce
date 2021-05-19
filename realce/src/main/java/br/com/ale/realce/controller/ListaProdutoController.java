package br.com.ale.realce.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.ale.realce.model.dao.ProdutoDao;
import br.com.ale.realce.model.entidade.Produto;
import br.com.ale.realce.model.enuns.Status;
import br.com.ale.realce.model.tableView.ListaProdutoTav;
import br.com.ale.realce.util.Banco;
import br.com.ale.realce.util.Mensagem;
import br.com.ale.realce.util.ObjectFactory;
import br.com.ale.realce.util.Rota;
import br.com.ale.realce.util.Util;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaProdutoController implements Initializable {

    @FXML
    private TableView<ListaProdutoTav> tavProduto;

    @FXML
    private TableColumn<ListaProdutoTav, String> tacCodigo;

    @FXML
    private TableColumn<ListaProdutoTav, String> tacProduto;

    @FXML
    private Button but_cadastro;

    @FXML
    private Button but_edita;

    @FXML
    private Button butStatus;

    @FXML
    private TextField tef_filtro;

    @FXML
    private CheckBox chb_ativos;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        inicializa();

        tef_filtro.setOnKeyReleased(e -> {
            this.filtro();
        });

        but_cadastro.setOnAction(e -> {
            openCadastroProduto();
        });

        chb_ativos.setOnAction(e -> {
            filtro();
        });

        butStatus.setOnAction(e -> atualizaStatusProduto());
        butStatus.setOnKeyPressed(e -> {
            atualizaStatusProduto();
        });

        but_edita.setOnAction(event -> editaProduto());
    }

    private final ProdutoDao produtoDao;
    private final Rota rota;
    private Produto produto;
    private final Mensagem mensagem;
    private final Banco banco;

    public ListaProdutoController() {
        super();
        this.produtoDao = new ProdutoDao();
        this.rota = new Rota();
        this.mensagem = new Mensagem();
        this.banco = Banco.getInstance();
    }

    private void inicializa() {

        inicializaTableColumns();
        chb_ativos.setSelected(true);

        banco.abreConn();
        try {
            atualizaTableView();
        } finally {
            banco.fechaConn();
        }
    }

    private void inicializaTableColumns() {
        tacCodigo.setCellValueFactory(new PropertyValueFactory<>("tacCodigo"));
        tacProduto.setCellValueFactory(new PropertyValueFactory<>("tacProduto"));
    }

    private void atualizaTableView() {

        String filtro = tef_filtro.getText().trim();
        Status status = chb_ativos.isSelected() ? Status.ATIVO : Status.INATIVO;

        List<ListaProdutoTav> registros = produtoDao.getListaProdutoTav(status, filtro);

        if (registros != null) {
            tavProduto.getItems().clear();
            tavProduto.getItems().addAll(registros);
        }

    }

    private void openCadastroProduto() {

    	boolean ok =false;
    	
        produto = ObjectFactory.getProduto();

        banco.abreTransacao();        

        if ( rota.openCadastroProduto(produto)) {
            atualizaTableView();
            ok = true;
        }
        
        banco.fechaTransacao(ok);

    }

    private void atualizaStatusProduto() {
        boolean ok = false;
        banco.abreTransacao();
        try {
            Util.validaTableView(tavProduto);
            produto = produtoDao.getProdutoPeloId(tavProduto.getSelectionModel().getSelectedItem().getId());
            produto.atualizaStatus();
            produtoDao.editar(produto);
            atualizaTableView();
            ok = true;
        } catch (Exception e) {
            mensagem.publica(e.getMessage());
        } finally {
            banco.fechaTransacao(ok);
        }
    }

    private void filtro() {
        banco.abreConn();
        try {
            atualizaTableView();
        } finally {
            banco.fechaConn();
        }
    }

    private void editaProduto() {
        boolean editar = false;
        banco.abreTransacao();
        try {
            Util.validaTableView(tavProduto);
            produto = produtoDao.getProdutoPeloId(tavProduto.getSelectionModel().getSelectedItem().getId());
            
            if (rota.openCadastroProduto(produto)) {
                atualizaTableView();
                editar = true;
            }
        } catch (Exception e) {
            mensagem.publica(e.getMessage());
        } finally {
            banco.fechaTransacao(editar);
        }
    }
}
