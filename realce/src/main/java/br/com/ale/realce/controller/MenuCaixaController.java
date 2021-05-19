package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.util.ObjectFactory;
import br.com.ale.realce.util.Rota;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class MenuCaixaController implements Initializable {

	@FXML
	private BorderPane bopCaixa;

	@FXML
	void usuariosOnAction(ActionEvent event) {
		rota.openListaCaixaPorPeriodo(bopCaixa);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rota.openListaCaixaPorPeriodo(bopCaixa);
	}

	private final Rota rota;

	public MenuCaixaController() {
		super();
		this.rota = ObjectFactory.getRota();
	}

}
