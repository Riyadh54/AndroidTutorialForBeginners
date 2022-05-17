package com.example.a23_tablayout.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a23_tablayout.R;


public class MyFragment extends Fragment {

    TextView textView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NAME_KEY = "name";
    private static final String ID_KEY = "id";


    private String name;
    private int id;

    public MyFragment() {
        // Required empty public constructor
    }


    public static MyFragment newInstance(String name, int id) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(NAME_KEY, name);
        args.putInt(ID_KEY, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ID_KEY);
            name = getArguments().getString(NAME_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my, container, false);
        textView = v.findViewById(R.id.fragment_tv);
        textView.setText(""+id+":"+name);
        return v;
    }
}