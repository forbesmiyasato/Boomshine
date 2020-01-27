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
  private final double X_SCALE_FACTOR = 4;//To make text x position responsive
  private final double Y_SCALE_FACTOR = 4;//To make text y position responsive
  private final int SCALE_BY_TWO = 2; //To scale the size of components
  private final int SCALE_BY_THREE = 3;
  private final int BACK_BUTTON_WIDTH = 400;
  private final int BACK_BUTTON_HEIGHT = 200;
  private final int Y_INCREMENT = 100;
  private final int HIGH_SCORE_AMOUNT = 5;

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
   */
  @Override
  public void onDraw(Canvas canvas)
  {
    final float TEXT_SCALE = 0.05f;
    final String sHighScoresText = "High Scores";
    final String sJSONHighScore = "HighScore";
    final String sJSONName = "Name";
    mWidth = getWidth();
    mHeight = getHeight();
    String HighScore;
    String Name;
    mcPaint.setTextSize(mHeight * TEXT_SCALE);
    mcPaint.setColor(getResources().getColor(R.color.cBlack));
    mcPaint.setStyle(Paint.Style.FILL);
    mcPaint.setTextAlign(Paint.Align.CENTER);

    canvas.drawText(sHighScoresText,
            mWidth / SCALE_BY_TWO, (int) (mHeight
                    / (Y_SCALE_FACTOR * SCALE_BY_TWO)), mcPaint);

    for (int i = 1; i <= HIGH_SCORE_AMOUNT; i++)
    {
      try
      {
        HighScore = mcHighScores.getJSONObject(i - 1).getString(sJSONHighScore);
        Name = mcHighScores.getJSONObject(i - 1).getString(sJSONName);

        canvas.drawText(HighScore,
                (int) (mWidth / X_SCALE_FACTOR), Y_INCREMENT * i
                        + (int) (mHeight / Y_SCALE_FACTOR), mcPaint);
        canvas.drawText(Name, (int) (mWidth * SCALE_BY_THREE / X_SCALE_FACTOR),
                Y_INCREMENT * i + (int) (mHeight / Y_SCALE_FACTOR), mcPaint);
      } catch (JSONException e)
      {
        e.printStackTrace();
      }
    }
    Drawable back = getResources().getDrawable(R.drawable.backtomenu, null);
    back.setBounds((mWidth - BACK_BUTTON_WIDTH) / SCALE_BY_TWO,
            Y_INCREMENT * HIGH_SCORE_AMOUNT + (int) (mHeight / Y_SCALE_FACTOR),
            (mWidth - BACK_BUTTON_WIDTH) / SCALE_BY_TWO + BACK_BUTTON_WIDTH,
            Y_INCREMENT * HIGH_SCORE_AMOUNT + (int) (mHeight / Y_SCALE_FACTOR)
                    + BACK_BUTTON_HEIGHT);
    back.draw(canvas);
  }

  /**
   * Handles the behavior when the user touches the screen.
   * (goes back to menu when back button clicked)
   *
   * @param event event input details from the touch screen event
   */
  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    int x = (int) event.getX();
    int y = (int) event.getY();
    if (event.getAction() != MotionEvent.ACTION_DOWN)
    {
      return super.onTouchEvent(event);
    }

    //If x and y are within the back button boundaries
    if ((x >= (mWidth - BACK_BUTTON_WIDTH) / SCALE_BY_TWO
            && x <= (mWidth - BACK_BUTTON_WIDTH) / SCALE_BY_TWO
            + BACK_BUTTON_WIDTH)
            && (y >= Y_INCREMENT * HIGH_SCORE_AMOUNT +
            (int) (mHeight / Y_SCALE_FACTOR)
            && y <= Y_INCREMENT * HIGH_SCORE_AMOUNT +
            (int) (mHeight / Y_SCALE_FACTOR) + BACK_BUTTON_HEIGHT))
    {
      mcGameReference.onBackClicked();
    }

    return true;
  }

  /**
   * Gets the game high scores via http request to database
   */
  private void getHighScores()
  {
    mcCompositeDisposable.add(mcService.getHighScores()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>()
            {
              @Override
              public void accept(String response) throws Exception
              {
                //Returned response has ""x"" format
                response = response.replace("\"", "");
                mcHighScores = new JSONArray(response);
              }
            }));
  }
}

