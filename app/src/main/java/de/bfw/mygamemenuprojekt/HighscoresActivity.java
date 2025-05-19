package de.bfw.mygamemenuprojekt;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HighscoresActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startGameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        startGameBtn = findViewById(R.id.startgame_btn);
        startGameBtn.setOnClickListener(this);

        // database part
        try (DatabaseHelperOpen databaseHelperOpen = new DatabaseHelperOpen(this)) {

            // create dummydata
            // createDummydata();

            ArrayList<Highscores> highscoresList = databaseHelperOpen.getAllHighscores();
            createHighscoreTable(highscoresList);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDummydata() {

        // create dummydata
        for(int i = 0; i < 5; i++) {

            try (DatabaseHelperOpen databaseHelperOpen = new DatabaseHelperOpen(this)) {
                Highscores h = new Highscores("username" + i, 12 * i + 4);
                databaseHelperOpen.instertHighscore(h);
            }

            catch (Exception e) {
                e.printStackTrace();
            }
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
        headerRow.setLayoutParams(new TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT));

        TextView platzTV = new TextView(this);
        platzTV.setText("Platz");
        platzTV.setTypeface(null, Typeface.BOLD);
        platzTV.setTextColor(getResources().getColor(R.color.white, null));
        platzTV.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1)); // 1/3 Gewichtung
        headerRow.addView(platzTV);

        TextView usernameTV = new TextView(this);
        usernameTV.setText("Spielername");
        usernameTV.setTypeface(null, Typeface.BOLD);
        usernameTV.setTextColor(getResources().getColor(R.color.white, null));
        usernameTV.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2)); // 1/3 Gewichtung
        headerRow.addView(usernameTV);

        TextView punkteTV = new TextView(this);
        punkteTV.setText("Punkte");
        punkteTV.setTypeface(null, Typeface.BOLD);
        punkteTV.setTextColor(getResources().getColor(R.color.white, null));
        punkteTV.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2)); // 1/3 Gewichtung
        headerRow.addView(punkteTV);

        tableLayout.addView(headerRow);

        // Leere Zeile erstellen
        TableRow emptyRow = new TableRow(this);
        emptyRow.setLayoutParams(new TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT));

        // Platzhalter-TextView hinzufügen (optional, falls nötig für Layout-Konsistenz)
        TextView emptyTextView = new TextView(this);
        emptyTextView.setText(""); // Kein Text für die Leerzeile
        emptyTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        emptyRow.addView(emptyTextView);

        // Leere Zeile zur Tabelle hinzufügen
        tableLayout.addView(emptyRow);

        // Platzierungen der Tabelle hinzufügen
        for(int i = 0; i < highscoresList.size(); i++) {

            // Row erstellen
            TableRow tblRow = new TableRow(this);
            tblRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

            TextView platzierungTV = new TextView(this);
            platzierungTV.setTextColor(getResources().getColor(R.color.white, null));
            platzierungTV.setText(String.valueOf(i+1));
            platzierungTV.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            tblRow.addView(platzierungTV);

            TextView spielernameTV = new TextView(this);
            spielernameTV.setTextColor(getResources().getColor(R.color.white, null));
            spielernameTV.setText(highscoresList.get(i).getUsername());
            spielernameTV.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2));
            tblRow.addView(spielernameTV);

            TextView pktTV = new TextView(this);
            pktTV.setTextColor(getResources().getColor(R.color.white, null));
            pktTV.setText(String.valueOf(highscoresList.get(i).getPunkte()));
            pktTV.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2));
            tblRow.addView(pktTV);

            tableLayout.addView(tblRow);
        }
    }
}
