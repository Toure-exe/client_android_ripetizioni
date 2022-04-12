package com.example.onlinelessons.ui.slideshow;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinelessons.JsonPlaceHolderApi;
import com.example.onlinelessons.Model.AlertPane;
import com.example.onlinelessons.Model.StudentTutoring;
import com.example.onlinelessons.R;

import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActiveFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_active, container, false);
        view = inflater.inflate(R.layout.fragment_active, container, false);

        TableLayout tab = view.findViewById(R.id.table_active);
        TableRow row = new TableRow(getActivity());
        requestActiveBooking(tab,row);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void requestActiveBooking(TableLayout tab, TableRow row){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.81:8080/progettoIUMTweb_war_exploded/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<StudentTutoring>> call = jsonPlaceHolderApi.createGetActiveBooking("getStudentTutoring");
        call.enqueue(new Callback<List<StudentTutoring>>() {
            @Override
            public void onResponse(Call<List<StudentTutoring>> call, Response<List<StudentTutoring>> response) {
                if(!response.isSuccessful()){
                    Log.d("LoggedActivity", "HTTP CODE: "+response.code());
                    return;
                }else{
                    List<StudentTutoring> resp = new ArrayList<>();
                    resp = response.body();
                    if(!resp.isEmpty())
                        printTable(tab,row,resp);
                    else
                        Toast.makeText(view.getContext(),"The list is empty",Toast.LENGTH_SHORT).show();

                    return;
                }
            }

            @Override
            public void onFailure(Call<List<StudentTutoring>> call, Throwable t) {
                Log.d("active", "FAILURE: "+t.getMessage());
            }
        });

    }

    private void printTable(TableLayout tab, TableRow row, List<StudentTutoring> resp){
        View tempView = this.view;
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


        tab.addView(row);

        for(StudentTutoring temp : resp){
            TableRow riga = new TableRow(getActivity());
            TextView t1v = new TextView(getActivity());
            t1v.setText(temp.getSubject());
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            riga.addView(t1v);

            TextView t2v = new TextView(getActivity());
            t2v.setText(temp.getTeacher());
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            riga.addView(t2v);

            TextView t3v = new TextView(getActivity());
            t3v.setText(temp.getDate());
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            riga.addView(t3v);

            TextView t4v = new TextView(getActivity());
            t4v.setText(temp.getHour());
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            riga.addView(t4v);

            Button actions = new Button(getActivity());
            actions.setText("ACTIONS");
            riga.addView(actions);

            actions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view2) {
                    TableRow row2 = (TableRow) view2.getParent();
                    int index = (tab.indexOfChild(row2) - 1);
                    String subject = resp.get(index).getSubject();
                    String teacher = resp.get(index).getTeacher();
                    String date = resp.get(index).getDate();
                    String hour = resp.get(index).getHour();
                    String emailStudent = resp.get(index).getStudent();
                    openDialog(subject,teacher, date, hour,emailStudent, tempView);
                }
            });
            tab.addView(riga);
        }
    }

    public void openDialog(String subject, String teacher, String date, String hour,String emailStudent, View view) {
        AlertPane exampleDialog = new AlertPane(subject,teacher,date,hour,emailStudent, view);
        exampleDialog.show(getActivity().getSupportFragmentManager(), "actions dialog");
    }
}