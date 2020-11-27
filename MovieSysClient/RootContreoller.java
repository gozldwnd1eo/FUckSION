package MovieSysClient;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import MovieSysServer.LoginProtocol.*;

public class RootContreoller implements Initializable {

    @FXML
    private TextField idfield;
    @FXML
    private PasswordField pwfield;
    @FXML
    private Button loginbtn;
    @FXML
    private Button exitbtn;
    @FXML
    private Button signup;
    @FXML
    private Button searchbtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginbtn.setOnAction(event->{
            try{
                String id=idfield.getText();
                String pw=pwfield.getText();
                
                // Protocol protocol=new Protocol(Protocol.PT_REQ_LOGIN);
                // protocol.setId(id);

                Parent second=FXMLLoader.load(getClass().getResource("customerMain.fxml"));
                Scene scene = new Scene(second);
                Stage primaryStage=(Stage) loginbtn.getScene().getWindow();
                primaryStage.setScene(scene);
            }catch(IOException e){
                e.printStackTrace();
            }
        });
    }


    @FXML
    public void exitpush(ActionEvent event) {
        Platform.exit();
    }

    // @FXML
    // public void loginpush(ActionEvent event) throws IOException {
    //     Stage customerStage=new Stage();
    //     Parent root =FXMLLoader.load(getClass().getResource("customerMain.fxml"));
    //     Scene scene = new Scene(root);
    //     customerStage.setScene(scene);
    //     customerStage.show();
    //     return;
    // }
}