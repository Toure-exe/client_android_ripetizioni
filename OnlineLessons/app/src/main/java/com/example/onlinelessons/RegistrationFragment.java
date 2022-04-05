package com.example.onlinelessons;

import android.os.Bundle;
import androidx.fragment.app.*;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationFragment extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registration, container, false);


        Button ok = (Button) view.findViewById(R.id.button_second2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView emailobj = (TextView) view.findViewById(R.id.emailText);
                TextView pass1obj = (TextView) view.findViewById(R.id.passwordText);
                TextView pass2obj = (TextView) view.findViewById(R.id.passwordText2);
                String email = emailobj.getText().toString();
                String pass1 = pass1obj.getText().toString();
                String pass2 = pass2obj.getText().toString();


                if((!email.equals("") && !pass1.equals("") && !pass2.equals(""))){
                    if(email.contains("@") && email.contains(".")){
                        if(pass1.equals(pass2)){
                            createPostRegister(email,pass1);
                            NavHostFragment.findNavController(RegistrationFragment.this)
                                    .navigate(R.id.action_RegistrationFragment_to_FirstFragment);

                        }else{
                            Toast.makeText(getActivity(),"passwords not equals!",Toast.LENGTH_SHORT).show();
                        }

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

    private void createPostRegister(String email, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.81:8080/progettoIUMTweb_war_exploded/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<String> call = jsonPlaceHolderApi.createPost("registration",email,password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(view.getContext(),"HTTP ERROR CODE: "+response.code(),Toast.LENGTH_SHORT).show();
                }else{
                    String resp = response.body();
                    if(resp.equals("ok")){
                        Toast.makeText(view.getContext(),"user correctly inserted in the system",Toast.LENGTH_SHORT).show();
                    }else if(resp.equals("error")){
                        Toast.makeText(view.getContext(),"user already inserted into the system",Toast.LENGTH_SHORT).show();
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