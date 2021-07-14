package com.example.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Weather> arrayList;

    public CustomAdapter(Context context, ArrayList<Weather> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.listview,null);
        Weather weather = arrayList.get(position);
        TextView textDay = view.findViewById((R.id.weekday));
        TextView textStatus = view.findViewById((R.id.status));
        TextView textMaxTemp = view.findViewById((R.id.textviewMaxTemp));
        TextView textMinTemp = view.findViewById((R.id.textviewMinTemp));
        ImageView imgStatus = (ImageView) view.findViewById(R.id.imageviewStatus);

        textDay.setText(weather.day);
        textStatus.setText(weather.status);
        textMaxTemp.setText(weather.maxTemp+"C");
        textMinTemp.setText(weather.minTemp+"C");
        Picasso.with(context).load("http://openweathermap.org/img/w/"+weather.image+".png").into(imgStatus);
        return view;
    }
}
