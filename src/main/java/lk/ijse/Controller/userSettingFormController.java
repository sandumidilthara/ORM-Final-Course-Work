package lk.ijse.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Bo.BoFactory;
import lk.ijse.Bo.Custom.UserBo;
import lk.ijse.Dao.Custom.UserDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Entity.User;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFieldType;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class  userSettingFormController {

    @FXML
    private AnchorPane anpChangePassword;

    @FXML
    private Button btnPasswordReset;

    @FXML
    public javafx.scene.control.TextField txtConfirmPassword;

    @FXML
    public javafx.scene.control.TextField txtContact;

    @FXML
    public javafx.scene.control.TextField txtEmail;

    @FXML
    public javafx.scene.control.TextField txtNewPassword;

    @FXML
    public javafx.scene.control.TextField txtRole;

    @FXML
    public javafx.scene.control.TextField txtSearch;

    ArrayList<User> users = new ArrayList<>();
    UserBo userBo = (UserBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.USER);
    UserDao userDao = (UserDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.USER);
    ObservableList<User> observableUserList = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        getAllUsers();
    }

    private void getAllUsers() throws IOException {
        List<User> userList = userBo.getUserList();
        users = (ArrayList<User>) userList;
        observableUserList.addAll(users);
    }

    private void searchUsername() {
        String search = txtSearch.getText();
        boolean found = false;

        for (User user : observableUserList) {
            if (user.getUsername().equalsIgnoreCase(search)) {

                txtContact.setText(user.getUser_phone());
                txtRole.setText(user.getUser_role());
                found = true;
                break;
            }
        }

        if (!found) {

            txtEmail.clear();
            txtContact.clear();
            txtRole.clear();
            System.out.println("No user found with the provided username.");
        }
    }
    @FXML
    void btnPasswordResetOnAction(ActionEvent event) {
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (newPassword.equals(confirmPassword)) {

            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            String username = txtSearch.getText();
            for (User user : observableUserList) {
                if (user.getUsername().equalsIgnoreCase(username)) {

                    user.setPassword(hashedPassword);


                    if (userDao.updateUser(user)) {
                        new Alert(Alert.AlertType.INFORMATION, "Password updated successfully!").show();
                        clearPasswordFields();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to update the password.").show();
                    }
                    break;
                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        searchUsername();
    }
    private void clearPasswordFields() {
        txtNewPassword.clear();
        txtConfirmPassword.clear();
    }

    public void navigrateToHome(MouseEvent mouseEvent) throws IOException {

        URL resource = this.getClass().getResource("/view/dashboard-main.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anpChangePassword.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());



    }

    public void txtUserNameOnKeyReleased(KeyEvent keyEvent) {

        Regex.setTextColor(TextFieldType.NAME,txtSearch);
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.CONTACT,txtContact);
    }

    public void txtConfirmOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.PASSWORD,txtConfirmPassword);
    }

    public void txtRoleOnKeyReleased(KeyEvent keyEvent) {

    }

    public void txtNewOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.PASSWORD,txtConfirmPassword);
    }
}
