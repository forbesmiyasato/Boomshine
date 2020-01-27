package edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.httprequests;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Defines the RetrofitClient that contains a Retrofit instance
 * used to make Http Requests
 *
 * @author Forbes Miyasato
 * @version 1.0
 * @since 1.26.2019
 */
public class RetrofitClient {
  private static Retrofit cRetrofitInstance;

  /**
   * Gets the Retrofit instance.
   * Creates instance if no Retrofit instance is created yet
   *
   * @return The retrofit instance.
   */
  public static Retrofit getInstance() {
    if (cRetrofitInstance == null)
    {
      cRetrofitInstance = new Retrofit.Builder().baseUrl("http://10.0.2.2:8000.io/")
              .addConverterFactory(ScalarsConverterFactory.create())
              .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }
    return cRetrofitInstance;
  }
}
