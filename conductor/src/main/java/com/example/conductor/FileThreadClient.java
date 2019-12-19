package com.example.conductor;

import android.app.Activity;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Enumeration;

public class FileThreadClient extends Thread {

    private Socket socket;
    private File file;
    private File fileToBeSaved;
    private Activity activity;

    public FileThreadClient(Socket socket, File file, Activity activity) {
        this.socket = socket;
        this.file = file;
        this.activity = activity;
    }

    @Override
    public void run() {
        fileToBeSaved = new File(Environment.getExternalStorageDirectory(), file.getName());

        byte[] bytes = new byte[(int) fileToBeSaved.length()];
        BufferedInputStream bis;

        try {
            bis = new BufferedInputStream(new FileInputStream(fileToBeSaved));
            bis.read(bytes, 0, bytes.length);
            OutputStream os = socket.getOutputStream();
            os.write(bytes, 0, bytes.length);
            os.flush();
            socket.close();

            final String sentMsg = "file sent to: " + socket.getInetAddress();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getBaseContext(), sentMsg, Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
