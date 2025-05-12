package de.bfw.mygamemenuprojekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("de.bfw.mygamemenuprojekt", Context.MODE_PRIVATE);

        boolean isBackgroundSoundOn = sharedPreferences.getBoolean("backgroundSoundOn", true);

        if(isBackgroundSoundOn) {
            startService(new Intent(MainActivity.this, ServiceSound.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startGameBtn = findViewById(R.id.startgame_btn);
        Button highscoresBtn = findViewById(R.id.highscores_btn);
        Button settingsBtn = findViewById(R.id.settings_btn);
        Button aboutBtn = findViewById(R.id.about_btn);

        startGameBtn.setOnClickListener(this);
        highscoresBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.startgame_btn) {
            Intent i = new Intent(this, GameActivity.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.highscores_btn) {
            Intent i = new Intent(this, HighscoresActivity.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.settings_btn) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.about_btn) {
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        }
    }
}
