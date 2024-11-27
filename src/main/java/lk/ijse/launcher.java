package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;




    public class launcher extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login.fxml"));

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setTitle("Login Page");

            stage.show();
        }
    }

