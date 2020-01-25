package edu.pacificu.cs.group6Boomshine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.userauth.RetrofitClient;
import edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.userauth.HttpService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
public class MainActivity extends AppCompatActivity {
  private Display mDisplay;
  private BoomshineView mGraphicsView;

  Button mLoginButton;
  EditText mUsername;
  EditText mPassword;

  CompositeDisposable mcCompositeDisposable;

  private HttpService mService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    WindowManager window = getWindowManager();
    mDisplay = window.getDefaultDisplay();

    mGraphicsView = new BoomshineView(this, mDisplay);
    mGraphicsView.setBackgroundColor(Color.BLACK);
    setContentView(R.layout.activity_main);

    //Init service
    mcCompositeDisposable = new CompositeDisposable();
    Retrofit retrofitClient = RetrofitClient.getInstance();
    mService = retrofitClient.create(HttpService.class);

    //Init view
    mLoginButton = findViewById(R.id.register);
    mPassword = findViewById(R.id.password);
    mUsername = findViewById(R.id.username);
  }

  @Override
  protected void onStop() {
    mcCompositeDisposable.clear();
    super.onStop();
  }

  /**
   * Overrides the super.onCreateOptionsMenu method.Inflates the
   * options menu contained in res/menu/menu.
   *
   * @param menu The menu to inflate into. The items and
   *             submenus in the XML will be added to this menu.
   */

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menuAbout) {
      onAbout();
    }
    return true;
  }

  /**
   * Starts a new About Activity when the user presses the about button.
   */
  public void onAbout() {
    startActivity(new Intent(this,
            About.class));
  }

  public void playAsGuestClicked(View cView) {
    startActivity(new Intent(this,
            BoomshineGame.class));
  }

  public void registerClicked(View cView) {
    loginUser(mUsername.getText().toString(), mPassword.getText().toString());
  }

  private void loginUser(String name, String password) {
    if (TextUtils.isEmpty(name)) {
      Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
      return;
    }
    if (TextUtils.isEmpty(password)) {
      Toast.makeText(this, "password cannot be empty", Toast.LENGTH_SHORT).show();
      return;
    }

    mcCompositeDisposable.add(mService.loginUser(name, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
              @Override
              public void accept(String response) throws Exception {
                Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
              }
            }));
  }
}