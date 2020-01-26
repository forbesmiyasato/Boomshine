//package edu.pacificu.cs.group6Boomshine;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class PowerUpScreen extends ImageView {
//  //View objects
//  private Button mMultiBuy;
//  private Button mSuperBuy;
//  private Button mUltiBuy;
//  private TextView mPointsView;
//  private View mPowerUpLayout;
//
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_powerup);
//
//
//    //Init View
//    mPowerUpLayout = LayoutInflater.from(BoomshineGame.this).inflate(R.layout.activity_powerup, null);
//    mPointsView = mPowerUpLayout.findViewById(R.id.points_amount);
//    mMultiBuy = mPowerUpLayout.findViewById(R.id.multi_buy);
//    mSuperBuy = mPowerUpLayout.findViewById(R.id.super_buy);
//    mUltiBuy = mPowerUpLayout.findViewById(R.id.ulti_buy);
//    checkIfCanBuy ();
//  }
//
//  public void onBackClicked (View cView)
//  {
//    setContentView(R.layout.activity_boomshine_game);
//  }
//
//  public void onMultiBuy (View cView)
//  {
//    mPWMulti++;
//    mPoints -= MULTI_PRICE;
//    checkIfCanBuy ();
//  }
//
//  public void onSuperBuy (View cView)
//  {
//    mPWSuper++;
//    mPoints -= SUPER_PRICE;
//    checkIfCanBuy ();
//  }
//
//  public void onUltiBuy (View cView)
//  {
//    mPWUlti++;
//    mPoints -= ULTI_PRICE;
//    checkIfCanBuy ();
//  }
//
////  public void checkIfCanBuy () {
////    if (mPoints < MULTI_PRICE)
////    {
////      mMultiBuy.setEnabled(false);
////    }
////    if (mPoints < SUPER_PRICE)
////    {
////      mSuperBuy.setEnabled(false);
////    }
////    if (mPoints < ULTI_PRICE)
////    {
////      mUltiBuy.setEnabled(false);
////    }
////  }
//}
