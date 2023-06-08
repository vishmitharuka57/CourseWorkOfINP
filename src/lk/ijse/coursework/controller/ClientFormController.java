package lk.ijse.coursework.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientFormController extends Thread{
    public TextField txtField;
    public Label lblName;
    public Pane emojiPane;
    public VBox vBox;
    public FileChooser chooser;
    public File path;

    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public void initialize(){
        emojiPane.setVisible(false);
        String userName = LoginFormController.userName;
        lblName.setText(userName);

        try {
            socket = new Socket("localhost",4000);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(),true);
            this.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void btnSendOnAction(ActionEvent actionEvent) {
        String message = txtField.getText();
        printWriter.println(lblName.getText() +" : " +message);
        printWriter.flush();
        txtField.clear();
        emojiPane.setVisible(false);
        if (message.equalsIgnoreCase("bye")){
            Stage stage = (Stage) txtField.getScene().getWindow();
            stage.close();
        }


    }

    public void run(){
        while (true) {
            try {
                String message = bufferedReader.readLine();
                String[] tokens = message.split(" ");
                String command = tokens[0];

                StringBuilder clientMessage = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    clientMessage.append(tokens[i] + " ");
                }

                String[] messageAr = message.split(" ");
                String string = "";
                for (int i = 0; i < messageAr.length - 1; i++) {
                    string += messageAr[i + 1] + " ";
                }

                Text text = new Text(string);
                String fChar = "";

                if (string.length() > 3) {
                    fChar = string.substring(0, 3);
                }

                if (fChar.equalsIgnoreCase("img")) {
                    string = string.substring(3, string.length() - 1);

                    File file = new File(string);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitWidth(150);
                    imageView.setFitHeight(150);

                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);

                    vBox.setAlignment(Pos.TOP_LEFT);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    Text text1 = new Text(command + " :");
                    hBox.getChildren().add(text1);
                    hBox.getChildren().add(imageView);

                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));

                } else {
                    TextFlow tempTextFlow = new TextFlow();

                    if (!command.equalsIgnoreCase(lblName.getText() + ":")) {
                        Text name = new Text(command + " ");
                        name.getStyleClass().add("name");
                        tempTextFlow.getChildren().add(name);
                    }

                    tempTextFlow.getChildren().add(text);
                    tempTextFlow.setMaxWidth(500);

                    TextFlow textFlow = new TextFlow(tempTextFlow);
                    HBox hBox = new HBox(12);

                    vBox.setAlignment(Pos.TOP_LEFT);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.getChildren().add(textFlow);

                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void attachmentMouseClickedOnAction(MouseEvent mouseEvent) {

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        chooser = new FileChooser();
        chooser.setTitle("Open Image");
        this.path = chooser.showOpenDialog(stage);
        printWriter.println(lblName.getText() + " " + "img" + path.getPath());
        printWriter.flush();
    }

    public void emojiMouseClickedOnAction(MouseEvent mouseEvent) {
        if (!emojiPane.isVisible()){
            emojiPane.setVisible(true);
        }else {
            emojiPane.setVisible(false);
        }
    }

    public void smileMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE10");
    }

    public void smilefullMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE03");
    }

    public void grinningMouseClickedOnAction(MouseEvent mouseEvent) {
    }

    public void laughMouseClickedOnAction(MouseEvent mouseEvent) {
    }

    public void loveMouseClickedOnAction(MouseEvent mouseEvent) {
    }

    public void sickMouseClickedOnAction(MouseEvent mouseEvent) {
    }

    public void cryMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE02");

    }

    public void angryMouseClickedOnAction(MouseEvent mouseEvent) {
    }

    public void shakehandMouseClickedOnAction(MouseEvent mouseEvent) {
    }

    public void heartMouseClickedOnAction(MouseEvent mouseEvent) {
    }

    public void sadMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE14");

    }

    public void clapMouseClickedOnAction(MouseEvent mouseEvent) {
    }
}
