package edu.pacificu.cs.group6Boomshine;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class BoomshineGame extends AppCompatActivity {

  private Display mDisplay;
  private BoomshineView mGraphicsView;

  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate (savedInstanceState);

    WindowManager window = getWindowManager ();
    mDisplay = window.getDefaultDisplay ();

    mGraphicsView = new BoomshineView (this, mDisplay);
    mGraphicsView.setBackgroundColor (Color.BLACK);
    setContentView (R.layout.activity_boomshine_game);
  }

  public void onPlayClicked (View cView)
  {
    setContentView(mGraphicsView);
  }
}
