package com.example.onlinelessons.Model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.onlinelessons.JsonPlaceHolderApi;
import com.example.onlinelessons.R;
import com.example.onlinelessons.ui.slideshow.ActiveFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AlertPane extends AppCompatDialogFragment {
    private String subject;
    private String teacher;
    private String date;
    private String hour;
    private String emailStudent;
    private View view;

    public AlertPane(String subject, String teacher, String date, String hour,String emailStudent, View view){
        super();
        this.subject = subject;
        this.teacher = teacher;
        this.date = date;
        this.hour = hour;
        this.view = view;
        this.emailStudent = emailStudent;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("SELECT")
                .setMessage("Select an option")
                .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("ALERT: ","CONFIRM");
                        requestConfirmTutoring();
                        reloadFragment(new ActiveFragment());
                    }
                }) .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("ALERT: ","DELETE");
                requestCancelTutoring();
                reloadFragment(new ActiveFragment());

            }
        });


        return builder.create();
    }

    public void requestCancelTutoring(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.81:8080/progettoIUMTweb_war_exploded/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<String> call = jsonPlaceHolderApi.createPostCancelTutoring("deleteTutoring",this.date,this.hour,this.teacher,this.subject,this.emailStudent);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.d("LoggedActivity", "HTTP CODE: "+response.code());
                }else{

                    String resp = response.body();

                    if(resp.equals("true")){
                        //Log.d("Confirm tutoring","TRUE");
                        //AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
                        Toast.makeText(view.getContext(),"Your booking has been successfully deleted",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(view.getContext(),"Error, please retry",Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("active", "FAILURE: "+t.getMessage());
            }
        });

    }

    private void requestConfirmTutoring(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.81:8080/progettoIUMTweb_war_exploded/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<String> call = jsonPlaceHolderApi.createGetConfirmTutoring("confirmTutoring",this.date,this.hour,this.teacher,this.subject);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.d("LoggedActivity", "HTTP CODE: "+response.code());
                    return;
                }else{

                    String resp = response.body();

                    if(resp.equals("true")){
                        //Log.d("Confirm tutoring","TRUE");
                        //AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
                        Toast.makeText(view.getContext(),"Your booking has been successfully confirmed",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(view.getContext(),"Error, please retry",Toast.LENGTH_SHORT).show();
                    }

                    return;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("active", "FAILURE: "+t.getMessage());
            }
        });

    }

    private void reloadFragment(Fragment fg){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,fg);
        ft.commit();
    }

}
