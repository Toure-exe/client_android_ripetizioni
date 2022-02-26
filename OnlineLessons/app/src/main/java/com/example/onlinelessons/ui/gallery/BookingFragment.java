package com.example.onlinelessons.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinelessons.JsonPlaceHolderApi;
import com.example.onlinelessons.R;
import com.example.onlinelessons.databinding.FragmentBookingBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookingFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private BookingViewModel bookingViewModel;
    private FragmentBookingBinding binding;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_booking, container, false);
        requestSubjects();
        Log.d("frag","print");



        return view;
    }

    public void requestSubjects(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.81:8080/progettoIUMTweb_war_exploded/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<String>> call = jsonPlaceHolderApi.createGetSubjectAvailable("subjectAvailable");
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(!response.isSuccessful()){
                    Log.d("LoggedActivity", "HTTP CODE: "+response.code());
                }else{

                    List<String> resp = new ArrayList<>();
                    resp = response.body();
                    fillSpinner(resp,R.id.spinnerMateria);
                    Log.d("ciao","ciao");
                    Log.d("materie: ",resp.get(0));

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                // Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("active", "FAILURE: "+t.getMessage());
            }
        });

    }

    public void requestTeachers(String subject){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.81:8080/progettoIUMTweb_war_exploded/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<String>> call = jsonPlaceHolderApi.createGetTeachers("getTeachers",subject,"false");
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(!response.isSuccessful()){
                    Log.d("LoggedActivity", "HTTP CODE: "+response.code());
                }else{

                    List<String> resp = new ArrayList<>();
                    resp = response.body();
                    fillSpinner(resp,R.id.spinnerProf);

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                // Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("active", "FAILURE: "+t.getMessage());
            }
        });

    }

    private void fillSpinner(List<String> respList, int spinnerId){
        Spinner spinner = view.findViewById(spinnerId);
        String[] content = new String[respList.size()];
        content = respList.toArray(content);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, content);//ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, respList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spinnerMateria:
                String subject = adapterView.getItemAtPosition(i).toString();
                requestTeachers(subject);
                break;
            case R.id.spinnerProf:
               /* String teacher = adapterView.getItemAtPosition(i).toString();
                requestTeachers(teacher);*/
                break;
            default:
                Log.d("SPINNER","SPINNER ID DOESN'T EXISTS");
                break;
            //case R.id.spinner3:
                //your code here
               // break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}