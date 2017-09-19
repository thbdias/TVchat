/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 912313
 */
public class TVchatServer extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Telas/Server/Server.fxml"));
        Scene scene = new Scene(loader.load());

//        Scene scene = new Scene(root, 300, 250);
//        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Fechou      !");
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        System.exit(0);
    }

}
