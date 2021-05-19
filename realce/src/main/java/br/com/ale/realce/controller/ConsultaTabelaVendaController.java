package br.com.ale.realce.controller;

import br.com.ale.realce.model.dao.ProdutoDao;
import br.com.ale.realce.model.tableView.ListaTabelaVendaTav;
import br.com.ale.realce.util.Banco;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConsultaTabelaVendaController implements Initializable {

    @FXML
    private TextField tefProdutoCategoria;

    @FXML
    private Button butFiltro;

    @FXML
    private TableView<ListaTabelaVendaTav> tavTabelaVenda;

    @FXML
    private TableColumn<ListaTabelaVendaTav, String> tacProduto;

    @FXML
    private TableColumn<ListaTabelaVendaTav, String> tacUnidade;

    @FXML
    private TableColumn<ListaTabelaVendaTav, String> tacCaixa;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        inicializa();

        butFiltro.setOnAction(e -> filtraTableview());
        butFiltro.setOnKeyPressed(e -> filtraTableview());
        tefProdutoCategoria.setOnKeyReleased(e -> filtraTableview());
    }

    private final Banco banco;
    private final ProdutoDao produtoDao;

    public ConsultaTabelaVendaController() {
        super();
        this.banco = Banco.getInstance();
        this.produtoDao = new ProdutoDao();
    }

    private void inicializa() {
        inicializaTableColumns();
        filtraTableview();
    }

    private void filtraTableview() {
        banco.abreConn();
        try {
            atualizaTableView();
        } finally {
            banco.fechaConn();
        }
    }

    private void atualizaTableView() {

        String filtro = tefProdutoCategoria.getText().trim();

        tavTabelaVenda.getItems().clear();
        tavTabelaVenda.getItems().addAll(produtoDao.getListaTabelaVenda(filtro));
    }

    private void inicializaTableColumns() {

        tacCaixa.setCellValueFactory(new PropertyValueFactory<>("tacAtacado"));
        tacProduto.setCellValueFactory(new PropertyValueFactory<>("tacProduto"));
        tacUnidade.setCellValueFactory(new PropertyValueFactory<>("tacUnidadeEmReais"));
    }

}
