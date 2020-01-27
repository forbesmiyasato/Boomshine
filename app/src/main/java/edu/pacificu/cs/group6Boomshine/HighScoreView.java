package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;

import edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.httprequests.HttpService;
import edu.pacificu.cs.group6Boomshine.edu.pacificu.cs.httprequests.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Defines the HighScoreView class that contains display and
 * interaction logic for the High Score view.
 *
 * @author Forbes Miyasato
 * @version 1.0
 * @since 1.26.2019
 */

public class HighScoreView extends ImageView
{
  private Paint mcPaint;
  private final BoomshineGame mcGameReference;
  private int mHeight;
  private int mWidth;
  private JSONArray mcHighScores;
  private CompositeDisposable mcCompositeDisposable;
  private HttpService mcService;
  //View placement variables
  private final double X_SCALE_FACTOR = 4; //To make text x position responsive
  private final double Y_SCALE_FACTOR = 4; //To make text y position responsive
  private final int BACK_BUTTON_WIDTH = 400;
  private final int BACK_BUTTON_HEIGHT = 200;
  private final int Y_INCREMENT = 100;
  public HighScoreView(Context context)
  {
    super(context);
    setFocusable(true); // make sure we get key events
    mcPaint = new Paint();
    this.mcGameReference = (BoomshineGame) context;
    //Init service
    mcCompositeDisposable = new CompositeDisposable();
    Retrofit retrofitClient = RetrofitClient.getInstance();
    mcService = retrofitClient.create(HttpService.class);

    mcPaint = new Paint();
    mcPaint.setAntiAlias(true);

    getHighScores();
  }

  /**
   * Draw method that is called to display the high score view
   *
   * @param canvas used to host the draw calls
   * @since 1.0
   */

  @Override
  public void onDraw(Canvas canvas)
  {
    mWidth = getWidth();
    mHeight = getHeight();
    String HighScore;
    String Name;
    mcPaint.setTextSize(mHeight * 0.05f);
    mcPaint.setColor(getResources().getColor(R.color.cBlack));
    mcPaint.setStyle(Paint.Style.FILL);
    mcPaint.setTextAlign(Paint.Align.CENTER);

    canvas.drawText("High Scores",
            mWidth / 2, (int)(mHeight / (Y_SCALE_FACTOR * 2)), mcPaint);

    for (int i = 1; i <= 5; i++) {
      try {

        HighScore = mcHighScores.getJSONObject(i - 1).getString("HighScore");
        Name = mcHighScores.getJSONObject(i - 1).getString("Name");

        canvas.drawText(HighScore,
                (int) (mWidth / X_SCALE_FACTOR), Y_INCREMENT * i
                        + (int) (mHeight / Y_SCALE_FACTOR), mcPaint);
        canvas.drawText(Name, (int) (mWidth * 3 / X_SCALE_FACTOR),
                Y_INCREMENT * i + (int) (mHeight / Y_SCALE_FACTOR), mcPaint);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    Drawable back = getResources().getDrawable(R.drawable.backtomenu, null);
    back.setBounds((mWidth - BACK_BUTTON_WIDTH) / 2, Y_INCREMENT * 5 + (int) (mHeight / Y_SCALE_FACTOR),
            (mWidth - BACK_BUTTON_WIDTH) / 2 + BACK_BUTTON_WIDTH,
            Y_INCREMENT * 5 + (int) (mHeight / Y_SCALE_FACTOR) + BACK_BUTTON_HEIGHT);
    back.draw(canvas);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    int x = (int) event.getX();
    int y = (int) event.getY();
    if (event.getAction() != MotionEvent.ACTION_DOWN) {
      return super.onTouchEvent(event);
    }

    if ((x >= (mWidth - BACK_BUTTON_WIDTH) / 2
            && x <= (mWidth - BACK_BUTTON_WIDTH) / 2 + BACK_BUTTON_WIDTH)
    && (y >= Y_INCREMENT * 5 + (int) (mHeight / Y_SCALE_FACTOR)
            && y <= Y_INCREMENT * 5 + (int) (mHeight / Y_SCALE_FACTOR) + BACK_BUTTON_HEIGHT))
    {
      mcGameReference.onBackClicked();
    }

    return true;
  }

  private void getHighScores()
  {
    mcCompositeDisposable.add(mcService.getHighScores()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
              @Override
              public void accept(String response) throws Exception {
                response = response.replace("\"", ""); //Returned response has ""x"" format
                mcHighScores = new JSONArray(response);
              }
            }));
  }
}

