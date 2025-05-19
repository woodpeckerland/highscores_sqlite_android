package de.bfw.mygamemenuprojekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HighscoresActivity extends AppCompatActivity implements View.OnClickListener{

    private Button startGameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        startGameBtn = findViewById(R.id.startgame_btn);
        startGameBtn.setOnClickListener(this);

        // database part
        try (DatabaseHelperOpen databaseHelperOpen = new DatabaseHelperOpen(this)){
            ArrayList<Highscores> highscoresList = databaseHelperOpen.getAllHighscores();
            createHighscoreTable(highscoresList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == startGameBtn.getId()) {
            Intent i = new Intent(this, GameActivity.class);
            startActivity(i);
        }
    }

    private void createHighscoreTable(ArrayList<Highscores> highscoresList) {

        TableLayout tableLayout = findViewById(R.id.tbl_highscores);

        // Tabellenkopf erstellen
        TableRow headerRow = new TableRow(this);
        TextView platzTV = new TextView(this);
        platzTV.setText("Platz");
        headerRow.addView(platzTV);

        TextView usernameTV = new TextView(this);
        platzTV.setText("Spielername");
        headerRow.addView(usernameTV);

        TextView punkteTV = new TextView(this);
        platzTV.setText("Punkte");
        headerRow.addView(punkteTV);

        tableLayout.addView(headerRow);

        // Platzierungen der Tabelle hinzufügen
        for(int i = 0; i < highscoresList.size(); i++) {
            // Row erstellen
            TableRow tblRow = new TableRow(this);
            TextView platzierungTV = new TextView(this);
            platzierungTV.setText(String.valueOf(i+1));
            tblRow.addView(platzierungTV);

            TextView spielernameTV = new TextView(this);
            spielernameTV.setText(highscoresList.get(i).getUsername());
            tblRow.addView(spielernameTV);

            TextView pktTV = new TextView(this);
            pktTV.setText(String.valueOf(highscoresList.get(i).getPunkte()));
            tblRow.addView(pktTV);

            tableLayout.addView(tblRow);
        }
    }
}
