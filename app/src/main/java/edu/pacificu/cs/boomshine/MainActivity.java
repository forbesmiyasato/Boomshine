package edu.pacificu.cs.boomshine;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity
{
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
    setContentView (mGraphicsView);
  }
}