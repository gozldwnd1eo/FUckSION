package MovieSysClient;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

public class moviedetailController implements Initializable {
    // fx:controller="MovieSysClient.moviedetailController"
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
    private Label openingdat;

    @FXML
    private Label genre;

    @FXML
    private Label duringtime;

    @FXML
    private TextArea summary;

    @FXML
    private Hyperlink tieser;

    @FXML
    private Button before_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_FILM_DETAIL);
        byte[] buf = protocol.getPacket();
        protocol.setFlimID(Userchoice.getFilmID());
        try {
            Myconn.os.write(protocol.getPacket());
            protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_FILM_DETAIL_OK);
            buf = protocol.getPacket();

            String details = "";
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
                details += protocol.getListBody();
                if (last == 1) {
                    stopread = true;
                }
            }
            String[] body = details.split("\\\\");
            moviename.setText(body[0]);
            tieser.setText(body[1]);
            String[] staffs = body[2].split("~");
            directorname.setText(staffs[0]);
            actors.setText(staffs[1] + ", " + staffs[2] + ", " + staffs[3]);
            genre.setText(body[3]);
            openingdat.setText(body[4]);
            summary.setText(body[5]);
            duringtime.setText(staffs[4]);

        } catch (IOException e) {
            e.printStackTrace();
        }
        tieser.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URL(tieser.getText()).toURI());
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        before_btn.setOnAction(event -> {
            Parent parent;
            try {
                parent = FXMLLoader.load(getClass().getResource("customerMain.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = (Stage) before_btn.getScene().getWindow();
                primaryStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}