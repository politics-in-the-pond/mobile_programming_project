package com.example.mpteam.modules;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mpteam.R;

import java.util.ArrayList;

public class MyGridViewAdapter extends BaseAdapter {

    ArrayList<Bitmap> items;
    Context context;

    public MyGridViewAdapter(ArrayList<Bitmap>  items, Context context){
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    // 이상 생략
    // create a new ImageView for each item referenced by the Adapter
    // 참고자료 내 Android Developers에 게시된 Example 수정
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
        }
        Bitmap mThumbnail = ThumbnailUtils.extractThumbnail(items.get(position), 150, 150);
        imageView.setPadding(40, 100, 40, 100);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));
        imageView.setImageBitmap(mThumbnail);
        return imageView;
    }

}