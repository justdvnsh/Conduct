package com.example.conductor;

import android.app.Activity;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocketThread extends Thread {

    private Activity activity;
    private String ip;
    private int port;
    private String type;
    private File file;

    public ClientSocketThread(Activity activity, String ip, int port, String type) {
        this.activity = activity;
        this.ip = ip;
        this.port = port;
        this.type = type.toLowerCase().trim();
    }

    public ClientSocketThread(Activity activity, String ip, int port, String type, File file) {
        this.activity = activity;
        this.ip = ip;
        this.port = port;
        this.type = type.toLowerCase().trim();
        this.file = file;
    }

    @Override
    public void run() {
        Socket socket = null;

        try {
            socket = new Socket(ip, port);

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

