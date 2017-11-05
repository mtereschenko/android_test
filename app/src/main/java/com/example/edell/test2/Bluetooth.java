package com.example.edell.test2;

import android.bluetooth.BluetoothAdapter;

import java.io.OutputStream;

public class Bluetooth {

    private static boolean state = false;
    protected OutputStream outStream = null;

    public static boolean blueTooth () {

        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        if (!bluetooth.isEnabled()) {
            System.out.println("Bluetooth is Disable...");
            state = true;
        } else if (bluetooth.isEnabled()) {
            String address = bluetooth.getAddress();
            String name = bluetooth.getName();
            System.out.println(name + " : " + address);
            state = false;
        }
        return state;
    }

}