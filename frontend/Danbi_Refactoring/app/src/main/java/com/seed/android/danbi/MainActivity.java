package com.seed.android.danbi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.camera.simplemjpeg.MjpegActivity;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private SectionPagerAdapter mSectionsPagerAdapter;

    private ImageButton imageButton_cctv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitView();
        SetCustomActionBar();
        AboutView();
    }

    public void InitView() {
        viewPager = findViewById(R.id.main_pager);
        mSectionsPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
        viewPager.setCurrentItem(0);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        imageButton_cctv = findViewById(R.id.imageButton_cctv);
    }

    public void AboutView () {
        imageButton_cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, MjpegActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SetCustomActionBar () {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View actionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_main, null);
        actionBar.setCustomView(actionBarView);

        toolbar = (Toolbar) actionBarView.getParent();
        toolbar.setContentInsetsAbsolute(0,0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(actionBarView, params);

    }

//    @Override

//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//
//        inflater.inflate(R.menu.menu_main, menu);
//
//        return super.onCreateOptionsMenu(menu);
//
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case 1:
//                Toast.makeText(this, "설정 클릭", Toast.LENGTH_SHORT).show();
//                return true;
//        }
//        return false;
//    }


    public class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
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
        public int getItemPosition(Object object) {
            return POSITION_NONE;
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
