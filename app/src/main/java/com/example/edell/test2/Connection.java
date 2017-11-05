package com.example.edell.test2;

import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.widget.LinearLayout.LayoutParams;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Connection extends MenuCreator {

    //    private TextView out;
    private LinearLayout deviceList;
    private BluetoothAdapter adapter;

    private static final String TAG = "bluetooth";

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;

    BluetoothService bluetoothService;
    boolean mBound = false;
    private Intent intent;
    ServiceConnection sConn;

    final String LOG_TAG = "myLogs";

    protected OutputStream outStream;
    protected static String addressToConnect = null;  //Вместо “00:00” Нужно нудет ввести MAC нашего bluetooth
    protected static UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    // SPP UUID сервиса


    // MAC-адрес Bluetooth модуля
    @Override
    protected void onStart () {
        super.onStart();
        // Bind to LocalService
        intent = new Intent(this, BluetoothService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        MY_UUID = UUID.fromString(MY_UUID.toString());

        btAdapter = BluetoothAdapter.getDefaultAdapter();

        intent = new Intent(this, BluetoothService.class);

        sConn = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "MainActivity onServiceConnected");
//                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "MainActivity onServiceDisconnected");
//                bound = false;
            }
        };

        deviceList = (LinearLayout) findViewById(R.id.bluetoothInfo);
        setBluetoothData();

        if (Bluetooth.blueTooth()) {
            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    public void onResume () {
        super.onResume();
    }

    @Override
    public void onPause () {
        super.onPause();
//
//        Log.d(TAG, "...In onPause()...");
//
//        if (outStream != null) {
//            try {
//                outStream.flush();
//            } catch (IOException e) {
//                errorExit("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
//            }
//        }
//
//        try {
//            btSocket.close();
//        } catch (IOException e2) {
//            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
//        }
    }

    private void setBluetoothData () {

        btAdapter.startDiscovery();
        Set<BluetoothDevice> devices = btAdapter.getBondedDevices();

        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.bluetoothInfo);
        for (BluetoothDevice device : devices) {
            final TextView quote = new TextView(this);
            quote.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            quote.setPadding(4, 0, 4, 0);    //left,top,right,bottom
            quote.setText(device.getAddress());
            quote.setClickable(true);
            quote.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick (View v) {
                    addressToConnect = quote.getText().toString();
                    connect(addressToConnect);
                }
            });
            linearLayout.addView(quote);
        }
    }

    public void connect (String address) {
        bluetoothService.setMacAddress(address);
        startService(intent);
        unbindService(mConnection);
        Intent color_picker_activity = new Intent(this, ColorPicker.class);
        startActivity(color_picker_activity);
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

    protected void errorExit (String title, String message) {
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }

}


