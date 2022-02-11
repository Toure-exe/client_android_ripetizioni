package com.example.onlinelessons;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.onlinelessons.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

   /* public void onRegistrationButton(View v){
        TextView emailobj = findViewById(R.id.emailText);
        TextView pass1obj = findViewById(R.id.passwordText);
        TextView pass2obj = findViewById(R.id.passwordText2);
        String email = emailobj.getText().toString();
        String pass1 = pass1obj.getText().toString();
        String pass2 = pass2obj.getText().toString();


        if((!email.equals("") && !pass1.equals("") && !pass2.equals(""))){
            if(email.contains("@") && email.contains(".")){
                if(pass1.equals(pass2)){
                   // Toast.makeText(MainActivity.this, "UGUALI ", Toast.LENGTH_LONG).show();
                    createPost(email,pass1);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                    .replace(R.id.home, FirstFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null) // name can be null
                    .commit();

                }else{
                    Toast.makeText(MainActivity.this, "diversi " , Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(MainActivity.this, "email non valida", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "compilare tutti i campi", Toast.LENGTH_LONG).show();
        }



    }

    private void createPost(String email, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.81:8080/progettoIUMTweb_war_exploded/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Post> call = jsonPlaceHolderApi.createPost("registration",email,password);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                    if(!response.isSuccessful()){
                        Log.d("MainActivity", "CODE: "+response.code());
                        return;
                    }else{
                        Toast.makeText(MainActivity.this, "registrazione effettuata", Toast.LENGTH_LONG).show();
                    }



            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }*/
}