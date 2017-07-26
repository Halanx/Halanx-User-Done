package com.halanx.tript.userapp.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.halanx.tript.userapp.R;

public class ReferEarnActivity extends AppCompatActivity {

    ImageButton ib_frag_share;
    TextView tvShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_earn);

        ib_frag_share = (ImageButton) findViewById(R.id.ib_frag_share);
        tvShare = (TextView) findViewById(R.id.tv_share);
        ib_frag_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareIt();
            }
        });
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareIt();
            }
        });

    }

    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

        /*
        Set a MIME type for the content you're sharing. This will determine which applications
        the chooser list presents to your users. Plain text, HTML, images and videos are among
        the common types to share. The following Java code demonstrates sending plain text.
         */
        sharingIntent.setType("text/plain");

        /*
       You can pass various elements of your sharing content to the send Intent, including subject,
       text / media content, and addresses to copy to in the case of mobile sharing. This Java code
       builds a string variable to hold the body of the text content to share:
   */

        String shareBody = "Get grocery and food delivered from your favorite stores and " +
                "restaurants in as little as an hour. Download app now : " +
                "https://play.google.com/store/apps/details?id=com.halanx.tript.userapp";

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Halanx : Grocery and Food delivery");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

        /*
        You can't share the same content in a text message or tweet that you could send using
        mobile. For this reason it's best to keep your sharing content as general as possible, so
        that the function will be as effective for Twitter and Facebook as it is for Gmail and mobile.
         */


    }
}
