package com.example.foodfindr2.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodfindr2.fragments.PostListFragment;

public class ProfilePagerAdapter extends FragmentStateAdapter {

    public ProfilePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return PostListFragment.newInstance(PostListFragment.LIST_TYPE_MY_POSTS); // My Posts
            case 1:
                return PostListFragment.newInstance(PostListFragment.LIST_TYPE_PENDING_REQUESTS); // Pending Requests
            case 2:
                return PostListFragment.newInstance(PostListFragment.LIST_TYPE_CLAIMED); // Claimed Donations
            default:
                return PostListFragment.newInstance(PostListFragment.LIST_TYPE_MY_POSTS);
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}


