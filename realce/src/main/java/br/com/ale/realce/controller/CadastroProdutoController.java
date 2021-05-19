package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.interfaces.ModalObject;
import br.com.ale.realce.model.dao.CategoriaDao;
import br.com.ale.realce.model.dao.ProdutoDao;
import br.com.ale.realce.model.dao.VolumeDao;
import br.com.ale.realce.model.entidade.Categoria;
import br.com.ale.realce.model.entidade.Produto;
import br.com.ale.realce.model.entidade.Volume;
import br.com.ale.realce.model.enuns.Status;
import br.com.ale.realce.util.Mensagem;
import br.com.ale.realce.util.ValidaUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class CadastroProdutoController implements Initializable, ModalObject {

	@FXML
	private ComboBox<Categoria> cob_categoria;

	@FXML
	private Button but_addCategoria;

	@FXML
	private ComboBox<Volume> cob_volume;

	@FXML
	private Button but_addVolume;

	@FXML
	private ComboBox<Status> cob_status;

	@FXML
	private TextField tef_nomeProduto;

	@FXML
	private Label lbl_codigoLeitura;

	@FXML
	private TextField tef_codigoBarra;

	@FXML
	private TextField tef_atacado;

	@FXML
	private TextField tef_margemLucro;

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
		return this.ok;
	}

	@Override
	public void setObject(Object object) {
		this.produto = (Produto) object;
		atualizaAtributosTela();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		inicializa();
		actions();
	}

	private Stage stage;
	private boolean ok;
	private Produto produto;
	private final ProdutoDao prodDao;
	private final ValidaUtil<Produto> prodVal;
	private final CategoriaDao catDao;
	private final ValidaUtil<Categoria> catVal;
	private final ValidaUtil<Volume> volVal;
	private final VolumeDao volDao;
	private final Mensagem mensagem;

	public CadastroProdutoController() {
		super();
		this.prodDao = new ProdutoDao();
		this.catDao = new CategoriaDao();
		this.volDao = new VolumeDao();
		this.mensagem = new Mensagem();
		this.catVal = new ValidaUtil<>();
		this.volVal = new ValidaUtil<>();
		this.prodVal = new ValidaUtil<>();
	}

	private void actions() {

		but_addCategoria.setOnAction(e -> addCategoria());
		but_addVolume.setOnAction(e -> addVolume());
		cob_categoria.setOnAction(e -> proximoCodigoProduto());
		cob_volume.setOnAction(e -> proximoCodigoProduto());
		but_cancelar.setOnAction(e -> cancelar());
		but_cancelar.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				cancelar();
			}
		});
		but_salvar.setOnAction(e -> salvar());
		but_salvar.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				salvar();
			}
		});
	}

	private void inicializa() {
		atualizaComboCategoria();
		atualizaComboVolume();
		atualizaComboStatus();
	}

	private void atualizaComboStatus() {
		this.cob_status.getItems().clear();
		this.cob_status.getItems().addAll(Status.values());
		this.cob_status.getSelectionModel().selectFirst();
	}

	private void atualizaComboVolume() {

		this.cob_volume.getItems().clear();
		this.cob_volume.getItems().addAll(this.volDao.getAll());
		if (this.cob_volume.getItems().size() > 0) {
			this.cob_volume.getSelectionModel().selectFirst();
		}
	}

	private void atualizaComboCategoria() {
		cob_categoria.getItems().clear();
		cob_categoria.getItems().addAll(catDao.getAll());
		if (cob_categoria.getItems().size() > 0) {
			cob_categoria.getSelectionModel().selectFirst();
		}
	}

	private void atualizaAtributosTela() {

		boolean preenche = produto.getId() != null;

		tef_atacado.setText(preenche ? produto.getAtacadoToString() : "");
		tef_codigoBarra.setText(preenche ? produto.getCodigoBarra() : "");
		tef_margemLucro.setText(preenche ? produto.getMargemLucroToString() : "");
		tef_nomeProduto.setText(preenche ? produto.getNome() : "");
		if (preenche) {
			lbl_codigoLeitura.setText(produto.getCodigoLeitura());
			cob_categoria.getSelectionModel().select(produto.getCategoria());
			cob_status.getSelectionModel().select(produto.getStatus());
			cob_volume.getSelectionModel().select(produto.getVolume());
		} else {
			proximoCodigoProduto();
		}

		tef_nomeProduto.requestFocus();

	}

	private void addCategoria() {

		String texto = mensagem.questiona("Digite nova Categoria :");

		if (texto.isEmpty()) {

			mensagem.publica("Categoria não cadastrada!!");

		} else {

			Categoria categoria = new Categoria();
			categoria.setNome(texto);
			categoria.setCodigo(catDao.getProximoCodigo());

			try {
				catVal.valida(categoria);// atributos
				catVal.ehCadastrado(catDao.ehCadastrado(categoria), Categoria.class);// banco dados
				catDao.salvar(categoria);
				atualizaComboCategoria();
				proximoCodigoProduto();
			} catch (Exception e) {
				mensagem.publica(e.getMessage());
			}
		}
	}

	private void addVolume() {

		String texto = mensagem.questiona("Digite novo Volume :");

		if (texto.isEmpty()) {
			mensagem.publica("Volume não cadastrado!!");
		} else {
			try {
				Volume volume = new Volume();
				volume.setCodigo(volDao.getProximoCodigo());
				volume.setVolume(texto);

				volVal.valida(volume);
				volVal.ehCadastrado(volDao.ehCadastrado(volume), Volume.class);

				volDao.salvar(volume);

				atualizaComboVolume();

				proximoCodigoProduto();

			} catch (Exception e) {
				mensagem.publica(e.getMessage());
			}
		}
	}

	private void proximoCodigoProduto() {

		if (cob_categoria.getItems().size() > 0 && cob_volume.getItems().size() > 0) {

			Categoria categoria = cob_categoria.getSelectionModel().getSelectedItem();
			Volume volume = cob_volume.getSelectionModel().getSelectedItem();

			produto.setCodigo(prodDao.getProximoCodigo(categoria.getId(), volume.getId()));

			produto.setCodigoLeitura(categoria.getCodigo().toString() + "." + volume.getCodigo().toString()
					+ produto.getCodigo().toString());

			lbl_codigoLeitura.setText(produto.getCodigoLeitura());

		}
	}

	private void salvar() {

		try {
			produto.setAtacado(tef_atacado.getText());
			produto.setCodigoBarra(tef_codigoBarra.getText());
			produto.setMargemLucro(tef_margemLucro.getText());
			produto.setStatus(cob_status.getSelectionModel().getSelectedItem());
			produto.setNome(tef_nomeProduto.getText());
			produto.setCategoria(cob_categoria.getSelectionModel().getSelectedItem());
			produto.setVolume(cob_volume.getSelectionModel().getSelectedItem());

			prodVal.valida(produto);
			prodVal.ehCadastrado(prodDao.ehCadastrado(produto), Produto.class);

			prodDao.salvar(produto);

			ok = true;
			stage.close();

		} catch (Exception e) {
			mensagem.publica(e.getMessage());
		}
	}

	private void cancelar() {
		ok = false;
		stage.close();
	}

}
