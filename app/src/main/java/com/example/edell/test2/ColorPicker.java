package com.example.edell.test2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.IBinder;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flask.colorpicker.ColorPickerView;
//import com.skydoves.colorpickerview.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
//import com.madrapps.pikolo.HSLColorPicker;
//import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;
import com.skydoves.colorpickerview.ColorListener;

public class ColorPicker extends MenuCreator {
//
//    ColorPickerView colorPickerView;

    ColorPickerView color_picker_view;
    private Intent intent;
    BluetoothService bluetoothService;
    boolean mBound = false;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        ColorSelector();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = new Intent(this, BluetoothService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void ColorSelector () {

//        colorPickerView = (ColorPickerView) findViewById(R.id.colorPickerView);
//        colorPickerView.setColorListener(new ColorListener() {
//            @Override
//            public void onColorSelected(int color) {
//                String command = "color" + getRGB(Integer.toHexString(color));
//                Log.d("ColorPickerC", "onColorChanged: " + command);
//                Log.d("ColorPickerC", "onColorChanged: " + Integer.toHexString(color));
//                sendCommand(command);
//            }
//        });

        color_picker_view = (ColorPickerView) findViewById(R.id.color_picker_view);
        color_picker_view.addOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged (int selectedColor) {
                String command = "color" + getRGB(Integer.toHexString(selectedColor));
                Log.d("ColorPickerC", "onColorChanged: " + command);
                Log.d("ColorPickerC", "onColorChanged: " + Integer.toHexString(selectedColor));
                sendCommand(command);
            }
        });
        color_picker_view.addOnColorSelectedListener(new OnColorSelectedListener() {
            @Override
            public void onColorSelected (int selectedColor) {
                String command = "color" + getRGB(Integer.toString(selectedColor));
                Log.d("ColorPickerS", "onColorChanged: " + command);
                Log.d("ColorPickerS", "onColorChanged: " + Integer.toString(selectedColor));
                sendCommand(command);
            }
        });
    }

    private void sendCommand (String data) {
        intent.putExtra("command", data);
        startService(intent);
    }

    private String getRGB (String colorStr) {

        String ret;
        int r = Integer.parseInt(colorStr.substring(2, 4), 16); // 16 for hex
        int g = Integer.parseInt(colorStr.substring(4, 6), 16); // 16 for hex
        int b = Integer.parseInt(colorStr.substring(6, 8), 16); // 16 for hex

        ret = "/" + r + "/" + g + "/" + b;

        return ret;
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
