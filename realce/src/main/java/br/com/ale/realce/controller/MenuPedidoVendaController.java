package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.util.Rota;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MenuPedidoVendaController implements Initializable {

	@FXML
	private BorderPane bopPedidoVenda;

	@FXML
	private Button butPedidos;

	@FXML
	private Button butCaixa;

	@FXML
	private Button butTabela;

	private final Rota rota;

	public MenuPedidoVendaController() {
		super();
		this.rota = new Rota();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rota.openListaPedidoVenda(bopPedidoVenda);
	}

	@FXML
	void caixaOnAction(ActionEvent event) {
		rota.openListaCaixaParaUsuario(bopPedidoVenda);
	}

	@FXML
	void pedidoOnAction(ActionEvent event) {
		rota.openListaPedidoVenda(bopPedidoVenda);
	}

	@FXML
	void tabelaOnAction(ActionEvent event) {
		rota.openConsultaTabelaVenda(bopPedidoVenda);
	}

}
