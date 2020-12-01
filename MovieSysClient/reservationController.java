package MovieSysClient;

import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;

import java.io.IOException;
import java.net.URL;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;

public class reservationController implements Initializable{

    @FXML
    private ImageView poster;

    @FXML
    private ComboBox<String> localcombox;

    @FXML
    private ComboBox<String> theatercombox;

    @FXML
    private ComboBox<String> schedcombox;

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

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
        Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP,Protocol.CODE_PT_REQ_LOOKUP_AREA);
        byte[] buf=protocol.getPacket();
        protocol.setFlimID(Userchoice.getFilmID());
        Myconn.os.write(protocol.getPacket());

        protocol=new Protocol(Protocol.PT_RES_LOOKUP,Protocol.CODE_PT_RES_LOOKUP_AREA_OK);
        buf=protocol.getPacket();

        Myconn.is.read(buf);

        // 프로토콜에 온 지역들을 스트링배열로 빼옴
        protocol.

        String[] areas;
        localcombox.setItems(FXCollections.observableArrayList(areas)); // 되나?

        }catch (IOException e){
            e.printStackTrace();
        }



    }
    
    @FXML
    void selectarea(ActionEvent event) {

    }

    @FXML
    void selectschedule(ActionEvent event) {

    }

    @FXML
    void selecttheater(ActionEvent event) {

    }

}
