package edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.httprequests;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Defines the HttpService interface for all the http requests
 *
 * @author Forbes Miyasato
 * @version 1.0
 * @since 1.26.2019
 */

public interface HttpService {
  @POST("register/")
  @FormUrlEncoded
  Observable<String> registerUser(@Field("Name") String Name,
                                  @Field("Password") String Password);

  @POST("login/")
  @FormUrlEncoded
  Observable<String> loginUser(@Field("Name") String Name,

                               @Field("Password") String Password);
  @POST("UpdateUserData/")
  @FormUrlEncoded
  Observable<String> updateUser(@Field("Name") String Name,
                                @Field("HighScore") int HighScore,
                                @Field("Points") int Points,
                                @Field("PWMulti") int PWMulti,
                                @Field("PWSuper") int PWSuper,
                                @Field("PWUlti") int PWUlti);
  @GET("GetUserData/")
  Observable<String> getUserData(@Query(value = "Name",
          encoded = true) String Name);

  @GET("GetHighScore/")
  Observable<String> getHighScores();
}
