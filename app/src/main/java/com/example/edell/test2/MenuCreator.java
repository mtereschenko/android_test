package com.example.edell.test2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.OutputStream;

/**
 * Created by edell on 04.11.2017.
 */

public class MenuCreator extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.color_picker:
                Intent color_picker_activity = new Intent(this, ColorPicker.class);
                startActivity(color_picker_activity);
                return true;
            case R.id.rainbow:
                Intent rainbow_activity = new Intent(this, Rainbow.class);
                startActivity(rainbow_activity);
                return true;
            case R.id.strobe:
                Intent strobe_activity = new Intent(this, Strobe.class);
                startActivity(strobe_activity);
                return true;
            case R.id.connection:
                Intent connection_activity = new Intent(this, Connection.class);
                startActivity(connection_activity);
                return true;
            case R.id.off:
                return true;
            case R.id.about:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
