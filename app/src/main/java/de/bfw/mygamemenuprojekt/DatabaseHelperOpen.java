package de.bfw.mygamemenuprojekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class DatabaseHelperOpen extends SQLiteOpenHelper {

    private Context context;
    private SQLiteDatabase database;

    // database
    private static final String DB_NAME = "myDatabase.db";
    private static final int DB_VERSION = 1;
    private static String dbPath = "";

    // tables
    private static final String TABLE_HIGHSCORES = "tbl_highscores";

    // table ITEMS
    private static final String HIGHSCORES_COLUMN_ID = "id";
    private static final String HIGHSCORES_COLUMN_USERNAME = "username";
    private static final String HIGHSCORES_COLUMN_PUNKTE = "punkte";


    public DatabaseHelperOpen(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        dbPath = context.getDatabasePath(DB_NAME).getPath();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        database = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        database = db;
    }

    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }

    // Open the database, so we can query it
    public boolean openDataBase() {
        String mPath = dbPath + DB_NAME;
        database = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return database != null;
    }

    public void createDataBase() {
        //If the database does not exist, copy it from the assets.
        boolean dbExists = checkDataBase();
        if (!dbExists) {
            this.getReadableDatabase();
            copyDataBase();
        }
    }

    // Check that the database exists here: /data/data/your package/databases/DbName -  /data/user/0/your package/databases/
    private boolean checkDataBase() {
        return new File(dbPath).exists();
    }

    // Copy the database from assets
    private void copyDataBase() {

        FileOutputStream output = null;
        try {
            InputStream is = context.getAssets().open("databases/" + DB_NAME);
            output = new FileOutputStream(dbPath);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = is.read(mBuffer)) > 0) {
                output.write(mBuffer, 0, mLength);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * write new Highscore in database
     *
     * @param highscores new Highscores entry
     * @return true|false if saved or not
     */
    public boolean instertHighscore(Highscores highscores) {
        SQLiteDatabase database = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HIGHSCORES_COLUMN_USERNAME, highscores.getUsername());
        values.put(HIGHSCORES_COLUMN_PUNKTE, highscores.getPunkte());

        // Insert the new row and returning the primary key value of the new row
        long newRowId = database.insert(TABLE_HIGHSCORES, null, values);

        return newRowId != -1;
    }

    /**
     * update Highscores in database
     *
     * @param highscores new Highscores entry
     * @return true|false if saved or not
     */
    /*
    public boolean updateHighscores(Highscores highscores) {
        SQLiteDatabase database = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HIGHSCORES_COLUMN_USERNAME, highscores.getUsername());
        values.put(HIGHSCORES_COLUMN_PUNKTE, highscores.getPunkte());

        // Insert the new row and returning the primary key value of the new row
        long newRowId = database.update(TABLE_HIGHSCORES, values, "id=" + highscores.getId(), null);

        return newRowId != -1;
    }
     */

    /**
     * delete highscores in database
     *
     * @param highscores new Highscores entry
     * @return true|false if saved or not
     */
    public boolean deleteHighscores(Highscores highscores) {
        SQLiteDatabase database = getWritableDatabase();

        // Insert the new row and returning the primary key value of the new row
        long newRowId = database.delete(TABLE_HIGHSCORES, "id=?", new String[]{String.valueOf(highscores.getId())});

        return newRowId != -1;
    }

    /**
     * get all table entries from db
     *
     * @return list of all Highscores
     */
    public ArrayList<Highscores> getAllHighscores() {
        this.database = getReadableDatabase();
        ArrayList<Highscores> list = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_HIGHSCORES;
        Cursor cursorItems = database.rawQuery(sql, null);

        if (cursorItems.moveToFirst()) {
            do {
                int id = cursorItems.getInt(0);
                String username = cursorItems.getString(1);
                int punkte = cursorItems.getInt(2);

                // Objekt erstellen und in die Liste packen
                Highscores highscores = new Highscores(username, id, punkte);
                list.add(highscores);

            } while (cursorItems.moveToNext());
        }

        cursorItems.close();
        return list;
    }
}
