package br.com.ale.realce.controller;

import br.com.ale.realce.interfaces.ModalObject;
import br.com.ale.realce.model.dao.ProdutoDao;
import br.com.ale.realce.model.entidade.Produto;
import br.com.ale.realce.util.Mensagem;
import br.com.ale.realce.util.Util;
import br.com.ale.realce.util.ValidaUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditaProdutoController implements Initializable, ModalObject {

    @FXML
    private TextField tefNomeProduto;

    @FXML
    private TextField tefMargemLucro;

    @FXML
    private TextField tefCodigoBarras;

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
        return ok;
    }

    @Override
    public void setObject(Object object) {
        this.produto = (Produto) object;
        atualizaAtributoTela();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //ações usuário
        butCancelar.setOnKeyReleased(event -> cancelar());
        butCancelar.setOnAction(event -> cancelar());

        butSalvar.setOnAction(event -> salvar());
        butSalvar.setOnKeyPressed(event -> salvar());

    }

    private boolean ok = false;
    private Stage stage;
    private Produto produto;
    private Mensagem mensagem;
    private ValidaUtil<Produto> valida;
    private ProdutoDao produtoDao;

    public EditaProdutoController() {
        this.mensagem = new Mensagem();
        this.valida = new ValidaUtil<>();
        this.produtoDao = new ProdutoDao();
    }

    private void atualizaAtributoTela() {
        if (produto.getId() != null) {
            tefNomeProduto.setText(produto.getNome());
            tefCodigoBarras.setText(produto.getCodigoBarra());
            tefMargemLucro.setText(Util.toString(produto.getMargemLucro()));
            tefNomeProduto.requestFocus();
        }
    }

    private void cancelar(){
        ok=false;
        stage.close();
    }

    private void salvar(){

        try{
            //atualiza atributo produto
            produto.setNome(tefNomeProduto.getText());
            produto.setCodigoBarra(tefCodigoBarras.getText());
            produto.setMargemLucro(Util.toDouble(tefMargemLucro.getText()));

            //valida atributo
            valida.valida(produto);

            //valida banco de dados
            valida.ehCadastrado(produtoDao.ehCadastrado(produto),Produto.class);

            //encerra tela
            ok=true;
            stage.close();

        }catch (Exception e){
            mensagem.publica(e.getMessage());
        }
    }
}
