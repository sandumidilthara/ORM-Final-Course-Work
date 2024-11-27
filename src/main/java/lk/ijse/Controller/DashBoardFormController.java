package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.Dao.Custom.UserDao;
import lk.ijse.Dao.DaoFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class DashBoardFormController {


    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imgCustomer;
    @FXML
    private ImageView imgItem;
    @FXML
    private ImageView imgOrder;


    @FXML
    private ImageView imgSupplier;

    @FXML
    private ImageView imgSupplierDetails;

    @FXML
    private ImageView imgdailyreview;

    @FXML
    private ImageView imgdetails;

    @FXML
    private Label lblMenu;
    @FXML
    private Label lblDescription;

    @FXML
    private Label lblCurrent;

    private boolean userAllowed;

    private boolean studentAllowed;

    private boolean paymentAllowed;

    private boolean programAllowed;

    private boolean settingsAllowed;


    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    @FXML
    private void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()) {
                case "imgCustomer":
                    lblMenu.setText("Manage Student");
                    lblDescription.setText("Click to add, edit, delete, search or view students");
                    break;
                case "imgItem":
                    lblMenu.setText("Manage Courses");
                    lblDescription.setText("Click to add, edit, delete, search or view courses");
                    break;
                case "imgOrder":
                    lblMenu.setText("Student Registration ");
                    lblDescription.setText("Click here if you want to Register a student");
                    break;
                case "imgSupplier":
                    lblMenu.setText("Manage Users");
                    lblDescription.setText("Click to add, edit, delete, search or view Users");
                    break;
                case "imgSupplierDetails":
                    lblMenu.setText("Manage User Settings");
                    lblDescription.setText("Click to your all settings");
                    break;


            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }


    @FXML
    private void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent roots = null;

            switch (icon.getId()) {
                case "imgCustomer":
                    roots = FXMLLoader.load(this.getClass().getResource("/view/student.fxml"));
                    break;
                case "imgItem":
                    roots = FXMLLoader.load(this.getClass().getResource("/view/program.fxml"));
//
//
                    break;

                case "imgOrder":
                    roots = FXMLLoader.load(this.getClass().getResource("/view/student-register.fxml"));
                    break;
                case "imgSupplier":
                    roots = FXMLLoader.load(this.getClass().getResource("/view/user.fxml"));

                    break;
                case "imgSupplierDetails":
roots = FXMLLoader.load(this.getClass().getResource("/view/user-setting.fxml"));
                    break;


            }

            if (roots != null) {
                Scene subScene = new Scene(roots);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

            }
        }
    }
//

    public void navigrateToHome(MouseEvent mouseEvent) throws IOException {


        URL resource = this.getClass().getResource("/view/login.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());


    }

// 
    public void setAccess(String userRole) {
        lblCurrent.setText(userRole);

        // Reset all access to false initially
        userAllowed = false;
        studentAllowed = false;
        paymentAllowed = false;
        programAllowed = false;
        settingsAllowed = false;

        if (userRole != null) {
            System.out.println("userRole: " + userRole);

            switch (userRole) {
                case "Admin":
                    userAllowed = true;
                    studentAllowed = true;
                    paymentAllowed = true;
                    programAllowed = true;
                    settingsAllowed = true;
                    break;

                case "Coordinator":
                    studentAllowed = true;
                    break;

                default:
                    // No access for other roles
                    break;
            }

            // Enable or disable buttons based on access levels
            imgCustomer.setDisable(!userAllowed);
            imgItem.setDisable(!studentAllowed);
            imgOrder.setDisable(!paymentAllowed);
            imgSupplier.setDisable(!programAllowed);
            imgSupplierDetails.setDisable(!settingsAllowed);
        } else {
            lblCurrent.setText("No role assigned");
        }
    }




    }

