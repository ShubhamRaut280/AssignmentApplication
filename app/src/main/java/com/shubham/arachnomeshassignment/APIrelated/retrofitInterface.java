package com.shubham.arachnomeshassignment.APIrelated;

import com.shubham.arachnomeshassignment.userData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface retrofitInterface {

    @POST("users")
    Call<userData> postDataToApi(@Body userData userdata);
}
