package de.bfw.mygamemenuprojekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HighscoresActivity extends AppCompatActivity implements View.OnClickListener{

    private Button startGameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        startGameBtn = findViewById(R.id.startgame_btn);
        startGameBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == startGameBtn.getId()) {
            Intent i = new Intent(this, GameActivity.class);
            startActivity(i);
        }
    }
}
