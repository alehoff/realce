package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.interfaces.ModalObject;
import br.com.ale.realce.model.dao.EstoqueDao;
import br.com.ale.realce.model.entidade.Estoque;
import br.com.ale.realce.util.Mensagem;
import br.com.ale.realce.util.Util;
import br.com.ale.realce.util.ValidaUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class EditaValorMinimoController implements ModalObject, Initializable {

	@FXML
	Label lblProduto;

	@FXML
	Label lblMaiorQuantidade;

	@FXML
	Label lblMenorQuantidade;

	@FXML
	Label lblMediaVenda;

	@FXML
	TextField tefCaixa;

	@FXML
	TextField tefUnidade;

	@FXML
	Button butSalvar;

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
		this.estoque = (Estoque) object;
		atualizaAtributosTela();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		butSalvar.setOnAction(e -> {
			salvar();
		});
		butSalvar.setOnKeyReleased(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				salvar();
			}
		});
	}

	private Estoque estoque;
	private Stage stage;
	private boolean ok;
	private final EstoqueDao estoqueDao;
	private final Mensagem mensagem;
	private final ValidaUtil<Estoque> valEstoque;

	public EditaValorMinimoController() {
		super();
		this.ok = false;
		this.estoqueDao = new EstoqueDao();
		this.valEstoque = new ValidaUtil<Estoque>();
		this.mensagem = new Mensagem();
	}

	private void atualizaAtributosTela() {

		if (estoque != null) {
			try {
				String maior = Util.toCaixaUnidade(estoqueDao.getMaiorVendaProduto(estoque.getProduto().getId()),
						estoque.getProduto().getAtacado());
				lblMaiorQuantidade.setText(maior);
				String menor = Util.toCaixaUnidade(estoqueDao.getMenorVendaProduto(estoque.getProduto().getId()),
						estoque.getProduto().getAtacado());
				lblMenorQuantidade.setText(menor);
				lblMediaVenda
						.setText(Util.arredondaDouble(estoqueDao.getMediarVendaProduto(estoque.getProduto().getId())));
				lblProduto.setText(estoque.getProduto().getDescricaoProduto());
				tefCaixa.setText("");
				tefUnidade.setText("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void salvar() {

		Integer caixa = Util.toInteger(tefCaixa.getText());
		Integer unidade = Util.toInteger(tefUnidade.getText());
		Integer atacado = estoque.getProduto().getAtacado();

		try {
			estoque.setMinimo(Util.getValorCaixaUnidade(caixa, unidade, atacado));
			valEstoque.valida(estoque);
			estoqueDao.editar(estoque);
			// finaliza tela
			ok = true;
			stage.close();
		} catch (Exception e) {
			mensagem.publica(e.getMessage());
		}
	}

}
