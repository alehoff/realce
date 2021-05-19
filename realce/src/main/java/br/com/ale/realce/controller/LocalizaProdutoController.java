package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.interfaces.ModalConsulta;
import br.com.ale.realce.model.dao.ProdutoDao;
import br.com.ale.realce.model.entidade.Produto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class LocalizaProdutoController implements ModalConsulta, Initializable {

	@FXML
	private TableView<Produto> tav_produto;
	@FXML
	private TableColumn<Produto,String> tac_nomeProduto;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		inicializa();
		actions();
	}

	@Override
	public void setObject(Object object) {
		atualizaTableView(object.toString());
	}

	@Override
	public Object getObject() {
		return produto;
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private ProdutoDao produtoDao;
	private Produto produto;
	private Stage stage;

	public LocalizaProdutoController() {
		super();
		this.produto = null;
		this.produtoDao = new ProdutoDao();
	}
	
	private void actions() {		
		tav_produto.setOnKeyPressed(e->{
			if(e.getCode().equals(KeyCode.ENTER)) {
				selecionaEncerra();
			}else if(e.getCode().equals(KeyCode.ESCAPE)) {
				encerra();
			}
		});
	}

	private void inicializa() {
		
		tac_nomeProduto.setCellValueFactory(new PropertyValueFactory<>("descricaoProduto"));
		
	}
	
	private void atualizaTableView(String filtro) {
		
		tav_produto.getItems().clear();
		
		if(tav_produto.getItems().addAll(produtoDao.getAll(filtro))) {
			tav_produto.getSelectionModel().selectFirst();
		}
	}
	
	private void selecionaEncerra() {
		produto = tav_produto.getSelectionModel().getSelectedItem();
		stage.close();
	}
	
	private void encerra() {
		produto = null;
		stage.close();
	}

}
