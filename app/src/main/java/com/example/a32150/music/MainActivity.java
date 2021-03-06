package com.example.a32150.music;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    String[] songname = new String[]{"AAA"};
    String[] songfile = new String[]{"a.mp3"};
    private int cListItem=0;
    private boolean flag=false;
    MediaPlayer mediaPlayer;
    private String songPath = new String("/sdcard/");

    Button btnprev, btnpause, btnstop, btnplay, btnnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnprev = (Button) findViewById(R.id.btn_prev);
        btnpause = (Button) findViewById(R.id.btn_pause);
        btnplay = (Button) findViewById(R.id.btn_play);
        btnstop = (Button) findViewById(R.id.btn_stop);
        btnnext = (Button) findViewById(R.id.btn_next);
        lv = (ListView)findViewById(R.id.lv);
        lv.setOnItemClickListener(lsListener);


        mediaPlayer = new MediaPlayer();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, songname);
        lv.setAdapter(adapter);
    }

    ListView.OnItemClickListener lsListener = new ListView.OnItemClickListener()    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            cListItem=i;
            play(songPath+songfile[cListItem]);
        }
    };


    public void onClick(View v)   {
        switch (v.getId())  {
            case R.id.btn_stop:
                mediaPlayer.release();
                break;
            case R.id.btn_play:
                if(flag){
                    mediaPlayer.start();
                    flag=false;
                }else{
                    play(songPath + songfile[cListItem]);
                }
                break;
            case R.id.btn_prev:
                prev();
                break;
            case R.id.btn_pause:
                mediaPlayer.pause();
                flag=true;
                break;
        }
    }

    public void play(String name)  {
        try{
            mediaPlayer.reset();
			mediaPlayer.setDataSource(name);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    next();
                }
            });


        }   catch(IOException e) {


        }


    }

    public void pause()  {



    }

    public void next()  {
        cListItem++;

        if(cListItem >= lv.getCount())  {
            cListItem=0;
        }
        play(songPath+songfile[cListItem]);
    }

    public void prev()  {
        cListItem--;
        if(cListItem<0) {
            cListItem=lv.getCount()-1;

        }
        play(songPath+songfile[cListItem]);
    }
}
