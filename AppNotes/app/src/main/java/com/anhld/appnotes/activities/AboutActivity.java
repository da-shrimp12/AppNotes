package com.anhld.appnotes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.anhld.appnotes.R;
import com.google.android.material.navigation.NavigationView;

public class AboutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private TextView sendMail;
    private String email = "ducanhle0102@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        sendMail = findViewById(R.id.sendMail);

        //event for drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navagation_drawer_open, R.string.navagation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.about).setChecked(true);

        sendMail.setOnClickListener(v -> sendMail());
    }

    private void sendMail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            redirectActivity(this, MainActivity.class);
        } else if (id == R.id.nav_mic) {
            redirectActivity(this, SpeechToTextActivity.class);
        } else if (id == R.id.nav_pdf) {
            redirectActivity(this, ReadPDFActivity.class);
        } else if (id == R.id.nav_scan) {
            redirectActivity(this, OCRTextActivity.class);
        } else if (id == R.id.about) {
            recreate();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void redirectActivity(Activity mActivity, Class mClass) {
        Intent intent = new Intent(mActivity, mClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}