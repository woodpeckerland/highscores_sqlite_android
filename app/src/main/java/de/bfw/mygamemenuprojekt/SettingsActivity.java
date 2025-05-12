package de.bfw.mygamemenuprojekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences.Editor editor;

    private EditText usernameET;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = getSharedPreferences("de.bfw.mygamemenuprojekt", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean isBackgroundSoundOn = sharedPreferences.getBoolean("backgroundSoundOn", true);

        SwitchMaterial backgroundSoundSwitch = findViewById(R.id.switchSound);
        backgroundSoundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("backgroundSoundOn", true);
                    startBackgroundSound();
                } else {
                    stopBackgroundSound();
                    editor.putBoolean("backgroundSoundOn", false);
                }

                editor.apply();
            }
        });

        backgroundSoundSwitch.setChecked(isBackgroundSoundOn);

        usernameET = findViewById(R.id.usernameET);

        username = sharedPreferences.getString("username", "");

        if (!username.isEmpty()) {
            usernameET.setText(username);
        }

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(!usernameET.getText().toString().equals(username)) {
            editor.putString("username", usernameET.getText().toString());
            editor.apply();
        }
    }

    /**
     * started background sound
     */
    protected void startBackgroundSound() {
        startService(new Intent(SettingsActivity.this, ServiceSound.class));
    }

    /**
     * stopping background sound
     */
    protected void stopBackgroundSound() {
        stopService(new Intent(SettingsActivity.this, ServiceSound.class));
    }
}
