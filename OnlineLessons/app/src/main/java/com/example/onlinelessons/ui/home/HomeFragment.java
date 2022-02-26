package com.example.onlinelessons.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinelessons.JsonPlaceHolderApi;
import com.example.onlinelessons.Model.StudentTutoring;
import com.example.onlinelessons.R;
import com.example.onlinelessons.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    //private FragmentHomeBinding binding;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        setHasOptionsMenu(true);

      //  binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = inflater.inflate(R.layout.fragment_home, container, false);

       //TableLayout tab = view.findViewById(R.id.table_active);
        //TableRow row = new TableRow(getActivity());
        //requestActiveBooking(tab,row);
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
      //  binding = null;
    }

}