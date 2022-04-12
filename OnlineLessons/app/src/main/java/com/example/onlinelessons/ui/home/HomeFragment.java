package com.example.onlinelessons.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.lifecycle.ViewModelProvider;

import com.example.onlinelessons.R;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        setHasOptionsMenu(true);

        view = inflater.inflate(R.layout.fragment_home, container, false);
        changeFragment(new ImgpageFragment());
        return view;
    }

    private void changeFragment(Fragment fg){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,fg);
        ft.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}