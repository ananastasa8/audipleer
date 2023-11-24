package com.example.audioplayer;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;import android.media.AudioManager;
import android.media.MediaPlayer;import android.os.Bundle;
import android.view.View;import android.widget.AdapterView;
import android.widget.ArrayAdapter;import android.widget.Button;
import android.widget.ListView;import android.widget.SeekBar;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    MediaPlayer mPlayer;
    Button playButton, pauseButton, stopButton;    SeekBar volumeControl;
    AudioManager audioManager;
    ListView songList;
    private String[] musicTitles = { // Названия музыкальных треков
            "music1","music2", "music3", "music4",  "music5"    };

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {            @Override
        public void onCompletion(MediaPlayer mp) {                stopPlay();
        }        });
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);        stopButton = findViewById(R.id.stopButton);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);        int curValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeControl = findViewById(R.id.volumeControl);
        volumeControl.setMax(maxVolume);        volumeControl.setProgress(curValue);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {            @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
        }
            @Override            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }        });
        songList = findViewById(R.id.songList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, musicTitles);        songList.setAdapter(adapter);
        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playSong(position);            }
        });
        pauseButton.setEnabled(false);        stopButton.setEnabled(false);
    }
    private void playSong(int songIndex) {        int resID = getResources().getIdentifier(musicTitles[songIndex], "raw", getPackageName());
        if (mPlayer.isPlaying()) {            stopPlay();
        }        mPlayer = MediaPlayer.create(this, resID);
        mPlayer.start();        playButton.setEnabled(false);
        pauseButton.setEnabled(true);        stopButton.setEnabled(true);
    }
    private void stopPlay(){        mPlayer.stop();
        pauseButton.setEnabled(false);        stopButton.setEnabled(false);
        try {            mPlayer.prepare();
            mPlayer.seekTo(0);            playButton.setEnabled(true);
        }        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();        }
    }
    public void play(View view){
        mPlayer.start();
        playButton.setEnabled(false);        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);    }
    public void pause(View view){
        mPlayer.pause();
        playButton.setEnabled(true);        pauseButton.setEnabled(false);
        stopButton.setEnabled(true);    }
    public void stop(View view){        stopPlay();
    }@Override    public void onDestroy() {
        super.onDestroy();        if (mPlayer.isPlaying()) {
            stopPlay();        }
    }}