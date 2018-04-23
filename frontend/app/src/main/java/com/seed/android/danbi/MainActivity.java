package com.seed.android.danbi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            Bundle args = null;
            switch (position) {
                case 0:
                    fragment = new Fragment_Water(MainActivity.this);
                    args = new Bundle();
                    break;
                case 1:
                    fragment = new Fragment_Temperature(MainActivity.this);
                    args = new Bundle();
                    break;
                case 2:
                    fragment = new Fragment_Weather(MainActivity.this);
                    args = new Bundle();
                    break;
                case 3:
                    fragment = new Fragment_Door(MainActivity.this);
                    args = new Bundle();
                    break;
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragment;
        }

        @Override
        public int getCount() {

            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "물주기";
                case 1:
                    return "온/습도";
                case 2:
                    return "날씨";
                case 3:
                    return "문열기";
            }
            return null;
        }
    }
}
