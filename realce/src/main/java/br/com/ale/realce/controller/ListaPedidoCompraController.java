package br.com.ale.realce.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import br.com.ale.realce.model.dao.PedidoCompraDao;
import br.com.ale.realce.model.entidade.PedidoCompra;
import br.com.ale.realce.model.tableView.ListaPedidoCompraTav;
import br.com.ale.realce.sessao.UsuarioSessao;
import br.com.ale.realce.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaPedidoCompraController implements Initializable {

    @FXML
    private DatePicker dat_inicio;

    @FXML
    private DatePicker dat_termino;

    @FXML
    private Button but_filtro;

    @FXML
    private TableView<ListaPedidoCompraTav> tavPedidoCompra;

    @FXML
    private TableColumn<ListaPedidoCompraTav, String> tacData;

    @FXML
    private TableColumn<ListaPedidoCompraTav, String> tacNumero;

    @FXML
    private TableColumn<ListaPedidoCompraTav, String> tacValorTotalPedido;

    @FXML
    private TableColumn<ListaPedidoCompraTav, String> tacItens;

    @FXML
    private Button but_novoPedido;

    @FXML
    private Button but_addItens;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        //configuração inicial tela
        inicializa();

        // Gera um novo pedido para cadastro de itens
        but_novoPedido.setOnAction(e -> {
            novoPedido();
        });

        // adiciona itens ao pedido gerado
        but_addItens.setOnAction(e -> {
            editaPedido();
        });

        // filtra os pedidos cadastrados no sistema pela data selecionada

        dat_inicio.setOnAction(e->{
        	filtraTableView();
        });
        dat_termino.setOnAction(e->{
        	filtraTableView();
        });
    }

    // Classes controller
    private final PedidoCompraDao pedidoCompraDao;
    private final Mensagem mensagem;
    private final Rota rota;
    private final Banco banco;

    public ListaPedidoCompraController() {
        super();
       this.banco = Banco.getInstance();
        this.pedidoCompraDao = new PedidoCompraDao();
        this.tavPedidoCompra = null;
        this.mensagem = new Mensagem();
        this.rota = new Rota();
    }

    private void inicializa() {

        dat_inicio.setValue(Datas.getInicioMes());

        dat_termino.setValue(Datas.getDataAtual());

        inicializaTableColumns();

        filtraTableView();

    }

    private void filtraTableView(){
        banco.abreConn();
        try{
            atualizaTableView();
        }finally {
            banco.fechaConn();
        }
    }

    private void inicializaTableColumns(){
        tacData.setCellValueFactory(new PropertyValueFactory<>("tacData"));
        tacItens.setCellValueFactory(new PropertyValueFactory<>("tacItens"));
        tacNumero.setCellValueFactory(new PropertyValueFactory<>("tacNumero"));
        tacValorTotalPedido.setCellValueFactory(new PropertyValueFactory<>("tacValorTotalPedido"));
    }

    private void atualizaTableView() {
        //limpa tabela
        tavPedidoCompra.getItems().clear();
        //seleciona datas
        Date inicio = Datas.converteParaDate(dat_inicio.getValue());
        Date termino = Datas.converteParaDate(dat_termino.getValue());
        //atualiza tabela
        tavPedidoCompra.getItems().addAll(pedidoCompraDao.getAll(inicio, termino));
    }

    private void editaPedido() {

        boolean persistir = false;

         banco.abreTransacao();

        try {
            Util.validaTableView(tavPedidoCompra);
              PedidoCompra pedidoCompra = pedidoCompraDao.getById(tavPedidoCompra.getSelectionModel().getSelectedItem().getIdPedidoCompra());
              persistir = rota.openEditaPedido(pedidoCompra);
            if (persistir) {
                 pedidoCompraDao.editar(pedidoCompra);
                 atualizaTableView();
              }
        } catch (Exception e) {
            mensagem.publica(e.getMessage());
        } finally {
             banco.fechaTransacao(persistir);
        }
    }

    private void novoPedido() {

        boolean persistir = false;

        banco.abreTransacao();
        try {
            // verifica se existem pedidos em aberto
            if (pedidoCompraDao.pedidoPossuiItensCadastrados()) {
                throw new Exception("Existem pedidos sem itens");
            }

            PedidoCompra pedidoCompra = ObjectFactory.getPedidoCompra(UsuarioSessao.getUsuario());
            pedidoCompra.setNumero(pedidoCompraDao.getProximoNumeroPedido());

            persistir = rota.openEditaPedido(pedidoCompra);

            if (persistir) {
                pedidoCompraDao.salvar(pedidoCompra);
                atualizaTableView();
            }

        } catch (Exception e) {
            mensagem.publica(e.getMessage());
        } finally {
            banco.fechaTransacao(persistir);
        }
    }


}
