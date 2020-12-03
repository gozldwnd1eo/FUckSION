package MovieSysClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class signupController implements Initializable {

    @FXML
    private TextField idfield;

    @FXML
    private PasswordField pwfield;

    @FXML
    private PasswordField pwconfirm;

    @FXML
    private TextField namefield;

    @FXML
    private TextField emailfield;

    @FXML
    private TextField birthfield;

    @FXML
    private TextField phonefield;

    @FXML
    private RadioButton radio_man;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton radio_woman;

    @FXML
    private Button before_btn;

    @FXML
    private Button next_btn;

    @FXML
    private TextField accountfield;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        before_btn.setOnAction(event -> {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("root.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = (Stage) before_btn.getScene().getWindow();
                primaryStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        next_btn.setOnAction(event -> {
            try {
                Stage primaryStage = (Stage) next_btn.getScene().getWindow();
                boolean is_proh_char = false;
                boolean is_not_full = false;
                boolean is_not_match_pw = false;

                RadioButton selected = (RadioButton) gender.getSelectedToggle();
                if (!(idfield.getText().trim().isEmpty() | pwfield.getText().trim().isEmpty()
                        | pwconfirm.getText().trim().isEmpty() | namefield.getText().trim().isEmpty()
                        | emailfield.getText().trim().isEmpty() | birthfield.getText().trim().isEmpty()
                        | phonefield.getText().trim().isEmpty() | selected == null
                        | accountfield.getText().trim().isEmpty())) {
                    String[] data = { idfield.getText().trim(), pwfield.getText().trim(), namefield.getText().trim(),
                            phonefield.getText().trim(), accountfield.getText().trim(), selected.getText().trim(),
                            emailfield.getText().trim(), birthfield.getText().trim() };// 아이디 비번 이름 폰번호 계좌번호 성별 이메일 생년월일
                    if (data[5].equals("남")) {
                        data[5] = "M";
                    } else {
                        data[5] = "F";
                    }
                    for (int i = 0; i < data.length; i++) {
                        if (data[i].matches("\\\\\\|")) {
                            is_proh_char = true;
                            break;
                        } else {
                            if (!pwfield.getText().trim().equals(pwconfirm.getText().trim())) {
                                is_not_match_pw = true;
                            }
                        }
                    }
                    Protocol protocol = new Protocol(Protocol.PT_REQ_UPDATE, Protocol.CODE_PT_REQ_UPDATE_ADD_MEM);
                    byte[] buf = protocol.getPacket();
                    protocol.setMemberJoin(data);
                    Myconn.os.write(protocol.getPacket());

                    protocol = new Protocol();
                    buf = protocol.getPacket();
                    Myconn.is.read(buf);

                    protocol.setPacket(buf[0], buf[1], buf);
                    if (protocol.getProtocolType() == Protocol.PT_RES_UPDATE
                            & protocol.getProtocolCode() == Protocol.CODE_PT_RES_UPDATE_ADD_MEM_OK) {
                        Stage dialog = new Stage(StageStyle.UTILITY);
                        dialog.initModality(Modality.WINDOW_MODAL);
                        dialog.initOwner(primaryStage);
                        dialog.setTitle("Success");
                        Parent parent = FXMLLoader.load(getClass().getResource("errordialog.fxml"));
                        Label dialogtext = (Label) parent.lookup("#dialogtext");
                        dialogtext.setText("성공");
                        Button ok_btn = (Button) parent.lookup("#ok_btn");
                        ok_btn.setOnAction(e -> {
                            try {
                                dialog.close();
                                Parent parent2 = FXMLLoader.load(getClass().getResource("root.fxml"));
                                Scene scene = new Scene(parent2);
                                primaryStage.setScene(scene);
                            } catch (IOException excep) {
                                excep.printStackTrace();
                            }
                        });
                        Scene scene = new Scene(parent);

                        dialog.setScene(scene);
                        dialog.setResizable(false);
                        dialog.show();
                    } else {
                        Stage dialog = new Stage(StageStyle.UTILITY);
                        dialog.initModality(Modality.WINDOW_MODAL);
                        dialog.initOwner(primaryStage);
                        dialog.setTitle("error");
                        Parent parent = FXMLLoader.load(getClass().getResource("errordialog.fxml"));
                        Label dialogtext = (Label) parent.lookup("#dialogtext");
                        dialogtext.setText("중복된 아이디가 서버에 있습니다.");
                        Button ok_btn = (Button) parent.lookup("#ok_btn");
                        ok_btn.setOnAction(e -> {
                            dialog.close();
                            // Parent parent2 =
                            // FXMLLoader.load(getClass().getResource("root.fxml"));
                            // Scene scene = new Scene(parent2);
                            // primaryStage.setScene(scene);

                        });
                        Scene scene = new Scene(parent);

                        dialog.setScene(scene);
                        dialog.setResizable(false);
                        dialog.show();
                    }
                } else {
                    is_not_full = true;
                }
                if (is_not_full | is_proh_char | is_not_match_pw) {
                    String errormsg = "";
                    if (is_not_full) {
                        errormsg = "모든 필드를 입력하고 진행해 주세요.";
                    } else if (is_proh_char) {
                        errormsg = "포함될 수 없는 문자가 포함되었습니다.";
                    } else if (is_not_match_pw) {
                        errormsg = "비밀번호와 비밀번호 확인이 일치하지 않습니다";
                    }
                    Stage dialog = new Stage(StageStyle.UTILITY);
                    dialog.initModality(Modality.WINDOW_MODAL);
                    dialog.initOwner(primaryStage);
                    dialog.setTitle("Error");
                    Parent parent = FXMLLoader.load(getClass().getResource("errordialog.fxml"));
                    Label dialogtext = (Label) parent.lookup("#dialogtext");
                    dialogtext.setText(errormsg);
                    Button ok_btn = (Button) parent.lookup("#ok_btn");
                    ok_btn.setOnAction(e -> {
                        dialog.close();
                        // Parent parent2 =
                        // FXMLLoader.load(getClass().getResource("reservationController.fxml"));
                        // Scene scene = new Scene(parent2);
                        // primaryStage.setScene(scene);
                    });
                    Scene scene = new Scene(parent);

                    dialog.setScene(scene);
                    dialog.setResizable(false);
                    dialog.show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}