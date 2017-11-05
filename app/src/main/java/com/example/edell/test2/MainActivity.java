package com.example.edell.test2;

import android.os.Bundle;

public class MainActivity extends MenuCreator {

//    String[] data = {"one", "two", "three", "four", "five"};
//    ColorPickerView color_picker_view;
//    Spinner spinner;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ModeSelector();
//        ColorSelector();
    }

//    private String getRGB (final String rgb) {
//        String ret = "";
//        for (int i = 0; i < 3; i++) {
//            ret += "/" + Integer.parseInt(rgb.substring(i * 2, i * 2 + 2), 16);
//        }
//        return ret;
//    }
//
//    private void ColorSelector () {
//        color_picker_view = (ColorPickerView) findViewById(R.id.color_picker_view);
//        color_picker_view.addOnColorChangedListener(new OnColorChangedListener() {
//            @Override
//            public void onColorChanged (int selectedColor) {
//                Log.d("ColorPicker", "onColorChanged: " + getRGB(Integer.toHexString(selectedColor)));
//            }
//        });
//        color_picker_view.addOnColorSelectedListener(new OnColorSelectedListener() {
//            @Override
//            public void onColorSelected (int selectedColor) {
//                Toast.makeText(
//                        MainActivity.this,
//                        "selectedColor: " + Integer.toHexString(selectedColor).toUpperCase(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private void ModeSelector () {
//        // адаптер
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//        spinner.setAdapter(adapter);
//        // заголовок
//        spinner.setPrompt("Title");
//        // выделяем элемент
//        spinner.setSelection(2);
//        // устанавливаем обработчик нажатия
//        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//            @Override
//            public void onItemSelected (AdapterView<?> parent, View view,
//                                        int position, long id) {
//                // показываем позиция нажатого элемента
//                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected (AdapterView<?> arg0) {
//            }
//        });
//    }
}
