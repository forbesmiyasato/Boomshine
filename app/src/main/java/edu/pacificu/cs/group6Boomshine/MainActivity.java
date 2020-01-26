package edu.pacificu.cs.group6Boomshine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.userauth.HttpService;
import edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.userauth.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
public class MainActivity extends AppCompatActivity {
  private Display mDisplay;
  private BoomshineView mGraphicsView;

  EditText mLoginUsername;
  EditText mLoginPassword;
  String mName;
  TextView mCreateAccount;

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
    mLoginPassword = findViewById(R.id.login_password);
    mLoginUsername = findViewById(R.id.login_username);
    mCreateAccount = findViewById(R.id.create_account);

    mCreateAccount.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        createAccountClicked();
      }
    });
  }

  @Override
  protected void onStop() {
    mcCompositeDisposable.clear();
    super.onStop();
  }

  /**
   * Overrides the superball.onCreateOptionsMenu method.Inflates the
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

  public void createAccountClicked()
  {
    final View registerLayout = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_register, null);

    new MaterialStyledDialog.Builder(MainActivity.this)
            .setIcon(R.drawable.ic_account)
            .setTitle("REGISTRATION")
            .setDescription("Please fill all fields")
            .setCustomView(registerLayout)
            .setNegativeText("CANCEL")
            .onNegative(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
              }
            })
            .setPositiveText("REGISTER")
            .onPositive(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                MaterialEditText editRegisterName = registerLayout.findViewById(R.id.register_username);
                MaterialEditText editRegisterPassword = registerLayout.findViewById(R.id.register_password);

                if (TextUtils.isEmpty(editRegisterName.getText().toString())) {
                  Toast.makeText(MainActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                  return;
                }
                if (TextUtils.isEmpty(editRegisterPassword.getText().toString())) {
                  Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                  return;
                }

                registerUser(editRegisterName.getText().toString(), editRegisterPassword.getText().toString());
              }
            }).show();
  }

  private void registerUser(String name, String password) {
    mName = name;
    mcCompositeDisposable.add(mService.registerUser(name, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
              @Override
              public void accept(String response) throws Exception {
                Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                response = response.replace("\"", ""); //Returned response has ""x"" format
                if (response.equals("User Added"))
                {
                  Intent cIntent = new Intent(MainActivity.this,
                          BoomshineGame.class);
                  cIntent.putExtra("Username", mName);
                  startActivity(cIntent);
                }
              }
            }));
  }

  public void playAsGuestClicked(View cView) {
    startActivity(new Intent(this,
            BoomshineGame.class));
  }

  public void loginClicked (View cView) {
    loginUser(mLoginUsername.getText().toString(), mLoginPassword.getText().toString());
  }

  private void loginUser(String name, String password) {
    if (TextUtils.isEmpty(name)) {
      Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
      return;
    }
    if (TextUtils.isEmpty(password)) {
      Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
      return;
    }
    mName = name;
    mcCompositeDisposable.add(mService.loginUser(name, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
              @Override
              public void accept(String response) throws Exception {
                Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                response = response.replace("\"", ""); //Returned response has ""x"" format
                if (response.equals("Login Success"))
                {
                  Intent cIntent = new Intent(MainActivity.this,
                        BoomshineGame.class);
                  cIntent.putExtra("Username", mName);
                  startActivity(cIntent);
                }
              }
            }));
  }
}