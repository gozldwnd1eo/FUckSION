package MovieSysClient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class CustomerMainController implements Initializable {

    @FXML
    private ListView<String> film_list;

    @FXML
    private Button detail_btn;

    @FXML
    private Button rev_btn;

    @FXML
    private Label filmname;

    @FXML
    private Label film_starpt;

    @FXML
    private Label filmrevrate;

    @FXML
    private ComboBox<String> theaterCombox;

    @FXML
    private TextArea theaterDetailTxtfd;

    @FXML
    private ListView<String> theaterFilmName;

    @FXML
    private ListView<String> theaterFilmSchdule;

    @FXML
    private Button theat_rev_btn;

    @FXML
    private PasswordField pwcheckfd;

    @FXML
    private Button pwchek_btn;

    @FXML
    private Button change_imf_btn;

    @FXML
    private Button myreview_btn;

    @FXML
    private Button myrevhis_btn;

    @FXML
    private Button dropuser_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 최초 로그인시 영화탭의 기본 정보 출력
        // 영화탭의 영화 리스트=담당자가 상영스케줄을 등록한 영화들만 출력됨(예약해야 하기 때문)
        // select *,avg(rev_starpoint)
        // from film,review
        // where film_id=(select distinct film_id from screen)
        // groupby film_id
        try {
            Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_ALL_SCREEN);
            byte[] buf = protocol.getPacket();
            Myconn.os.write(protocol.getPacket());
            ArrayList<String> data = new ArrayList<String>();

            protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_ALL_SCREEN_OK);
            buf = protocol.getPacket();

            byte last = 0;
            boolean stopread = false;
            while (!stopread) {
                Myconn.is.read(buf);
                int packetType = buf[0];
                int packetCode = buf[1];
                byte[] b = new byte[2];
                System.arraycopy(buf, 3, b, 0, 2);
                int packetBodyLen = (b[0] & 0x000000ff << 8);
                packetBodyLen += (b[1] & 0x000000ff);
                // int packetBodyLen = buf[3];
                int packetFlag = buf[5];
                last = buf[6];
                int packetSeqNum = buf[7];
                protocol.setPacket(packetType, packetCode, packetBodyLen, packetFlag, last, packetSeqNum, buf);
                String temp = protocol.getScreenList();
                data.add(temp);
                if (last == 1)
                    stopread = true;
            }
            String body = "";
            Iterator<String> it = data.iterator();
            while (it.hasNext()) {
                body += it.next();
            }
            String[] bodydiv = body.split("\\|");
            ArrayList<String[]> filmliststring = new ArrayList<String[]>();

            for (int i = 0; i < bodydiv.length; i++) {
                String[] fielddiv = bodydiv[i].split("\\\\");
                filmliststring.add(fielddiv);
            }
            ObservableList<String> nowscreenlist = FXCollections.observableArrayList();

            for (int i = 0; i < filmliststring.size(); i++) {
                String[] line = filmliststring.get(i);
                nowscreenlist.add(line[1]);
            }
            film_list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            film_list.setItems(nowscreenlist);

            film_list.setOnMouseClicked(event -> {
                String selected = film_list.getSelectionModel().getSelectedItem();
                Iterator<String[]> ite = filmliststring.iterator();
                String[] temp = null;
                while (ite.hasNext()) {
                    temp = ite.next();
                    if (temp[1].equals(selected)) {
                        break;
                    }
                }
                filmname.setText(temp[1]);
                film_starpt.setText(temp[3]);
                filmrevrate.setText(temp[2]);
            });

            detail_btn.setOnAction(event -> {
                try {
                    String selected = film_list.getSelectionModel().getSelectedItem();
                    Iterator<String[]> ite = filmliststring.iterator();
                    String[] temp = null;
                    while (ite.hasNext()) {
                        temp = ite.next();
                        if (temp[1].equals(selected)) {
                            break;
                        }
                    }
                    Userchoice.setFilmID(temp[0]);
                    Parent parent = FXMLLoader.load(getClass().getResource("moviedetail.fxml"));
                    Scene scene = new Scene(parent);
                    Stage primaryStage = (Stage) detail_btn.getScene().getWindow();
                    primaryStage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            rev_btn.setOnAction(event -> {
                try {
                    String selected = film_list.getSelectionModel().getSelectedItem();
                    Iterator<String[]> ite = filmliststring.iterator();
                    String[] temp = null;
                    while (ite.hasNext()) {
                        temp = ite.next();
                        if (temp[1].equals(selected)) {
                            break;
                        }
                    }
                    Userchoice.setFilmID(temp[0]);
                    Parent parent = FXMLLoader.load(getClass().getResource("reservation.fxml"));
                    Scene scene = new Scene(parent);
                    Stage primaryStage = (Stage) detail_btn.getScene().getWindow();
                    primaryStage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}