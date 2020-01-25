package edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.userauth;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpService {
  @POST("register/")
  @FormUrlEncoded
  Observable<String> registerUser(@Field("Name") String Name,
                                  @Field("Password") String Password);

  @POST("login/")
  @FormUrlEncoded
  Observable<String> loginUser(@Field("Name") String Name,
                                  @Field("Password") String Password);
}
