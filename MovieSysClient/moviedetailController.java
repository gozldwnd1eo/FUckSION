package MovieSysClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class moviedetailController implements Initializable {

    @FXML
    private ImageView poster;

    @FXML
    private Label moviename;

    @FXML
    private Label directorname;

    @FXML
    private Label actors;

    @FXML
    private Label revrate;

    @FXML
    private Label genre;

    @FXML
    private Label duringtime;

    @FXML
    private ListView<String> reviewlist;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_FILM_DETAIL);
        byte[] buf = protocol.getPacket();
        protocol.setFlimID(Userchoice.getFilmID());
        try {
            Myconn.os.write(protocol.getPacket());

            protocol=new Protocol(Protocol.PT_RES_LOOKUP,Protocol.CODE_PT_RES_LOOKUP_FILM_DETAIL_OK);
            buf=protocol.getPacket();

            Myconn.is.read(buf);

            int packetType=buf[0];
            int packetCode=buf[1];

            // 여기서 프로토콜에 받은 정보 꺼내서 레이블들 이름 다 변경해줌
            String 
            protocol.

            String filmname;
            moviename.setText(filmname);
            String dirname;
            directorname.setText(dirname);
            String actor;
            actors.setText(actor);
            String rvrate;
            revrate.setText(rvrate);
            String gnr;
            genre.setText(gnr);
            String durtime;
            duringtime.setText(durtime);
            

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}