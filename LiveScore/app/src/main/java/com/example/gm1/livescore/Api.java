package com.example.gm1.livescore;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST(value = "auth/user_login")
    Call<SignInResponse> userSignIn(
            @Field("email") String email,
            @Field("password") String password,
            @Field("role_id") int role_id
    );
}
