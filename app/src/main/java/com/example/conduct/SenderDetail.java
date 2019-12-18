package com.example.conduct;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import static com.example.conductor.ClientSocketThread.getSocket;

public class SenderDetail extends BaseActivity {

    private Socket socket;
    private static final int FILE_CODE= 0;
    File file;
    TextView senderPortDetail;
    Button choose;

    public void chooseFiles(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case FILE_CODE:
                if (resultCode == RESULT_OK) {

                    // get the uri of the file
                    Uri uri = data.getData();
                    String path = getPath(getBaseContext(), uri);
//                    file = new File(path);
                    Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private static String getPath(Context context, Uri uri) {
        String result = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data", MediaStore.Images.Media.DATA};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int col = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    result =  cursor.getString(col);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
                cursor.close();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            result = uri.getPath();
        }

        return result;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_detail);
        senderPortDetail = findViewById(R.id.sender_port_detail);
        choose = findViewById(R.id.choose_files);

        // client socket.
        socket = getSocket();
        senderPortDetail.setText("Connected to " + socket.getRemoteSocketAddress().toString());
    }
}
