package com.example.conductor;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocketThread extends Thread {

    private static Socket socket = null;

    private Activity activity;
    private String ip;
    private int port;
    private String type;
    private File file;
    private Class<? extends Activity> resultActivity;

    public ClientSocketThread(Activity activity, String ip, int port, String type, Class<? extends Activity> resultActivity) {
        this.activity = activity;
        this.ip = ip;
        this.port = port;
        this.type = type.toLowerCase().trim();
        this.resultActivity = resultActivity;
    }

//    public ClientSocketThread(Activity activity, String ip, int port, String type, File file) {
//        this.activity = activity;
//        this.ip = ip;
//        this.port = port;
//        this.type = type.toLowerCase().trim();
//        this.file = file;
//    }

    public static synchronized Socket getSocket() {
        return socket;
    }

    public static synchronized void setSocket(Socket socket) {
        ClientSocketThread.socket = socket;
    }

    @Override
    public void run() {

        try {
            socket = new Socket(ip, port);
            OutputStream out = socket.getOutputStream();
            DataOutputStream outputStream = new DataOutputStream(out);
            outputStream.writeUTF("Hello from " + socket.getLocalSocketAddress());

//            byte[] bytes = new byte[1024];
//            InputStream is = socket.getInputStream();
//            FileOutputStream fos = new FileOutputStream(file);
//            BufferedOutputStream bos = new BufferedOutputStream(fos);
//            int bytesRead = is.read(bytes, 0, bytes.length);
//            bos.write(bytes, 0, bytesRead);
//            bos.close();
//            socket.close();

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getBaseContext(), "Finished", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity.getBaseContext(), resultActivity);
                    activity.startActivity(intent);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();

            final String msg = "Something went wrong : " + e.getMessage();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

