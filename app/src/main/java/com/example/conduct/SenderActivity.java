package com.example.conduct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.conductor.ClientSocketThread;

public class SenderActivity extends BaseActivity {

    TextView port;
    EditText ipAddress;
    Button connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        port = (TextView) findViewById(R.id.server_port);
        ipAddress = (EditText) findViewById(R.id.ip_address);
        connect = (Button) findViewById(R.id.connect);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = ipAddress.getText().toString();
                ClientSocketThread clientSocketThread = new ClientSocketThread(SenderActivity.this, ip, 8080, "file", SenderDetail.class);
                clientSocketThread.start();
            }
        });
    }
}
