package MovieSysClient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class adminMainController implements Initializable {

    @FXML
    private ListView<String> theaterList;

    @FXML
    private ListView<String> audiList;

    @FXML
    private Button addTheater_btn;

    @FXML
    private Button delTheater_btn;

    @FXML
    private Button revTheater_btn;

    @FXML
    private Button addAudi_btn;

    @FXML
    private Button delAudi_btn;

    @FXML
    private Button revAudi_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_THEATER_FOR_ADMIN);
            byte[] buf = protocol.getPacket();
            protocol.setID(Myconn.SessionUserID);

            Myconn.os.write(protocol.getPacket());

            protocol = new Protocol();
            buf = protocol.getPacket();
            Myconn.is.read(buf);

            int ptType = buf[0];
            int ptCode = buf[1];
            int frag = buf[5];
            protocol.setPacket(ptType, ptCode, buf);
            buf = protocol.getPacket();
            String body = "";
            if (frag == 1) {
                ArrayList<String> bodylist = new ArrayList<String>();
                int last = buf[6];
                bodylist.add(protocol.getListBody());
                while (last != 1) {
                    bodylist.add(protocol.getListBody());
                }
                for (int i = 0; i < bodylist.size(); i++) {
                    body += bodylist.get(i);
                }
            } else {
                body += protocol.getListBody();
            }
            String[] bodydiv = body.split("\\|");
            ArrayList<String[]> theaterlist = new ArrayList<String[]>();
            for (int i = 0; i < bodydiv.length; i++) {
                theaterlist.add(bodydiv[i].split("\\\\"));
            }
            ObservableList<String> theaterlistview = FXCollections.observableArrayList();
            for (int i = 0; i < theaterlist.size(); i++) {
                theaterlistview.add(theaterlist.get(i)[1]);
            }
            theaterList.setItems(theaterlistview);

            theaterList.setOnMouseClicked(event -> {
                try {
                    String selectedTheater = theaterList.selectionModelProperty().getValue().getSelectedItem();
                    Protocol protocol2 = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_AUDI);
                    byte[] buf2 = protocol2.getPacket();
                    protocol2.setList(selectedTheater);
                    Myconn.os.write(protocol2.getPacket());

                    protocol2 = new Protocol();
                    buf2 = protocol2.getPacket();
                    Myconn.is.read(buf2);

                    int ptType2 = buf2[0];
                    int ptCode2 = buf2[1];
                    int frag2 = buf2[5];
                    protocol2.setPacket(ptType2, ptCode2, buf2);
                    buf2 = protocol2.getPacket();
                    String body2 = "";
                    if (frag2 == 1) {
                        ArrayList<String> bodylist = new ArrayList<String>();
                        int last = buf2[6];
                        bodylist.add(protocol2.getListBody());
                        while (last != 1) {
                            bodylist.add(protocol2.getListBody());
                        }
                        for (int i = 0; i < bodylist.size(); i++) {
                            body2 += bodylist.get(i);
                        }
                    } else {
                        body2 += protocol2.getListBody();
                    }
                    String[] bodydiv2 = body2.split("\\|");
                    ArrayList<String[]> audilist = new ArrayList<String[]>();
                    for (int i = 0; i < bodydiv2.length; i++) {
                        audilist.add(bodydiv2[i].split("\\\\"));
                    }
                    ObservableList<String> audilistview = FXCollections.observableArrayList();
                    for (int i = 0; i < audilist.size(); i++) {
                        audilistview.add(audilist.get(i)[0]);
                    }
                    audiList.setItems(audilistview);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            addTheater_btn.setOnAction(event -> {

            });

            revTheater_btn.setOnAction(event -> {
                try {
                    Userchoice.theater=theaterList.selectionModelProperty().getValue().getSelectedItem();
                    Parent parent = FXMLLoader.load(getClass().getResource("adminsTheaterUpdate.fxml"));
                    Scene scene = new Scene(parent);
                    Stage primaryStage = (Stage) revTheater_btn.getScene().getWindow();
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