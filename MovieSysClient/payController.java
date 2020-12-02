package MovieSysClient;

import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class payController implements Initializable{

    @FXML
    private ImageView poster;
    private Button pay_btn;
    private TextArea paydatafield;
    private TextField customeraccount;
    private TextField amount;
    private CheckBox confirm;
    private Button before_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
            Protocol protocol=new Protocol(Protocol.PT_REQ_LOOKUP,Protocol.CODE_PT_REQ_LOOKUP_ACCOUNT);
            byte[] buf = protocol.getPacket();
            Myconn.os.write(protocol.getPacket());
            
            protocol= new Protocol(Protocol.PT_RES_LOOKUP,Protocol.CODE_PT_RES_LOOKUP_ACCOUNT_OK);
            buf=protocol.getPacket();
            
            Myconn.is.read(buf);

            int packetType=buf[0];
            int packetCode=buf[1];


        }catch (IOException e){
            e.printStackTrace();
        }
    }
}