package br.com.ale.realce.controller;

import br.com.ale.realce.model.dao.CaixaDao;
import br.com.ale.realce.model.entidade.Caixa;
import br.com.ale.realce.model.entidade.HistoricoCaixa;
import br.com.ale.realce.sessao.UsuarioSessao;
import br.com.ale.realce.util.Banco;
import br.com.ale.realce.util.Datas;
import br.com.ale.realce.util.ObjectFactory;
import br.com.ale.realce.util.Util;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaCaixaParaUsuariocontroller implements Initializable {

    @FXML
    private Label lblCaixa;

    @FXML
    private Label lblSaldo;

    @FXML
    private Label lblDinheiro;

    @FXML
    private Label lblCartao;

    @FXML
    private DatePicker dtpDataCaixa;

    @FXML
    private TableView<HistoricoCaixa> tavHistoricoCaixa;

    @FXML
    private TableColumn<HistoricoCaixa, String> tacHorario;

    @FXML
    private TableColumn<HistoricoCaixa, String> tacDescricao;

    @FXML
    private TableColumn<HistoricoCaixa, String> tacOperacao;

    @FXML
    private TableColumn<HistoricoCaixa, String> tacValor;

    @FXML
    private TableColumn<HistoricoCaixa, String> tacSaldo;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        inicializa();
        actions();
    }

    private final CaixaDao caixaDao;
    private final Banco banco;
    private Caixa caixa;

    public ListaCaixaParaUsuariocontroller() {
        super();
        this.banco = Banco.getInstance();
        this.caixaDao = ObjectFactory.getCaixaDao();
        this.caixa = null;
    }

    private void inicializa() {
        dtpDataCaixa.setValue(Datas.getDataAtual());
        inicializaTableColumn();
        getCaixaParaUsuario();
    }

    private void actions() {
        dtpDataCaixa.setOnAction(e -> {
            getCaixaParaUsuario();
        });
    }

    private void inicializaTableColumn() {
        tacDescricao.setCellValueFactory(new PropertyValueFactory<>("tacDescricao"));
        tacHorario.setCellValueFactory(new PropertyValueFactory<>("tacHorario"));
        tacOperacao.setCellValueFactory(new PropertyValueFactory<>("tacOperacao"));
        tacSaldo.setCellValueFactory(new PropertyValueFactory<>("tacSaldo"));
        tacValor.setCellValueFactory(new PropertyValueFactory<>("tacValor"));
    }

    private void getCaixaParaUsuario() {

        banco.abreConn();
        try {
            caixa = caixaDao.getCaixaUsuario(UsuarioSessao.getUsuario(),
                    Datas.converteParaDate(dtpDataCaixa.getValue()));
            if (caixa != null) {
                lblCaixa.setText("Usuário: " + UsuarioSessao.getUsuario().getUsuario());
                lblSaldo.setText("Saldo Caixa: " + Util.toMoeda(caixa.getSaldo()));
                calculaSomaCartaoDinheiro(caixa.getHistoricosCaixa());
                atualizaTableView(caixa.getHistoricosCaixa());
            } else {
                tavHistoricoCaixa.getItems().clear();
                lblCaixa.setText("");
                lblCartao.setText("");
                lblDinheiro.setText("");
                lblSaldo.setText("");
            }
        } finally {
            banco.fechaConn();
        }

    }

    private void calculaSomaCartaoDinheiro(List<HistoricoCaixa> itens) {
        BigDecimal dinheiro = Util.stringParaBigDecimal("0.0");
        BigDecimal cartao = Util.stringParaBigDecimal("0.0");

        for (HistoricoCaixa hc : itens) {
            if (hc.getDescricao().contains("Dinheiro")) {
                dinheiro = dinheiro.add(hc.getValor());
            } else {
                cartao = cartao.add(hc.getValor());
            }
        }
        lblCartao.setText("Pagamentos em Cartão: " + Util.toMoeda(cartao));
        lblDinheiro.setText("Pagamentos em Dinheiro: " + Util.toMoeda(dinheiro));
    }

    private void atualizaTableView(List<HistoricoCaixa> itens) {
        tavHistoricoCaixa.getItems().clear();
        tavHistoricoCaixa.getItems().addAll(itens);
    }

}
