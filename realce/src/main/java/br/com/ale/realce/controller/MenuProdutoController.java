package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.util.Rota;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MenuProdutoController implements Initializable {

	@FXML
	private BorderPane bop_produto;
	@FXML
	private Button but_cadastro;

	@FXML
	private Button but_tabelaPreco;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rota.openListaProduto(bop_produto);
		actions();
	}

	private Rota rota;

	public MenuProdutoController() {
		this.rota = new Rota();
	}

	private void actions() {
		but_cadastro.setOnAction(e -> rota.openListaProduto(bop_produto));
		but_tabelaPreco.setOnAction(e -> rota.openListaTabelaVenda(bop_produto));
	}

}
