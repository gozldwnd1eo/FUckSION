package MovieSysClient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class adminsTheaterUpdateController implements Initializable {

    @FXML
    private TextField theaterName;

    @FXML
    private PasswordField theaterArea;

    @FXML
    private PasswordField theaterAddress;

    @FXML
    private Button before_btn;

    @FXML
    private Button change_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String selected = Userchoice.theater;
            Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_SCREEN_TIME_AT_TAB);
            byte[] buf = protocol.getPacket();
            protocol.setList(selected);
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
            ArrayList<String[]> screenlist = new ArrayList<String[]>();
            for (int i = 0; i < bodydiv.length; i++) {
                screenlist.add(bodydiv[i].split("\\\\"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}