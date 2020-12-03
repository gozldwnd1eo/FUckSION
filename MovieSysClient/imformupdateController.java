package MovieSysClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.*;

public class imformupdateController implements Initializable {

    @FXML
    private TextField idfd;

    @FXML
    private PasswordField pwfd;

    @FXML
    private PasswordField pwcnffd;

    @FXML
    private TextField namefd;

    @FXML
    private TextField emailfd;

    @FXML
    private TextField birthfd;

    @FXML
    private TextField phonefd;

    @FXML
    private RadioButton radio_man;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton radio_woman;

    @FXML
    private Button before_btn;

    @FXML
    private Button rev_btn;

    @FXML
    private TextField accountfd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_MY_INFO);
            byte[] buf = protocol.getPacket();

            Myconn.os.write(protocol.getPacket());

            protocol = new Protocol();
            buf = protocol.getPacket();

            Myconn.is.read(buf);

            int protocolType = buf[0];
            int protocolCode = buf[1];

            protocol.setPacket(protocolType, protocolCode, buf);
            String body = protocol.getListBody();

            String[] bodydiv = body.split("\\\\");

            idfd.setText(bodydiv[0]);
            pwfd.setText(bodydiv[1]);
            pwcnffd.setText(bodydiv[2]);
            phonefd.setText(bodydiv[3]);
            accountfd.setText(bodydiv[4]);
            if (bodydiv[5].equals("남")) {
                radio_man.setSelected(true);
            } else {
                radio_woman.setSelected(true);
            }
            emailfd.setText(bodydiv[7]);
            birthfd.setText(bodydiv[8]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rev_btn.setOnAction(event -> {
            try {
                String data = "";
                if (pwfd.getText().trim().equals(pwcnffd.getText().trim())) {
                    data += pwfd.getText().trim() + "\\";
                    data += phonefd.getText().trim() + "\\";
                    data += emailfd.getText().trim() + "\\";
                    data += accountfd.getText().trim() + "\\";

                    Protocol protocol = new Protocol(Protocol.PT_REQ_UPDATE,
                            Protocol.CODE_PT_REQ_UPDATE_CHANGE_MEM_INFO);
                    byte[] buf = protocol.getPacket();

                    protocol.setList(data);

                    Myconn.os.write(protocol.getPacket());

                    protocol = new Protocol();
                    buf = protocol.getPacket();

                    Myconn.is.read(buf);
                    int ptType = buf[0];
                    int ptCode = buf[1];

                    protocol.setPacket(ptType, ptCode, buf);

                    if (ptCode == Protocol.CODE_PT_RES_UPDATE_CHANGE_MEM_INFO_OK) {
                        Stage primaryStage = (Stage) rev_btn.getScene().getWindow();
                        Stage dialog = new Stage(StageStyle.UTILITY);
                        dialog.initModality(Modality.WINDOW_MODAL);
                        dialog.initOwner(primaryStage);
                        dialog.setTitle("Success");

                        Parent parent = FXMLLoader.load(getClass().getResource("errordialog.fxml"));
                        Label dialogtext = (Label) parent.lookup("#dialogtext");
                        dialogtext.setText("정보 수정 성공.");
                        Button ok_btn = (Button) parent.lookup("#ok_btn");
                        ok_btn.setOnAction(e -> {
                            dialog.close();
                        });
                        Scene scene = new Scene(parent);
                        dialog.setScene(scene);
                        dialog.setResizable(false);
                        dialog.show();
                        Parent parent2 = FXMLLoader.load(getClass().getResource("customerMain.fxml"));
                        Scene scene2 = new Scene(parent2);
                        primaryStage.setScene(scene2);
                    } else {
                        Stage primaryStage = (Stage) rev_btn.getScene().getWindow();
                        Stage dialog = new Stage(StageStyle.UTILITY);
                        dialog.initModality(Modality.WINDOW_MODAL);
                        dialog.initOwner(primaryStage);
                        dialog.setTitle("error");

                        Parent parent = FXMLLoader.load(getClass().getResource("errordialog.fxml"));
                        Label dialogtext = (Label) parent.lookup("#dialogtext");
                        dialogtext.setText("정보 수정 실패.");
                        Button ok_btn = (Button) parent.lookup("#ok_btn");
                        ok_btn.setOnAction(e -> {
                            dialog.close();
                        });
                        Scene scene = new Scene(parent);
                        dialog.setScene(scene);
                        dialog.setResizable(false);
                        dialog.show();
                    }
                }
            } catch (IOException e) {

            }
        });
    }

}
