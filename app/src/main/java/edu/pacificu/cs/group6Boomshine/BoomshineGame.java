package edu.pacificu.cs.group6Boomshine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

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

  static final int MULTI_PRICE = 10;
  static final int SUPER_PRICE = 20;
  static final int ULTI_PRICE = 50;
  CompositeDisposable mcCompositeDisposable;
  private HttpService mService;
  private Display mDisplay;
  private BoomshineView mGraphicsView;
  private PowerUpView mPowerUpView;

  //User data
  private JSONObject mUserData;
  private int mHighScore = 0;
  private int mPoints = 100;
  private int mPWSuper = 0;
  private int mPWMulti = 0;
  private int mPWUlti = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    WindowManager window = getWindowManager();
    mDisplay = window.getDefaultDisplay();

    View mPowerUpLayout = LayoutInflater.from(BoomshineGame.this).inflate(R.layout.activity_powerup, null);

    mPowerUpView = new PowerUpView(this, mDisplay);
    mGraphicsView = new BoomshineView(this, mDisplay);
    mGraphicsView.setBackgroundColor(Color.BLACK);
    setContentView(R.layout.activity_boomshine_game);

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
  protected void onStop() {
    super.onStop();
  }

  public void onPlayClicked(View cView) {
    setContentView(mGraphicsView);
  }

  public void onBackClicked()
  {
    setContentView(R.layout.activity_boomshine_game);
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

  public void onPowerupsClicked(View cView) {
    setContentView(mPowerUpView);
  }

  public boolean onMultiBuy() {
    mPWMulti++;
    mPoints -= MULTI_PRICE;
    Log.d("BUY", String.valueOf(mPoints));
    return mPoints >= MULTI_PRICE;
  }

  public boolean onSuperBuy() {
    mPWSuper++;
    mPoints -= SUPER_PRICE;

    return mPoints >= SUPER_PRICE;
  }

  public boolean onUltiBuy() {
    mPWUlti++;
    mPoints -= ULTI_PRICE;

    return mPoints >= ULTI_PRICE;
  }

  public int getHighScore() {
    return mHighScore;
  }

  public int getPoints() {
    return mPoints;
  }

  public int getPWMulti() {
    return mPWMulti;
  }

  public int getPWSuper() {
    return mPWSuper;
  }

  public int getPWUlti() {
    return mPWUlti;
  }

  public void setHighScore(int highScore) {
    mHighScore = highScore;
  }

  public void setPoints(int points) {
    mPoints = points;
  }

  public void setPWMulti(int pwMulti) {
    mPWMulti = pwMulti;
  }

  public void setPWSuper(int pwSuper) {
    mPWSuper = pwSuper;
  }

  public void setPWUlti(int pwUlti) {
    mPWUlti = pwUlti;
  }

  public boolean canBuyMulti ()
  {
    return mPoints >= MULTI_PRICE;
  }

  public boolean canBuySuper ()
  {
    return mPoints >= SUPER_PRICE;
  }

  public boolean canBuyUlti ()
  {
    return mPoints >= ULTI_PRICE;
  }
}
