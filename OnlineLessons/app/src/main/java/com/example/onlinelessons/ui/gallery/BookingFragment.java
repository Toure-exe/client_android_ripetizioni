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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinelessons.JsonPlaceHolderApi;
import com.example.onlinelessons.Model.Tutoring;
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
    private static List<Tutoring> currentTutoring = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_booking, container, false);
        requestSubjects();

        Button book = this.view.findViewById(R.id.buttonBook);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner SpinnerSubject = (Spinner) view.findViewById(R.id.spinnerMateria);
                Spinner SpinnerTeacher = (Spinner) view.findViewById(R.id.spinnerProf);
                Spinner SpinnerDay = (Spinner) view.findViewById(R.id.spinnerGiorno);
                Spinner SpinnerHour = (Spinner) view.findViewById(R.id.spinnerOra);

                Object itemSubject = SpinnerSubject.getSelectedItem();
                Object itemTeacher = SpinnerTeacher.getSelectedItem();
                Object itemDay = SpinnerDay.getSelectedItem();
                Object itemHour = SpinnerHour.getSelectedItem();

                if(itemSubject != null && itemTeacher != null && itemDay != null && itemHour != null){
                    String currentSubject  = itemSubject.toString();
                    String currentDay  = itemDay.toString();
                    String currentTeacher  = itemTeacher.toString();
                    String currentHour  = ((itemHour.toString()).split("-"))[0];
                    if(!currentSubject.equals("Select a suject") && !currentDay.equals("Select a day") && !currentTeacher.equals("Select a teacher")
                            && !currentHour.equals("Select an hour"))
                        requestInsertBooking(currentSubject,currentDay,currentTeacher,currentHour);
                    else
                        Toast.makeText(view.getContext(),"You must fill in the fields",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(view.getContext(),"You must fill in the fields",Toast.LENGTH_SHORT).show();
                }




            }
        });



        return view;
    }

    public void requestInsertBooking(String currentSubject, String currentDay,String currentTeacher,String currentHour){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.81:8080/progettoIUMTweb_war_exploded/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<String> call = jsonPlaceHolderApi.createGetInsertBooking("insertBooking"," "+currentTeacher," "+currentSubject,currentDay,currentHour);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.d("insert Booking:", "HTTP CODE: "+response.code());
                }else{

                    String resp = response.body();
                   if(resp.equals("true")){
                        Toast.makeText(view.getContext(),"Your booking has been correctly inserted in the system",Toast.LENGTH_SHORT).show();
                        reloadFragment(new BookingFragment());
                    }else {
                        Toast.makeText(view.getContext(),"error, please retry",Toast.LENGTH_SHORT).show();

                    }


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("insert booking", "FAILURE: "+t.getMessage());
            }
        });

    }

    private void reloadFragment(Fragment fg){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,fg);
        ft.commit();
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
                    resp.add(0,"Select a subject");
                    fillSpinner(resp,R.id.spinnerMateria);


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
                    resp.add(0,"Select a teacher");
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



    public void requestAvailability(String subject, String teacher){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.81:8080/progettoIUMTweb_war_exploded/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Tutoring>> call = jsonPlaceHolderApi.createGetTeacherAvailability("teacherAvailability"," "+subject," "+teacher);
        call.enqueue(new Callback<List<Tutoring>>() {
            @Override
            public void onResponse(Call<List<Tutoring>> call, Response<List<Tutoring>> response) {
                if(!response.isSuccessful()){
                    Log.d("requestAvailability", "HTTP CODE: "+response.code());
                }else{

                   // List<Tutoring> resp = new ArrayList<>();
                    currentTutoring = response.body();
                  //  fillSpinner(resp,R.id.spinnerProf);

                }
            }

            @Override
            public void onFailure(Call<List<Tutoring>> call, Throwable t) {
                // Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("active", "FAILURE: "+t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<String> fillDays(){
        List<String> days = new ArrayList<>();
        days.add("Select a day");
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");

        return days;
    }

    private List<String> fillHours(String daySelected){
        List<String> hours = new ArrayList<>();
        hours.add("Select an hour");
        hours.add("15-16");
        hours.add("16-17");
        hours.add("17-18");
        hours.add("18-19");

        if(!currentTutoring.isEmpty()){
            for(Tutoring temp : currentTutoring){
                if(temp.getDate().equals(daySelected)){
                    switch (temp.getHour()){
                        case "15":
                            hours.remove(1);
                            break;
                        case "16":
                            hours.remove(2);
                            break;
                        case "17":
                            hours.remove(3);
                            break;
                        case "18":
                            hours.remove(4);
                            break;
                        default:
                            Log.d("switchHours:","error in day");
                            break;
                    }
                }
            }
        }
        return hours;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spinnerMateria:
                String subject = adapterView.getItemAtPosition(i).toString();
                if(!subject.equals("Select a subject"))
                    requestTeachers(subject);
                break;
            case R.id.spinnerProf:
                String teacherSelected = adapterView.getItemAtPosition(i).toString();
                if(!teacherSelected.equals("Select a teacher")){
                    List<String> days = new ArrayList<>();
                    days = fillDays();
                    fillSpinner(days,R.id.spinnerGiorno);
                    Spinner mySpinner = (Spinner) this.view.findViewById(R.id.spinnerMateria);
                    String subjectSelected = mySpinner.getSelectedItem().toString();
                    requestAvailability(subjectSelected,teacherSelected);
                }

                break;
            case R.id.spinnerGiorno:
                String daySelected = adapterView.getItemAtPosition(i).toString();
                if(!daySelected.equals("Select a day")){
                    List<String> hoursList = fillHours(daySelected);
                    fillSpinner(hoursList,R.id.spinnerOra);
                }
                break;
            default:
                Log.d("SPINNER","SPINNER ID DOESN'T EXISTS");
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}