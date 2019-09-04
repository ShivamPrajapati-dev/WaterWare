package com.shivamprajapati.waterware;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class USerPagerAdapter extends FragmentPagerAdapter {

    private int noOfTabs;
    USerPagerAdapter(FragmentManager fm, int noOfTabs) {
        super(fm);

        this.noOfTabs=noOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){

            case 0:
            {
                CurrentRequestTab1 currentRequestTab1=new CurrentRequestTab1();
                return  currentRequestTab1;
            }
            case 1:
            {
                PreviousHistoryTab2 previousHistoryTab2=new PreviousHistoryTab2();
                return previousHistoryTab2;
            }
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
