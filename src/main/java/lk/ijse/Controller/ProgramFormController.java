package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.Bo.Custom.ProgramBo;
import lk.ijse.Dao.Custom.ProgramDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Dto.ProgramDto;
import lk.ijse.Entity.Program;
import lk.ijse.EntityTm.ProgramTm;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFieldType;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class ProgramFormController {




    @FXML
     public JFXButton btnClear;

    @FXML
     public JFXButton btnDelete;

    @FXML
    public JFXButton btnSave;

    @FXML
    public JFXButton btnUpdate;

    @FXML
    private AnchorPane roots;

    @FXML
     public TableView<ProgramTm> tblCustomers;




    @FXML
    private TableColumn<?, ?> colCouFee;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colRegFee;


    @FXML
    public javafx.scene.control.TextField txtCOUDU;

    @FXML
    public javafx.scene.control.TextField txtCOUFEE;




    public javafx.scene.control.TextField txtProgramId;

    @FXML
    public javafx.scene.control.TextField txtCUSNAME;

    @FXML
    public javafx.scene.control.TextField txtREGFEE;



    ProgramBo courseBo = (ProgramBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.PROGRAM);
    ProgramDao courseDao = (ProgramDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.COURSE);
    ObservableList<ProgramTm> courseObservableList = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        setCellValueFactory();
        setTable();
        selectTableRow();
        clearFields();
        generateNewId();
//        filterCourse();
    }

    private String generateNewId() throws IOException {
        String nextId = courseDao.getCurrentId();
        txtProgramId.setText(nextId);
        return nextId;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        colCouFee.setCellValueFactory(new PropertyValueFactory<>("course_fee"));
        colRegFee.setCellValueFactory(new PropertyValueFactory<>("register_fee"));
        colName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }
    private void setTable() throws IOException {
        courseObservableList.clear();
        List<Program> courseList = courseBo.getCourseList();
        for (Program course : courseList) {
            ProgramTm courseTm =  new ProgramTm(course.getCourse_id(),course.getCourse_name(),course.getDuration(),course.getCourse_fee(),course.getRegister_fee());
            courseObservableList.add(courseTm);
        }
        tblCustomers.setItems(courseObservableList);
    }

    private void clearFields() throws IOException {
        txtProgramId.clear();
        txtCOUDU.clear();
        txtCOUFEE.clear();
        txtCUSNAME.clear();
        txtREGFEE.clear();
    }


    private void selectTableRow() {
         tblCustomers.setOnMouseClicked(mouseEvent -> {
            int row = tblCustomers.getSelectionModel().getSelectedIndex();
           ProgramTm courseTm = tblCustomers.getItems().get(row);
            txtProgramId.setText(courseTm.getCourse_id());
            txtCUSNAME.setText(courseTm.getCourse_name());
            txtCOUFEE.setText(String.valueOf(courseTm.getCourse_fee()));
             txtREGFEE.setText(String.valueOf(courseTm.getRegister_fee()));

             txtCOUDU.setText(courseTm.getDuration());

        });
    }




    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {


        URL resource = this.getClass().getResource("/view/dashboard-main.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.roots.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {


        String id = txtProgramId.getText();
        String name = txtCUSNAME.getText();
        String duration = txtCOUDU.getText();
        double free = Double.parseDouble(txtCOUFEE.getText());
        double reg_free = Double.parseDouble(txtREGFEE.getText());

        ProgramDto courseDto = new ProgramDto(id, name, duration, free, reg_free);
        if (courseBo.update(courseDto)){
            new Alert(Alert.AlertType.CONFIRMATION,"Course Updated Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Course Not Updated Successfully").show();
        }
        clearFields();
        setTable();
        generateNewId();
    }

    public void updateDeleteOnAction(ActionEvent actionEvent) throws IOException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if(result.orElse(no) == yes) {
            if (courseBo.delete(txtProgramId.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Deleted Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "SQL Error").show();
            }
        }
        clearFields();
        setTable();
        generateNewId();


    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws IOException {


        String id = txtProgramId.getText();
        String name = txtCUSNAME.getText();
        String duration = txtCOUDU.getText();
        double free = Double.parseDouble(txtCOUFEE.getText());
        double reg_free = Double.parseDouble(txtREGFEE.getText());

      ProgramDto courseDto = new ProgramDto(id, name, duration, free ,reg_free);
        if (courseBo.save(courseDto)){
            new Alert(Alert.AlertType.CONFIRMATION,"Course Added Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Course Not Added Successfully").show();
        }
        clearFields();
        setTable();
        generateNewId();
    }

    public void btnClearOnAction(ActionEvent actionEvent) throws IOException {
        clearFields();
        generateNewId();
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {

        Regex.setTextColor(TextFieldType.NAME,txtCUSNAME);
    }

    public void txtDurationOnKeyReleased(KeyEvent keyEvent) {

//        Regex.setTextColor(TextFieldType.DURATION,txtCOUDU);
    }

    public void txtCourseFeeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.FEE,txtCOUFEE);
    }

    public void txtRegisterFeeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.FEE,txtREGFEE);
    }
}
