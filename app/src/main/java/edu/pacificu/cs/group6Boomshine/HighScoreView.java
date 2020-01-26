package edu.pacificu.cs.group6Boomshine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.httprequests.HttpService;
import edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.httprequests.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class HighScoreView extends ImageView
{
  private Paint mPaint;
  private final BoomshineGame mcGame;
  private int mHeight;
  private int mWidth;
  private JSONArray mHighScores;
  private CompositeDisposable mcCompositeDisposable;
  private HttpService mService;

  public HighScoreView(Context context)
  {
    super(context);
    setFocusable(true); // make sure we get key events
    mPaint = new Paint();
    this.mcGame = (BoomshineGame) context;
    //Init service
    mcCompositeDisposable = new CompositeDisposable();
    Retrofit retrofitClient = RetrofitClient.getInstance();
    mService = retrofitClient.create(HttpService.class);

    mPaint = new Paint();
    mPaint.setAntiAlias(true);

    getHighScores();
  }

  /**
   * Draw method that is repeatedly called for animation
   *
   * @param canvas used to host the draw calls
   * @since 1.0
   */

  @Override
  public void onDraw(Canvas canvas)
  {
    final double SCALE_FACTOR = 4;
    mWidth = getWidth();
    mHeight = getHeight();
    String HighScore;
    String Name;
    for (int i = 1; i <= 5; i++) {
      try {

        HighScore = mHighScores.getJSONObject(i - 1).getString("HighScore");
        Name = mHighScores.getJSONObject(i - 1).getString("Name");
        mPaint.setTextSize(mHeight * 0.05f);
        mPaint.setColor(getResources().getColor(R.color.cBlack));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);

        Log.d("ONDRAW", String.valueOf(mWidth));
        canvas.drawText(HighScore,
                (int) (mWidth / SCALE_FACTOR), 100 * i, mPaint);
        canvas.drawText(Name, (int) (mWidth * 3 / SCALE_FACTOR), 100 * i, mPaint);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    if (event.getAction() != MotionEvent.ACTION_DOWN) {
      return super.onTouchEvent(event);
    }


    return true;
  }

  private void getHighScores()
  {
    mcCompositeDisposable.add(mService.getHighScores()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
              @Override
              public void accept(String response) throws Exception {
                response = response.replace("\"", ""); //Returned response has ""x"" format
                mHighScores = new JSONArray(response);
                Log.d("HIGHSCORE", response);
              }
            }));
  }
}

