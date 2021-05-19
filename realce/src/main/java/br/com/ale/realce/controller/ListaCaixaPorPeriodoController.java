package br.com.ale.realce.controller;

import br.com.ale.realce.model.dao.CaixaDao;
import br.com.ale.realce.model.entidade.Caixa;
import br.com.ale.realce.model.tableView.ListaCaixaPorPeriodoTav;
import br.com.ale.realce.util.Banco;
import br.com.ale.realce.util.Datas;
import br.com.ale.realce.util.Mensagem;
import br.com.ale.realce.util.ObjectFactory;
import br.com.ale.realce.util.Rota;
import br.com.ale.realce.util.Util;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaCaixaPorPeriodoController implements Initializable {

    @FXML
    private DatePicker dtpInicio;

    @FXML
    private DatePicker dtpTermino;

    @FXML
    private TableView<ListaCaixaPorPeriodoTav> tavCaixa;

    @FXML
    private TableColumn<ListaCaixaPorPeriodoTav, String> tacUsuario;

    @FXML
    private TableColumn<ListaCaixaPorPeriodoTav, String> tacData;

    @FXML
    private TableColumn<ListaCaixaPorPeriodoTav, String> tacSaldo;

    @FXML
    private TableColumn<ListaCaixaPorPeriodoTav, Long> tacTotalPedidos;

    @FXML
    void dtpInicioOnAction(ActionEvent event) {
        consultaItensTabelaBancoDados();
    }

    @FXML
    void dtpTerminoOnAction(ActionEvent event) {
        consultaItensTabelaBancoDados();
    }

    @FXML
    void lancamentosOnAction(ActionEvent event) {
        addHistoricoCaixa();
    }

    @FXML
    void imprimirOnAction(ActionEvent event) {

    }

    @FXML
    private Button butNovoCaixa;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        inicializa();

        butNovoCaixa.setOnAction(e -> novoCaixa());
    }

    private final CaixaDao caixaDao;
    private final Mensagem mensagem;
    private final Banco banco;
    private Caixa caixa;
    private final Rota rota;

    public ListaCaixaPorPeriodoController() {
        super();
        this.caixaDao = ObjectFactory.getCaixaDao();
        this.mensagem = ObjectFactory.getMensagem();
        this.banco = Banco.getInstance();
        this.caixa = null;
        this.rota = ObjectFactory.getRota();
    }

    private void inicializa() {
        dtpInicio.setValue(Datas.getInicioMes());
        dtpTermino.setValue(Datas.getDataAtual());

        inicializaTableColumns();

        consultaItensTabelaBancoDados();
    }

    private void inicializaTableColumns() {
        tacData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tacSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        tacTotalPedidos.setCellValueFactory(new PropertyValueFactory<>("totalPedidos"));
        tacUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
    }

    private void consultaItensTabelaBancoDados() {
        banco.abreConn();
        try {
            atualizaTableView();
        } finally {
            banco.fechaConn();
        }
    }

    private void atualizaTableView() {
        Date inicio = Datas.converteParaDate(dtpInicio.getValue());
        Date termino = Datas.converteParaDate(dtpTermino.getValue());

        if (Datas.ehSuperior(inicio, termino)) {
            tavCaixa.getItems().clear();
            tavCaixa.getItems().addAll(caixaDao.getCaixaPorPeriodo(inicio, termino));
        } else {
            mensagem.publica("Data inicial/final inválida para período");
        }
    }

    private void addHistoricoCaixa() {
        boolean ok = false;
        banco.abreTransacao();
        try {
            Util.validaTableView(tavCaixa);
            caixa = caixaDao.getCaixa(tavCaixa.getSelectionModel().getSelectedItem().getId());
            if (caixa != null) {
                ok = rota.openLancamentoHistoricoCaixa(caixa);
                if (ok) {
                    atualizaTableView();
                }
            } else {
                mensagem.publica("Erro ao localizar Caixa");
            }
        } catch (Exception e) {
            mensagem.publica(e.getMessage());
        } finally {
            banco.fechaTransacao(ok);
        }
    }

    private void novoCaixa() {

        caixa = ObjectFactory.getCaixa(null);
        boolean ok = false;

        banco.abreTransacao();
        try {
            ok = rota.openNovoCaixa(caixa);
            if (ok) {
                caixaDao.salvar(caixa);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            banco.fechaTransacao(ok);
        }
    }

}
