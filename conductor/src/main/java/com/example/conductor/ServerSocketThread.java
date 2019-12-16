package com.example.conductor;

import android.app.Activity;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class ServerSocketThread extends Thread {

    public static ServerSocket serverSocket;
    private Activity activity;
    private int port;
    private String type;
    private TextView infoPort;

    public ServerSocketThread(Activity activity, int port, String type, TextView infoPort) {
        this.activity = activity;
        this.port = port;
        this.type = type.toLowerCase().trim();
        this.infoPort = infoPort;
    }

    public static String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }

    @Override
    public void run() {
        Socket socket = null;


        try {
            serverSocket = new ServerSocket(port);

            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    infoPort.setText("Ip address of the server is " + serverSocket.getLocalPort());
                }
            });

            while (true) {

                socket = serverSocket.accept();

//                if (type == "files") {
//                    FileThreadServer fileThread = new FileThreadServer(socket);
//                    fileThread.start();
//                }
//                else if (type == "chat") {
//                    ChatThread chatThread = new ChatThread(socket);
//                    chatThread.start();
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {

                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
