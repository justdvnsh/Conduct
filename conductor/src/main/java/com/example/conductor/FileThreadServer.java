package com.example.conductor;

import java.io.File;
import java.net.Socket;

public class FileThreadServer extends Thread {

    private Socket socket;
    private File file;

    public FileThreadServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
