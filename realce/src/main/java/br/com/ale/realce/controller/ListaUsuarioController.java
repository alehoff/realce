/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ale.realce.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ale.realce.model.dao.UsuarioDao;
import br.com.ale.realce.model.entidade.Usuario;
import br.com.ale.realce.model.enuns.Privilegio;
import br.com.ale.realce.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author mbs-6
 */
public class ListaUsuarioController implements Initializable {

    @FXML
    private TableView<Usuario> tav_usuarios;

    @FXML
    private TableColumn<Usuario, String> tac_usuario;

    @FXML
    private TableColumn<Usuario, Privilegio> tac_privilegio;

    @FXML
    private Button but_privilegio;

    @FXML
    private TextField tef_filtro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializa();
        actions();
    }

    private final UsuarioDao usuarioDao;
    private final Rota rota;
    private final Mensagem mensagem;
    private final Banco banco;

    public ListaUsuarioController() {
        super();
        this.usuarioDao = new UsuarioDao();
        this.rota = new Rota();
        this.mensagem = new Mensagem();
        this.banco = Banco.getInstance();
    }

    private void actions() {
        //filtra usuários do sistema
        tef_filtro.setOnKeyReleased(e -> {
            banco.abreConn();
            try {
                atualizaTableView();
            } finally {
                banco.fechaConn();
            }
        });

        but_privilegio.setOnAction(e -> {
            banco.abreTransacao();
            banco.fechaTransacao(openEditaPrivilegio());
        });

        but_privilegio.setOnMouseClicked(e -> {
            banco.abreTransacao();
            banco.fechaTransacao(openEditaPrivilegio());
        });
    }

    private void inicializa() {
        banco.abreConn();
        try {
            tef_filtro.clear();
            inicializaTableView();
            atualizaTableView();
        } finally {
            banco.fechaConn();
        }
    }

    private boolean openEditaPrivilegio() {
        boolean salvar = false;
        try {
            Util.validaTableView(tav_usuarios);
            Usuario usuario = tav_usuarios.getSelectionModel().getSelectedItem();
            if (usuario != null) {
                salvar = rota.openEditaPermissaoUsuario(usuario);
                if (salvar) {
                    atualizaTableView();
                }
            } else {
                mensagem.publica("Usuário inválido");
            }
        } catch (Exception e) {
            mensagem.publica(e.getMessage());
        }

        return salvar;
    }

    private void atualizaTableView() {
        tav_usuarios.getItems().clear();
        tav_usuarios.getItems().addAll(usuarioDao.getAll(tef_filtro.getText()));
    }

    private void inicializaTableView() {
        tac_privilegio.setCellValueFactory(new PropertyValueFactory<>("privilegio"));
        tac_usuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
    }

}
