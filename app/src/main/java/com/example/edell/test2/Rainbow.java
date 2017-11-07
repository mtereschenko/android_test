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

public class Rainbow extends MenuCreator  implements SeekBar.OnSeekBarChangeListener {

    Button start_rainbow;
    String mode = "rainbow";
    private Intent intent;
    BluetoothService bluetoothService;
    boolean mBound = false;
    private TextView mTextView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rainbow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = new Intent(this, BluetoothService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        start_rainbow = (Button) findViewById(R.id.start_rainbow);

        start_rainbow.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                sendCommand(mode);
            }
        });

        final SeekBar set_lag = (SeekBar)findViewById(R.id.set_lag);
        set_lag.setOnSeekBarChangeListener(this);

        mTextView = (TextView)findViewById(R.id.lag_text_view);
        mTextView.setText("0");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mTextView.setText(String.valueOf(seekBar.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        String lag = String.valueOf(seekBar.getProgress());
        mTextView.setText(lag);
        Log.d("set_lag", mode+"/"+lag);
        sendCommand(mode+"/"+lag);
    }

    private void sendCommand (String data) {
        intent.putExtra("command", data);
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
