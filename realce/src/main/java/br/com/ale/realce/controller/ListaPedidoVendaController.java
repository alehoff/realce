package br.com.ale.realce.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import br.com.ale.realce.model.dao.CaixaDao;
import br.com.ale.realce.model.dao.PedidoVendaDao;
import br.com.ale.realce.model.entidade.Caixa;
import br.com.ale.realce.model.entidade.PedidoVenda;
import br.com.ale.realce.model.tableView.ListaPedidoVendaTav;
import br.com.ale.realce.relatorio.RelatorioPedidoVenda;
import br.com.ale.realce.sessao.UsuarioSessao;
import br.com.ale.realce.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaPedidoVendaController implements Initializable {

    @FXML
    private DatePicker dapDataPedidoVenda;

    @FXML
    private TableView<ListaPedidoVendaTav> tavPedidoVenda;

    @FXML
    private TableColumn<ListaPedidoVendaTav, String> tacNumeroPedido;

    @FXML
    private TableColumn<ListaPedidoVendaTav, String> tacDescricao;

    @FXML
    private TableColumn<ListaPedidoVendaTav, String> tacValorPedido;

    @FXML
    private Button butNovoPedido;

    @FXML
    private Button butImprimir;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        inicializa();

        // abre formulário para novo pedido de venda
        butNovoPedido.setOnAction(e -> {
            novoPedido();
        });
        dapDataPedidoVenda.setOnAction(e -> {
        	atualizaTableView();
        });
        butImprimir.setOnAction(e -> imprimir());
    }

    private PedidoVenda pedidoVenda;
    private final Banco banco;
    private final PedidoVendaDao pedidoVendaDao;
    private final Rota rota;
    private final CaixaDao caixaDao;
    private final Mensagem mensagem;
    private final RelatorioPedidoVenda relatorio;

    public ListaPedidoVendaController() {
        super();
        this.banco = Banco.getInstance();
        this.pedidoVendaDao = new PedidoVendaDao();
        this.pedidoVenda = null;
        this.rota = new Rota();
        this.caixaDao = new CaixaDao();
        this.mensagem = new Mensagem();
        this.relatorio = new RelatorioPedidoVenda();
    }

    private void inicializa() {

        dapDataPedidoVenda.setValue(Datas.getDataAtual());

        inicializaTableColumn();

        atualizaTableView();
    }

    private void atualizaTableView() {
        banco.abreConn();
        try {
            atualizaRegistrosTableView();
        } finally {
            banco.fechaConn();
        }

    }

    private void inicializaTableColumn() {
        tacDescricao.setCellValueFactory(new PropertyValueFactory<>("tacDescricao"));
        tacNumeroPedido.setCellValueFactory(new PropertyValueFactory<>("tacNumeroPedido"));
        tacValorPedido.setCellValueFactory(new PropertyValueFactory<>("tacValorPedido"));
    }

    private void atualizaRegistrosTableView() {
        Date dataConsulta = Datas.converteParaDate(dapDataPedidoVenda.getValue());

        tavPedidoVenda.getItems().clear();
        tavPedidoVenda.getItems().addAll(pedidoVendaDao.getAll(dataConsulta, UsuarioSessao.getUsuario()));
    }

    private void novoPedido() {
        Boolean persistir = false;
        pedidoVenda = ObjectFactory.getPedidoVenda(UsuarioSessao.getUsuario());

        banco.abreTransacao();
        pedidoVenda.setNumero(pedidoVendaDao.getProximoNumeroPedido());
        try {
            persistir = rota.openPedidoVenda(pedidoVenda);

            if (persistir) {

                pedidoVendaDao.salvar(pedidoVenda);

                Caixa caixaUsuario = caixaDao.getCaixaUsuario(UsuarioSessao.getUsuario(), Datas.getDataSistema());

                if (caixaUsuario == null) {
                    caixaUsuario = ObjectFactory.getCaixa(UsuarioSessao.getUsuario());
                }

                caixaUsuario.addHistoricoCaixa(pedidoVenda.getPagamentos());

                if (caixaUsuario.getId() == null) {
                    caixaDao.salvar(caixaUsuario);
                }

                relatorio.relatorioPedidoVenda(pedidoVenda);
                atualizaRegistrosTableView();
            }
        } finally {
           banco.fechaTransacao(persistir);
        }
    }

    private void imprimir() {
    	banco.abreConn();
	try {
			Util.validaTableView(tavPedidoVenda);
			pedidoVenda = pedidoVendaDao.getById(tavPedidoVenda.getSelectionModel().getSelectedItem().getIdPedido());
			relatorio.relatorioPedidoVenda(pedidoVenda);
		} catch (Exception e) {
			mensagem.publica(e.getMessage());
		}finally {
			banco.fechaConn();
			pedidoVenda = null;
		}
    }

}
