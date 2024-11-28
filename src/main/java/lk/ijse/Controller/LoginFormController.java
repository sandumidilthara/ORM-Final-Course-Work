package lk.ijse.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Dao.Custom.UserDao;
import lk.ijse.Dao.DaoFactory;

import java.io.IOException;
import java.net.URL;

public class LoginFormController {

    @FXML
    private AnchorPane root;
    @FXML
    public javafx.scene.control.TextField txtUsername;

    @FXML
    public javafx.scene.control.TextField txtPassword;
    UserDao userDao = (UserDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.USER);
    String username;
    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {




        username = txtUsername.getText();
        String password = txtPassword.getText();

        boolean isAuthenticated = userDao.checkCredential(username, password);

        if (isAuthenticated && userDao.getUserRole(username).equals("Admin")) {
           navigateToTheAdminDashboard();

        } else  if (isAuthenticated){
            navigateToTheCoordinatorDashboard();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password!").show();
        }
    }



    private void navigateToTheAdminDashboard() throws IOException {

        URL resource = this.getClass().getResource("/view/dashboard-main.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }


    private void navigateToTheCoordinatorDashboard() throws IOException {

        URL resource = this.getClass().getResource("/view/dashboard-coordinator.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }


}
