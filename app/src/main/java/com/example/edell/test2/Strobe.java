package com.example.edell.test2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Strobe extends MenuCreator implements SeekBar.OnSeekBarChangeListener {

    Button start_strobe;
    String mode = "strobe";
    private Intent intent;
    BluetoothService bluetoothService;
    boolean mBound = false;
    private TextView mTextViewLag, mTextViewLagInterval;
    String lag = "50";
    String interval = "200";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strobe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = new Intent(this, BluetoothService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        start_strobe = (Button) findViewById(R.id.start_strobe);

        start_strobe.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                sendCommand(mode);
            }
        });

        final SeekBar set_lag = (SeekBar) findViewById(R.id.set_lag);
        set_lag.setOnSeekBarChangeListener(this);

        final SeekBar set_interval = (SeekBar) findViewById(R.id.set_interval);
        set_interval.setOnSeekBarChangeListener(this);

        mTextViewLag = (TextView) findViewById(R.id.lag_text_view);
        mTextViewLag.setText("0");
        mTextViewLagInterval = (TextView) findViewById(R.id.interval_text_view);
        mTextViewLagInterval.setText("0");
    }

    @Override
    public void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.set_lag:
                mTextViewLag.setText(String.valueOf(seekBar.getProgress()));
                break;
            case R.id.set_interval:
                mTextViewLagInterval.setText(String.valueOf(seekBar.getProgress()));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch (SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch (SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.set_lag:
                lag = String.valueOf(seekBar.getProgress());
                mTextViewLag.setText(String.valueOf(seekBar.getProgress()));
                break;
            case R.id.set_interval:
                interval = String.valueOf(seekBar.getProgress());
                mTextViewLagInterval.setText(String.valueOf(seekBar.getProgress()));
                break;
        }
        sendCommand(mode + "/" + interval + "/" + lag);
    }

    private void sendCommand (String data) {
        intent.putExtra("command", data + "/" + interval + "/" + lag);
        startService(intent);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected (ComponentName className,
                                        IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BluetoothService.LocalBinder binder = (BluetoothService.LocalBinder) service;
            bluetoothService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected (ComponentName arg0) {
            mBound = false;
        }
    };
}
