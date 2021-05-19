package br.com.ale.realce.controller;

import br.com.ale.realce.interfaces.ModalObject;
import br.com.ale.realce.model.entidade.Caixa;
import br.com.ale.realce.util.Datas;
import br.com.ale.realce.util.MascarasFX;
import br.com.ale.realce.util.Mensagem;
import br.com.ale.realce.util.ObjectFactory;
import br.com.ale.realce.util.Util;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LancamentoHistoricoCaixaController implements Initializable, ModalObject {

    @FXML
    private TextField tefDescricao;

    @FXML
    private TextField tefValor;

    @FXML
    void creditoOnAction(ActionEvent event) {
        credito();
    }

    @FXML
    void creditoOnKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            credito();
        }
    }

    @FXML
    void debitoOnAction(ActionEvent event) {
        debito();
    }

    @FXML
    void debitoOnKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            debito();
        }
    }

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
        caixa = (Caixa) object;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        MascarasFX.mascaraNumero(tefValor);
    }

    private boolean ok = false;
    private Caixa caixa;
    private Stage stage;
    private final Mensagem mensagem;

    public LancamentoHistoricoCaixaController() {
        super();
        this.mensagem = ObjectFactory.getMensagem();
    }

    private void debito() {
        try {
            // add caixa
            caixa.setDebito(Util.stringParaBigDecimal(tefValor.getText()), tefDescricao.getText(), Datas.getDataSistema());

            // fecha tela
            ok = true;
            stage.close();

        } catch (Exception e) {
            mensagem.publica("Verifique se os valores informados estão corretos");
        }
    }

    private void credito() {

        try {
            // add caixa
            caixa.setCredito(Util.stringParaBigDecimal(tefValor.getText()), tefDescricao.getText(), Datas.getDataSistema());

            // fecha tela
            ok = true;
            stage.close();

        } catch (Exception e) {
            mensagem.publica("Verifique se os valores informados estão corretos");
        }
    }

}
