package com.example.conductor;

import android.os.Environment;

import java.io.File;
import java.net.Socket;
import java.util.Enumeration;

public class FileThreadServer extends Thread {

    private Socket socket;

    public FileThreadServer(Socket socket) {
        this.socket = socket;
    }

//    @Override
//    public void run() {
//        File file = new File(Environment.getExternalStorageDirectory(), )
//    }
}
