package de.bfw.mygamemenuprojekt;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class ServiceSound extends Service {
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * create the player with sound file
     */
    public void onCreate() {
        player = MediaPlayer.create(this, R.raw.background_sound); //select music file
        player.setLooping(true);
    }

    /**
     *  starting playing sound file
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return Service.START_NOT_STICKY;
    }

    /**
     * stopping playing sound file
     */
    public void onDestroy() {
        player.stop();
        player.release();
        stopSelf();
        super.onDestroy();
    }
}
