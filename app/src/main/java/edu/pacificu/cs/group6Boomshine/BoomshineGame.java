package edu.pacificu.cs.group6Boomshine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.userauth.HttpService;
import edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.userauth.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class BoomshineGame extends AppCompatActivity {

  CompositeDisposable mcCompositeDisposable;
  private HttpService mService;
  private Display mDisplay;
  private BoomshineView mGraphicsView;
  private JSONObject mUserData;
  private int mHighScore = 0;
  private int mPoints = 0;
  private int mPWSuper = 0;
  private int mPWMulti = 0;
  private int mPWUlti = 0;

  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate (savedInstanceState);

    WindowManager window = getWindowManager ();
    mDisplay = window.getDefaultDisplay ();

    mGraphicsView = new BoomshineView (this, mDisplay);
    mGraphicsView.setBackgroundColor (Color.BLACK);
    setContentView (R.layout.activity_boomshine_game);

    //Init service
    mcCompositeDisposable = new CompositeDisposable();
    Retrofit retrofitClient = RetrofitClient.getInstance();
    mService = retrofitClient.create(HttpService.class);

    //Get user info
    Intent cIntent = getIntent();

    String username = cIntent.getStringExtra("Username");

    if (username != null) {
      getUserData(username);
    }
  }

  @Override
  protected void onStop ()
  {
    super.onStop();
  }

  public void onPlayClicked (View cView)
  {
    setContentView(mGraphicsView);
    Log.d("BoomshineGame", String.valueOf(mPWMulti));
  }

  private void getUserData(String name) {
    mcCompositeDisposable.add(mService.getUserData(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
              @Override
              public void accept(String response) throws Exception {
                response = response.replace("\"", ""); //Returned response has ""x"" format
                mUserData = new JSONObject(response);
                try {
                  mHighScore = (mUserData.getInt("HighScore"));
                  mPoints = (mUserData.getInt("Points"));
                  mPWSuper = (mUserData.getInt("PWSuper"));
                  mPWMulti = (mUserData.getInt("PWMulti"));
                  mPWUlti = (mUserData.getInt("PWUltimate"));
                } catch (JSONException e) {
                  e.printStackTrace();
                }
              }
            }));
  }
}
