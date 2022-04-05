package com.example.onlinelessons;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.onlinelessons.databinding.FragmentSecondBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second, container, false);

        Button loginButton = view.findViewById(R.id.button_second);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView emailobj = (TextView) view.findViewById(R.id.EmailText);
                String email = emailobj.getText().toString();
                TextView passobj = view.findViewById(R.id.passwordText3);
                String psw = passobj.getText().toString();

                if(!email.equals("") && !psw.equals("")){
                    if(email.contains("@") && email.contains(".")){
                        createPostLoginWrapper(email,psw);
                    }else{
                        Toast.makeText(getActivity(),"invalid email!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Fill in all the fields! ",Toast.LENGTH_SHORT).show();
                }


            }
        });


        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void createPostLoginWrapper(String email, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.81:8080/progettoIUMTweb_war_exploded/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<String> call = jsonPlaceHolderApi.createPostLogin("login",email,password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(view.getContext(),"HTTP ERROR CODE: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    String resp = response.body();
                    if(resp.equals("1")){
                        Intent myIntent = new Intent(getActivity(), LoggedActivity.class);
                        myIntent.putExtra("email",email); //Optional parameters
                        getActivity().startActivity(myIntent);

                    }else if(resp.equals("0")){
                        Toast.makeText(getActivity(),"User not found! ",Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(view.getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}