package edu.pacificu.cs.group6Boomshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    setContentView (R.layout.activity_main);
  }

  /**
   * Overrides the super.onCreateOptionsMenu method.Inflates the
   * options menu contained in res/menu/menu.
   *
   * @param menu The menu to inflate into. The items and
   *             submenus in the XML will be added to this menu.
   */

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected (MenuItem item)
  {
    if(item.getItemId() == R.id.menuAbout)
    {
        onAbout();
    }
    return true;
  }

  /**
   * Starts a new About Activity when the user presses the about button.
   *
   */
  public void onAbout ()
  {
    startActivity (new Intent(this,
            About.class));
  }

  public void playAsGuestClicked (View cView) {
    startActivity (new Intent(this,
            BoomshineGame.class));
  }
}