package MovieSysClient;

import java.io.*;
import java.net.Socket;

public class Myconn {
    public static boolean socketconnect = false;
    public static Socket socket = null;
    public static OutputStream os = null;
    public static InputStream is = null;
    public static String SessionUserID = null;
    public static String SessionUserPW = null;

    public static void setConnect(boolean state) {
        socketconnect = state;
    }

    public static void setSocket(Socket s) {
        socket = s;
    }

    public static void setOutputStream(OutputStream o) {
        os = o;
    }

    public static void setInputStream(InputStream i) {
        is = i;
    }

    public static void setSessUserID(String i) {
        SessionUserID = i;
    }

    public static void setSessUserPW(String p) {
        SessionUserPW = p;
    }
}
