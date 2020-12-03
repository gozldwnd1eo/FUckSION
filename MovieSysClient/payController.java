package MovieSysClient;

import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class payController implements Initializable {

    @FXML
    private ImageView poster;
    private Button pay_btn;
    private TextArea paydatafield;
    private TextField customeraccount;
    private TextField amount;
    private CheckBox confirm;
    private Button before_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_ACCOUNT);
            byte[] buf = protocol.getPacket();
            protocol.setID(Myconn.SessionUserID);
            Myconn.os.write(protocol.getPacket());

            protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_ACCOUNT_OK);
            buf = protocol.getPacket();

            Myconn.is.read(buf);

            int packetType = buf[0];
            int packetCode = buf[1];
            // account get
            // protocol.getMemberDetails();

        } catch (IOException e) {
            e.printStackTrace();
        }

        pay_btn.setOnAction(event -> {
            // 결제 프로토콜 진행
            try {
                Stage primaryStage = (Stage) pay_btn.getScene().getWindow();
                Protocol protocol = new Protocol(Protocol.PT_REQ_UPDATE, Protocol.CODE_PT_REQ_UPDATE_ADD_PAY_RESV);
                int price = 8000 * Integer.parseInt(Userchoice.getMannum());
                String[] data = { Myconn.SessionUserID, Userchoice.getSchedule(), Userchoice.getSelectSeat(),
                        Userchoice.getMannum(), Integer.toString(price) };
                protocol.setAdd_Pay_Resv(data); // 갱신요청코드5(고객ID\상영영화ID\좌석번호\인원수\금액)
                Myconn.os.write(protocol.getPacket());

                protocol = new Protocol();
                byte[] buf = protocol.getPacket();

                Myconn.is.read(buf);

                int packetType = buf[0];
                int packetCode = buf[1];
                protocol.setPacket(packetType, packetCode, buf);
                if ((protocol.getProtocolType() == Protocol.PT_RES_UPDATE)
                        && (protocol.getProtocolCode() == Protocol.CODE_PT_RES_UPDATE_ADD_PAY_RESV_OK)) {
                    Stage dialog = new Stage(StageStyle.UTILITY);
                    dialog.initModality(Modality.WINDOW_MODAL);
                    dialog.initOwner(primaryStage);
                    dialog.setTitle("Error");

                    Parent parent = FXMLLoader.load(getClass().getResource("errordialog.fxml"));
                    Label dialogtext = (Label) parent.lookup("#dialogtext");
                    dialogtext.setText("예기치 못한 오류가 발생했습니다.");
                    Button ok_btn = (Button) parent.lookup("#ok_btn");
                    ok_btn.setOnAction(e -> {
                        try {
                            dialog.close();
                            Parent parent2 = FXMLLoader.load(getClass().getResource("reservationController.fxml"));
                            Scene scene = new Scene(parent2);
                            primaryStage.setScene(scene);
                        } catch (IOException excep) {
                            excep.printStackTrace();
                        }
                    });
                    Scene scene = new Scene(parent);

                    dialog.setScene(scene);
                    dialog.setResizable(false);
                    dialog.show();
                } else {
                    Parent parent = FXMLLoader.load(getClass().getResource("customerMain.fxml"));
                    Scene scene = new Scene(parent);
                    primaryStage.setScene(scene);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}