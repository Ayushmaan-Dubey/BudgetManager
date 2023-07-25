package com.example.budgetmanagerexpensetracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DemoCollectionAdapter extends FragmentStateAdapter {
    public DemoCollectionAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        String tabNameByPosition = CollectionDemoFragment.TAB_NAMES[position];
        Fragment newFragment = new Fragment();
        switch(tabNameByPosition) {
            case "Stats":
                newFragment = new StatsFragment();
                break;
            case "Summary":
                newFragment = new SummaryFragment();
            default:
                break;
            case "Add Transaction":
                newFragment = new TransactionFragment();
                break;
        }

        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(StatsFragment.ARG_OBJECT, position + 1);
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}