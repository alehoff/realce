package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.sessao.UsuarioSessao;
import br.com.ale.realce.util.Banco;
import br.com.ale.realce.util.Rota;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MenuUsuarioController implements Initializable {

	@FXML
	private Button but_permissao;

	@FXML
	private Button but_senha;

	@FXML
	private BorderPane bop_menuUsuario;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rota.openListaUsuario(bop_menuUsuario);
		actions();
	}

	private final Rota rota;
	private final Banco banco;

	public MenuUsuarioController() {
		super();
		this.rota = new Rota();
		this.banco = Banco.getInstance();
	}

	private void actions() {

		but_permissao.setOnAction(e -> rota.openListaUsuario(bop_menuUsuario));

		but_senha.setOnAction(e -> {
			banco.abreTransacao();
			banco.fechaTransacao(rota.openCadastroUsuario(UsuarioSessao.getUsuario()));

		});
	}

}
