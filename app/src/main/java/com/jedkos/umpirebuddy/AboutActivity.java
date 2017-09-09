package com.jedkos.umpirebuddy;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resources = getResources();
        setContentView(R.layout.activity_about);
        updateAboutText();
    }

    private void updateAboutText() {
        TextView aboutTextView = (TextView)findViewById(R.id.aboutTextView);
        aboutTextView.setText(resources.getText(R.string.app_name) + " by " +
                              resources.getText(R.string.author));
    }
}
