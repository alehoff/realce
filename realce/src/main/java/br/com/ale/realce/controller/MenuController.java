package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.sessao.UsuarioSessao;
import br.com.ale.realce.util.Rota;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MenuController implements Initializable {

	private Rota rota;

	@FXML
	private BorderPane bopMenu;
	@FXML
	private Button butUsuario;

	@FXML
	private Button butProduto;

	@FXML
	private Button butEstoque;

	@FXML
	private Button butCaixa;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if(!UsuarioSessao.possuiAcesso()) {
			butCaixa.setDisable(true);
			butEstoque.setDisable(true);
			butProduto.setDisable(true);
			butUsuario.setDisable(true);
		}
		
		rota.openMenuPedidoVenda(bopMenu);
	}

	public MenuController() {
		this.rota = new Rota();
	}

	@FXML
	void estoqueOnAction(ActionEvent event) {
		rota.openMenuEstoque(bopMenu);
	}

	@FXML
	void produtosOnAction(ActionEvent event) {
		rota.openMenuProduto(bopMenu);
	}

	@FXML
	void usuariosOnAction(ActionEvent event) {
		rota.openMenuUsuario(bopMenu);
	}

	@FXML
	void vendasOnAction(ActionEvent event) {
		rota.openMenuPedidoVenda(bopMenu);
	}

	@FXML
	void caixaOnAction(ActionEvent event) {
		rota.openMenuCaixa(bopMenu);
	}

}
