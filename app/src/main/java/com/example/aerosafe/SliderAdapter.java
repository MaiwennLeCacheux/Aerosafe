package com.example.aerosafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Arrays
    public String[] slide_titles = {
            "Titre 1",
            "Titre 2",
            "Titre 3"
    };


    @Override
    public int getCount() {
        return slide_titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    public Object instantiateItem(ViewGroup container, int position){

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        TextView slideTitle = (TextView) view.findViewById(R.id.info_airport_name);

        slideTitle.setText(slide_titles[position]);

        container.addView(view);

        return view;
    }

    //peut-être à mettre en comm
    public void destroyItem (ViewGroup container, int position, Object object){
        container.removeView((ConstraintLayout)object);
    }
}
