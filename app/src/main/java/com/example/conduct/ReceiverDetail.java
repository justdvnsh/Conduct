package com.example.conduct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.net.Socket;

import static com.example.conductor.ServerSocketThread.getSocket;

public class ReceiverDetail extends BaseActivity {

    private Socket socket;

    TextView receiverPortDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_detail);
        receiverPortDetail = findViewById(R.id.receiver_port_detail);

        // server socket.
        socket = getSocket();
        receiverPortDetail.setText("Connected to " + socket.getRemoteSocketAddress().toString());

    }
}
