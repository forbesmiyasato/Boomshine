package edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.userauth;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpService {
  @POST("register/")
  @FormUrlEncoded
  Observable<String> registerUser(@Field("Name") String Name,
                                  @Field("Password") String Password);

  @POST("login/")
  @FormUrlEncoded
  Observable<String> loginUser(@Field("Name") String Name,
                                  @Field("Password") String Password);

  @GET("GetUserData/")
  Observable<String> getUserData(@Query(value="Name", encoded =true) String Name);
}
