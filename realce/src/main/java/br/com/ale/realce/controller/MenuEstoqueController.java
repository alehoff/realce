package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.util.Rota;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MenuEstoqueController implements Initializable {

	@FXML
	private BorderPane bop_estoque;

	@FXML
	private Button but_listar;

	@FXML
	private Button but_comprar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rota.openListaEstoque(bop_estoque);
		actions();
	}

	private final Rota rota;

	public MenuEstoqueController() {
		super();
		this.rota = new Rota();
	}

	private void actions() {
		but_listar.setOnAction(e -> rota.openListaEstoque(bop_estoque));
		but_comprar.setOnAction(e -> rota.openListaPedidoCompra(bop_estoque));
	}

}
