package com.example.cz2006.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cz2006.R;

public class SettingCustomAdapter extends BaseAdapter {

    private Context mContext;
    private String[] Title;
    private int[] img;

    public SettingCustomAdapter(Context context, String[] text1, int[] imageIds){
        mContext = context;
        Title = text1;
        img = imageIds;
    }

    public int getCount() {
        return Title.length;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_row, parent, false);
        TextView title;
        ImageView i1;
        i1 = (ImageView) row.findViewById(R.id.settingRowIcon);
        title = (TextView) row.findViewById(R.id.settingRowText);
        title.setText(Title[position]);
        i1.setImageResource(img[position]);
        return row;
    }
}
