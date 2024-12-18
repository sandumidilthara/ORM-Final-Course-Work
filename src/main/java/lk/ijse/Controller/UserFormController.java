package lk.ijse.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Bo.BoFactory;
import lk.ijse.Bo.Custom.UserBo;
import lk.ijse.Dao.Custom.UserDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Dto.UserDto;
import lk.ijse.Entity.User;
import lk.ijse.EntityTm.UserTm;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFieldType;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UserFormController {

    @FXML
    private AnchorPane anpUser;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colRole;

    @FXML
    private TableColumn<?, ?> colUsername;

    @FXML
    private TableView<UserTm> tblUser;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtDate;



    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUsername;

    UserBo userBo = (UserBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.USER);
    UserDao userDao = (UserDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.USER);
    ObservableList<UserTm> userTmObservableList = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        setCellValueFactory();
        setTable();
        selectTableRow();
        generateNewId();

    }

    private String generateNewId() throws IOException {
        String nextId = userDao.getCurrentId();
        txtUserId.setText(nextId);
        return nextId;
    }

    private void setTable() throws IOException {
        userTmObservableList.clear();
        List<User> userList = userBo.getUserList();
        for(User user : userList){
            UserTm userTm = new UserTm(
                    user.getUser_id(),
                    user.getUsername(),

                    user.getUser_phone(),
                    user.getUser_role()
            );
            userTmObservableList.add(userTm);

        }
        tblUser.setItems(userTmObservableList);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("user_role"));

        colContact.setCellValueFactory(new PropertyValueFactory<>("user_phone"));
    }


    private void selectTableRow() {
        tblUser.setOnMouseClicked(mouseEvent -> {
            int row = tblUser.getSelectionModel().getSelectedIndex();
            UserTm userTm = tblUser.getItems().get(row);
            txtUserId.setText(userTm.getUser_id());
            txtUsername.setText(userTm.getUsername());

            txtContact.setText(userTm.getUser_phone());
            txtRole.setText(userTm.getUser_role());
        });
    }

    public void clearFields(){
        txtContact.clear();

        txtPassword.clear();
        txtRole.clear();
        txtUserId.clear();
        txtUsername.clear();
        txtPassword.clear();
    }


    @FXML
    void btnClearOnAction(ActionEvent event) throws IOException {
        clearFields();
        generateNewId();
        setTable();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws IOException {
        ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if(result.orElse(no) == yes) {
            if (userBo.delete(txtUserId.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Deleted Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "SQL Error").show();
            }
        }
        clearFields();
        setTable();
        generateNewId();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws IOException {
        String id = txtUserId.getText();
        String role = txtRole.getText();
        String username = txtUsername.getText();
        String rawPassword = txtPassword.getText();

        String contact = txtContact.getText();


        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        UserDto userDto = new UserDto(id, username, hashedPassword,  contact, role);
        if (userBo.save(userDto)) {
            clearFields();
            txtUserId.setText(generateNewId());
            new Alert(Alert.AlertType.CONFIRMATION, "User Added Successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "SQL Error").show();
        }
        clearFields();
        setTable();
        generateNewId();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws IOException {
        String id = txtUserId.getText();
        String role = txtRole.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        String contact = txtContact.getText();

        UserDto userDto = new UserDto(id, username, password, contact, role);
        if(userBo.update(userDto)){
            new Alert(Alert.AlertType.CONFIRMATION, "User Updated Successfully!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "SQL Error").show();
        }
        clearFields();
        setTable();
        generateNewId();
    }

    @FXML
    void txtContactOnAction(ActionEvent event) {

    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {

    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtRoleOnAction(ActionEvent event) {

    }

    @FXML
    void txtUserIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {

    }

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {


        URL resource = this.getClass().getResource("/view/dashboard-main.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anpUser.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());

    }

    public void txtRoleOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.ROLE,txtRole);

    }

    public void txtNumberOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.CONTACT,txtContact);
    }

    public void txtPasswordOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.PASSWORD,txtPassword);
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.NAME,txtUsername);
    }
}
