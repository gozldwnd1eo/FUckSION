import java.net.Socket;

public class Myconn {
    public static boolean socketconnect = false;
    public static Socket socket = null;

    public static void setConnect(boolean state) {
        socketconnect = state;
    }

    public static void setSocket(Socket s) {
        socket = s;
    }
}
