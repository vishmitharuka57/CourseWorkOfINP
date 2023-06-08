package lk.ijse.coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane loginContext;
    public TextField txtUserName;
    public static String userName;


    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().length()>0){
            userName =txtUserName.getText();
            setUi("../view/ClientForm");
        }else {
            new Alert(Alert.AlertType.WARNING,"Please Enter User Name").show();
        }
    }

    public void setUi(String location) throws IOException {
        Stage stage = (Stage) loginContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(location+".fxml"))));
        stage.show();
    }
}
