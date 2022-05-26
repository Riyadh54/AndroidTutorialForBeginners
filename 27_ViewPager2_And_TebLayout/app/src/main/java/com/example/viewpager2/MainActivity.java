package com.example.viewpager2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.viewpager2.Adapters.MyPagerAdapter;
import com.example.viewpager2.Fragment.FragmentProducts;
import com.example.viewpager2.Utils.Utils;
import com.example.viewpager2.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());

        Utils.FillData();

        ArrayList<Fragment> fragments = new ArrayList<>();

        for (int i =0;i<Utils.categories.size();i++){
            fragments.add(FragmentProducts.newInstance(Utils.categories.get(i)));
        }

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this,fragments);

        binding.mainViewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(binding.mainTabLayout, binding.mainViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(Utils.categories.get(position));
            }
        }).attach();
    }
}