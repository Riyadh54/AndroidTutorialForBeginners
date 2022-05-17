package com.example.a23_tablayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.a23_tablayout.Adapter.PagerAdapter;
import com.example.a23_tablayout.Fragments.MyFragment;
import com.example.a23_tablayout.Model.CategoryModel;
import com.example.a23_tablayout.Model.MyTabsModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    private ArrayList<MyTabsModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout= findViewById(R.id.main_tl);
        viewPager= findViewById(R.id.main_vp);

        String[] categories = getResources().getStringArray(R.array.categories);

        for (int i =0 ;i<categories.length;i++)
        {
            data.add(new MyTabsModel(MyFragment.newInstance(categories[i],i),new CategoryModel(i,categories[i])));
        }
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),data);
        viewPager.setAdapter(pagerAdapter);


        tabLayout.setupWithViewPager(viewPager);
          // other Method To create Tab Items
        //tabLayout.addTab(tabLayout.newTab().setText("Tab Name"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab Name"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab Name"));


















        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(MainActivity.this, "On Tab Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Toast.makeText(MainActivity.this, "On Tab Unselected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Toast.makeText(MainActivity.this, "on Tab Reselected", Toast.LENGTH_SHORT).show();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Toast.makeText(MainActivity.this, "on Page Scrolled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this, "on Page Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Toast.makeText(MainActivity.this, "on Page Scroll State Changed", Toast.LENGTH_SHORT).show();
            }
        });



    }
}