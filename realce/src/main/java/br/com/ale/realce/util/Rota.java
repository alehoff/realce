package br.com.ale.realce.util;

import java.io.IOException;

import br.com.ale.realce.interfaces.Modal;
import br.com.ale.realce.interfaces.ModalConsulta;
import br.com.ale.realce.interfaces.ModalObject;
import br.com.ale.realce.model.entidade.Estoque;
import br.com.ale.realce.model.entidade.Pedido;
import br.com.ale.realce.model.entidade.PedidoVenda;
import br.com.ale.realce.model.entidade.Produto;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Rota {

    private final String ROTA_PEDIDOCOMPRA = "/fxml/pedidoCompra/";
    private final String ROTA_PEDIDOVENDA = "/fxml/pedidoVenda/";
    private final String ROTA_MENU = "/fxml/Menu/";
    private final String ROTA_USUARIO = "/fxml/usuario/";
    private final String ROTA_PRODUTO = "/fxml/produto/";
    private final String ROTA_TABELAVENDA = "/fxml/tabelaVenda/";
    private final String ROTA_ESTOQUE = "/fxml/estoque/";
    private final String ROTA_CAIXA = "/fxml/caixa/";

    private boolean openModal(String url, String titulo) {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(url));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(titulo);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            // dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Define a pessoa no controller.
            Modal controller = loader.getController();
            controller.setStage(dialogStage);

            // Mostra a janela e espera até o usuário fechar.
            dialogStage.showAndWait();

            return controller.isOk();

        } catch (IOException e) {
            return false;
        }
    }

    private boolean openModalComObjeto(String url, String titulo, Object object) {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(url));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(titulo);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            // dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Define a pessoa no controller.
            ModalObject controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setObject(object);

            // Mostra a janela e espera até o usuário fechar.
            dialogStage.showAndWait();

            return controller.isOk();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Object openModalConsulta(String filtro, String url, String titulo) {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(url));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(titulo);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            // dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Define a pessoa no controller.
            ModalConsulta controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setObject(filtro);

            // Mostra a janela e espera até o usuário fechar.
            dialogStage.showAndWait();

            return controller.getObject();

        } catch (IOException e) {
            return null;
        }
    }

    private void openMenuSistema(BorderPane pane, String url) {
        try {
            pane.setCenter(FXMLLoader.load(getClass().getResource(url)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * abre tela principal sistema
     *
     * @param stage
     */
    public void openMain(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(ROTA_MENU + "Menu.fxml"));
            stage.setScene(new Scene(root));

            stage.setTitle("Distribuidora Bebidas Realce");
            stage.setMaximized(true);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openMenuUsuario(BorderPane pane) {
        openMenuSistema(pane, ROTA_USUARIO + "MenuUsuario.fxml");
    }

    public void openListaUsuario(BorderPane pane) {
        try {
            pane.setCenter(FXMLLoader.load(getClass().getResource(ROTA_USUARIO + "ListaUsuario.fxml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean openLogin() {
        return openModal(ROTA_USUARIO + "Login.fxml", "Login");
    }

    public boolean openCadastroUsuario(Object object) {
        return openModalComObjeto(ROTA_USUARIO + "CadastroUsuario.fxml", "Cadastro Usuário", object);
    }

    public boolean openEditaPermissaoUsuario(Object usuario) {
        return openModalComObjeto(ROTA_USUARIO + "EditaPermissaoUsuario.fxml", "Edita Permissão Usuário", usuario);
    }

    public void openMenuProduto(BorderPane pane) {
        openMenuSistema(pane, ROTA_PRODUTO + "MenuProduto.fxml");
    }

    public void openListaProduto(BorderPane pane) {
        openMenuSistema(pane, ROTA_PRODUTO + "ListaProduto.fxml");
    }

    public boolean openEditaProduto(Produto produto){
       return  openModalComObjeto(ROTA_PRODUTO+"EditaProduto.fxml","Edição Produto",produto);
    }

    public boolean openCadastroProduto(Produto produto) {
        return openModalComObjeto(ROTA_PRODUTO + "CadastroProduto.fxml", "Cadastro Produto", produto);
    }

    public void openListaTabelaVenda(BorderPane pane) {
        openMenuSistema(pane, ROTA_TABELAVENDA + "ListaTabelaVenda.fxml");

    }

    public void openListaEstoque(BorderPane pane) {
        openMenuSistema(pane, ROTA_ESTOQUE + "ListaEstoque.fxml");
    }

    public boolean openEditaValorMinimo(String titulo, Estoque object) {
        return openModalComObjeto(ROTA_ESTOQUE + "EditaValorMinimo.fxml", titulo, object);
    }

    public void openMenuEstoque(BorderPane pane) {
        openMenuSistema(pane, ROTA_ESTOQUE + "MenuEstoque.fxml");
    }

    public void openMenuPedidoCompra(BorderPane pane) {
        openMenuSistema(pane, ROTA_PEDIDOCOMPRA + "MenuPedidoCompra.fxml");
    }

    public void openListaPedidoCompra(BorderPane pane) {
        openMenuSistema(pane, ROTA_PEDIDOCOMPRA + "ListaPedidoCompra.fxml");
    }

    public boolean openEditaPedido(Pedido pedido) {
        return openModalComObjeto(ROTA_PEDIDOCOMPRA + "EditaPedido.fxml", "Edita Pedido", pedido);
    }

    public Object openLocalizaProduto(String filtro) {
        return openModalConsulta(filtro, ROTA_PRODUTO + "LocalizaProduto.fxml", "Localiza Produto");
    }

    // PEDIDOS DE VENDA
    public void openMenuPedidoVenda(BorderPane bPane) {
        openMenuSistema(bPane, ROTA_PEDIDOVENDA + "MenuPedidoVenda.fxml");
    }

    public void openListaPedidoVenda(BorderPane bPane) {
        openMenuSistema(bPane, ROTA_PEDIDOVENDA + "ListaPedidoVenda.fxml");
    }

    public boolean openPedidoVenda(PedidoVenda pedidoVenda) {
        return openModalComObjeto(ROTA_PEDIDOVENDA + "PedidoVenda.fxml", "Venda Mercadoria", pedidoVenda);
    }

    public void openConsultaTabelaVenda(BorderPane pane) {
        openMenuSistema(pane, ROTA_TABELAVENDA + "ConsultaTabelaVenda.fxml");
    }

    public void openListaCaixaParaUsuario(BorderPane pane) {
        openMenuSistema(pane, ROTA_CAIXA + "ListaCaixaParaUsuario.fxml");
    }

    public void openListaCaixaPorPeriodo(BorderPane pane) {
        openMenuSistema(pane, ROTA_CAIXA + "ListaCaixaPorPeriodo.fxml");
    }

    public void openMenuCaixa(BorderPane pane) {
        openMenuSistema(pane, ROTA_CAIXA + "MenuCaixa.fxml");
    }

    public boolean openLancamentoHistoricoCaixa(Object caixa) {
        return openModalComObjeto(ROTA_CAIXA + "LancamentoHistoricoCaixa.fxml", "Lançamentos Caixa", caixa);
    }

    public boolean openNovoCaixa(Object caixa) {
        return openModalComObjeto(ROTA_CAIXA + "NovoCaixa.fxml", "Cadastro Novo Caixa Usuário", caixa);
    }

}
