package MovieSysClient;

import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("root.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Main");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("192.168.0.15", 7127);

        Myconn.setSocket(socket);
        Myconn.setConnect(socket.isConnected());
        Myconn.setOutputStream(socket.getOutputStream());
        Myconn.setInputStream(socket.getInputStream());

        launch(args);
    }
}