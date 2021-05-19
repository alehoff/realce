package br.com.ale.realce.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class Mensagem {

	public Mensagem() {
	}

	public void publica(String mensagem) {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
		alerta.setContentText(mensagem);
		alerta.setTitle("SGF");

		alerta.showAndWait();
	}

	public String questiona(String mensagem) {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Bebidas realce");
		dialog.setHeaderText("Cadastro");
		dialog.setContentText(mensagem);

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			return result.get();
		} else {
			return "";
		}
	}

	public boolean confirma(String mensagem) {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Bebidas Realce");
		alert.setContentText(mensagem);
		ButtonType okButton = new ButtonType("Sim", ButtonData.YES);
		ButtonType noButton = new ButtonType("Não", ButtonData.NO);

		alert.getButtonTypes().setAll(okButton, noButton);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get().getText().equalsIgnoreCase("Sim")) {
			return true;
		} else {
			return false;
		}
	}

}
