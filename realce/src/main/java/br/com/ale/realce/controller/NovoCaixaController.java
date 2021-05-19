package br.com.ale.realce.controller;

import br.com.ale.realce.interfaces.ModalObject;
import br.com.ale.realce.model.comboBox.UsuarioCB;
import br.com.ale.realce.model.dao.CaixaDao;
import br.com.ale.realce.model.dao.UsuarioDao;
import br.com.ale.realce.model.entidade.Caixa;
import br.com.ale.realce.util.Datas;
import br.com.ale.realce.util.Mensagem;
import br.com.ale.realce.util.Util;
import br.com.ale.realce.util.ValidaUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class NovoCaixaController implements Initializable, ModalObject {

    @FXML
    private DatePicker dtpData;

    @FXML
    private ComboBox<UsuarioCB> cobUsuario;

    @FXML
    private TextField tefDescricao;

    @FXML
    private TextField tefValor;

    @FXML
    private Button butCredito;

    @FXML
    private Button butDebito;

    @FXML
    private Button butCancelar;

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
        this.caixa = (Caixa) object;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializa();

        butCancelar.setOnAction(e -> cancelar());
        butCancelar.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                cancelar();
            }
        });

        butCredito.setOnAction(e -> credito());
        butCredito.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                credito();
            }
        });

        butDebito.setOnAction(e -> debito());
        butDebito.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                debito();
            }
        });
    }

    private boolean ok = false;
    private Caixa caixa = null;
    private Stage stage;
    private UsuarioDao usuarioDao;
    private final Mensagem mensagem;
    private final ValidaUtil<Caixa> validaCaixa;
    private final CaixaDao caixaDao;

    public NovoCaixaController() {
        super();
        this.usuarioDao = new UsuarioDao();
        this.mensagem = new Mensagem();
        this.validaCaixa = new ValidaUtil<Caixa>();
        this.caixaDao = new CaixaDao();
    }

    private void inicializa() {
        atualizaComboUsuario();
        tefDescricao.clear();
        tefValor.clear();
        dtpData.setValue(Datas.getDataAtual());
    }

    private void atualizaComboUsuario() {
        cobUsuario.getItems().clear();
        cobUsuario.getItems().addAll(usuarioDao.getAll());
        cobUsuario.getSelectionModel().selectFirst();
    }

    private void cancelar() {
        ok = false;
        stage.close();
    }

    private void credito() {
        try {
            validaNovoCaixa();
            caixa.setData(Datas.converteParaDate(dtpData.getValue()));
            caixa.setUsuario(usuarioDao.getByUsuario(cobUsuario.getSelectionModel().getSelectedItem().getUsuario()));
            caixa.setCredito(Util.stringParaBigDecimal(tefValor.getText()), tefDescricao.getText(),
                    Datas.converteParaDate(dtpData.getValue()));
            validaCaixa.valida(caixa);
            ok = true;
            stage.close();
        } catch (Exception e) {
            mensagem.publica(e.getMessage());
        }
    }

    private void debito() {
        try {
            validaNovoCaixa();
            caixa.setData(Datas.converteParaDate(dtpData.getValue()));
            caixa.setUsuario(usuarioDao.getByUsuario(cobUsuario.getSelectionModel().getSelectedItem().getUsuario()));
            caixa.setDebito(Util.stringParaBigDecimal(tefValor.getText()), tefDescricao.getText(),
                    Datas.converteParaDate(dtpData.getValue()));
            validaCaixa.valida(caixa);
            ok = true;
            stage.close();
        } catch (Exception e) {
            mensagem.publica(e.getMessage());
        }
    }

    /**
     * Verifica se caixa a ser criado já existe banco de dados
     *
     * @throws Exception
     */
    private void validaNovoCaixa() throws Exception {
        if (caixaDao.getCaixaUsuario(usuarioDao.getByUsuario(cobUsuario.getSelectionModel().getSelectedItem().getUsuario()), Datas.converteParaDate(dtpData.getValue())) != null) {
            throw new Exception("Caixa já existe sistema!!");
        }
    }

}
