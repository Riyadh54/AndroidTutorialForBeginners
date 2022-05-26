package com.example.viewpager2.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.viewpager2.Adapters.MyPagerAdapter;
import com.example.viewpager2.Adapters.RecyclerAdapter;
import com.example.viewpager2.Model.ProductModel;
import com.example.viewpager2.R;
import com.example.viewpager2.Utils.Utils;
import com.example.viewpager2.databinding.FragmentProductsBinding;

import java.util.ArrayList;


public class FragmentProducts extends Fragment {


    private static final String CATEGORY = "category";


    private String category;


    public FragmentProducts() {
        // Required empty public constructor
    }


    public static FragmentProducts newInstance(String category) {
        FragmentProducts fragment = new FragmentProducts();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProductsBinding binding = FragmentProductsBinding.inflate(inflater,container,false);
        ArrayList<ProductModel> products = Utils.GetProductByCategory(category);

        binding.fragmentRv.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false);
        binding.fragmentRv.setLayoutManager(lm);
        RecyclerAdapter adapter = new RecyclerAdapter(products);
        binding.fragmentRv.setAdapter(adapter);

        return binding.getRoot();
    }
}