package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.interfaces.Modal;
import br.com.ale.realce.model.dao.UsuarioDao;
import br.com.ale.realce.sessao.UsuarioSessao;
import br.com.ale.realce.util.Banco;
import br.com.ale.realce.util.Mensagem;
import br.com.ale.realce.util.ObjectFactory;
import br.com.ale.realce.util.Rota;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable, Modal {

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField pswSenha;

    @FXML
    private Button butLogar;

    @FXML
    private Label lblNaoPossuoAcesso;

    @FXML
    private Label blMensagem;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public boolean isOk() {
        return ok;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        inicializa();
        actions();
    }

    private final UsuarioDao usuarioDao;
    private boolean ok;
    private Stage stage;
    private final Mensagem msg;
    private final Rota rota;
    private final Banco banco;

    public LoginController() {
        this.ok = false;
        this.msg = new Mensagem();
        this.usuarioDao = new UsuarioDao();
        this.rota = new Rota();
        this.banco = Banco.getInstance();
    }

    private void inicializa() {
        txtLogin.setText("");// alessandro
        pswSenha.setText("");// 192901
        txtLogin.requestFocus();
    }

    private void actions() {
        // abre modal de cadastro para novo usuário
        lblNaoPossuoAcesso.setOnMouseClicked(e -> {
            banco.abreTransacao();
            banco.fechaTransacao(openCadastroUsuario());
        });

        // realiza o login do usuario no sistema
        butLogar.setOnAction(e -> {

            banco.abreConn();
            try {
                logar();
            } finally {
                banco.fechaConn();
            }

        });
        butLogar.setOnKeyPressed(e -> {
            banco.abreConn();
            try {
                logar();
            } finally {
                banco.fechaConn();
            }
        });
    }

    private void logar() {
        UsuarioSessao.conecta(usuarioDao.getByUsuario(txtLogin.getText()));
        try {
            UsuarioSessao.usuarioEhCadastrado();
            UsuarioSessao.usuarioApto();
            UsuarioSessao.confereSenha(pswSenha.getText(), blMensagem);
            ok = true;
            stage.close();
        } catch (Exception e) {
            msg.publica(e.getMessage());
        }
    }

    private boolean openCadastroUsuario() {
        return rota.openCadastroUsuario(ObjectFactory.getUsuario());
    }
}
