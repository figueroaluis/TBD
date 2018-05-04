package com.figueroaluis.finalproject271;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AudioRecordActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    private final static int REQUEST_CODE = 89;
    private String mFileName;
    private String mFilePath;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private Button startRecording;
    private Button stopRecording;
    private Button startPlayback;
    private Button stopPlayback;
    private Button backButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_record_activity);
        mContext = getApplicationContext();
        mActivity = AudioRecordActivity.this;
        startRecording = findViewById(R.id.audio_record_start_button);
        stopRecording = findViewById(R.id.audio_record_stop_button);
        startPlayback = findViewById(R.id.audio_record_start_playback);
        stopPlayback = findViewById(R.id.audio_record_stop_playback);
        backButton = findViewById(R.id.audio_record_back_button);
        saveButton = findViewById(R.id.audio_record_save_button);
        checkPermission();
        mFilePath = getFilesDir().getAbsolutePath();
        mFileName = this.getIntent().getExtras().getString("TaskTitle") + ".3gp";
        mFilePath = mFilePath + "/" + mFileName;
        startRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(mActivity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(mContext, "Please grant permissions to use this feature", Toast.LENGTH_LONG).show();
                }
                else{
                    startRecording();
                }

            }
        });
        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecording();
            }
        });
        startPlayback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlaying();
            }
        });
        stopPlayback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent goBack = new Intent();
                setResult(RESULT_CANCELED, goBack);

                mContext.deleteFile(mFileName);

                finish();
            }

        });
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                File file = getBaseContext().getFileStreamPath(mFileName);
                if(file.exists()) {
                    Intent save = new Intent();
                    save.putExtra("audioFilePath", mFilePath);
                    setResult(RESULT_OK, save);
                    finish();
                }
                else{
                    Toast.makeText(mContext, "No File Recorded", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }
    @Override
    public void onStop(){
        super.onStop();
        if(mRecorder != null){
            mRecorder.release();
            mRecorder = null;
        }
        if(mPlayer != null){
            mPlayer.release();
            mPlayer = null;
        }
    }


    protected void checkPermission(){

        if(ContextCompat.checkSelfPermission(mActivity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.RECORD_AUDIO)){
                // build alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage("Record Audio permission is needed to use this feature.");
                builder.setTitle("Please grant these permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_CODE);
                            }
                        }
                );

                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();


            } else{
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE);
            }

        }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == REQUEST_CODE){
            // when request is canceled, result array is empty
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(mContext, "Permission granted, press the record button to start", Toast.LENGTH_SHORT).show();
            }
            else{
                // permission not granted
                Toast.makeText(mContext, "Permissions denied, this feature will not function properly", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startRecording(){
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFilePath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try{
            mRecorder.prepare();

        }
        catch(IOException e){
            Toast.makeText(mContext, "IO Exception at recorder prepare()", Toast.LENGTH_LONG).show();
        }
        mRecorder.start();
        Toast.makeText(mContext,"Recording Started", Toast.LENGTH_SHORT).show();
    }
    private void stopRecording(){
        if(mRecorder != null){
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            Toast.makeText(mContext, "Recording Stopped", Toast.LENGTH_SHORT).show();
        }

    }
    private void startPlaying(){
        if(mPlayer == null) {
            mPlayer = new MediaPlayer();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mPlayer.release();
                    mPlayer = null;
                }
            });
            try {
                mPlayer.setDataSource(mFilePath);
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
                Toast.makeText(mContext, "No File to Play", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void stopPlaying(){
        if(mPlayer != null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}