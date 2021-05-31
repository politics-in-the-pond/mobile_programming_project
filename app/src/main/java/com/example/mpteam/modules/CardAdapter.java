package com.example.mpteam.modules;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.mpteam.R;
import com.example.mpteam.data.CardData;

import java.util.ArrayList;

public class CardAdapter extends BaseAdapter {

    ArrayList<CardData> items;
    Context context;

    public CardAdapter(ArrayList<CardData> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void toggle(int position) {
        items.get(position).setToggle(!items.get(position).isToggle());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
        }
        if (items.get(position).isToggle() == false) {
            imageView.setImageResource(R.drawable.card_close);
        } else {
            imageView.setImageResource(items.get(position).getImage());
        }
        imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 340));
        return imageView;
    }
}
