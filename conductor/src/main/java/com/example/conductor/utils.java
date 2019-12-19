package com.example.conductor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class utils {

    public static DataOutputStream sendDataToConnectedDevice(Socket socket){
        DataOutputStream outputStream = null;

        try {
            OutputStream out = socket.getOutputStream();
            outputStream = new DataOutputStream(out);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream;
    }

    public static String receiveDataFromConnectedDevice(Socket socket) {
        String msg = "";

        try {

            DataInputStream in = new DataInputStream(socket.getInputStream());
            msg = in.readUTF();

        }catch (Exception e) {
            e.printStackTrace();
        }

        return msg;
    }

}
