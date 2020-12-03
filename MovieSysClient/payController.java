package MovieSysClient;

import java.util.ArrayList;
import java.util.Iterator;
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
    @FXML
    private Button pay_btn;
    @FXML
    private TextArea paydatafield;
    @FXML
    private TextField customeraccount;
    @FXML
    private TextField amount;
    @FXML
    private CheckBox confirm;
    @FXML
    private Button before_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // try {
        // Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP,
        // Protocol.CODE_PT_REQ_LOOKUP_ACCOUNT);
        // byte[] buf = protocol.getPacket();
        // protocol.setID(Myconn.SessionUserID);
        // Myconn.os.write(protocol.getPacket());
        // ArrayList<String[]> data = new ArrayList<String[]>();

        // protocol = new Protocol(Protocol.PT_RES_LOOKUP,
        // Protocol.CODE_PT_RES_LOOKUP_ACCOUNT_OK);
        // buf = protocol.getPacket();

        // byte last = 0;
        // boolean stopread = false;
        // while (!stopread) {
        // Myconn.is.read(buf);
        // int packetType = buf[0];
        // int packetCode = buf[1];
        // byte[] b = new byte[2];
        // System.arraycopy(buf, 3, b, 0, 2);
        // int packetBodyLen = (b[0] & 0x000000ff << 8);
        // packetBodyLen += (b[1] & 0x000000ff);
        // // int packetBodyLen = buf[3];
        // int packetFlag = buf[5];
        // last = buf[6];
        // int packetSeqNum = buf[7];
        // protocol.setPacket(packetType, packetCode, packetBodyLen, packetFlag, last,
        // packetSeqNum, buf);
        // String[] temp = protocol.getAccountInfo();
        // data.add(temp);
        // if (last == 1)
        // stopread = true;
        // }
        // String[] body=new String[1];
        // Iterator<String[]> it = data.iterator();
        // while (it.hasNext()) {
        // body = it.next();
        // }
        // String[] bodydiv = body[1].split("\\\\");
        // customeraccount.setText(bodydiv[0]);

        paydatafield.setText("인원수 : " + Userchoice.mannum + "명 \r\n좌석 : " + Userchoice.selectSeat);
        amount.setText(Integer.toString(8000 * Integer.parseInt(Userchoice.mannum)));

        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        before_btn.setOnAction(event -> {
            Parent parent;
            try {
                parent = FXMLLoader.load(getClass().getResource("reservation.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = (Stage) before_btn.getScene().getWindow();
                primaryStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        pay_btn.setOnAction(event -> {
            // 결제 프로토콜 진행
            if (confirm.isSelected())
                try {
                    Stage primaryStage = (Stage) pay_btn.getScene().getWindow();
                    Protocol protocol = new Protocol(Protocol.PT_REQ_UPDATE, Protocol.CODE_PT_REQ_UPDATE_ADD_PAY_RESV);
                    int price = 8000 * Integer.parseInt(Userchoice.getMannum());
                    String[] data = { Myconn.SessionUserID, Userchoice.getSchedule(), Userchoice.getSelectSeat(),
                            Userchoice.getMannum(), Integer.toString(price) };
                    protocol.setAdd_Pay_Resv(data); // 갱신요청코드5(고객ID\상영영화ID\좌석번호\인원수\금액)
                    Myconn.os.write(protocol.getPacket());

                    protocol = new Protocol(Protocol.PT_RES_UPDATE, Protocol.CODE_PT_RES_UPDATE_ADD_PAY_RESV_OK);
                    byte[] buf = protocol.getPacket();

                    Myconn.is.read(buf);

                    int packetType = buf[0];
                    int packetCode = buf[1];
                    protocol.setPacket(packetType, packetCode, buf); // 찌발
                    if ((protocol.getProtocolType() == Protocol.PT_RES_UPDATE)
                            && (protocol.getProtocolCode() == Protocol.CODE_PT_RES_UPDATE_ADD_PAY_RESV_OK)) { // 성공
                        Stage dialog = new Stage(StageStyle.UTILITY);
                        dialog.initModality(Modality.WINDOW_MODAL);
                        dialog.initOwner(primaryStage);
                        dialog.setTitle("Success");

                        Parent parent = FXMLLoader.load(getClass().getResource("errordialog.fxml"));
                        Label dialogtext = (Label) parent.lookup("#dialogtext");
                        dialogtext.setText("예매 완료");
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
                    } else if ((protocol.getProtocolType() == Protocol.PT_RES_UPDATE)
                            && (protocol.getProtocolCode() == Protocol.CODE_PT_RES_UPDATE_ADD_PAY_RESV_NO2)) { // 돈모자람/좌석은
                                                                                                               // 남아있음
                        Stage dialog = new Stage(StageStyle.UTILITY);
                        dialog.initModality(Modality.WINDOW_MODAL);
                        dialog.initOwner(primaryStage);
                        dialog.setTitle("Error");
                        Parent parent = FXMLLoader.load(getClass().getResource("errordialog.fxml"));
                        Label dialogtext = (Label) parent.lookup("#dialogtext");
                        dialogtext.setText("잔액이 부족합니다.");
                        Button ok_btn = (Button) parent.lookup("#ok_btn");
                        ok_btn.setOnAction(e -> {
                            dialog.close();
                        });
                        Scene scene = new Scene(parent);

                        dialog.setScene(scene);
                        dialog.setResizable(false);
                        dialog.show();
                        Parent parent2 = FXMLLoader.load(getClass().getResource("reservation.fxml"));
                        Scene scene2 = new Scene(parent2);
                        primaryStage.setScene(scene2);
                    } else if ((protocol.getProtocolType() == Protocol.PT_RES_UPDATE)
                            && (protocol.getProtocolCode() == Protocol.CODE_PT_RES_UPDATE_ADD_PAY_RESV_NO1)) { // 좌석x
                                                                                                               // 돈있음
                        Stage dialog = new Stage(StageStyle.UTILITY);
                        dialog.initModality(Modality.WINDOW_MODAL);
                        dialog.initOwner(primaryStage);
                        dialog.setTitle("Error");

                        Parent parent = FXMLLoader.load(getClass().getResource("errordialog.fxml"));
                        Label dialogtext = (Label) parent.lookup("#dialogtext");
                        dialogtext.setText("선택한 좌석이 유효하지 않습니다.");
                        Button ok_btn = (Button) parent.lookup("#ok_btn");
                        ok_btn.setOnAction(e -> {
                            dialog.close();
                        });
                        Scene scene = new Scene(parent);

                        dialog.setScene(scene);
                        dialog.setResizable(false);
                        dialog.show();
                        Parent parent2 = FXMLLoader.load(getClass().getResource("reservation.fxml"));
                        Scene scene2 = new Scene(parent2);
                        primaryStage.setScene(scene2);
                    } else { // 좌x돈x
                        Stage dialog = new Stage(StageStyle.UTILITY);
                        dialog.initModality(Modality.WINDOW_MODAL);
                        dialog.initOwner(primaryStage);
                        dialog.setTitle("Error");

                        Parent parent = FXMLLoader.load(getClass().getResource("errordialog.fxml"));
                        Label dialogtext = (Label) parent.lookup("#dialogtext");
                        dialogtext.setText("선택한 좌석이 유효하지 않습니다. 잔액이 부족합니다.");
                        Button ok_btn = (Button) parent.lookup("#ok_btn");
                        ok_btn.setOnAction(e -> {
                            dialog.close();
                        });
                        Scene scene = new Scene(parent);
                        dialog.setScene(scene);
                        dialog.setResizable(false);
                        dialog.show();
                        Parent parent2 = FXMLLoader.load(getClass().getResource("reservation.fxml"));
                        Scene scene2 = new Scene(parent2);
                        primaryStage.setScene(scene2);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });
    }
}
