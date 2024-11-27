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
import lk.ijse.Bo.Custom.StudentBo;
import lk.ijse.Bo.Custom.UserBo;
import lk.ijse.Dao.Custom.ProgramDao;
import lk.ijse.Dao.Custom.StudentDao;
import lk.ijse.Dao.Custom.UserDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Dto.ProgramDto;
import lk.ijse.Dto.StudentDto;
import lk.ijse.Entity.Program;
import lk.ijse.Entity.Student;
import lk.ijse.Entity.Studnet_Course;
import lk.ijse.Entity.User;
import lk.ijse.EntityTm.ProgramTm;
import lk.ijse.EntityTm.StudentTm;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFieldType;
import org.springframework.security.core.parameters.P;

import java.awt.*;
import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lk.ijse.Bo.BoFactory.BoType.PROGRAM;

public class studentFormController {

    @FXML
    private AnchorPane anpStudent;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSave1;

    @FXML
    private JFXButton btnSave2;

    @FXML
    private JFXButton btnSave3;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colCourseName;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private ComboBox<String> comboCourse;

    @FXML
    private TableView<StudentTm> tblStudent;

    @FXML
    public javafx.scene.control.TextField  txtADDRESS;

    @FXML
    public javafx.scene.control.TextField txtCONTACT;

    @FXML
    public javafx.scene.control.TextField  txtCOURSENAME;

    @FXML
    public javafx.scene.control.TextField  txtDATE;

    @FXML
    public javafx.scene.control.TextField  txtEMAIL;

    @FXML
    public javafx.scene.control.TextField txtID;

    @FXML
    public javafx.scene.control.TextField  txtNAME;

    @FXML
    public javafx.scene.control.TextField  txtDuration;

    @FXML
    public javafx.scene.control.TextField  txtFree;

    @FXML
    public javafx.scene.control.TextField  txtcourseName;


    StudentBo studentBo = (StudentBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.STUDENT);
    ProgramBo courseBo = (ProgramBo) BoFactory.getBoFactory().getBoType(PROGRAM);
   // UserBo userBo = (UserBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.USER);
    ProgramDao courseDao = (ProgramDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.COURSE);
    UserDao userDao = (UserDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.USER);
    StudentDao studentDao = (StudentDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.STUDENT);
    ObservableList<StudentTm> studentTmObservableList = FXCollections.observableArrayList();
    ArrayList<Program> courseArrayList = new ArrayList<>();
    ArrayList<Student> studentArrayList = new ArrayList<>();

    public void initialize() throws IOException {
        generateNewId();
        getAllCourses();
        setCourseId();
        //setUserId();
        getAllStudents();
//        setDate();
        setCellValueFactory();
        setTable();
        //filterStudent();
        selectTableRow();
        // clearFields();
    }

    private void getAllStudents() throws IOException {
        List<Student> studentList = studentBo.getStudentList();
        studentArrayList = (ArrayList<Student>) studentList;
    }

    private void getAllCourses() throws IOException {
        List<Program> courseList = courseBo.getCourseList();
        courseArrayList = (ArrayList<Program>) courseList;
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("stu_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("stu_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("stu_address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("stu_phone"));
       // colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void setTable() throws IOException {
        studentTmObservableList.clear();
        List<Student> studentList = studentBo.getStudentList();
        for (Student student : studentList) {
            StudentTm studentTm = new StudentTm(
                    student.getStu_id(),
                    student.getStu_name(),
                    student.getStu_address(),
                    student.getStu_phone()
                   // student.getDate()
            );
            studentTmObservableList.add(studentTm);
        }
        tblStudent.setItems(studentTmObservableList);
    }

    private void selectTableRow() {
        tblStudent.setOnMouseClicked(mouseEvent -> {
            int row = tblStudent.getSelectionModel().getSelectedIndex();
            StudentTm studentTm = tblStudent.getItems().get(row);
            txtID.setText(studentTm.getStu_id());
            txtNAME.setText(studentTm.getStu_name());
            txtADDRESS.setText(studentTm.getStu_address());
            txtCONTACT.setText(studentTm.getStu_phone());
        });
    }

//    private void setDate() {
//        LocalDate now = LocalDate.now();
//        txtDate.setText(String.valueOf(now));
//    }

    private String generateNewId() throws IOException {
        String nextId = studentDao.getCurrentId();
        txtID.setText(nextId);
        return nextId;
    }

    private void clearFields() {
        txtADDRESS.clear();
        txtCONTACT.clear();
        //txtDate.clear();
        txtID.clear();
        txtNAME.clear();
       // txtSearch.clear();
        txtFree.clear();
        txtcourseName.clear();
       // txtSearch.clear();
    }



    public void setCourseId() {
        ObservableList<String> id = FXCollections.observableArrayList();
        for (Program course : courseArrayList) {
            id.add(course.getCourse_id());
        }
        comboCourse.setItems(id);
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws IOException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if (studentBo.delete(txtID.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Student Deleted Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "SQL Error").show();
            }
        }
        setTable();
        generateNewId();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws IOException {
        String studentId = txtID.getText();
        Student existingStudent = studentDao.getStudentById(studentId);

        // If the student already exists, register them for the new course
        if (existingStudent != null) {
            registerStudentForCourse(existingStudent);
        } else {
            // Create and save a new student if not found


            StudentDto studentDto = new StudentDto();
            studentDto.setStu_id(studentId);
            studentDto.setStu_name(txtNAME.getText());
            studentDto.setStu_address(txtADDRESS.getText());
            studentDto.setStu_phone(txtCONTACT.getText());


            studentBo.save(studentDto);


            Student newStudent = studentDao.getStudentById(studentId);
            registerStudentForCourse(newStudent);
        }
        setTable();
        new Alert(Alert.AlertType.INFORMATION, "Student Added With Course Successfully!").show();



    }

    private void registerStudentForCourse(Student student) throws IOException {
        String courseId = comboCourse.getValue();
        Program selectedCourse = null;

        // Find the course by ID
        for (Program course : courseArrayList) {
            if (course.getCourse_id().equals(courseId)) {
                selectedCourse = course;
                break;
            }
        }

        if (selectedCourse != null) {
            // Check if the student is already registered for this course
            if (!studentDao.isStudentRegisteredForCourse(student.getStu_id(), courseId)) {
                Studnet_Course studentCourse = new  Studnet_Course();
                studentCourse.setStudent(student);
                studentCourse.setCourse(selectedCourse);
                studentCourse.setRegistration_date(new java.util.Date());

                // Save the student-course relationship
                studentDao.saveStudentCourseDetails(studentCourse);
            } else {
                System.out.println("Student is already registered for this course.");
            }
        } else {
            System.out.println("Selected course not found.");
        }
        setTable();
    }



    @FXML
    void comboCourseOnAction(ActionEvent event) throws IOException {
        String courseId =  comboCourse.getValue();
      ProgramDto course = courseBo.getCourse(courseId);
        if (course != null) {
            txtcourseName.setText(course.getCourse_name());
            txtDuration.setText(course.getDuration());
            txtFree.setText(String.valueOf(course.getCourse_fee()));
        }
    }

//    @FXML
//    void comboUserOnAction(ActionEvent event) {
//        String userId = comboUser.getValue();
//        userBo.getUser(userId);
//    }

//    private void filterStudent() {
//        FilteredList<StudentTm> filterData = new FilteredList<>(studentTmObservableList, e -> true);
//
//        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
//            filterData.setPredicate(student -> {
//                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
//                    return true;
//                }
//
//                String searchKeyword = newValue.toLowerCase();
//                if (student.getStu_id().toLowerCase().contains(searchKeyword)) {
//                    return true;
//                } else if (student.getStu_phone().toLowerCase().contains(searchKeyword)) {
//                    return true;
//                } else if (student.getStu_name().toLowerCase().contains(searchKeyword)) {
//                    return true;
//                }
//                return false;
//            });
//        });
//
//        SortedList<StudentTm> studentTmSortedList = new SortedList<>(filterData);
//        studentTmSortedList.comparatorProperty().bind(tblStudent.comparatorProperty());
//        tblStudent.setItems(studentTmSortedList);
//    }

    @FXML
    void txtAddressOnAction(ActionEvent event) {

    }

    @FXML
    void txtContactOnAction(ActionEvent event) {

    }

    @FXML
    void txtDateOnAction(ActionEvent event) {

    }

    @FXML
    void txtIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtNameOnAction(ActionEvent event) {

    }

    public void btnSUpdateOnAction(ActionEvent actionEvent) throws IOException {

        String id = txtID.getText();
        String name = txtNAME.getText();
        String address = txtADDRESS.getText();
        String contact = txtCONTACT.getText();

        // Retrieve the associated user (consider whether `id` or another identifier is used)

        // Create a StudentDto object with the updated data
        StudentDto studentDto = new StudentDto(id, name, address, contact);

        // Call the update method and display the appropriate message
        if (studentBo.update(studentDto)) {
            new Alert(Alert.AlertType.INFORMATION, "Student Updated Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error updating student details").show();
        }

        // Refresh the table and generate a new ID for subsequent operations
        setTable();
        generateNewId();



    }

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {


        URL resource = this.getClass().getResource("/view/dashboard-main.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anpStudent.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());


    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {

        Regex.setTextColor(TextFieldType.ADDRESS,txtADDRESS);
    }

    public void txtNumberOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.CONTACT,txtCONTACT);
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.NAME,txtNAME);
    }
}
