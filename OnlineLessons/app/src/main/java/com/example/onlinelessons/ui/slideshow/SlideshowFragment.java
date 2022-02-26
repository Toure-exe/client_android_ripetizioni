package com.example.onlinelessons.ui.slideshow;

import android.content.Intent;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.onlinelessons.FirstFragment;
import com.example.onlinelessons.JsonPlaceHolderApi;
import com.example.onlinelessons.LoggedActivity;
import com.example.onlinelessons.MainActivity;
import com.example.onlinelessons.Model.StudentTutoring;
import com.example.onlinelessons.R;
import com.example.onlinelessons.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

       // binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        TableLayout tab = view.findViewById(R.id.tab_history);
        TableRow row = new TableRow(getActivity());

        requestHistory(tab,row);
        //


        //headers



       // Log.d("history"," "+resp.get(0).getSubject());

        return view;
    }

    private void requestHistory(TableLayout tab, TableRow row){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.81:8080/progettoIUMTweb_war_exploded/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<StudentTutoring>> call = jsonPlaceHolderApi.createPostGetHistory("getHistory");
        call.enqueue(new Callback<List<StudentTutoring>>() {
            @Override
            public void onResponse(Call<List<StudentTutoring>> call, Response<List<StudentTutoring>> response) {
                if(!response.isSuccessful()){
                    Log.d("LoggedActivity", "HTTP CODE: "+response.code());
                    return;
                }else{
                    List<StudentTutoring> resp = new ArrayList<>();
                    resp = response.body();
                    Log.d("History:"," "+resp.size());
                    Log.d("history"," "+resp.get(0).getSubject());
                    printTable(tab,row,resp);
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<StudentTutoring>> call, Throwable t) {
                // Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("LoggedActivity", "FAILURE: "+t.getMessage());
            }
        });

    }

    private void printTable(TableLayout tab, TableRow row, List<StudentTutoring> resp){
        int i=0;
        TextView tv1 = new TextView(getActivity());
        tv1.setText("Subject");
        tv1.setTextColor(Color.BLACK);
        tv1.setGravity(Gravity.CENTER);
        row.addView(tv1);

        TextView tv2 = new TextView(getActivity());
        tv2.setText("Teacher");
        tv2.setTextColor(Color.BLACK);
        tv2.setGravity(Gravity.CENTER);
        row.addView(tv2);

        TextView tv3 = new TextView(getActivity());
        tv3.setText("Date");
        tv3.setTextColor(Color.BLACK);
        tv3.setGravity(Gravity.CENTER);
        row.addView(tv3);

        TextView tv4 = new TextView(getActivity());
        tv4.setText("Hour");
        tv4.setTextColor(Color.BLACK);
        tv4.setGravity(Gravity.CENTER);
        row.addView(tv4);

        TextView tv5 = new TextView(getActivity());
        tv5.setText("State");
        tv5.setTextColor(Color.BLACK);
        tv5.setGravity(Gravity.CENTER);
        row.addView(tv5);

        tab.addView(row);

        for(StudentTutoring temp : resp){
            TableRow riga = new TableRow(getActivity());
            TextView t1v = new TextView(getActivity());
            t1v.setText(" "+temp.getSubject()+" ");
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            riga.addView(t1v);

            TextView t2v = new TextView(getActivity());
            t2v.setText(" "+temp.getTeacher()+" ");
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            riga.addView(t2v);

            TextView t3v = new TextView(getActivity());
            t3v.setText(" "+temp.getDate()+" ");
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            riga.addView(t3v);

            TextView t4v = new TextView(getActivity());
            t4v.setText(" "+temp.getHour()+" ");
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            riga.addView(t4v);

            TextView t5v = new TextView(getActivity());
            t5v.setText(" "+temp.getStatus()+" ");
            t5v.setTextColor(Color.BLACK);
            t5v.setGravity(Gravity.CENTER);
            riga.addView(t5v);

            tab.addView(riga);
            i++;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}