package MovieSysClient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import MovieSysServer.LoginProtocol.Protocol;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.*;

public class myhistoryController implements Initializable {

    @FXML
    private TableView<myhistoryTableRowModel> history;

    @FXML
    private TableColumn<myhistoryTableRowModel, String> filmnameCol;

    @FXML
    private TableColumn<myhistoryTableRowModel, String> theaternameCol;

    @FXML
    private TableColumn<myhistoryTableRowModel, String> audi_timeCol;

    @FXML
    private TableColumn<myhistoryTableRowModel, String> seatnumCol;

    @FXML
    private TableColumn<myhistoryTableRowModel, String> priceCol;

    @FXML
    private TableColumn<myhistoryTableRowModel, String> paydateCol;

    @FXML
    private TableColumn<myhistoryTableRowModel, String> refundCol;

    @FXML
    private Button cancel_btn;

    @FXML
    private Button before_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            filmnameCol.setCellValueFactory(cellData -> cellData.getValue().filmnameProperty());
            theaternameCol.setCellValueFactory(cellData -> cellData.getValue().theaternameProperty());
            audi_timeCol.setCellValueFactory(cellData -> cellData.getValue().audi_timeProperty());
            seatnumCol.setCellValueFactory(cellData -> cellData.getValue().seatnumProperty());
            priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
            paydateCol.setCellValueFactory(cellData -> cellData.getValue().paydateProperty());
            refundCol.setCellValueFactory(cellData -> cellData.getValue().refundProperty());

            Protocol protocol = new Protocol(Protocol.PT_REQ_LOOKUP, Protocol.CODE_PT_REQ_LOOKUP_RESV_LIST);
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
            ArrayList<String[]> historylist = new ArrayList<String[]>();
            for (int i = 0; i < bodydiv.length; i++) {
                historylist.add(bodydiv[i].split("\\\\"));
            }
            ObservableList<myhistoryTableRowModel> tablelist = FXCollections.observableArrayList();
            for (int i = 0; i < historylist.size(); i++) {
                String[] temp = historylist.get(i);
                tablelist.add(
                        new myhistoryTableRowModel(new SimpleStringProperty(temp[0]), new SimpleStringProperty(temp[1]),
                                new SimpleStringProperty(temp[2] + "관 / " + temp[3] + "~" + temp[4]),
                                new SimpleStringProperty(temp[5]), new SimpleStringProperty(temp[6]),
                                new SimpleStringProperty(temp[7]), new SimpleStringProperty(temp[8]),
                                new SimpleStringProperty(temp[9])));
            }
            history.setItems(tablelist);

            before_btn.setOnAction(event->{
                try{
                Parent parent = FXMLLoader.load(getClass().getResource("customerMain.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = (Stage) before_btn.getScene().getWindow();
                primaryStage.setScene(scene);
                }catch (IOException e){
                    e.printStackTrace();
                }
            });

            cancel_btn.setOnAction(event -> {
                try {
                    myhistoryTableRowModel selected = history.getSelectionModel().getSelectedItem();
                    Protocol protocol2 = new Protocol(Protocol.PT_REQ_UPDATE,
                            Protocol.CODE_PT_REQ_UPDATE_DELETE_PAY_RESV);
                    byte[] buf2 = protocol2.getPacket();
                    String[] temp = new String[2];
                    temp[0] = Myconn.SessionUserID;
                    temp[1] = selected.resv_numProperty().getValue();
                    protocol2.setDel_Pay_Resv(temp);
                    Myconn.os.write(protocol2.getPacket());

                    protocol2 = new Protocol();
                    buf2 = protocol2.getPacket();

                    Myconn.is.read(buf2);

                    int ptType2 = buf2[0];
                    int ptCode2 = buf2[1];
                    protocol2.setPacket(ptType, ptCode, buf2);
                    buf2 = protocol2.getPacket();
                    if (ptCode2 == Protocol.CODE_PT_RES_UPDATE_DELETE_PAY_RESV_OK) {
                        Stage primaryStage = (Stage) cancel_btn.getScene().getWindow();
                        Stage dialog = new Stage(StageStyle.UTILITY);
                        dialog.initModality(Modality.WINDOW_MODAL);
                        dialog.initOwner(primaryStage);
                        dialog.setTitle("Success");
                        Parent parent = FXMLLoader.load(getClass().getResource("errordialog.fxml"));
                        Label dialogtext = (Label) parent.lookup("#dialogtext");
                        dialogtext.setText("성공");
                        Button ok_btn = (Button) parent.lookup("#ok_btn");
                        ok_btn.setOnAction(e -> {
                            dialog.close();
                        });
                        Scene scene = new Scene(parent);

                        dialog.setScene(scene);
                        dialog.setResizable(false);
                        dialog.show();
                        Parent parent2 = FXMLLoader.load(getClass().getResource("myhistory.fxml"));
                        Scene scene2 = new Scene(parent2);
                        primaryStage.setScene(scene2);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
