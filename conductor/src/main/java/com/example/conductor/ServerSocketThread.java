package com.example.conductor;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class ServerSocketThread extends Thread {

    private static Socket socket = null;

    public static ServerSocket serverSocket;
    private Activity activity;
    private int port;
    private String type;
    private TextView infoPort;
    private Class<? extends Activity> resultActivity;

    public ServerSocketThread(Activity activity, int port, String type, TextView infoPort, Class<? extends Activity> resultActivity) {
        this.activity = activity;
        this.port = port;
        this.type = type.toLowerCase().trim();
        this.infoPort = infoPort;
        this.resultActivity = resultActivity;
    }

    public static synchronized Socket getSocket(){
        return socket;
    }

    public static synchronized void setSocket(Socket socket){
        ServerSocketThread.socket = socket;
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

        try {
            serverSocket = new ServerSocket(port);

            while (true) {

                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        infoPort.setText("Ip address of the server is " + serverSocket.getLocalPort());
                    }
                });

                socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                final String msg = in.readUTF();

//                if (type == "files") {
//                    FileThreadServer fileThread = new FileThreadServer(socket);
//                    fileThread.start();
//                }
//                else if (type == "chat") {
//                    ChatThread chatThread = new ChatThread(socket);
//                    chatThread.start();
//                }
                final Socket finalSocket = socket;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getBaseContext(), "Connection Accepted" + finalSocket.getRemoteSocketAddress(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(activity.getBaseContext(), msg, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(activity.getBaseContext(), resultActivity.getClass());
                        activity.startActivity(intent);
                    }
                });
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
