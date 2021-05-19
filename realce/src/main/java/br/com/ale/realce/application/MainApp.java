package br.com.ale.realce.application;

import br.com.ale.realce.util.Rota;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    private Rota rota;

    public MainApp() {
        super();
        this.rota = new Rota();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        try {
            if (rota.openLogin()) {
                rota.openMain(stage);
            }

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    t.consume();
                    Platform.exit();
                    System.exit(0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
