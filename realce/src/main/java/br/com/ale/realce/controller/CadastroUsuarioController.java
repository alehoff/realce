package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.interfaces.ModalObject;
import br.com.ale.realce.model.dao.UsuarioDao;
import br.com.ale.realce.model.entidade.Usuario;
import br.com.ale.realce.util.Mensagem;
import br.com.ale.realce.util.ValidaUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroUsuarioController implements Initializable, ModalObject {

	@FXML
	private TextField tefLogin;

	@FXML
	private PasswordField pswSenha;

	@FXML
	private Button butSalvar;

	@FXML
	private Button butCancelar;

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public boolean isOk() {
		return false;
	}

	@Override
	public void setObject(Object object) {
		this.usuario = (Usuario) object;
		atualizaAtributosTela();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		inicializa();
		actions();
	}

	private Stage stage;
	private UsuarioDao dao;
	private Usuario usuario;
	private ValidaUtil<Usuario> validar;
	private Mensagem mensagem;

	public CadastroUsuarioController() {
		super();
		this.dao = new UsuarioDao();
		this.validar = new ValidaUtil<Usuario>();
		this.mensagem = new Mensagem();
	}

	private void actions() {

		butCancelar
			.setOnAction(e -> cancelar());
		butCancelar
			.setOnKeyReleased(e -> cancelar());
		butSalvar
			.setOnAction(e -> salvar());
		butSalvar
			.setOnKeyPressed(e -> salvar());
	}

	private void inicializa() {
		tefLogin
			.clear();
		pswSenha
			.clear();
		tefLogin
			.requestFocus();
	}

	private void cancelar() {
		stage
			.close();
	}

	private void salvar() {

		boolean usuarioEhCadastrado = usuario.getId() != null;

		usuario
			.setUsuario(tefLogin.getText());
		usuario
			.setSenha(pswSenha.getText());		

		try {
			validar
				.valida(usuario);

			if (dao.ehCadastrado(usuario)) {
				throw new Exception("Usuário possui cadastro");
			}

			if (!usuarioEhCadastrado) {
				dao
					.salvar(usuario);
			} else {
				dao
					.editar(usuario);
			}

			String msg = usuarioEhCadastrado ? "Senha alterada com sucesso" : "Cadastro realizado com Sucesso!";
			mensagem
				.publica(msg);

			stage
				.close();

		} catch (Exception e) {
			mensagem
				.publica(e.getMessage());
		}
	}

	private void atualizaAtributosTela() {

		if (usuario.getId() != null) {
			tefLogin
				.setText(usuario.getUsuario());
			tefLogin
				.setEditable(false);
			pswSenha
				.requestFocus();
		} else {
			tefLogin
				.clear();
		}
		pswSenha
			.clear();		
	}

}
