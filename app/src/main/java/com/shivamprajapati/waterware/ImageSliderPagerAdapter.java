package com.shivamprajapati.waterware;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ImageSliderPagerAdapter extends FragmentStatePagerAdapter {

    ImageSliderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment;
        switch(i){
            case 0:
                fragment=new ImageOne();
                return fragment;
            case 1:
                fragment=new ImageTwo();
                return fragment;
            case 2:
                fragment=new ImageThree();
                return fragment;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
