package com.example.sellit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class About extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout star;
    private LinearLayout Rohit;
    private LinearLayout ayshman;
    private LinearLayout nikhil;
    private LinearLayout akhil;
    private LinearLayout article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        star = findViewById(R.id.Star_on_github);
        Rohit = findViewById(R.id.Rohit_about);
        ayshman = findViewById(R.id.Ayusman_about);
        article = findViewById(R.id.Submit_article);
        nikhil = findViewById(R.id.nikhil);
        akhil = findViewById(R.id.akhil);
        ayshman.setOnClickListener(this);
        nikhil.setOnClickListener(this);
        Rohit.setOnClickListener(this);
        akhil.setOnClickListener(this);
        star.setOnClickListener(this);
        article.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.Star_on_github:
                openweb("https://github.com/cyber-psych0/Sell-It");
                break;
            case R.id.Rohit_about:
                openweb("https://github.com/refactor-droidyy");
                break;
            case R.id.nikhil:
                openweb("hhttps://github.com/Debug-The-Code");
                break;
            case R.id.akhil:
                openweb("https://github.com/D1ABL3");
                break;
            case R.id.Ayusman_about:
                openweb("https://github.com/cyber-psych0/");
            case R.id.Submit_article:
                sendMail();
            default :
                break;
        }

    }
    public void openweb(String url) {

        Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(implicit);
    }
    private void sendMail() {

        String mailto = "mailto:projects.iiitl@gmail.com";

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));
        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {

        }
    }

}
