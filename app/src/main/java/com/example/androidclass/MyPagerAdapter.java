package com.example.androidclass;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyPagerAdapter extends FragmentStateAdapter {

    public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return different fragments for different positions
        switch (position) {
            case 0:
                return new PageFragment1();
            case 1:
                return new PageFragment2();
            case 2:
                return new PageFragment3();
            default:
                return new PageFragment1();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Total number of pages
    }
}