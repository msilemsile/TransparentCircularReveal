package com.msile.android.anims.transparentcircularreveal;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * 透明圆形动画
 */
public class TransparentCircularRevealView extends View {

    private float mCircleCenterX = 66;
    private float mCircleCenterY = 66;
    private float mStartCircleRadius = 66;
    private float mCurrentCircleRadius = mStartCircleRadius;
    private Paint mTransPaint;
    private boolean isAnimEnd = true;

    public TransparentCircularRevealView(Context context) {
        this(context, null);
    }

    public TransparentCircularRevealView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTransPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTransPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(mCircleCenterX, mCircleCenterY, mCurrentCircleRadius, mTransPaint);
    }

    public void startCircularRevealAnim() {
        if (!isAnimEnd) {
            return;
        }
        isAnimEnd = false;
        final float maxRadius = (float) Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight());
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mStartCircleRadius, maxRadius).setDuration(800);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentCircleRadius = (float) animation.getAnimatedValue();
                postInvalidate();
                if (mCurrentCircleRadius >= maxRadius) {
                    mCurrentCircleRadius = mStartCircleRadius;
                    isAnimEnd = true;
                }
            }
        });
        valueAnimator.start();
    }
}
