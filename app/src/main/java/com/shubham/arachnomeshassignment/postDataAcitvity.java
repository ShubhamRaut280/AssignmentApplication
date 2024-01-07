package com.shubham.arachnomeshassignment;

import static android.view.View.GONE;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shubham.arachnomeshassignment.APIrelated.retrofitInterface;

import org.parceler.Parcels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class postDataAcitvity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    TextView userid, username, usercontact, latitude, longitude, status, statuscode, error;
    ProgressBar progressBar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_data);
        progressBar = findViewById(R.id.progressbarin_post);
        relativeLayout = findViewById(R.id.relativelayout);
        userid = findViewById(R.id.userid);
        username = findViewById(R.id.username);
        usercontact = findViewById(R.id.usercontact);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        status = findViewById(R.id.status);
        statuscode = findViewById(R.id.statuscode);
        error = findViewById(R.id.error);


        relativeLayout.setVisibility(GONE);
        progressBar.setVisibility(View.VISIBLE);

        // getting and storing the data received from first activity
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("userdata"))
        {
            // displaying this data
            Parcelable parcelable = bundle.getParcelable("userdata");
            userData userFromMainActivity = Parcels.unwrap(parcelable);
            userid.setText("USER ID : "+userFromMainActivity.userID);
            username.setText("USER NAME : "+userFromMainActivity.name);
            usercontact.setText("CONTACT : "+userFromMainActivity.contact);
            latitude.setText("LATITUDE : "+userFromMainActivity.latitude);
            longitude.setText("LONGITUDE: "+userFromMainActivity.longitude);

            // creating a post request
            retrofitWork(userFromMainActivity);
        }


    }


    // creating a post request
    public void retrofitWork(userData userdata)
    {

        // initialize retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // initializing the interface created earlier which consist of all the request methods defined
        retrofitInterface postdata = retrofit.create(retrofitInterface.class);

        //creating post request
        postdata.postDataToApi(userdata).enqueue(new Callback<userData>() {

            // if the reqeust successfully completes
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<userData> call, @NonNull Response<userData> response) {
                progressBar.setVisibility(GONE);
                relativeLayout.setVisibility(View.VISIBLE);

                // data successfully posted
                if(response.isSuccessful())
                {
                    progressBar.setVisibility(GONE);

                    // set status and status code in activity to display user and show toast messages to user
                    status.setText("STATUS : Successful");
                    statuscode.setText("STATUS CODE : "+response.code());
                    error.setVisibility(GONE);
                    Toast.makeText(postDataAcitvity.this, "Data Posted Successfully. Response code : "+ response.code(), Toast.LENGTH_LONG).show();
                }

                // error in data posting but the request completed successfully
                else
                {
                    try {
                        String errorBody = response.errorBody().string();

                        status.setText("STATUS : Not successful");
                        statuscode.setText("STATUS CODE : "+response.code());
                        error.setText(errorBody);

                        Toast.makeText(postDataAcitvity.this,"Something went wrong: " + errorBody,Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(postDataAcitvity.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                    }
                }
            }


            // request fails
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<userData> call, Throwable t) {
                progressBar.setVisibility(GONE);
                relativeLayout.setVisibility(View.VISIBLE);
                status.setText("STATUS : Request Failed !!");
                statuscode.setVisibility(GONE);
                error.setText("ERROR : "+t.getMessage());

                Toast.makeText(postDataAcitvity.this, "Data post request failed. error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t);
            }
        });


    }
}