package MovieSysClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;

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
    public void initialize(URL location, ResourceBundle resources){
        // 최초 로그인시 영화탭의 기본 정보 출력
        // 영화탭의 영화 리스트=담당자가 상영스케줄을 등록한 영화들만 출력됨(예약해야 하기 때문)
        // select * from film where film_id=(select distinct film_id from screen)
        Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_ALL_SCREEN);
        
    }

}