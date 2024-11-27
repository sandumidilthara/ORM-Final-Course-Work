package lk.ijse.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Bo.BoFactory;
import lk.ijse.Bo.Custom.StudentBo;
import lk.ijse.Bo.Custom.StudentCourseBo;
import lk.ijse.Bo.Custom.StudentRegisterBo;
import lk.ijse.Dao.Custom.StudentCourseDao;
import lk.ijse.Dao.Custom.StudentRegisterDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Dto.StudentRegisterDto;
import lk.ijse.Entity.Student;
import lk.ijse.Entity.Student_Register;
import lk.ijse.Entity.Studnet_Course;
import lk.ijse.EntityTm.StudentRegisterTM;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFieldType;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class studentCourseRegisterFormController {



    public javafx.scene.control.Label lblStatus;

    @FXML
    private AnchorPane anpPayment;

    @FXML
    private javafx.scene.control.Button btnConfirm;

    @FXML
    private javafx.scene.control.Button btnSave;

    @FXML
    private TableColumn<?, ?> colBalancePay;

    @FXML
    private TableColumn<?, ?> colBtnRemove;

    @FXML
    private TableColumn<?, ?> colCourseId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStudentCourseDetailId;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colUpfrontPay;

    @FXML
    private ComboBox<String> comboCourses;

    @FXML
    private ComboBox<String> comboStudent;

    @FXML
    private TableView<StudentRegisterTM> tblPayment;

    @FXML
    private javafx.scene.control.TextField txtCoursefee;

    @FXML
    private javafx.scene.control.TextField txtDate;

    @FXML
    private javafx.scene.control.TextField txtId;

    @FXML
    private javafx.scene.control.TextField txtPayAmount;

    @FXML
    private javafx.scene.control.TextField txtStatus;

    @FXML
    private javafx.scene.control.TextField txtStuCouDetail;


    @FXML
    private javafx.scene.control.TextField txtReg;

    StudentCourseDao studentCourseDao = (StudentCourseDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.STUDENT_COURSE);
    StudentBo studentBo = (StudentBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.STUDENT);
    ArrayList<Student> studentArrayList = new ArrayList<>();
     StudentRegisterDao paymentDao = (StudentRegisterDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.PAYMENT);
    ArrayList<Studnet_Course> studentCourseArrayList = new ArrayList<>();
    StudentCourseBo studentCourseBo = (StudentCourseBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.STUDENT_COURSE);
    ObservableList<String> studentObservableList = FXCollections.observableArrayList();
    ObservableList<StudentRegisterTM> paymentTmObservableList = FXCollections.observableArrayList();
    StudentRegisterBo paymentBo = (StudentRegisterBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.PAYMENT);
    ArrayList<Student_Register> paymentArrayList = new ArrayList<>();

    public void initialize() throws IOException {
        generateNewId();
        getAllStudentCourses();
        getAllStudent();
        getAllPayment();
        searchStudent();
//        searchPayment();
        setDate();
        setCellValueFactory();
    }


    private void getAllPayment() throws IOException {
        java.util.List<Student_Register> paymentList = paymentBo.getPaymentList();
        paymentArrayList = (ArrayList<Student_Register>) paymentList;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("pay_id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("stu_id"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("cou_id"));
        colUpfrontPay.setCellValueFactory(new PropertyValueFactory<>("upfront_amount"));
        colBalancePay.setCellValueFactory(new PropertyValueFactory<>("balance_amount"));
        colStudentCourseDetailId.setCellValueFactory(new PropertyValueFactory<>("student_course_id"));
        colBtnRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void getAllStudent() throws IOException {
        java.util.List<Student> studentList = studentBo.getStudentList();
        studentArrayList = (ArrayList<Student>) studentList;
    }

    private void searchStudent() {
        for (Student student : studentArrayList) {
            studentObservableList.add(student.getStu_id());
        }
        comboStudent.setItems(studentObservableList);
    }

    private void setDate() {
        //  txtDate.setEditable(true);
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    private void getAllStudentCourses() throws IOException {
        List<Studnet_Course> studentCourseList = studentCourseBo.getStudentCourseList();
        studentCourseArrayList = (ArrayList<Studnet_Course>) studentCourseList;
    }

    private String generateNewId() throws IOException {
        String nextId = paymentDao.getCurrentId();
        txtId.setText(nextId);
        return nextId;
    }
    @FXML
    void btnConfirmOnAction(ActionEvent event) {
        if (txtId.getText().isEmpty() || comboCourses.getValue() == null || txtPayAmount.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all required fields").show();
            return;
        }


        String id = txtId.getText();
        String courseId = comboCourses.getValue();
        String studentId = comboStudent.getValue();
        String status = txtStatus.getText();
        Long stu_cou_id = Long.valueOf(txtStuCouDetail.getText());
        double upFront;
        double getFee;

        try {
            upFront = Double.parseDouble(txtPayAmount.getText());
            getFee = Double.parseDouble(txtCoursefee.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format for payment or course fee").show();
            return;
        }


        double balancePay = getFee - upFront;

        if (balancePay < 0) {
            new Alert(Alert.AlertType.WARNING, "Payment exceeds the course fee").show();
            return;
        }


        javafx.scene.control.Button btnRemove = new Button("Remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove?", yes, no).showAndWait();
            if (type.orElse(no) == yes) {
                int selectedIndex = tblPayment.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    paymentTmObservableList.remove(selectedIndex);
                    tblPayment.refresh();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Please select an item to remove").show();
                }
            }
        });


         StudentRegisterTM paymentTm = new  StudentRegisterTM(id, status, upFront, balancePay, studentId,courseId,stu_cou_id,btnRemove);
        paymentTmObservableList.add(paymentTm);
        tblPayment.setItems(paymentTmObservableList);
        tblPayment.refresh();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws IOException {
        StudentRegisterTM selectedPayment = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedPayment == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a payment to save").show();
            return;
        }

    Studnet_Course studentCourse = studentCourseDao.getStudentCourseById(Long.valueOf(txtStuCouDetail.getText()));

       StudentRegisterDto paymentDto = new  StudentRegisterDto();
        paymentDto.setPay_id(txtId.getText());
        paymentDto.setStatus(txtStatus.getText());
        paymentDto.setBalance_amount(selectedPayment.getBalance_amount()); // Use value from the selected item
        paymentDto.setPay_amount(Double.parseDouble(txtPayAmount.getText()));
        paymentDto.setPay_date(txtDate.getText());
        paymentDto.setStudent_course(studentCourse);

        paymentBo.savePayment(paymentDto);

        new Alert(Alert.AlertType.INFORMATION, "Payment saved successfully").show();

    }

    @FXML
    void comboCoursesOnAction(ActionEvent event) {
        String selectedCourseName = comboCourses.getValue();
        String selectedStudentId = comboStudent.getValue();

        for (Studnet_Course studentCourse : studentCourseArrayList) {
            // Check if both the student ID and course name match
            if (selectedStudentId != null && selectedCourseName != null &&
                    selectedStudentId.equals(studentCourse.getStudent().getStu_id()) &&
                    selectedCourseName.equals(studentCourse.getCourse().getCourse_name())) {

                // Display course fee and student_course_id
                 txtCoursefee.setText(String.valueOf(studentCourse.getCourse().getCourse_fee()));
                txtStuCouDetail.setText(String.valueOf(studentCourse.getStudent_course_id()));

                break;
            }
        }
    }

    @FXML
    void comboStudentOnAction(ActionEvent event) {
        String studentId = comboStudent.getValue();
        ObservableList<String> studentCourseObservableList = FXCollections.observableArrayList();


        for ( Studnet_Course studentCourse : studentCourseArrayList) {
            if (studentCourse.getStudent().getStu_id().equals(studentId)) {
                studentCourseObservableList.add(studentCourse.getCourse().getCourse_name());
            }
        }

        comboCourses.setItems(studentCourseObservableList);

        if (!studentCourseObservableList.isEmpty()) {
            comboCourses.setValue(studentCourseObservableList.get(0));
        }
    }

    @FXML
    void txtDateOnAction(ActionEvent event) {

    }

    @FXML
    void txtIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtPayAmountOnAction(ActionEvent event) {

    }

    @FXML
    void txtStatusOnAction(ActionEvent event) {

    }



    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {


        URL resource = this.getClass().getResource("/view/dashboard-main.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anpPayment.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }


    public void txtStatusOnKeyReleased(KeyEvent keyEvent) {

      // Regex.setTextColor(TextFieldType.STATUS,txtStatus);
    }

    public void txtPayAmountOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.FEE,txtPayAmount);
    }
}
