package com.pankajkcodes.videodownloader.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pankajkcodes.videodownloader.fragments.PostFragment;
import com.pankajkcodes.videodownloader.fragments.WPFragment;
import com.pankajkcodes.videodownloader.fragments.FBVideoFragment;
import com.pankajkcodes.videodownloader.fragments.ReelsFragment;

public class PagerAdapter extends FragmentStateAdapter {


    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new PostFragment();
        } else if (position == 2) {
            return new FBVideoFragment();

        } else if (position == 3) {
            return new WPFragment();
        }
        return new ReelsFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
