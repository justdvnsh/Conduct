package com.example.conduct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.conductor.ClientSocketThread;
import com.example.conductor.ServerSocketThread;

public class ReceiverActivity extends BaseActivity {

    TextView serverIp;
    TextView serverPort;
    TextView connectionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        serverIp = (TextView) findViewById(R.id.server_ip);
        serverPort = (TextView) findViewById(R.id.server_port_receiver);
        connectionStatus = (TextView) findViewById(R.id.server_connection_status);

        serverIp.setText(ServerSocketThread.getIpAddress());
        serverPort.setText("8080");

        ServerSocketThread serverSocketThread = new ServerSocketThread(this, 8080, "file", serverPort, ReceiverDetail.class);
        serverSocketThread.start();
    }
}
