package com.hazyaz.weshare.introduction;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.hazyaz.weshare.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapterIntro extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapterIntro(Context context){
        this.context=context;
    }
    //array of images
    public String[] slide_images={

            "welcomeanimate.json",

            "shareanimate.json",

            "secureanimate.json",

            "locationanimate.json",

            "notificationanimate.json"


    };

    //array of images
    public String[] slide_title={
            "Welcome To We Share Donation App"
            ,"Sharing is Caring"
            ,"Secure Donation"
            ,"Know how your donation is being used"
            ,"Get Notified"
    };

    //array of images
    public String[] slide_description={
            " During COVID-19 many people has lost their jobs and it has impacted them so much. With this app we are trying to help them   "
            ,"Donate anything you want"
            ,"Secured by SSL/TLS and PCI compliant\n "
            ,"Monitor how your donation is being utilised"
            ,"Get Notified at each step "
    };

    @Override
    public int getCount() {
        return slide_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view =layoutInflater.inflate(R.layout.slideradapterintro_layout,container,false);

//        ImageView slideImageView=(ImageView)view.findViewById(R.id.intro_img);
        TextView slideTitle=(TextView) view.findViewById(R.id.intro_title);
        LottieAnimationView slideImageView=(LottieAnimationView)view.findViewById(R.id.intro_img);
//        slideImageView.setSpeed(200f);


        TextView slideDescription=(TextView) view.findViewById(R.id.intro_description);

        slideImageView.setAnimation(slide_images[position]);
        slideTitle.setText(slide_title[position]);
        slideDescription.setText(slide_description[position]);

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
