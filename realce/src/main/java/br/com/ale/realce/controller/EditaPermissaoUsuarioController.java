package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.interfaces.ModalObject;
import br.com.ale.realce.model.dao.UsuarioDao;
import br.com.ale.realce.model.entidade.Usuario;
import br.com.ale.realce.model.enuns.Privilegio;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EditaPermissaoUsuarioController implements Initializable, ModalObject {

	@FXML
	private Label lbl_usuario;

	@FXML
	private ComboBox<Privilegio> cob_privilegio;

	@FXML
	private Button but_salvar;

	@FXML
	private Button but_cancelar;

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public boolean isOk() {
		return ok;
	}

	@Override
	public void setObject(Object object) {
		this.usuario = (Usuario) object;
		atualizaTela();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		inicializa();
		actions();
	}

	private void inicializa() {
		// inicializa combo permissão
		inicializaComboPermissao();
	}

	private void actions() {
		but_cancelar.setOnAction(e -> cancelar());
		but_cancelar.setOnKeyReleased(e -> cancelar());
		but_salvar.setOnAction(e -> salvar());
		but_salvar.setOnKeyReleased(e -> salvar());
	}

	private boolean ok = false;
	private Stage stage;
	private UsuarioDao usuarioDao;
	private Usuario usuario;

	public EditaPermissaoUsuarioController() {
		super();
		this.usuarioDao = new UsuarioDao();
	}

	private void salvar() {
		usuario.setPrivilegio(cob_privilegio.getSelectionModel().getSelectedItem());
		usuarioDao.editar(usuario);
		ok = true;
		stage.close();
	}

	private void cancelar() {
		ok = false;
		stage.close();
	}

	private void atualizaTela() {
		lbl_usuario.setText(usuario.getUsuario());
		cob_privilegio.getSelectionModel().select(usuario.getPrivilegio());
	}

	private void inicializaComboPermissao() {
		cob_privilegio.getItems().clear();
		if (cob_privilegio.getItems().addAll(Privilegio.values())) {
			cob_privilegio.getSelectionModel().selectFirst();
		}

	}

}
