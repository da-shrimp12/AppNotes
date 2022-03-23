package com.anhld.appnotes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.anhld.appnotes.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class SpeechToTextActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private static final int RECOGNIZER_RESULT = 1;

    ImageView speechButton;
    EditText edtSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        //event for drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navagation_drawer_open, R.string.navagation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.nav_mic).setChecked(true);

        //event for speech
        speechButton = findViewById(R.id.imageView);
        edtSpeech = findViewById(R.id.editText);

        speechButton.setOnClickListener(view -> {
            recordSpeech();
        });

        Toast.makeText(this, "Press icon mic to Speech to Text", Toast.LENGTH_LONG).show();
    }


    private void recordSpeech() {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        try {
            startActivityForResult(speechIntent, RECOGNIZER_RESULT);
        } catch (Exception e) {
            Toast.makeText(this, "Your device don't support Speech recognizer", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECOGNIZER_RESULT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                edtSpeech.setText(text.get(0));
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            redirectActivity(this, MainActivity.class);
        } else if (id == R.id.nav_mic) {
            recreate();
        } else if (id == R.id.nav_pdf) {
            redirectActivity(this, ReadPDFActivity.class);
        } else if (id == R.id.nav_scan) {
            redirectActivity(this, OCRTextActivity.class);
        } else if (id == R.id.about) {
            redirectActivity(this, AboutActivity.class);
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