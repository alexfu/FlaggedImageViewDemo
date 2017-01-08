package com.alexfu.flaggedimageviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * An ImageView that renders a small flag on the upper right hand corner.
 */
public class FlaggedImageView extends ImageView {

  // We are using a Path to draw the shape of the flag
  private final Path flagPath = new Path();
  private final Paint flagPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private int flagWidth = 0, flagHeight = 0;

  public FlaggedImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    flagPaint.setColor(Color.RED);
    flagPaint.setStyle(Paint.Style.FILL);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    float aspectRatio = 4.25f;
    flagWidth = (int) (getMeasuredWidth() * 0.30);
    flagHeight = (int) (flagWidth / aspectRatio);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);

    // We layout the path moving from right to left and top to bottom
    // We start with the origin which will be the top-right corner of the flag
    int currentX = getWidth();
    int currentY = (int) dpToPx(20);
    flagPath.moveTo(currentX, currentY);

    // Furthest top-left corner
    currentX -= flagWidth;
    flagPath.lineTo(currentX, currentY);

    // Layout the top half of the triangular indentation
    currentX += (flagWidth * .10);
    currentY += flagHeight / 2;
    flagPath.lineTo(currentX, currentY);

    // ...and now the bottom half of the triangular indentation
    currentX  -= (flagWidth * .10);
    currentY += flagHeight / 2;
    flagPath.lineTo(currentX, currentY);

    // Line back to the origin
    currentX = getRight();
    flagPath.lineTo(currentX, currentY);

    currentY = getTop();
    flagPath.lineTo(currentX, currentY);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawPath(flagPath, flagPaint);
  }

  private float dpToPx(float dp) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
  }
}
