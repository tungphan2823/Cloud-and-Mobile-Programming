package com.example.myapplication7;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ColorSpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] colorNames;
    private String[] colorValues;

    public ColorSpinnerAdapter(Context context, String[] colorNames, String[] colorValues) {
        super(context, R.layout.spinner_item, colorNames);
        this.context = context;
        this.colorNames = colorNames;
        this.colorValues = colorValues;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        TextView colorNameView = view.findViewById(R.id.colorName);
        View colorPreviewView = view.findViewById(R.id.colorPreview);

        colorNameView.setText(colorNames[position]);
        colorPreviewView.setBackgroundColor(Color.parseColor(colorValues[position]));

        return view;
    }
}
