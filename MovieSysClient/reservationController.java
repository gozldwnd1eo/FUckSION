package MovieSysClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;

import java.io.IOException;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class reservationController implements Initializable {

    @FXML
    private ImageView poster;
    @FXML
    private ComboBox<String> localcombox;
    @FXML
    private ComboBox<theaterListModel> theatercombox;
    @FXML
    private ComboBox<scheduleListModel> schedcombox;
    @FXML
    private CheckBox a1;
    @FXML
    private CheckBox a2;
    @FXML
    private CheckBox a3;
    @FXML
    private CheckBox a4;
    @FXML
    private CheckBox a5;
    @FXML
    private CheckBox a6;
    @FXML
    private CheckBox a7;
    @FXML
    private CheckBox a8;
    @FXML
    private CheckBox a9;
    @FXML
    private CheckBox a10;
    @FXML
    private CheckBox a11;
    @FXML
    private CheckBox a12;
    @FXML
    private CheckBox a13;
    @FXML
    private CheckBox a14;
    @FXML
    private CheckBox b1;
    @FXML
    private CheckBox b2;
    @FXML
    private CheckBox b3;
    @FXML
    private CheckBox b4;
    @FXML
    private CheckBox b5;
    @FXML
    private CheckBox b6;
    @FXML
    private CheckBox b7;
    @FXML
    private CheckBox b8;
    @FXML
    private CheckBox b9;
    @FXML
    private CheckBox b10;
    @FXML
    private CheckBox b11;
    @FXML
    private CheckBox b12;
    @FXML
    private CheckBox b13;
    @FXML
    private CheckBox b14;
    @FXML
    private CheckBox c1;
    @FXML
    private CheckBox c2;
    @FXML
    private CheckBox c3;
    @FXML
    private CheckBox c4;
    @FXML
    private CheckBox c5;
    @FXML
    private CheckBox c6;
    @FXML
    private CheckBox c7;
    @FXML
    private CheckBox c8;
    @FXML
    private CheckBox c9;
    @FXML
    private CheckBox c10;
    @FXML
    private CheckBox c11;
    @FXML
    private CheckBox c12;
    @FXML
    private CheckBox c13;
    @FXML
    private CheckBox c14;
    @FXML
    private CheckBox d1;
    @FXML
    private CheckBox d2;
    @FXML
    private CheckBox d3;
    @FXML
    private CheckBox d4;
    @FXML
    private CheckBox d5;
    @FXML
    private CheckBox d6;
    @FXML
    private CheckBox d7;
    @FXML
    private CheckBox d8;
    @FXML
    private CheckBox d9;
    @FXML
    private CheckBox d10;
    @FXML
    private CheckBox d11;
    @FXML
    private CheckBox d12;
    @FXML
    private CheckBox d13;
    @FXML
    private CheckBox d14;
    @FXML
    private CheckBox e1;
    @FXML
    private CheckBox e2;
    @FXML
    private CheckBox e3;
    @FXML
    private CheckBox e4;
    @FXML
    private CheckBox e5;
    @FXML
    private CheckBox e6;
    @FXML
    private CheckBox e7;
    @FXML
    private CheckBox e8;
    @FXML
    private CheckBox e9;
    @FXML
    private CheckBox e10;
    @FXML
    private CheckBox e11;
    @FXML
    private CheckBox e12;
    @FXML
    private CheckBox e13;
    @FXML
    private CheckBox e14;
    @FXML
    private CheckBox f1;
    @FXML
    private CheckBox f2;
    @FXML
    private CheckBox f3;
    @FXML
    private CheckBox f4;
    @FXML
    private CheckBox f5;
    @FXML
    private CheckBox f6;
    @FXML
    private CheckBox f7;
    @FXML
    private CheckBox f8;
    @FXML
    private CheckBox f9;
    @FXML
    private CheckBox f10;
    @FXML
    private CheckBox f11;
    @FXML
    private CheckBox f12;
    @FXML
    private CheckBox f13;
    @FXML
    private CheckBox f14;
    @FXML
    private Button nextbtn;
    @FXML
    private Button beforebtn;

    ArrayList<CheckBox> btnlist = new ArrayList<CheckBox>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            btnlist.add(a1);
            btnlist.add(a2);
            btnlist.add(a3);
            btnlist.add(a4);
            btnlist.add(a5);
            btnlist.add(a6);
            btnlist.add(a7);
            btnlist.add(a8);
            btnlist.add(a9);
            btnlist.add(a10);
            btnlist.add(a11);
            btnlist.add(a12);
            btnlist.add(a13);
            btnlist.add(a14);
            btnlist.add(b1);
            btnlist.add(b2);
            btnlist.add(b3);
            btnlist.add(b4);
            btnlist.add(b5);
            btnlist.add(b6);
            btnlist.add(b7);
            btnlist.add(b8);
            btnlist.add(b9);
            btnlist.add(b10);
            btnlist.add(b11);
            btnlist.add(b12);
            btnlist.add(b13);
            btnlist.add(b14);
            btnlist.add(c1);
            btnlist.add(c2);
            btnlist.add(c3);
            btnlist.add(c4);
            btnlist.add(c5);
            btnlist.add(c6);
            btnlist.add(c7);
            btnlist.add(c8);
            btnlist.add(c9);
            btnlist.add(c10);
            btnlist.add(c11);
            btnlist.add(c12);
            btnlist.add(c13);
            btnlist.add(c14);
            btnlist.add(d1);
            btnlist.add(d2);
            btnlist.add(d3);
            btnlist.add(d4);
            btnlist.add(d5);
            btnlist.add(d6);
            btnlist.add(d7);
            btnlist.add(d8);
            btnlist.add(d9);
            btnlist.add(d10);
            btnlist.add(d11);
            btnlist.add(d12);
            btnlist.add(d13);
            btnlist.add(d14);
            btnlist.add(e1);
            btnlist.add(e2);
            btnlist.add(e3);
            btnlist.add(e4);
            btnlist.add(e5);
            btnlist.add(e6);
            btnlist.add(e7);
            btnlist.add(e8);
            btnlist.add(e9);
            btnlist.add(e10);
            btnlist.add(e11);
            btnlist.add(e12);
            btnlist.add(e13);
            btnlist.add(e14);
            btnlist.add(f1);
            btnlist.add(f2);
            btnlist.add(f3);
            btnlist.add(f4);
            btnlist.add(f5);
            btnlist.add(f6);
            btnlist.add(f7);
            btnlist.add(f8);
            btnlist.add(f9);
            btnlist.add(f10);
            btnlist.add(f11);
            btnlist.add(f12);
            btnlist.add(f13);
            btnlist.add(f14);

            Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_AREA);
            byte[] buf = protocol.getPacket();
            protocol.setFlimID(Userchoice.getFilmID());
            Myconn.os.write(protocol.getPacket());

            protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_AREA_OK);
            buf = protocol.getPacket();

            String areas = "";
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
                int packetFlag = buf[5];
                last = buf[6];
                int packetSeqNum = buf[7];
                protocol.setPacket(packetType, packetCode, packetBodyLen, packetFlag, last, packetSeqNum, buf);
                areas += protocol.getListBody();
                if (last == 1) {
                    stopread = true;
                }
            }
            String[] arealist = areas.split("\\|");
            ObservableList<String> areacomboxlist = FXCollections.observableArrayList();
            areacomboxlist.setAll(arealist);
            localcombox.setItems(areacomboxlist);

            beforebtn.setOnAction(event -> {

                Parent parent;
                try {
                    parent = FXMLLoader.load(getClass().getResource("reservation.fxml"));
                    Scene scene = new Scene(parent);
                    Stage primaryStage = (Stage) beforebtn.getScene().getWindow();
                    primaryStage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            nextbtn.setOnAction(event -> {
                ArrayList<String> selectedSeat = new ArrayList<String>();
                Iterator<CheckBox> it = btnlist.iterator();
                int mannum = 0;
                while (it.hasNext()) {
                    CheckBox temp = it.next();
                    if (temp.isSelected()) {
                        selectedSeat.add(temp.getText());
                        mannum++;
                    }
                }
                Userchoice.setMannum(Integer.toString(mannum));
                String seatstring = "";
                int i = 0;
                for (; i < selectedSeat.size() - 1; i++) {
                    seatstring += selectedSeat.get(i) + "~";
                }
                seatstring += selectedSeat.get(i);

                if (seatstring.length() < 1) {
                    return;
                }
                Userchoice.setSelectSeat(seatstring);

                try {
                    Parent parent;
                    parent = FXMLLoader.load(getClass().getResource("pay.fxml"));
                    Scene scene = new Scene(parent);
                    Stage primaryStage = (Stage) nextbtn.getScene().getWindow();
                    primaryStage.setScene(scene);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void selectarea(ActionEvent event) {
        try {
            Userchoice.setArea(localcombox.getValue());
            Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_THEATER);
            byte[] buf = protocol.getPacket();
            protocol.setTheaterArea_FlimID(localcombox.getValue(), Userchoice.getFilmID());
            Myconn.os.write(protocol.getPacket());

            protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_THEATER_OK);
            buf = protocol.getPacket();

            String theaters = "";
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
                int packetFlag = buf[5];
                last = buf[6];
                int packetSeqNum = buf[7];
                protocol.setPacket(packetType, packetCode, packetBodyLen, packetFlag, last, packetSeqNum, buf);
                theaters += protocol.getListBody();
                if (last == 1) {
                    stopread = true;
                }
            }
            String[] theaterlist = theaters.split("\\|");
            ArrayList<theaterListModel> theatermodel = new ArrayList<theaterListModel>();
            for (int i = 0; i < theaterlist.length; i++) {
                String[] temp = theaterlist[i].split("\\\\");
                theaterListModel model = new theaterListModel();
                model.setTheaterID(temp[0]);
                model.setTheaterName(temp[1]);
                model.setArea(temp[2]);
                model.setTheaterAddress(temp[3]);
                model.setAdminID(temp[4]);
                theatermodel.add(model);
            }
            ObservableList<theaterListModel> theatercomboxlist = FXCollections.observableArrayList();
            theatercomboxlist.addAll(theatermodel);
            theatercombox.setItems(theatercomboxlist);
            theatercombox.setCellFactory(parma -> new ListCell<theaterListModel>() {
                @Override
                protected void updateItem(theaterListModel item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.getTheaterName() == null) {
                        setText(null);
                    } else {
                        setText(item.getTheaterName());
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void selecttheater(ActionEvent event) {
        try {
            theatercombox.setPromptText(theatercombox.getValue().getTheaterName());
            Userchoice.setTheater(theatercombox.getValue().getTheaterID());
            Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_SCREEN_TIME);
            byte[] buf = protocol.getPacket();
            protocol.setTheaterID_FlimID(Userchoice.getTheater(), Userchoice.getFilmID());
            Myconn.os.write(protocol.getPacket());

            protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_SCREEN_TIME_OK);
            buf = protocol.getPacket();

            String scheds = "";
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
                int packetFlag = buf[5];
                last = buf[6];
                int packetSeqNum = buf[7];
                protocol.setPacket(packetType, packetCode, packetBodyLen, packetFlag, last, packetSeqNum, buf);
                scheds += protocol.getListBody();
                if (last == 1) {
                    stopread = true;
                }
            }
            String[] schedlist = scheds.split("\\|");
            ArrayList<scheduleListModel> schedulemodel = new ArrayList<scheduleListModel>();
            for (int i = 0; i < schedlist.length; i++) {
                String[] temp = schedlist[i].split("\\\\");
                scheduleListModel model = new scheduleListModel();
                model.setScreenID(temp[0]);
                model.setAudiNum(temp[1]);
                model.setStartTime(temp[2]);
                model.setEndTime(temp[3]);
                schedulemodel.add(model);
            } // 0:상영영화id 1:상영관번호 2:시작시간 3:종료시간
            ObservableList<scheduleListModel> schedcomboxlist = FXCollections.observableArrayList();
            schedcomboxlist.addAll(schedulemodel);
            schedcombox.setItems(schedcomboxlist);
            schedcombox.setCellFactory(parma -> new ListCell<scheduleListModel>() {
                @Override
                protected void updateItem(scheduleListModel item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.getListDisplay() == null) {
                        setText(null);
                    } else {
                        setText(item.getListDisplay());
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void selectschedule(ActionEvent event) {
        try {
            for (int i = 0; i < btnlist.size(); i++) {
                btnlist.get(i).setDisable(false);
            }
            schedcombox.setPromptText(schedcombox.getValue().getListDisplay());
            Userchoice.setSchedule(schedcombox.getValue().getScreenID());
            Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_SEAT_SITUATION);
            byte[] buf = protocol.getPacket();
            protocol.setScreenID(Userchoice.getSchedule());
            Myconn.os.write(protocol.getPacket());

            protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_SEAT_SITUATION_OK);
            buf = protocol.getPacket();

            String seats = "";
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
                int packetFlag = buf[5];
                last = buf[6];
                int packetSeqNum = buf[7];
                protocol.setPacket(packetType, packetCode, packetBodyLen, packetFlag, last, packetSeqNum, buf);
                seats += protocol.getListBody();
                if (last == 1) {
                    stopread = true;
                }
            }

            String[] seatlist = seats.split("~");
            for (int i = 0; i < seatlist.length; i++) {
                switch (seatlist[i]) {
                    case "A1":
                        a1.setDisable(true);
                        a1.setSelected(false);
                        break;
                    case "A2":
                        a2.setDisable(true);
                        a2.setSelected(false);
                        break;
                    case "A3":
                        a3.setDisable(true);
                        a3.setSelected(false);
                        break;
                    case "A4":
                        a4.setDisable(true);
                        a4.setSelected(false);
                        break;
                    case "A5":
                        a5.setDisable(true);
                        a5.setSelected(false);
                        break;
                    case "A6":
                        a6.setDisable(true);
                        a6.setSelected(false);
                        break;
                    case "A7":
                        a7.setDisable(true);
                        a7.setSelected(false);
                        break;
                    case "A8":
                        a8.setDisable(true);
                        a8.setSelected(false);
                        break;
                    case "A9":
                        a9.setDisable(true);
                        a9.setSelected(false);
                        break;
                    case "A10":
                        a10.setDisable(true);
                        a10.setSelected(false);
                        break;
                    case "A11":
                        a11.setDisable(true);
                        a11.setSelected(false);
                        break;
                    case "A12":
                        a12.setDisable(true);
                        a12.setSelected(false);
                        break;
                    case "A13":
                        a13.setDisable(true);
                        a13.setSelected(false);
                        break;
                    case "A14":
                        a14.setDisable(true);
                        a14.setSelected(false);
                        break;
                    case "B1":
                        b1.setDisable(true);
                        b1.setSelected(false);
                        break;
                    case "B2":
                        b2.setDisable(true);
                        b2.setSelected(false);
                        break;
                    case "B3":
                        b3.setDisable(true);
                        b3.setSelected(false);
                        break;
                    case "B4":
                        b4.setDisable(true);
                        b4.setSelected(false);
                        break;
                    case "B5":
                        b5.setDisable(true);
                        b5.setSelected(false);
                        break;
                    case "B6":
                        b6.setDisable(true);
                        b6.setSelected(false);
                        break;
                    case "B7":
                        b7.setDisable(true);
                        b7.setSelected(false);
                        break;
                    case "B8":
                        b8.setDisable(true);
                        b8.setSelected(false);
                        break;
                    case "B9":
                        b9.setDisable(true);
                        b9.setSelected(false);
                        break;
                    case "B10":
                        b10.setDisable(true);
                        b10.setSelected(false);
                        break;
                    case "B11":
                        b11.setDisable(true);
                        b11.setSelected(false);
                        break;
                    case "B12":
                        b12.setDisable(true);
                        b12.setSelected(false);
                        break;
                    case "B13":
                        b13.setDisable(true);
                        b13.setSelected(false);
                        break;
                    case "B14":
                        b14.setDisable(true);
                        b14.setSelected(false);
                        break;
                    case "C1":
                        c1.setDisable(true);
                        c1.setSelected(false);
                        break;
                    case "C2":
                        c2.setDisable(true);
                        c2.setSelected(false);
                        break;
                    case "C3":
                        c3.setDisable(true);
                        c3.setSelected(false);
                        break;
                    case "C4":
                        c4.setDisable(true);
                        c4.setSelected(false);
                        break;
                    case "C5":
                        c5.setDisable(true);
                        c5.setSelected(false);
                        break;
                    case "C6":
                        c6.setDisable(true);
                        c6.setSelected(false);
                        break;
                    case "C7":
                        c7.setDisable(true);
                        c7.setSelected(false);
                        break;
                    case "C8":
                        c8.setDisable(true);
                        c8.setSelected(false);
                        break;
                    case "C9":
                        c9.setDisable(true);
                        c9.setSelected(false);
                        break;
                    case "C10":
                        c10.setDisable(true);
                        c10.setSelected(false);
                        break;
                    case "C11":
                        c11.setDisable(true);
                        c11.setSelected(false);
                        break;
                    case "C12":
                        c12.setDisable(true);
                        c12.setSelected(false);
                        break;
                    case "C13":
                        c13.setDisable(true);
                        c13.setSelected(false);
                        break;
                    case "C14":
                        c14.setDisable(true);
                        c14.setSelected(false);
                        break;
                    case "D1":
                        d1.setDisable(true);
                        d1.setSelected(false);
                        break;
                    case "D2":
                        d2.setDisable(true);
                        d2.setSelected(false);
                        break;
                    case "D3":
                        d3.setDisable(true);
                        d3.setSelected(false);
                        break;
                    case "D4":
                        d4.setDisable(true);
                        d4.setSelected(false);
                        break;
                    case "D5":
                        d5.setDisable(true);
                        d5.setSelected(false);
                        break;
                    case "D6":
                        d6.setDisable(true);
                        d6.setSelected(false);
                        break;
                    case "D7":
                        d7.setDisable(true);
                        d7.setSelected(false);
                        break;
                    case "D8":
                        d8.setDisable(true);
                        d8.setSelected(false);
                        break;
                    case "D9":
                        d9.setDisable(true);
                        d9.setSelected(false);
                        break;
                    case "D10":
                        d10.setDisable(true);
                        d10.setSelected(false);
                        break;
                    case "D11":
                        d11.setDisable(true);
                        d11.setSelected(false);
                        break;
                    case "D12":
                        d12.setDisable(true);
                        d12.setSelected(false);
                        break;
                    case "D13":
                        d13.setDisable(true);
                        d13.setSelected(false);
                        break;
                    case "D14":
                        d14.setDisable(true);
                        d14.setSelected(false);
                        break;
                    case "E1":
                        e1.setDisable(true);
                        e1.setSelected(false);
                        break;
                    case "E2":
                        e2.setDisable(true);
                        e2.setSelected(false);
                        break;
                    case "E3":
                        e3.setDisable(true);
                        e3.setSelected(false);
                        break;
                    case "E4":
                        e4.setDisable(true);
                        e4.setSelected(false);
                        break;
                    case "E5":
                        e5.setDisable(true);
                        e5.setSelected(false);
                        break;
                    case "E6":
                        e6.setDisable(true);
                        e6.setSelected(false);
                        break;
                    case "E7":
                        e7.setDisable(true);
                        e7.setSelected(false);
                        break;
                    case "E8":
                        e8.setDisable(true);
                        e8.setSelected(false);
                        break;
                    case "E9":
                        e9.setDisable(true);
                        e9.setSelected(false);
                        break;
                    case "E10":
                        e10.setDisable(true);
                        e10.setSelected(false);
                        break;
                    case "E11":
                        e11.setDisable(true);
                        e11.setSelected(false);
                        break;
                    case "E12":
                        e12.setDisable(true);
                        e12.setSelected(false);
                        break;
                    case "E13":
                        e13.setDisable(true);
                        e13.setSelected(false);
                        break;
                    case "E14":
                        e14.setDisable(true);
                        e14.setSelected(false);
                        break;
                    case "F1":
                        f1.setDisable(true);
                        f1.setSelected(false);
                        break;
                    case "F2":
                        f2.setDisable(true);
                        f2.setSelected(false);
                        break;
                    case "F3":
                        f3.setDisable(true);
                        f3.setSelected(false);
                        break;
                    case "F4":
                        f4.setDisable(true);
                        f4.setSelected(false);
                        break;
                    case "F5":
                        f5.setDisable(true);
                        f5.setSelected(false);
                        break;
                    case "F6":
                        f6.setDisable(true);
                        f6.setSelected(false);
                        break;
                    case "F7":
                        f7.setDisable(true);
                        f7.setSelected(false);
                        break;
                    case "F8":
                        f8.setDisable(true);
                        f8.setSelected(false);
                        break;
                    case "F9":
                        f9.setDisable(true);
                        f9.setSelected(false);
                        break;
                    case "F10":
                        f10.setDisable(true);
                        f10.setSelected(false);
                        break;
                    case "F11":
                        f11.setDisable(true);
                        f11.setSelected(false);
                        break;
                    case "F12":
                        f12.setDisable(true);
                        f12.setSelected(false);
                        break;
                    case "F13":
                        f13.setDisable(true);
                        f13.setSelected(false);
                        break;
                    case "F14":
                        f14.setDisable(true);
                        f14.setSelected(false);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}