package de.bfw.mygamemenuprojekt;

public class Highscores {

    final private String username;
    final private int id, punkte;

    public Highscores(String username, int id, int punkte) {
        this.username = username;
        this.id = id;
        this.punkte = punkte;
    }

    public Highscores(String username, int punkte) {
        this.username = username;
        this.punkte = punkte;
        this.id = -1;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public int getPunkte() {
        return punkte;
    }
}
