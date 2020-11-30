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
        loginbtn.setOnAction(event -> {
            try {
                String id = idfield.getText();
                String pw = pwfield.getText();

                Protocol protocol = new Protocol(Protocol.PT_REQ_LOGIN);
                byte[] buf = protocol.getPacket();
                protocol.setID_Password(id, pw);
                Myconn.setSessUserID(id);
                Myconn.setSessUserPW(pw);
                Myconn.os.write(protocol.getPacket());

                Myconn.is.read(buf);
                int packetType = buf[0];
                int packetCode = buf[1];

                protocol.setPacket(packetType, packetCode, buf);
                
                String result = protocol.getLoginResult();

                if (packetCode==1) { // 고객 로그인
                    Parent second = FXMLLoader.load(getClass().getResource("customerMain.fxml"));
                    Scene scene = new Scene(second);
                    Stage primaryStage = (Stage) loginbtn.getScene().getWindow();
                    primaryStage.setScene(scene);
                }
                if (packetCode==2) { // 담당자 로그인
                    Parent third=FXMLLoader.load(getClass().getResource("adminMain.java"));
                    Scene scene=new Scene(third);
                    Stage primaryStage = (Stage)loginbtn.getScene().getWindow();
                    primaryStage.setScene(scene);
                }
                if (packetCode==3) { // 로그인 실패
                    idfield.clear();
                    pwfield.clear();
                }
            } catch (IOException e) {
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
    // Stage customerStage=new Stage();
    // Parent root =FXMLLoader.load(getClass().getResource("customerMain.fxml"));
    // Scene scene = new Scene(root);
    // customerStage.setScene(scene);
    // customerStage.show();
    // return;
    // }
}