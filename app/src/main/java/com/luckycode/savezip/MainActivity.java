package com.luckycode.savezip;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private DownloadDao download;
    private TTContent content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        download= new DownloadDao(this);
        content= new TTContent();
        content.setContentURL("/unileverData.zip");

    }

    @Override
    public void onClick(View v) {
        download.downloadContent(content);
        Log.e("SUPER PATH",content.getPath().getLocalPath());
        Log.e("SUPER ZIP PATH",content.getPath().getZipPath());
        //download.downloadContent("/unileverData.zip");
    }
}
