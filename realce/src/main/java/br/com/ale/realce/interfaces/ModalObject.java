package br.com.ale.realce.interfaces;

import javafx.stage.Stage;

public interface ModalObject {

	public void setStage(Stage stage);

	public boolean isOk();

	public void setObject(Object object);
}
